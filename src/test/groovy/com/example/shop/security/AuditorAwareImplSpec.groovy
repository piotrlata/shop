package com.example.shop.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification

class AuditorAwareImplSpec extends Specification {
    def auditorAware = new AuditorAwareImpl()
    def securityContext = Mock(SecurityContext)
    def authentication = Mock(Authentication)

    def 'should get current auditor'() {
        given:
        SecurityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        authentication.getName() >> 'string'

        when:
        def result = auditorAware.getCurrentAuditor()

        then:
        result.isPresent()
        result.get() == 'string'
    }

    def 'should not get current auditor'() {
        given:
        SecurityContextHolder.setContext(securityContext)

        when:
        def result = auditorAware.getCurrentAuditor()

        then:
        result.isEmpty()
    }
}
