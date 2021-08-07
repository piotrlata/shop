package com.example.shop.test

import org.springframework.data.jpa.domain.Specification
import spock.lang.Unroll

import java.time.LocalDate

@Unroll
class LogicSpec extends spock.lang.Specification{
    def 'test find second best student #students'() {
        given:
        def studentService = new Logic()

        when:
        def result = studentService.findSecondTheBestStudent(students)

        then:
        result == expected

        where:
        students || expected
        null || null
        Collections.emptyList() || null
        Collections.singletonList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || null
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.5)) || null
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.4)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.6)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.3), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.3), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(1999, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
        Arrays.asList(new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.4), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.7), new Student("name", "surename", LocalDate.of(2000, 2, 20), 4.5), new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)) || new Student("name", "surename", LocalDate.of(2001, 2, 20), 4.5)
    }
}
