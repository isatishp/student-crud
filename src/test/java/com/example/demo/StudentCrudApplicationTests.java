package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentCrudApplicationTests {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TransactionTemplate txt;

    @Test
    void createUpdateStudents() {
        Stream.of("ABC", "DEF", "GHI", "JKL", "MNO", "PQR", "STU", "VWXY")
                .map(Student::new)
                .forEach(studentRepository::save);

        var students = txt.execute(tx -> studentRepository.findAll());
        System.out.println(students);

        assert students != null;
        students.stream()
                .peek(student -> student.setName(student.getName() + ".updated"))
                .forEach(studentRepository::save);

        var updatedStudents = txt.execute(tx -> studentRepository.findAll());
        System.out.println(updatedStudents);

        assert updatedStudents != null;
        IntStream.range(0, students.size())
                .forEach(i -> {
                            var student = students.get(i);
                            var updatedStudent = updatedStudents.get(i);
                            assertThat(updatedStudent).isEqualTo(student);
                            assertThat(updatedStudent.getCreatedAt()).isEqualTo(student.getCreatedAt());
                            assertThat(updatedStudent.getModifiedAt()).isBefore(student.getModifiedAt());
                        }
                );
    }
}
