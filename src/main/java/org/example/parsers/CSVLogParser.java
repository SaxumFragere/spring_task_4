package org.example.parsers;

import org.apache.commons.csv.CSVRecord;
import org.example.entities.Login;
import org.example.entities.User;
import org.example.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CSVLogParser {

    private final UserRepo userRepo;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    @Autowired
    public CSVLogParser(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User parseUser(CSVRecord record){
        User dbUser = userRepo.findByUsername(record.get(0));
        if (dbUser == null) {

            dbUser = new User();
            dbUser.setUsername(record.get(0));
            dbUser.setFio(record.get(1));
        }
        return dbUser;
    }
    public Login parseLogin(CSVRecord record){
        Login dbLogin = new Login();
        try {
            dbLogin.setAccessDate(formatter.parse(record.get(2)));
        } catch(ParseException e) {}

        dbLogin.setApplication(record.get(3));
        return dbLogin;
    }
}
