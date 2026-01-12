package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.entities.Student;
import com.project.diplomation.service.StudentService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.CreateStudentViewModel;
import com.project.diplomation.web.view.model.StudentViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentViewController {
    private final MapperUtil mapperUtil;
    private final StudentService studentService;

    @GetMapping("/view/{id}")
    public String getStudentView(Model model, @PathVariable long id) {
        StudentViewModel student = mapperUtil
                .getModelMapper()
                .map(this.studentService.getStudent(id), StudentViewModel.class);
        model.addAttribute("student", student);
        return "students/view";
    }
    @GetMapping
    String getStudentsView(Model model) {
        List<StudentViewModel> students = mapperUtil
                .mapList(this.studentService.getAllStudents(), StudentViewModel.class);
        model.addAttribute("students", students);
        // returns the name of the template page(view) to be rendered
        return "students/students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        this.studentService.deleteStudent(id);
        return "redirect:/students";
    }
    @GetMapping("/edit/{id}")
    public String showEditStudentForm(Model model, @PathVariable Long id) {
        model.addAttribute("student", this.studentService.getStudent(id));
        return "/students/edit";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable long id, Student student) {
        this.studentService.updateStudent(student, id);
        return "redirect:/students";
    }
    @GetMapping("/create")
    public String showCreateStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "/students/create";
    }
    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("student") CreateStudentViewModel student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/students/create";
        }
        this.studentService
            .createStudentDTO(mapperUtil.getModelMapper().map(student, Student.class));
        return "redirect:/students";
    }
}
