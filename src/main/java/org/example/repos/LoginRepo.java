package org.example.repos;

import org.example.entities.AV_Logins;
import org.springframework.data.repository.CrudRepository;

public interface LoginRepo extends CrudRepository<AV_Logins, Integer> {
}
