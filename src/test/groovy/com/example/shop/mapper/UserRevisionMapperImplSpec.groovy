package com.example.shop.mapper

import com.example.shop.domain.dao.User
import com.example.shop.domain.dto.UserDto
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import spock.lang.Specification

class UserRevisionMapperImplSpec extends Specification {
    def userRevisionMapper = new UserRevisionMapperImpl()

    def 'should map dao to dto'() {
        given:
        def user = new User(id: 1, firstName: "zxc", lastName: "asd", email: "qwe")
        def userRevision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity(id: 1)), user)

        when:
        def result = userRevisionMapper.daoToDto(userRevision)

        then:
        result.id == userRevision.entity.id
        result.firstName == userRevision.entity.firstName
        result.lastName == userRevision.entity.lastName
        result.email == userRevision.entity.email
        result.revisionNumber == userRevision.metadata.entity.id
    }
}
