package com.example.shop.validator.impl

import com.example.shop.domain.dto.UserDto
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {
    def passwordValidator = new PasswordValidator()

    def 'should test password validator'() {
        given:
        def userDto = new UserDto(password: password, confirmedPassword: confirmedPassword)

        when:
        def result = passwordValidator.isValid(userDto, null)

        then:
        result == expected

        where:
        password | confirmedPassword || expected
        'string' | 'string'          || true
        'string' | 'dasd'            || false
    }
}
