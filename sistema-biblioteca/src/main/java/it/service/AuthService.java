package it.service;
import org.springframework.stereotype.Service;

import it.dto.LoginDto;
import it.entity.User;
import it.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());

        if (user == null) {
            return null;
        }

        if (!user.getUserPassword().equals(loginDto.getPassword())) {
            return null;
        }

        return user;
    }
}
