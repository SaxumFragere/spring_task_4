package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.AV_Logins;
import org.example.entities.AV_Users;
import org.example.parsers.CSVLogParser;
import org.example.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.mockito.Mockito.when;

public class CSVLogParserTest {

    UserRepo userRepo = Mockito.mock(UserRepo.class);;
    CSVLogParser csvLogParser = new CSVLogParser(userRepo);
    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setDelimiter(";")
            .build();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    @Test
    void parseUser() throws IOException {
        CSVRecord record = CSVParser.parse("petya123;Petrov Petr Petrovich;01.01.2000;web", csvFormat).getRecords().get(0);
        when(userRepo.findByUsername("petya123")).thenReturn(null);
        AV_Users dbUser = csvLogParser.parseUser(record);
        Assertions.assertEquals("petya123", dbUser.getUsername());
        Assertions.assertEquals("Petrov Petr Petrovich", dbUser.getFio());
    }

    @Test
    void parseLogin() throws IOException, ParseException {
        CSVRecord record = CSVParser.parse("petya123;Petrov Petr Petrovich;01.01.2000;web", csvFormat).getRecords().get(0);
        when(userRepo.findByUsername("petya123")).thenReturn(null);
        AV_Logins login = csvLogParser.parseLogin(record);
        Assertions.assertEquals(formatter.parse("01.01.2000"),login.getAccess_date());
        Assertions.assertEquals("web", login.getApplication());
    }


}
