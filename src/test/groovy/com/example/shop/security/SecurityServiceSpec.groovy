package com.example.shop.security

import com.example.shop.domain.dao.Role
import com.example.shop.domain.dao.User
import com.example.shop.repository.UserRepository
import com.example.shop.service.impl.UserServiceImpl
import spock.lang.Specification

import java.time.LocalDateTime

class SecurityServiceSpec extends Specification {
    def securityService
    def userService = Mock(UserServiceImpl)

//    def 'should check access to user'() {
//        given:
//        userService.getCurrentUser() >> new User(1,'','','','', LocalDateTime.now(),LocalDateTime.now(),'','',new List<Role>() {
//            @Override
//            int size() {
//                return 1
//            }
//
//            @Override
//            boolean isEmpty() {
//                return false
//            }
//
//            @Override
//            boolean contains(Object o) {
//                return false
//            }
//
//            @Override
//            Iterator<Role> iterator() {
//                return null
//            }
//
//            @Override
//            Object[] toArray() {
//                return new Object[0]
//            }
//
//            @Override
//            def <T> T[] toArray(T[] a) {
//                return null
//            }
//
//            @Override
//            boolean add(Role role) {
//                return false
//            }
//
//            @Override
//            boolean remove(Object o) {
//                return false
//            }
//
//            @Override
//            boolean containsAll(Collection<?> c) {
//                return false
//            }
//
//            @Override
//            boolean addAll(Collection<? extends Role> c) {
//                return false
//            }
//
//            @Override
//            boolean addAll(int index, Collection<? extends Role> c) {
//                return false
//            }
//
//            @Override
//            boolean removeAll(Collection<?> c) {
//                return false
//            }
//
//            @Override
//            boolean retainAll(Collection<?> c) {
//                return false
//            }
//
//            @Override
//            void clear() {
//
//            }
//
//            @Override
//            Role get(int index) {
//                return null
//            }
//
//            @Override
//            Role set(int index, Role element) {
//                return null
//            }
//
//            @Override
//            void add(int index, Role element) {
//
//            }
//
//            @Override
//            Role remove(int index) {
//                return null
//            }
//
//            @Override
//            int indexOf(Object o) {
//                return 0
//            }
//
//            @Override
//            int lastIndexOf(Object o) {
//                return 0
//            }
//
//            @Override
//            ListIterator<Role> listIterator() {
//                return null
//            }
//
//            @Override
//            ListIterator<Role> listIterator(int index) {
//                return null
//            }
//
//            @Override
//            List<Role> subList(int fromIndex, int toIndex) {
//                return null
//            }
//        })
//
//        when:
//
//
//        then:
//        userService.getCurrentUser().id == userId
//    }
}
