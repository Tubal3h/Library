package it.repository.interfaces;

import java.util.List;

import it.entity.User;

public interface UserRepositoryInterface {
	public User findByEmail(String email);
	public List<User> getAllUsers();
	public int countUsers();
	public int insertUser(String userName, String userLastName, String userEmail, String userPassword, String userRole);
	public boolean existsByEmail(String email);
	public int deleteUserById(String userId);
	public int updatePassword(String email, String newPassword);
	public User findById(int userId);
}
