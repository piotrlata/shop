package com.example.shop.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class SecurityUtilsSpec extends Specification {
    def 'should return current user email'() {
        given:
        def securityContext = Mock(SecurityContext)
        def authentication = Mock(Authentication)
        SecurityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> 'string'

        when:
        def result = SecurityUtils.getCurrentUserEmail()

        then:
        result == 'string'
    }

    def 'should not return current user email'() {
        given:
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)

        when:
        def result = SecurityUtils.getCurrentUserEmail()

        then:
        result == null
    }
}
