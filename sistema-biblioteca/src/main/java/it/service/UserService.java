package it.service;

import java.util.List;
import org.springframework.stereotype.Service;
import it.model.User;
import it.repository.UserRepository;

import it.dto.UserDto;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
    List<User> entities = userRepository.findAll(); // entity piena
        return entities.stream().map(u -> {
            UserDto dto = new UserDto();
            dto.setUserId(u.getUserId());
            dto.setUserName(u.getUserName());
            dto.setUserLastName(u.getUserSurname());
            dto.setUserEmail(u.getUserEmail());
            dto.setUserRole(u.getUserRole());
            return dto;
        }).toList();
    }
}
