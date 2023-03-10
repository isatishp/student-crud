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
        var saved = studentRepository.save(new Student("STUDENT"));
        System.out.println("Student saved to DB: \n" + saved);

        var student = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        System.out.println("Student fetched from DB:\n" + student);

        student.setName("STUDENT UPDATED");
        var updated = studentRepository.save(student);
        System.out.println("Updated Student saved to DB:\n" + updated);

        var updatedStudent = studentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Not Found"));
        System.out.println("Updated Student fetched from DB:\n" + updatedStudent);

        assertThat(updatedStudent).isEqualTo(student);
        assertThat(updatedStudent.getCreatedAt()).isEqualTo(student.getCreatedAt());
        assertThat(updatedStudent.getModifiedAt()).isAfter(student.getModifiedAt());
    }
}
