package org.example.loggers.errors;

import org.example.entities.AV_Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ErrorsLogger {
    @Value("${errors_logger.file_path}")
    private String filePath;
    @Value("${errors_logger.file_name}")
    private String fileName;
    public void log(String fileName, AV_Users user) throws IOException {
        FileWriter fw = new FileWriter(this.filePath + "\\" + this.fileName, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write("Errors writing to database from file " + fileName + ": username - " + user.getUsername() + ", fio - " + user.getFio());
        bw.close();
    }
}
