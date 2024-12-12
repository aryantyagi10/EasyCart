package com.spring.productInventory.Service;

import com.spring.productInventory.Entity.User;
import com.spring.productInventory.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isEmailExists(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();
    }

    public boolean isUsernameExists(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.isPresent();
    }

    public User registerUser(User user){
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password){
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

}
