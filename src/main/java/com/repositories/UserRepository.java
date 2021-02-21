package com.repositories;

import com.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserApp,Long> {

    UserApp findByNom(@Param("nom") String nom);
}
