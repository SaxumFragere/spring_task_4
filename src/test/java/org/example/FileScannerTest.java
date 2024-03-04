package org.example;

import org.apache.commons.csv.CSVRecord;
import org.example.scaners.FileScanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class FileScannerTest {
    @Test
    void readFilesInFileSet() throws IOException {
        FileScanner fileScanner = new FileScanner("src\\test\\resources\\SpringTasksRoot");
        HashMap<String, List<CSVRecord>> filesContent = fileScanner.readFilesInFileSet();
        Assertions.assertEquals(1, filesContent.size());
        int rowsCnt = 0;
        for (var entry : filesContent.entrySet()){
            rowsCnt += entry.getValue().size();
        }
        Assertions.assertEquals(1, rowsCnt);
    }
}
