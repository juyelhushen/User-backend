package com.jskool.springcrudreact.service;

import com.jskool.springcrudreact.entity.User;
import com.jskool.springcrudreact.exception.UserNotFoundException;
import com.jskool.springcrudreact.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private UserRepo repo;


    //saving
    public User AddUser(User user) {
        User userDetails = new User();
        userDetails.setName(user.getName());
        userDetails.setEmail(user.getEmail());
        userDetails.setPassword(user.getPassword());
        return repo.save(user);
    }

    @Cacheable(cacheNames = "cache1", key = "'#key'")
    public List<User> getAllUser() {
         List<User> user = repo.findAll();
         return user;
    }

    //userById

    public Optional<User> userById(Long id) {
        return repo.findById(id);
    }

   // update
    public User updateUser(Long id,User user) {
        Optional<User> userOptional = repo.findById(id);
        if(userOptional.isPresent()) {
           User user1 = userOptional.get();
           user1.setName(user.getName());
           user1.setEmail(user.getEmail());
           user1.setPassword(user.getPassword());
           user1.setUsername(user.getUsername());
           return repo.save(user1);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    //delete
    public void deleteUserById(Long id) {
        repo.deleteById(id);
    }

}
