package org.example.dbloaders;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.example.entities.Login;
import org.example.entities.User;
import org.example.loggers.errors.ErrorsLogger;
import org.example.parsers.CSVLogParser;
import org.example.repos.LoginRepo;
import org.example.repos.UserRepo;
import org.example.validators.login.LoginValidator;
import org.example.validators.user.UserValidator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@AllArgsConstructor
public class DBLoader {
    private final UserRepo userRepo;
    private final LoginRepo loginRepo;
    private final ErrorsLogger errorsLogger;
    private final CSVLogParser csvLogParser;
    private final List<UserValidator> userValidators;
    private final List<LoginValidator> loginValidators;

    public void loadToDataBase(HashMap<String, List<CSVRecord>> filesContent) throws IOException {
        for (var entry : filesContent.entrySet()) {

            String fileName = entry.getKey();
            List<CSVRecord> records = entry.getValue();

            for (CSVRecord record : records) {

                User dbUser = csvLogParser.parseUser(record);
                boolean userValid = validateUser(dbUser);

                Login dbLogin = csvLogParser.parseLogin(record);
                boolean loginValid = validateLogin(dbLogin);

                if (loginValid && userValid) {
                    userRepo.save(dbUser);
                    dbLogin.setUserId(dbUser.getId());
                    loginRepo.save(dbLogin);
                } else {
                    errorsLogger.log(fileName, dbUser);
                }
            }

        }
    }

    private boolean validateUser(User dbUser){
        boolean userValid = true;
        for (UserValidator u : userValidators) {
            userValid = u.validate(dbUser);
            if (!userValid) break;
        }
        return userValid;
    }

    private boolean validateLogin(Login dbLogin){
        boolean loginValid = true;
        for (LoginValidator lv : loginValidators) {
            loginValid = lv.validate(dbLogin);
            if (!loginValid) break;
        }
        return loginValid;
    }
}
