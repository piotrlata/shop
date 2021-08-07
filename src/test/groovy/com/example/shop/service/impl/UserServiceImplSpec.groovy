package com.example.shop.service.impl

import com.example.shop.domain.dao.Role
import com.example.shop.domain.dao.User
import com.example.shop.repository.RoleRepository
import com.example.shop.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
        def pageable = new PageRequest(1, 1, Sort.unsorted())
        when:
        userRepository.findAll(pageable)

        then:
        1 * userRepository.findAll(pageable)
        0 * _
    }
}