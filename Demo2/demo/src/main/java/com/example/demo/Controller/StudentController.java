package com.example.demo.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private List<Student> students;

    public StudentController() {
        // Load the student data from the JSON file when the controller is initialized
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Student>> typeReference = new TypeReference<List<Student>>() {};
        try {
            Resource resource = new PathMatchingResourcePatternResolver()
                    .getResource("classpath:static/data.json");
            InputStream inputStream = resource.getInputStream();
            students = mapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @GetMapping
    public List<Student> getAllStudents() {
        // Return the entire list of students
        return students;
    }

    @GetMapping("/{email}")
    public Student getStudentByEmail(@PathVariable String email) {
        // Find the student with the specified email ID
        Optional<Student> studentOptional = students.stream()
                .filter(student -> student.getEmail().equalsIgnoreCase(email))
                .findFirst();

        // Return the student if found, or null if not found
        return studentOptional.orElse(null);
    }
}
