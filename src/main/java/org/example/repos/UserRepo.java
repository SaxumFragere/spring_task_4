package org.example.repos;

import org.example.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
