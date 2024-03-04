package org.example.scaners;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class FileScanner {

    private final String rootFilePath;

    public FileScanner(@Value("${file_scanner.root_file_path}")
                       String rootFilePath)
    {
        this.rootFilePath = rootFilePath;
    }

    public HashMap<String, List<CSVRecord>> readFilesInFileSet() throws IOException {
        HashMap<String, List<CSVRecord>> filesContent = new HashMap<>();

        Set<String> fileSet = listFilesUsingDirectoryStream();
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(";")
                .build();

        for (String f : fileSet) {
            Reader in = new FileReader(rootFilePath + "\\" + f);
            List<CSVRecord> fRecords = csvFormat.parse(in).getRecords();
            filesContent.put(f, fRecords);
        }

        return filesContent;
    }

    private Set<String> listFilesUsingDirectoryStream() throws IOException {
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(rootFilePath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add(path.getFileName()
                            .toString());
                }
            }
        }
        return fileSet;
    }
}
