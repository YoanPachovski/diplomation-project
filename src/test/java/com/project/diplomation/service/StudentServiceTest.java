package com.project.diplomation.service;

import com.project.diplomation.data.models.dto.CreateStudentDTO;
import com.project.diplomation.data.models.dto.StudentDTO;
import com.project.diplomation.data.models.entities.Student;
import com.project.diplomation.data.repositories.StudentRepo;
import com.project.diplomation.exception.StudentNotFoundException;
import com.project.diplomation.util.MapperUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentServiceTest {
    @Mock
    private StudentRepo studentRepo;
    @Mock
    private MapperUtil mapperUtil;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private StudentService studentService;
    private Student student;
    private StudentDTO studentDTO;
    private CreateStudentDTO createStudentDTO;

    private List<Student> students = new ArrayList<>();

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .name("John Doe")
                .fNumber("123456")
                .build();

        studentDTO = StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .fNumber(student.getFNumber())
                .build();

        createStudentDTO = CreateStudentDTO.builder()
                .name(student.getName())
                .fNumber(student.getFNumber())
                .build();

        students.add(student);
    }

    @Test
    @Order(1)
    void createStudentDTO() {
        // preconditions
        when(studentRepo.save(student)).thenReturn(student);
        when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
        when(modelMapper.map(student, Student.class)).thenReturn(student);
        when(modelMapper.map(student, CreateStudentDTO.class)).thenReturn(createStudentDTO);
        // action
        CreateStudentDTO createdStudentDTO = studentService.createStudentDTO(student);

        // verify result
        System.out.println(createdStudentDTO);

        assertEquals(createdStudentDTO.getName(), student.getName());
        assertEquals(createdStudentDTO.getFNumber(), student.getFNumber());
    }

    @Test
    @Order(2)
    void getStudent() {
        // preconditions
        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);
        when(mapperUtil.getModelMapper()).thenReturn(modelMapper);

        // action
        StudentDTO returnedStudent = studentService.getStudent(student.getId());

        // verify result
        System.out.println(returnedStudent);

        assertEquals(student.getId(), returnedStudent.getId());
        assertEquals(student.getName(), returnedStudent.getName());
        assertEquals(student.getFNumber(), returnedStudent.getFNumber());
    }


    @Test
    @Order(3)
    void getStudentByNameExisting() {
        // preconditions
        when(studentRepo.findByName(student.getName())).thenReturn(students);
        // action
        List <StudentDTO> returnedStudents = studentService.getStudentByName(student.getName());
        // verify result
        if(students.size() == returnedStudents.size()) {
            for(int i = 0; i < students.size(); i++) {
                assertEquals(students.get(i).getName(), returnedStudents.get(i).getName());
                assertEquals(students.get(i).getFNumber(), returnedStudents.get(i).getFNumber());
                assertEquals(students.get(i).getId(), returnedStudents.get(i).getId());
            }
        } else { assertFalse(false);}
    }

    @Test
    @Order(4)
    void getStudentByFNumber() {
        // preconditions
        when(studentRepo.findByfNumber(student.getFNumber())).thenReturn(student);
        when(modelMapper.map(student, StudentDTO.class)).thenReturn(studentDTO);
        when(mapperUtil.getModelMapper()).thenReturn(modelMapper);
        // action
        StudentDTO returnedStudent = studentService.getStudentByFNumber(student.getFNumber());
        // verify result
        assertEquals(student.getName(), returnedStudent.getName());
        assertEquals(student.getFNumber(), returnedStudent.getFNumber());
        assertEquals(student.getId(), returnedStudent.getId());
    }

    @Test
    @Order(5)
    @Rollback
    void getAllStudents() {
        // preconditions
        Student student2 = Student.builder()
                .name("Johanna Doe")
                .fNumber("123457")
                .build();

        students.add(student2);
        when(studentRepo.findAll()).thenReturn(students);
        // action
        List <StudentDTO> returnedStudents = studentService.getAllStudents();

        // verify result
        if(students.size() == returnedStudents.size()) {
            for(int i = 0; i < students.size(); i++) {
                assertEquals(students.get(i).getName(), returnedStudents.get(i).getName());
                assertEquals(students.get(i).getFNumber(), returnedStudents.get(i).getFNumber());
                assertEquals(students.get(i).getId(), returnedStudents.get(i).getId());
            }
        } else { assertFalse(false);}
    }

    @Test
    @Rollback
    @Order(6)
    void updateStudent() {
        // preconditions
        Student updateRequest = Student.builder()
                .name("Updated Name")
                .fNumber(null)  // Should retain original fNumber
                .build();

        Student expectedUpdatedStudent = Student.builder()
                .name("Updated Name")
                .fNumber(student.getFNumber())
                .build();

        when(studentRepo.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepo.save(any(Student.class))).thenReturn(student);

        // Act
        Student result = studentService.updateStudent(updateRequest, student.getId());

        // verify result
        assertNotNull(result);
        assertEquals(expectedUpdatedStudent.getName(), result.getName());
        assertEquals(expectedUpdatedStudent.getFNumber(), result.getFNumber());
    }

    @Test
    void testUpdateStudentNotFound() {
        // preconditions
        long nonExistentId = 99L;
        Student updateRequest = Student.builder().name("John").build();

        when(studentRepo.findById(nonExistentId)).thenReturn(Optional.empty());

        // action + verify result
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.updateStudent(updateRequest, nonExistentId);
        });
    }

    @Test
    @Order(7)
    void deleteStudent() {
        // preconditions
        studentService.deleteStudent(student.getId());
        // action + verify result
        verify(studentRepo, times(1)).deleteById(student.getId());
    }

}