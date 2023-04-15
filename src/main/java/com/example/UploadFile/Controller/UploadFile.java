package com.example.UploadFile.Controller;

import com.example.UploadFile.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class UploadFile {
    @Autowired
    private FileUploadService fileUploadService;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("id") Long id, @RequestParam("path")String path, @RequestParam("file")MultipartFile file){

     String filename=   fileUploadService.storeFile(fileUploadService.convertMultiPartFileToFile(file),id,path);


        return ResponseEntity.ok(filename);
    }


}
