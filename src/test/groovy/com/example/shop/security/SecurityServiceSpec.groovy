package com.example.shop.security

import com.example.shop.domain.dao.User
import com.example.shop.service.impl.UserServiceImpl
import spock.lang.Specification

class SecurityServiceSpec extends Specification {
    def securityService
    def userService = Mock(UserServiceImpl)

//    def 'should check access to user'() {
//        when:
//
//
//        then:
//        userService.getCurrentUser() >> new User(1L, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', [])
//    }
}