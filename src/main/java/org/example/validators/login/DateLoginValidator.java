package org.example.validators.login;

import org.example.entities.AV_Logins;
import org.example.loggers.actions.LogTransformation;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@LogTransformation("C:\\SpringTasksRootVal\\date_log.txt")
public class DateLoginValidator implements LoginValidator{
    public boolean validate(AV_Logins login){
        Date d = login.getAccess_date();
        return d != null;
    }
}
