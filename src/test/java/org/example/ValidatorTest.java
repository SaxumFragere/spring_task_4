package org.example;

import org.example.entities.AV_Logins;
import org.example.entities.AV_Users;
import org.example.loggers.actions.LogTransformation;
import org.example.validators.login.LoginValidator;
import org.example.validators.user.UserValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ValidatorTest {
    @Autowired
    List<UserValidator> userValidators;
    @Autowired
    List<LoginValidator> loginValidators;
    static boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }

        return false;
    }

    @Test
    void userValidator() throws IOException {
        AV_Users user = new AV_Users(1, "vanya", "Ivanov Ivan Ivanovjch");

        for (var u : userValidators){
            LogTransformation logTransformation = ClassUtils.getUserClass(u).getAnnotation(LogTransformation.class);
            Path path = Paths.get(logTransformation.value());
            Files.deleteIfExists(path);
            u.validate(user);
            assertFalse(isEmpty(path));
        }
    }

    @Test
    void loginValidator() throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        AV_Logins login = new AV_Logins(1, formatter.parse("01.01.2000"), 1, "web");

        for (var u : loginValidators){
            LogTransformation logTransformation = ClassUtils.getUserClass(u).getAnnotation(LogTransformation.class);
            Path path = Paths.get(logTransformation.value());
            Files.deleteIfExists(path);
            u.validate(login);
            assertFalse(isEmpty(path));
        }
    }
}
