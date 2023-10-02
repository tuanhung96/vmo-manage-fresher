package com.vmo.demo.controller;

import com.vmo.demo.entity.Center;
import com.vmo.demo.entity.Fresher;
import com.vmo.demo.exception.CenterNotFoundException;
import com.vmo.demo.model.dto.CenterDTO;
import com.vmo.demo.service.CenterService;
import com.vmo.demo.service.FresherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.time.Instant;
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
    public ResponseEntity<List<Center>> findAllCenters(@RequestParam @Min(1) Integer pageNumber,
                                            @RequestParam @Min(1) Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.by("id"));
        List<Center> centerList = centerService.findAll(pageable);
        return ResponseEntity.ok(centerList);
    }

    @PostMapping("/centers")
    public ResponseEntity<Center> addCenter(@RequestBody CenterDTO centerDTO) {
        Center center = new Center(centerDTO.getName(), centerDTO.getAddress());
        Instant time = Instant.now();
        center.setCreateDate(time);
        center.setUpdateDate(time);
        return ResponseEntity.ok(centerService.save(center));
    }

    @DeleteMapping("/centers/{centerId}")
    public ResponseEntity<String> deleteCenter(@PathVariable @Min(1) Integer centerId) {
        Center center = centerService.findById(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Did not find center id - " + centerId);
        } else {
            centerService.deleteById(centerId);
            return ResponseEntity.ok("Deleted center id - " + centerId);
        }
    }

    @PutMapping("/centers/{centerId}")
    public ResponseEntity<Center> updateCenter(@PathVariable @Min(1) Integer centerId,
                                          @RequestBody CenterDTO centerDTO) {

        Center center = centerService.findById(centerId);
        if (center == null) {
            throw new CenterNotFoundException("Did not find center id - " + centerId);
        } else {
            center.setName(centerDTO.getName());
            center.setAddress(centerDTO.getAddress());
            center.setUpdateDate(Instant.now());
            return ResponseEntity.ok(centerService.save(center));
        }
    }

    @GetMapping("/centers/{centerId}/addFresher/{fresherId}")
    public ResponseEntity<Fresher> addFresherIntoCenter(@PathVariable @Min(1) Integer centerId,
                                                  @PathVariable @Min(1) Integer fresherId) {
        Fresher fresher = fresherService.findById(fresherId);
        Center center = centerService.findById(centerId);

        fresher.setCenter(center);
        Instant time = Instant.now();
        fresher.setJoinDate(time);
        fresher.setUpdateDate(time);
        fresherService.save(fresher);
        return ResponseEntity.ok(fresherService.save(fresher));
    }
}
