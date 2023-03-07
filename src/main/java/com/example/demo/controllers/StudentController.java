package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    @GetMapping
    List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @PostMapping
    Student addStudent(@RequestBody StudentRequest request) {
        var student = new Student();
        student.setName(request.getName());
        return studentRepository.save(student);
    }

    @PutMapping("/{id}")
    Student updateStudent(@PathVariable Long id, @RequestBody StudentRequest request) {
        return studentRepository.findById(id)
                .map(student -> {
                            student.setName(request.getName());
                            return student;
                        }
                )
                .map(studentRepository::save)
                .orElseThrow(() -> new RuntimeException("Student does not exist"));
    }
}
