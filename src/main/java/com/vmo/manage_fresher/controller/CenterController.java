package com.vmo.manage_fresher.controller;

import com.vmo.manage_fresher.entity.Center;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.exception.CenterNotFoundException;
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
public class CenterController {
    private CenterService centerService;
    private FresherService fresherService;

    @Autowired
    public CenterController(CenterService centerService, FresherService fresherService) {
        this.centerService = centerService;
        this.fresherService = fresherService;
    }

    @GetMapping("/centers")
    public ResponseEntity<List<Center>> findAllCenters(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Center> centerList = centerService.findAll(pageable);
        return ResponseEntity.ok(centerList);
    }

    @PostMapping("/centers")
    public ResponseEntity<Center> addCenter(@RequestBody Center center) {
        Center savedCenter = centerService.save(new Center(center.getName(), center.getAddress()));
        return ResponseEntity.ok(savedCenter);
    }

    @DeleteMapping("/centers/{centerId}")
    public ResponseEntity<String> deleteCenter(@PathVariable Integer centerId) {
        Center center = centerService.findById(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Did not find center id - " + centerId);
        } else {
            centerService.deleteById(centerId);
            return ResponseEntity.ok("Deleted center id - " + centerId);
        }
    }

    @PutMapping("/centers")
    public ResponseEntity<?> updateCenter(@RequestBody Center center) {
        if (center.getId() == null) {
            return new ResponseEntity<>("The given id must not be null! Please add id", HttpStatus.BAD_REQUEST);
        }

        Center foundCenter = centerService.findById(center.getId());
        if (foundCenter == null) {
            throw new CenterNotFoundException("Did not find center id - " + center.getId());
        } else {
            return ResponseEntity.ok(centerService.save(center));
        }
    }

    @GetMapping("/centers/{centerId}/addFresher/{fresherId}")
    public ResponseEntity<Fresher> addFresherIntoCenter(@PathVariable Integer centerId,
                                                  @PathVariable Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        Center center = centerService.findById(centerId);

        fresher.setCenter(center);
        fresherService.save(fresher);
        return ResponseEntity.ok(fresherService.save(fresher));
    }
}
