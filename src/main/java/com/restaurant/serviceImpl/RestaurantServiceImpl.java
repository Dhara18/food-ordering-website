package com.restaurant.serviceImpl;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.dto.RestaurantDTO;
import com.restaurant.model.Adress;
import com.restaurant.model.Restaurant;
import com.restaurant.model.User;
import com.restaurant.repo.AdressRepo;
import com.restaurant.repo.RestaurantRepo;
import com.restaurant.repo.UserRepo;
import com.restaurant.request.RestaureantRequest;
import com.restaurant.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService
{
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Autowired
	private AdressRepo adressRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public Restaurant createRestaurant(RestaureantRequest req, User user) 
	{
		//why because address has been taken as string first....
		//so first stored in addressRepo as address object then set to restaurant address
		
		//but here directly taken as address object
		Adress adress = adressRepo.save(req.getAdress());		//while adding restaurant only address added so..address saved here only
		
		Restaurant restaurant=new Restaurant();
		restaurant.setName(req.getName());
		restaurant.setDescription(req.getDescription());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setImges(req.getImges());
		restaurant.setRegistrationDate(LocalDateTime.now());
		
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setAddress(adress);
		restaurant.setOwner(user);
		
		return restaurantRepo.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(long restaurantId, RestaureantRequest req) throws Exception 
	{
		Restaurant restaurant = findRestaurantById(restaurantId);		//not repo method... it is a restaurant service method
		
		if(restaurant.getCuisineType()!=null)
		{
			restaurant.setCuisineType(req.getCuisineType());
		}
		if(restaurant.getDescription()!=null)
		{
			restaurant.setDescription(req.getDescription());
		}
		if(restaurant.getName()!=null)
		{
			restaurant.setName(req.getName());
		}
		
		return restaurantRepo.save(restaurant);
	}
	
	@Override
	public Restaurant findRestaurantById(Long restaurantId) throws Exception 
	{
		Optional<Restaurant> isExist = restaurantRepo.findById(restaurantId);
		if(isExist==null)
		{
			throw new Exception("restaurant with id not found");
		}
		return isExist.get();
	}

	@Override
	public void deleteRestaurant(long restaurantId) throws Exception 
	{
		//above method
		Restaurant restaurant = findRestaurantById(restaurantId);		//not repo method... it is a restaurant service method
		restaurantRepo.delete(restaurant);
		
		//restaurantRepo.deleteById(restaurantId);			but need to check if restaurant exist or not so above method
	}

	@Override
	public List<Restaurant> getAllRestaurants() 
	{
		return restaurantRepo.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurants(String keyword)		//keyword is for name and cuisineType 
	{
		return restaurantRepo.findBySearchQuery(keyword);
	}


	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception 
	{
		//findByOwnerId because variable is owner mapped with id
		//check DB for more
		Restaurant restaurant = restaurantRepo.findByOwnerId(userId);
		if(restaurant==null)
		{
			throw new Exception("restaurant not found with user id");
		}
		return restaurant;
	}

	@Override				//every time button clicked this method should be executed
	public RestaurantDTO addToFavorates(Long restaurantId, User user) throws Exception 
	{
		Restaurant restaurant = findRestaurantById(restaurantId);		//not repo method... it is a restaurant service method
		
		RestaurantDTO restaurantDTO= new RestaurantDTO();
		restaurantDTO.setDiscription(restaurant.getDescription());
		restaurantDTO.setTitle(restaurant.getName());
		restaurantDTO.setImages(restaurant.getImges());
		restaurantDTO.setId(restaurantId);//1
		
		List<RestaurantDTO> favorateList = user.getFavorateList();
		Iterator<RestaurantDTO> iterator = favorateList.iterator();
		
		boolean isRemoved=false;
		while(iterator.hasNext())
		{
			RestaurantDTO next = iterator.next();
			if(next.getId().equals(restaurantId))
			{
				isRemoved=favorateList.remove(next);
				//break;									//remove break giving concurrentModificationExp
			}
		}
		
//		boolean isRemoved=false;
//		for(RestaurantDTO fav:favorateList)
//		{
//			if(fav.getId().equals(restaurantId))
//			{
//				isRemoved=favorateList.remove(fav);
//			}
//		}
		
		if(isRemoved==false)
		favorateList.add(restaurantDTO);

		userRepo.save(user);
		return restaurantDTO;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception 
	{
		Restaurant restaurant = findRestaurantById(id);		//not repo method... it is a restaurant service method
		
		//isOpen method used....there is no getOpen method because of boolean.....
		restaurant.setOpen(!restaurant.isOpen());			//if restaurant open then closes...if closed then opens
		return restaurantRepo.save(restaurant);
	}
}
