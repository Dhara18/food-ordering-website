package com.restaurant.sucurity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurant.enums.UserRole;
import com.restaurant.model.User;
import com.restaurant.repo.UserRepo;

// this custom service makes not to generate auto password
@Service
public class CustomUserDetailService implements UserDetailsService
{
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException 
	{
		User user= userRepo.findByEmail(email);
		if(user==null)
		{
			throw new UsernameNotFoundException("user not found weth email");
		}
		UserRole userRole = user.getRole();
		
		//no need ad we have given role inside user entity
//		if(userRole==null)
//		{
//			userRole=UserRole.CUSTOMER;		//if user forgot to give the role then he wiil automatically selected as customer
//		}
		
		List<GrantedAuthority>authorities= new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userRole.toString()));		// after adding one value in array(new SimpleGrantedAuthority("CUSTOMER")or
																				//(new SimpleGrantedAuthority("OWNER")or
																				//(new SimpleGrantedAuthority("ADMIN")
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);	//authorities=[OBJ of SGA]..ie array list of OBJs of SGA...here one obj
		//User from security
		//email,authorities given to user are used in JwtTokenValidator Claims which is built by using key and headers of JwtConstant
	}

}
