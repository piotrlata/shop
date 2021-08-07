package com.example.shop.service.impl


import com.example.shop.domain.dao.Role
import com.example.shop.domain.dao.User
import com.example.shop.repository.BasketRepository
import com.example.shop.service.ProductService
import com.example.shop.service.UserService
import spock.lang.Specification

import java.time.LocalDateTime

class BasketServiceImplSpec extends Specification {
    def basketService
    def basketRepository = Mock(BasketRepository)
    def userService = Mock(UserService)
    def productService = Mock(ProductService)

    def setup() {
        basketService = new BasketServiceImpl(basketRepository, userService, productService)
    }

    def 'should delete product from basket'() {
        given:
        def productId = new Long(1)

        when:
        basketRepository.deleteById(productId)

        then:
        1 * basketRepository.deleteById(productId)
        0 * _
    }

    def 'should clear basket'() {
        when:
        userService.getCurrentUser() >> new User(1, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', new List<Role>() {
            @Override
            int size() {
                return 1
            }

            @Override
            boolean isEmpty() {
                return false
            }

            @Override
            boolean contains(Object o) {
                return false
            }

            @Override
            Iterator<Role> iterator() {
                return null
            }

            @Override
            Object[] toArray() {
                return new Object[0]
            }

            @Override
            def <T> T[] toArray(T[] a) {
                return null
            }

            @Override
            boolean add(Role role) {
                return false
            }

            @Override
            boolean remove(Object o) {
                return false
            }

            @Override
            boolean containsAll(Collection<?> c) {
                return false
            }

            @Override
            boolean addAll(Collection<? extends Role> c) {
                return false
            }

            @Override
            boolean addAll(int index, Collection<? extends Role> c) {
                return false
            }

            @Override
            boolean removeAll(Collection<?> c) {
                return false
            }

            @Override
            boolean retainAll(Collection<?> c) {
                return false
            }

            @Override
            void clear() {

            }

            @Override
            Role get(int index) {
                return null
            }

            @Override
            Role set(int index, Role element) {
                return null
            }

            @Override
            void add(int index, Role element) {

            }

            @Override
            Role remove(int index) {
                return null
            }

            @Override
            int indexOf(Object o) {
                return 0
            }

            @Override
            int lastIndexOf(Object o) {
                return 0
            }

            @Override
            ListIterator<Role> listIterator() {
                return null
            }

            @Override
            ListIterator<Role> listIterator(int index) {
                return null
            }

            @Override
            List<Role> subList(int fromIndex, int toIndex) {
                return null
            }
        })
        basketRepository.deleteByUserId(1)

        then:
        1 * basketRepository.deleteByUserId(1)
        0 * _
    }

    def 'should get products from basket'() {
        when:
        userService.getCurrentUser() >> new User(1, '', '', '', '', LocalDateTime.now(), LocalDateTime.now(), '', '', new List<Role>() {
            @Override
            int size() {
                return 1
            }

            @Override
            boolean isEmpty() {
                return false
            }

            @Override
            boolean contains(Object o) {
                return false
            }

            @Override
            Iterator<Role> iterator() {
                return null
            }

            @Override
            Object[] toArray() {
                return new Object[0]
            }

            @Override
            def <T> T[] toArray(T[] a) {
                return null
            }

            @Override
            boolean add(Role role) {
                return false
            }

            @Override
            boolean remove(Object o) {
                return false
            }

            @Override
            boolean containsAll(Collection<?> c) {
                return false
            }

            @Override
            boolean addAll(Collection<? extends Role> c) {
                return false
            }

            @Override
            boolean addAll(int index, Collection<? extends Role> c) {
                return false
            }

            @Override
            boolean removeAll(Collection<?> c) {
                return false
            }

            @Override
            boolean retainAll(Collection<?> c) {
                return false
            }

            @Override
            void clear() {

            }

            @Override
            Role get(int index) {
                return null
            }

            @Override
            Role set(int index, Role element) {
                return null
            }

            @Override
            void add(int index, Role element) {

            }

            @Override
            Role remove(int index) {
                return null
            }

            @Override
            int indexOf(Object o) {
                return 0
            }

            @Override
            int lastIndexOf(Object o) {
                return 0
            }

            @Override
            ListIterator<Role> listIterator() {
                return null
            }

            @Override
            ListIterator<Role> listIterator(int index) {
                return null
            }

            @Override
            List<Role> subList(int fromIndex, int toIndex) {
                return null
            }
        })
        basketRepository.findByUserId(1)

        then:
        1 * basketRepository.findByUserId(1)
        0 * _
    }
}