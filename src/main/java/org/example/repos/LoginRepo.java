package org.example.repos;

import org.example.entities.Login;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepo extends CrudRepository<Login, Integer> {
}
