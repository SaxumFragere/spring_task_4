package org.example;

import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.example.dbloaders.DBLoader;
import org.example.entities.User;
import org.example.repos.LoginRepo;
import org.example.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DBLoaderTest {
    @Autowired
    private DBLoader dbLoader;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LoginRepo loginRepo;

    @BeforeEach
    @SneakyThrows
    public void beforeSetup() {
        userRepo.deleteAll();
        loginRepo.deleteAll();
        FileUtils.cleanDirectory(new File("src\\test\\resources\\SpringTasksRootErr"));
    }

    static boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }

        return false;
    }

    @Test
    void loadToDataBase() throws IOException {
        HashMap<String, List<CSVRecord>> filesContent = new HashMap<>();
        String fileName = "tst.csv";
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(";")
                .build();
        CSVRecord csvRecord = CSVParser.parse("petya123;Petrov Petr Petrovich;01.01.2024;web", csvFormat).getRecords().get(0);
        List<CSVRecord> csvRecords = new ArrayList<>();
        csvRecords.add(csvRecord);
        filesContent.put(fileName, csvRecords);
        dbLoader.loadToDataBase(filesContent);

        User user = userRepo.findByUsername("petya123");
        assertThat(user).isNotNull();
        assertEquals(1, loginRepo.count());
    }

    @Test
    void errorLoadToDB() throws IOException {
        HashMap<String, List<CSVRecord>> filesContent = new HashMap<>();
        String fileName = "tst.csv";
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(";")
                .build();
        CSVRecord csvRecord = CSVParser.parse("petya123;Petrov Petr Petrovich;;web", csvFormat).getRecords().get(0);
        List<CSVRecord> csvRecords = new ArrayList<>();
        csvRecords.add(csvRecord);
        filesContent.put(fileName, csvRecords);
        dbLoader.loadToDataBase(filesContent);

        assertEquals(0, userRepo.count());
        assertEquals(0, loginRepo.count());

        assertFalse(isEmpty(Paths.get("src\\test\\resources\\SpringTasksRootErr")));
    }

}
