package com.vmo.manage_fresher.controller;

import com.vmo.manage_fresher.entity.Center;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.exception.FresherNotFoundException;
import com.vmo.manage_fresher.service.CenterService;
import com.vmo.manage_fresher.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FresherController {
    private FresherService fresherService;
    private CenterService centerService;

    @Autowired
    public FresherController(FresherService fresherService, CenterService centerService) {
        this.fresherService = fresherService;
        this.centerService = centerService;
    }

    @GetMapping("/freshers")
    public ResponseEntity<?> findAllFresher(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findAll(pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/{fresherId}")
    public ResponseEntity<?> findFresherById(@PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        return ResponseEntity.ok(fresher);
    }

    @GetMapping("/freshers/count")
    public ResponseEntity<?> countFresher() {
        long numberOfFreshers = fresherService.countFresher();
        return ResponseEntity.ok(numberOfFreshers);
    }

    @PostMapping("/freshers")
    public ResponseEntity<?> addFresher(@RequestBody Fresher fresher) {
        Fresher savedFresher = fresherService.save(
                new Fresher(fresher.getName(),
                        fresher.getEmail(),
                        fresher.getProgrammingLanguage(),
                        fresher.getScore_1(),
                        fresher.getScore_2(),
                        fresher.getScore_3()));
        return ResponseEntity.ok(savedFresher);
    }

    @DeleteMapping("/freshers/{fresherId}")
    public ResponseEntity<?> deleteFresher(@PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        if(fresher == null) {
            throw new FresherNotFoundException("Did not find fresher id - " + fresherId);
        } else {
            fresherService.deleteById(fresherId);
            return ResponseEntity.ok("Deleted fresher id - " + fresherId);
        }
    }

    @PutMapping("/freshers")
    public ResponseEntity<?> updateFresher(@RequestBody Fresher fresher) {
        if(fresher.getId() == null) {
            return new ResponseEntity<>("The given id must not be null! Please add id", HttpStatus.BAD_REQUEST);
        }

        Fresher foundFresher = fresherService.findById(fresher.getId());
        if(foundFresher == null) {
            throw new FresherNotFoundException("Did not find fresher id - " + fresher.getId());
        } else {
            return ResponseEntity.ok(fresherService.save(fresher));
        }
    }

    @GetMapping("/freshers/{fresherId}/averageScore")
    public ResponseEntity<?> averageScore(@PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        float avgScore = (fresher.getScore_1()+fresher.getScore_2()+fresher.getScore_3())/3;
        return ResponseEntity.ok(avgScore);
    }

    @GetMapping("/freshers/findByName")
    public ResponseEntity<?> findByName(@RequestParam String name,
                                        @RequestParam Integer pageNumber,
                                        @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByName(name, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByProgrammingLanguage")
    public ResponseEntity<?> findByProgrammingLanguage(@RequestParam String programmingLanguage,
                                        @RequestParam Integer pageNumber,
                                        @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByProgrammingLanguage(programmingLanguage, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByEmail")
    public ResponseEntity<?> findByEmail(@RequestParam String email,
                                                       @RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByEmail(email, pageable);
        return ResponseEntity.ok(fresherList);
    }

//    @GetMapping("/freshers/statisticFresherByCenter")
//    public ResponseEntity<?> statisticFresherByCenter(@RequestParam Integer pageNumber,
//                                                      @RequestParam Integer pageSize) {
//        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
//        List<Center> centers = centerService.findAll(pageable);
//        for (Center center: centers) {
//            int numberOfFreshers = fresherService.countFresherByCenterId(center.getId());
//
//        }
//    }
}
