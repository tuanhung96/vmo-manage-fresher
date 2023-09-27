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
    public ResponseEntity<Center> addCenter(@RequestBody CenterDTO centerDTO) {
        Center savedCenter = centerService.save(new Center(centerDTO.getName(), centerDTO.getAddress()));
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

    @PutMapping("/centers/{centerId}")
    public ResponseEntity<Center> updateCenter(@PathVariable Integer centerId,
                                          @RequestBody CenterDTO centerDTO) {

        Center foundCenter = centerService.findById(centerId);
        if (foundCenter == null) {
            throw new CenterNotFoundException("Did not find center id - " + centerId);
        } else {
            return ResponseEntity.ok(centerService.save(new Center(centerDTO.getName(), centerDTO.getAddress())));
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
