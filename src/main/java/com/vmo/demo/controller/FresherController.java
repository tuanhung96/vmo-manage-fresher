package com.vmo.demo.controller;

import com.vmo.demo.dao.FresherRepositoryRedis;
import com.vmo.demo.entity.Center;
import com.vmo.demo.entity.Fresher;
import com.vmo.demo.exception.FresherNotFoundException;
import com.vmo.demo.model.dto.FresherDTO;
import com.vmo.demo.model.response.JoinDateOfFresherWithLocalDateType;
import com.vmo.demo.model.response.NumberOfFresherEachCenter;
import com.vmo.demo.model.response.NumberOfFresherEachScoreRange;
import com.vmo.demo.service.CenterService;
import com.vmo.demo.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
public class FresherController {
    private FresherService fresherService;
    private CenterService centerService;
    private FresherRepositoryRedis fresherRepositoryRedis;

    @Autowired
    public FresherController(FresherService fresherService, CenterService centerService, FresherRepositoryRedis fresherRepositoryRedis) {
        this.fresherService = fresherService;
        this.centerService = centerService;
        this.fresherRepositoryRedis = fresherRepositoryRedis;
    }

    @GetMapping("/freshers")
    public ResponseEntity<List<Fresher>> findAllFresher(@RequestParam @Min(1) Integer pageNumber,
                                            @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findAll(pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/{fresherId}")
    public ResponseEntity<Fresher> findFresherById(@PathVariable @Min(1) Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        fresherRepositoryRedis.saveFresher(fresher);
        return ResponseEntity.ok(fresher);
    }

    @GetMapping("/hello")
    public List<Fresher> hello() {
        return fresherRepositoryRedis.findAll();
    }

    @GetMapping("/freshers/count")
    public ResponseEntity<Long> countFresher() {
        Long numberOfFreshers = fresherService.countFresher();
        return ResponseEntity.ok(numberOfFreshers);
    }

    @PostMapping("/freshers")
    public ResponseEntity<Fresher> addFresher(@RequestBody FresherDTO fresherDTO) {
        Fresher fresher = fresherDTO.convertToFresher();
        Instant time = Instant.now();
        fresher.setCreateDate(time);
        fresher.setUpdateDate(time);
        return ResponseEntity.ok(fresherService.save(fresher));
    }

    @DeleteMapping("/freshers/{fresherId}")
    public ResponseEntity<String> deleteFresher(@PathVariable @Min(1) Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        if(fresher == null) {
            throw new FresherNotFoundException("Did not find fresher id - " + fresherId);
        } else {
            fresherService.deleteById(fresherId);
            return ResponseEntity.ok("Deleted fresher id - " + fresherId);
        }
    }

    @PutMapping("/freshers/{fresherId}")
    public ResponseEntity<Fresher> updateFresher(@PathVariable @Min(1) Integer fresherId,
                                           @RequestBody FresherDTO fresherDTO) {

        Fresher fresher = fresherService.findById(fresherId);
        if(fresher == null) {
            throw new FresherNotFoundException("Did not find fresher id - " + fresherId);
        } else {
            fresher.setName(fresherDTO.getName());
            fresher.setEmail(fresherDTO.getEmail());
            fresher.setProgrammingLanguage(fresherDTO.getProgrammingLanguage());
            fresher.setScore1(fresherDTO.getScore1());
            fresher.setScore2(fresherDTO.getScore2());
            fresher.setScore3(fresherDTO.getScore3());
            fresher.setUpdateDate(Instant.now());
            return ResponseEntity.ok(fresherService.save(fresher));
        }
    }

    @GetMapping("/freshers/{fresherId}/averageScore")
    public ResponseEntity<Float> averageScore(@PathVariable @Min(1) Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        Float avgScore = calculateAverageScore(fresher);
        return ResponseEntity.ok(avgScore);
    }

    public Float calculateAverageScore(Fresher fresher) {
        return (fresher.getScore1()+fresher.getScore2()+fresher.getScore3())/3;
    }

    @GetMapping("/freshers/findByName")
    public ResponseEntity<List<Fresher>> findByName(@RequestParam @NotBlank String name,
                                        @RequestParam @Min(1) Integer pageNumber,
                                        @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByName(name, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByProgrammingLanguage")
    public ResponseEntity<List<Fresher>> findByProgrammingLanguage(@RequestParam @NotBlank @Size(max=10) String programmingLanguage,
                                        @RequestParam @Min(1) Integer pageNumber,
                                        @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByProgrammingLanguage(programmingLanguage, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByEmail")
    public ResponseEntity<List<Fresher>> findByEmail(@RequestParam @NotBlank
                                                     @Pattern(regexp = "[A-Za-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}", message = "email is invalid") String email,
                                                     @RequestParam @Min(1) Integer pageNumber,
                                                     @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByEmail(email, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/statisticFresherByCenter")
    public ResponseEntity<List<NumberOfFresherEachCenter>> statisticFresherByCenter(@RequestParam @Min(1) Integer pageNumber,
                                                      @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Center> centers = centerService.findAll(pageable);
        List<NumberOfFresherEachCenter> result = new ArrayList<>();
        for (Center center: centers) {
            Integer numberOfFresher = fresherService.countFresherByCenterId(center.getId());
            result.add(new NumberOfFresherEachCenter(center.getId(), center.getName(), numberOfFresher));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/freshers/statisticFresherByScore")
    public ResponseEntity<List<NumberOfFresherEachScoreRange>> statisticFresherByScore() {
        List<NumberOfFresherEachScoreRange> result = fresherService.getNumberOfFresherEachScoreRange();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/freshers/{fresherId}/getJoinDateAndGraduateDate")
    public ResponseEntity<JoinDateOfFresherWithLocalDateType> getJoinDateAndGraduateDate(@PathVariable @Min(1) Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        LocalDate joinDate = fresher.getJoinDate()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

        LocalDate graduateDate = fresher.getGraduateDate()
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate();
        return ResponseEntity.ok(new JoinDateOfFresherWithLocalDateType(joinDate, graduateDate));
    }

}
