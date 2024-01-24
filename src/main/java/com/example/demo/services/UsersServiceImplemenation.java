package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Users;
import com.example.demo.repositories.UsersRepository;

@Service
public class UsersServiceImplemenation implements UsersService {
   
	@Autowired
	UsersRepository repo;

	@Override
	public String addUser(Users user) {
		// TODO Auto-generated method stub
		repo.save(user);
		return " user succesfully added ";
	}

	@Override
	public boolean emailExists(String email) {
		// TODO Auto-generated method stub
		if(repo.findByEmail(email)==null) {
		return false;
	}
		else {
			return true;
		}

	
	}

	@Override
	public boolean validateUser(String email, String password) {
		// TODO Auto-generated method stub
		Users user=repo.findByEmail(email);
		String db_pass=user.getPassword();
		if(password.equals(db_pass)) {
			return true;
		}
		else {

			return false;
		}
		
		
	}

	@Override
	public String getRole(String email) {
		// TODO Auto-generated method stub
		Users user=repo.findByEmail(email);
		return user.getRole();
	}

	@Override
	public Users getUser(String email) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email);
	}

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub
		repo.save(user);
	}
}

