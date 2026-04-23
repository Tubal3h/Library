package it.repository;

import java.util.List;

import it.entity.User;

public interface UserRepositoryInterface {
	public User findByEmail(String email);
	public List<User> getAllUsers();
	public int countUsers();
}
