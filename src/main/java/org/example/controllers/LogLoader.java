package org.example.controllers;

import org.apache.commons.csv.CSVRecord;
import org.example.dbloaders.DBLoader;
import org.example.scaners.FileScanner;
import org.example.validators.login.LoginValidator;
import org.example.validators.user.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Component
@RestController
@RequestMapping(value = "/loaders")
public class LogLoader {
    @Autowired
    private FileScanner fileScanner;
    @Autowired
    private DBLoader dbLoader;

    @PostMapping("/log_load")
    public ResponseEntity<String> load() throws IOException {
        HashMap<String, List<CSVRecord>> filesContent = fileScanner.readFilesInFileSet();
        dbLoader.loadToDataBase(filesContent);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
