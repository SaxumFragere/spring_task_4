package org.example.validators.user;

import org.example.entities.AV_Users;
import org.example.loggers.actions.LogTransformation;
import org.springframework.stereotype.Component;

@Component
@LogTransformation("C:\\SpringTasksRootVal\\user_log.txt")
public class FioUserValidator implements UserValidator {

    public boolean validate(AV_Users user){
        String fio = user.getFio();

        StringBuilder stringBuilder = new StringBuilder();
        for(String value: fio.split(" ")){
            stringBuilder.append(Character.toUpperCase(value.charAt(0)));
            stringBuilder.append(value.substring(1));
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(fio.length());

        user.setFio(stringBuilder.toString());

        return true;
    }
}
