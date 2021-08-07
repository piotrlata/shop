package com.example.shop.security

import com.example.shop.domain.dao.User
import com.example.shop.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import spock.lang.Specification

import javax.persistence.EntityNotFoundException
import java.util.stream.Collectors

class UserDetailsServiceImplSpec extends Specification{
    def userRepository = Mock(UserRepository)

//    def 'should load user by email'() {
//        given:
//        def email = new String()
//
//        when:
//        userRepository.findByEmail(email)
//
//        then:
//        userRepository.findByEmail(email).map({ user ->
//            new User(email, user.password, user.roles.stream()
//                    .map({ role -> new SimpleGrantedAuthority(role.name) })
//                    .collect(Collectors.toSet()))
//        })
//        .orElseThrow({ -> new EntityNotFoundException(email) });
//    }
}
