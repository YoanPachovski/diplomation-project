package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.CreateDefenseDTO;
import com.project.diplomation.data.models.dto.DefenseDTO;
import com.project.diplomation.data.models.dto.ReviewDTO;
import com.project.diplomation.data.models.entities.Defense;
import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.repositories.DefenseRepo;
import com.project.diplomation.data.repositories.ReviewRepo;
import com.project.diplomation.exception.DefenseNotFoundException;
import com.project.diplomation.exception.ReviewNotFoundException;
import com.project.diplomation.service.DefenseService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/defenses")
public class DefenseController {
    private final DefenseService defenseService;
    private final ReviewRepo reviewRepo;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public CreateDefenseDTO createDefense(@RequestBody CreateDefenseDTO defenseDTO) {
        Review review = reviewRepo.getReferenceById(defenseDTO.getReviewId());

        Defense defense = new Defense(
                defenseDTO.getDate(),
                defenseDTO.getGrade(),
                review
        );

        return defenseService.createDefenseDTO(defense);
    }

    @PutMapping("/update/{id}")
    public DefenseDTO updateDefense(@PathVariable long id, @RequestBody DefenseDTO defenseDTO) {
        try {
            return this.defenseService.updateDefense(defenseDTO, id);
        } catch (DefenseNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Defense Not Found", exception);
        }
    }

    @GetMapping("/{id}")
    public DefenseDTO getDefense(@PathVariable long id){
        try {
            return this.defenseService.getDefense(id);
        } catch (DefenseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Defense Not Found", exception);
        }
    }

    @GetMapping("/all")
    public List<DefenseDTO> getAllDefenses() {
        return this.defenseService.getAllDefenses();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDefense(@PathVariable long id) {
        try {
            this.defenseService.deleteDefense(id);
        } catch (DefenseNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Defense Not Found", exception);
        }
    }

    @GetMapping("/grade/{min}/{max}")
    public List<DefenseDTO> getDefenseByGradeBetween(@PathVariable int min, @PathVariable int max) {
        return this.defenseService.getDefenseByGradeBetween(min, max);
    }

    @GetMapping("/by-review-id/{id}")
    public DefenseDTO getDefenseByReviewId(@PathVariable long id) {
        return this.defenseService.getDefenseByReviewId(id);
    }

    @GetMapping("by-date-between/{start}/{end}")
    public List<DefenseDTO> getDefenseByDateBetween(@PathVariable String start, @PathVariable String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return this.defenseService.getDefensesByDateBetween(startDate, endDate);
    }

    @GetMapping("by-date-and-grade-between/{start}/{end}/{min}/{max}")
    public List<DefenseDTO> getDefenseByDateGradeBetween(@PathVariable String start, @PathVariable String end, @PathVariable int min, @PathVariable int max) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return this.defenseService.getDefenseByDateBetweenAndGradeBetween(startDate, endDate, min, max);
    }
    @GetMapping("/grade/{grade}")
    public List<DefenseDTO> getDefensesByGrade(@PathVariable int grade) {
        return this.defenseService.getDefensesByGrade(grade);
    }
}
