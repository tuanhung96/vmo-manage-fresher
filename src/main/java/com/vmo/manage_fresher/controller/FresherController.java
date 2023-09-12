package com.vmo.manage_fresher.controller;

import com.vmo.manage_fresher.entity.Center;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.exception.FresherNotFoundException;
import com.vmo.manage_fresher.model.NumberOfFresherEachCenter;
import com.vmo.manage_fresher.model.NumberOfFresherEachScoreRange;
import com.vmo.manage_fresher.service.CenterService;
import com.vmo.manage_fresher.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<Fresher>> findAllFresher(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findAll(pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/{fresherId}")
    public ResponseEntity<Fresher> findFresherById(@PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        return ResponseEntity.ok(fresher);
    }

    @GetMapping("/freshers/count")
    public ResponseEntity<Long> countFresher() {
        Long numberOfFreshers = fresherService.countFresher();
        return ResponseEntity.ok(numberOfFreshers);
    }

    @PostMapping("/freshers")
    public ResponseEntity<Fresher> addFresher(@RequestBody Fresher fresher) {
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
    public ResponseEntity<Float> averageScore(@PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        Float avgScore = calculateAverageScore(fresher);
        return ResponseEntity.ok(avgScore);
    }
    public Float calculateAverageScore(Fresher fresher) {
        return (fresher.getScore_1()+fresher.getScore_2()+fresher.getScore_3())/3;
    }

    @GetMapping("/freshers/findByName")
    public ResponseEntity<List<Fresher>> findByName(@RequestParam String name,
                                        @RequestParam Integer pageNumber,
                                        @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByName(name, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByProgrammingLanguage")
    public ResponseEntity<List<Fresher>> findByProgrammingLanguage(@RequestParam String programmingLanguage,
                                        @RequestParam Integer pageNumber,
                                        @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByProgrammingLanguage(programmingLanguage, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/findByEmail")
    public ResponseEntity<List<Fresher>> findByEmail(@RequestParam String email,
                                                       @RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Fresher> fresherList = fresherService.findByEmail(email, pageable);
        return ResponseEntity.ok(fresherList);
    }

    @GetMapping("/freshers/statisticFresherByCenter")
    public ResponseEntity<List<NumberOfFresherEachCenter>> statisticFresherByCenter(@RequestParam Integer pageNumber,
                                                      @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Center> centers = centerService.findAll(pageable);
        List<NumberOfFresherEachCenter> result = new ArrayList<>();
        for (Center center: centers) {
            Integer numberOfFresher = fresherService.countFresherByCenterId(center.getId());
            result.add(new NumberOfFresherEachCenter(center.getId(), center.getName(), numberOfFresher));
        }
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/freshers/statisticFresherByScore")
//    public ResponseEntity<List<NumberOfFresherByScore>> statisticFresherByScore() {
//        List<Fresher> fresherList = fresherService.findAll();
//
//        int[] arr = new int[11];
//        for (Fresher fresher: fresherList) {
//            float avgScore = calculateAverageScore(fresher);
//            switch ((int) avgScore) {
//                case 10 -> arr[10]++;
//                case 9 -> arr[9]++;
//                case 8 -> arr[8]++;
//                case 7 -> arr[7]++;
//                case 6 -> arr[6]++;
//                case 5 -> arr[5]++;
//                case 4 -> arr[4]++;
//                case 3 -> arr[3]++;
//                case 2 -> arr[2]++;
//                case 1 -> arr[1]++;
//                case 0 -> arr[0]++;
//            }
//        }
//
//        List<NumberOfFresherByScore> result = new ArrayList<>();
//        for (int i = 0; i < 11; i++) {
//            if (i==10) result.add(new NumberOfFresherByScore(String.valueOf(i), arr[i]));
//            else result.add(new NumberOfFresherByScore(i + " - <" + (i+1), arr[i]));
//        }
//
//        return ResponseEntity.ok(result);
//    }

    @GetMapping("/freshers/statisticFresherByScore")
    public ResponseEntity<List<NumberOfFresherEachScoreRange>> statisticFresherByScore() {
        List<NumberOfFresherEachScoreRange> result = fresherService.getNumberOfFresherEachScoreRange();

        return ResponseEntity.ok(result);
    }

}
