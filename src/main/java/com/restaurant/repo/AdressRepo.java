package com.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.model.Adress;

public interface AdressRepo extends JpaRepository<Adress, Long>
{

}
