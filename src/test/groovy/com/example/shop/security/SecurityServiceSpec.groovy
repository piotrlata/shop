package com.example.shop.security

import com.example.shop.domain.dao.User
import com.example.shop.service.UserService
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class SecurityServiceSpec extends Specification {
    def securityService
    def userService = Mock(UserService)

    def setup() {
        securityService = new SecurityService(userService)
    }

    def 'should check access to user'() {
        given:
        userService.getCurrentUser() >> new User(id: 1)

        when:
        def result = securityService.hasAccessToUser(userId)

        then:
        expected == result

        where:
        userId || expected
        1      || true
        2      || false
    }

    def 'should not have access to user'() {
        given:
        def userId = 1L
        userService.getCurrentUser() >> { throw new EntityNotFoundException() }

        when:
        def result = securityService.hasAccessToUser(userId)

        then:
        !result
    }
}