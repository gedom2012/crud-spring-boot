package com.mariath.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mariath.crud.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
