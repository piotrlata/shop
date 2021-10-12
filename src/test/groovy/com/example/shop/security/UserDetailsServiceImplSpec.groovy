package com.example.shop.security

import com.example.shop.domain.dao.Role
import com.example.shop.domain.dao.User
import com.example.shop.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import spock.lang.Specification

class UserDetailsServiceImplSpec extends Specification {
    def userDetailsService
    def userRepository = Mock(UserRepository)

    def setup() {
        userDetailsService = new UserDetailsServiceImpl(userRepository)
    }

    def 'should not load user by email'() {
        given:
        userRepository.findByEmail('qwe') >> Optional.empty()

        when:
        userDetailsService.loadUserByUsername('qwe')

        then:
        thrown UsernameNotFoundException
    }

    def 'should load user by email'() {
        given:
        userRepository.findByEmail('qwe') >> Optional.of(new User(id: 1, email: 'qwe', password: 'asd', roles: [new Role(name: 'USER')]))

        when:
        def result = userDetailsService.loadUserByUsername('qwe')

        then:
        result.username == 'qwe'
        result.password == 'asd'
        result.authorities == [new SimpleGrantedAuthority('USER')] as Set
    }
}
