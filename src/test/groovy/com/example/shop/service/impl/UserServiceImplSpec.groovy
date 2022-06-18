package com.example.shop.service.impl

import com.example.shop.domain.dao.Role
import com.example.shop.domain.dao.User
import com.example.shop.repository.RoleRepository
import com.example.shop.repository.UserRepository
import com.example.shop.security.SecurityUtils
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class UserServiceImplSpec extends Specification {
    def userService
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def passwordEncoder = Mock(PasswordEncoder)

    def setup() {
        userService = new UserServiceImpl(userRepository, roleRepository, passwordEncoder)
    }

    def 'should save user'() {
        given:
        def user = new User()

        when:
        userService.save(user)

        then:
        1 * roleRepository.findByName('ROLE_USER') >> Optional.of(new Role())
        1 * passwordEncoder.encode(user.getPassword())
        1 * userRepository.save(user)
        0 * _
    }

    def 'should find user by id'() {
        given:
        userRepository.findById(1) >> Optional.of(new User())

        when:
        def result = userService.findUserById(1)

        then:
        result != null
    }

    def 'should not find user by id'() {
        given:
        userRepository.findById(1) >> Optional.empty()

        when:
        userService.findUserById(1)

        then:
        thrown EntityNotFoundException
    }

    def 'should delete user by id'() {
        when:
        userService.delete(1)

        then:
        1 * userRepository.deleteById(1)
        0 * _
    }

    def 'should update user'() {
        given:
        userRepository.findById(1) >> Optional.of(new User())
        def user = new User(firstName: 'qweasd', lastName: 'asdqwe', email: 'qweasd@gmail.com')

        when:
        def result = userService.update(user, 1)

        then:
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
    }

    def 'should page users'() {
        given:
        def pageable = PageRequest.of(1, 1)

        when:
        userService.getPage(pageable)

        then:
        1 * userRepository.findAll(pageable)
        0 * _
    }

    def 'should get current user'() {
        given:
        def context = Mock(SecurityContext)
        SecurityContextHolder.setContext(context)
        def authentication = Mock(Authentication)

        when:
        userService.getCurrentUser()

        then:
        1 * context.getAuthentication() >> authentication
        1 * authentication.getName() >> "string"
        1 * userRepository.findByEmail("string") >> Optional.of(new User(id: 1))
        0 * _
    }

    def 'should not get current user'() {
        given:
        def context = Mock(SecurityContext)
        SecurityContextHolder.setContext(context)
        def authentication = Mock(Authentication)
        context.getAuthentication() >> authentication
        userRepository.findByEmail(null) >> Optional.empty()

        when:
        userService.getCurrentUser()

        then:
        thrown EntityNotFoundException
    }
}