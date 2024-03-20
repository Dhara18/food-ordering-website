package com.restaurant.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant, Long>
{
	Restaurant findByOwnerId(long userId);		//restaurant has user in form of owner so...it becomes ownerId...check DB
	
	@Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:query,'%')) OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
	List<Restaurant> findBySearchQuery(String query);
	
//	@Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%',:value,'%')) OR lower(r.cuisineType) LIKE lower(concat('%',:value,'%'))")
//	List<Restaurant> findBySearchQuery(@Param("value")String query);
}
