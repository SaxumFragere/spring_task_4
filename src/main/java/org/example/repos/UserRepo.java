package org.example.repos;

import org.example.entities.AV_Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<AV_Users, Integer> {
    AV_Users findByUsername(String username);
}
