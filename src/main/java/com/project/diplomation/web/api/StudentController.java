package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.CreateStudentDTO;
import com.project.diplomation.data.models.entities.Student;
import com.project.diplomation.data.models.dto.StudentDTO;
import com.project.diplomation.exception.StudentNotFoundException;
import com.project.diplomation.service.StudentService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public CreateStudentDTO createStudent(@RequestBody CreateStudentDTO studentDTO) {
        return this.studentService.createStudentDTO(mapperUtil.getModelMapper().map(studentDTO, Student.class));
    }

    @GetMapping("/{id}")
    public StudentDTO getStudent(@PathVariable long id){
        try {
            return this.studentService.getStudent(id);
        } catch (StudentNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student Not Found", exception);
        }
    }
    @GetMapping("/by-name/{name}")
    public List<StudentDTO> getStudentByName(@PathVariable String name) {
        try {
            return this.studentService.getStudentByName(name);
        } catch (StudentNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with such name Not Found", exception);
        }
    }

    @GetMapping("/by-f_number/{fNumber}")
    public StudentDTO getStudentByFNumber(@PathVariable String fNumber) {
        try {
            return this.studentService.getStudentByFNumber(fNumber);
        } catch (StudentNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student with such fNumber Not Found", exception);
        }
    }
    @GetMapping("/all")
    public List<StudentDTO> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    @PutMapping("/update/{id}")
    public void updateStudent(@PathVariable long id, @RequestBody Student student) {
        try {
            this.studentService.updateStudent(student, id);
        } catch (StudentNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student to be updated Not Found", exception);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable long id) {
        try {
            this.studentService.deleteStudent(id);
        } catch (StudentNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student to be deleted Not Found", exception);
        }
    }

    @GetMapping("/graduated-between/{start}/{end}")
    public List<StudentDTO> getStudentsGraduatedBetween(@PathVariable String start, @PathVariable String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return this.studentService.getGraduatedStudentsInPeriod(startDate, endDate);
    }
}
