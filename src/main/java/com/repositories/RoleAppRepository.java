package com.repositories;

import com.entities.RoleApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleAppRepository extends JpaRepository<RoleApp,Long> {

    RoleApp findByRole(@Param("s") String s);
}
