package com.example.shop.mapper

import com.example.shop.domain.dao.Category
import com.example.shop.domain.dao.Product
import com.example.shop.domain.dao.User
import org.hibernate.envers.DefaultRevisionEntity
import org.springframework.data.envers.repository.support.DefaultRevisionMetadata
import org.springframework.data.history.Revision
import spock.lang.Specification

class RevisionMapperImplSpec extends Specification {
    def revisionMapper = new RevisionMapperImpl()

    def 'should map user revision to user dto'() {
        given:
        def user = new User(id: 1, firstName: "zxc", lastName: "asdw", email: "qwe")
        def userRevision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity(id: 1)), user)

        when:
        def result = revisionMapper.toUserDto(userRevision)

        then:
        result.id == userRevision.entity.id
        result.firstName == userRevision.entity.firstName
        result.lastName == userRevision.entity.lastName
        result.email == userRevision.entity.email
        result.revisionNumber == userRevision.metadata.entity.id
    }

    def 'should map product revision to product dto'() {
        given:
        def product = new Product(1, '', 11.22, '', 1, new Category())
        def productRevision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity(id: 1)), product)

        when:
        def result = revisionMapper.toProductDto(productRevision)

        then:
        result.id == productRevision.entity.id
        result.name == productRevision.entity.name
        result.price == productRevision.entity.price
        result.description == productRevision.entity.description
        result.quantity == productRevision.entity.quantity
        result.revisionNumber == productRevision.metadata.entity.id
    }

    def 'should map category revision to category dto'() {
        given:
        def category = new Category(1, '', '')
        def categoryRevision = Revision.of(new DefaultRevisionMetadata(new DefaultRevisionEntity(id: 1)), category)

        when:
        def result = revisionMapper.toCategoryDto(categoryRevision)

        then:
        result.id == categoryRevision.entity.id
        result.name == categoryRevision.entity.name
        result.description == categoryRevision.entity.description
        result.revisionNumber == categoryRevision.metadata.entity.id
    }
}
