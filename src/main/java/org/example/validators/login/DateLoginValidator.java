package org.example.validators.login;

import org.example.entities.Login;
import org.example.loggers.actions.LogTransformation;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@LogTransformation("C:\\SpringTasksRootVal\\date_log.txt")
public class DateLoginValidator implements LoginValidator{
    public boolean validate(Login login){
        Date d = login.getAccessDate();
        return d != null;
    }
}
