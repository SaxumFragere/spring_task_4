package org.example.parsers;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.AV_Logins;
import org.example.entities.AV_Users;
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

    public AV_Users parseUser(CSVRecord record){
        AV_Users dbUser = userRepo.findByUsername(record.get(0));
        if (dbUser == null) {

            dbUser = new AV_Users();
            dbUser.setUsername(record.get(0));
            dbUser.setFio(record.get(1));
        }
        return dbUser;
    }
    public AV_Logins parseLogin(CSVRecord record){
        AV_Logins dbLogin = new AV_Logins();
        try {
            dbLogin.setAccess_date(formatter.parse(record.get(2)));
        } catch(ParseException e) {}

        dbLogin.setApplication(record.get(3));
        return dbLogin;
    }
}
