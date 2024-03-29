package org.example.validators.login;

import org.example.entities.Login;
import org.example.loggers.actions.LogTransformation;
import org.springframework.stereotype.Component;

@Component
@LogTransformation("C:\\SpringTasksRootVal\\app_log.txt")
public class AppLoginValidator implements LoginValidator{
    enum apps {web, mobile};
    public boolean validate(Login login){
        String app = login.getApplication();
        try {
            apps.valueOf(app);
        } catch (IllegalArgumentException e) {
            login.setApplication("other_" + app);
        }
        return true;
    }
}
