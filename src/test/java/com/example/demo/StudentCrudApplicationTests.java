package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StudentCrudApplicationTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void createUpdateStudent() {
        studentRepository.save(new Student("STUDENT"));

        var student = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        System.out.println(student);

        student.setName("STUDENT UPDATED");
        studentRepository.save(student);

        var updatedStudent = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        System.out.println(updatedStudent);

        assertThat(updatedStudent).isEqualTo(student);
        assertThat(updatedStudent.getCreatedAt()).isEqualTo(student.getCreatedAt());
        assertThat(updatedStudent.getModifiedAt()).isAfter(student.getModifiedAt());
    }
}
