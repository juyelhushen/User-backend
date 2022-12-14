package com.jskool.springcrudreact.Controller;

import com.jskool.springcrudreact.exception.UserNotFoundException;
import com.jskool.springcrudreact.model.User;
import com.jskool.springcrudreact.repository.UserRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
@Api(value="User-Apis", description="Operations pertaining to user for user details access")
public class UserController {

    @Autowired
    private UserRepo repository;


    @ApiOperation(value = "View a list of existing user",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllTutorials() {
       try {
           List<User> users = repository.findAll();
           if (users.isEmpty()) {
               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(users, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }


    @ApiOperation(value = "Search a user with an ID",response = User.class)
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {

        Optional<User> userById = repository.findById(id);
//        if (userById != null) {
//            return new ResponseEntity<>(userById.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
        return userById.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                //.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @ApiOperation(value = "Add a new user")
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
           User user1 = repository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e)   {
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
    }

    @ApiOperation(value="Update an existing user")
    @PutMapping("/user/{id} ")
    public ResponseEntity<User> updateTutorial(@PathVariable Long id, @RequestBody User user) {

        Optional<User> userById = repository.findById(id);
//        if (userById.isPresent()) {
//            User updatedUser = repository.save(user);
//            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
//        } else {
//            throw new UserNotFoundException(id);
//        }

        if (userById.isPresent()) {
            User userItem = userById.get();
            userItem.setName(user.getName());
            userItem.setUsername(user.getUsername());
            userItem.setEmail(user.getEmail());
            return new ResponseEntity<>(repository.save(userItem),HttpStatus.OK);
        } else {
            throw new UserNotFoundException(id);
        }
    }

    @ApiOperation(value = "Delete an user")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable Long id) {
        Optional<User> useryId = repository.findById(id);
        if (useryId.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete all user")
    @DeleteMapping("/user")
    public ResponseEntity<HttpStatus> deleteAllTutorials( User user) {
        List<User> tutorials = repository.findAll();
        if (tutorials.size() > 0) {
            repository.delete(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
