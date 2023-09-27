package com.vmo.demo.controller;

import com.vmo.demo.entity.Center;
import com.vmo.demo.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
public class FileUploadController {
    private CenterService centerService;

    @Autowired
    public FileUploadController(CenterService centerService) {
        this.centerService = centerService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {

        String content = new String(multipartFile.getBytes(), StandardCharsets.UTF_8);
        List<Center> centerList = Arrays.stream(content.split("\n"))
                                        .map(line -> {
                                            String[] data = line.split(",");
                                            String name = data[0];
                                            String address = data[1];
                                            return new Center(name, address);
                                        }).toList();

        centerService.saveAll(centerList);

        return ResponseEntity.ok("Upload successfully!");
    }
}
