package com.proiect2.user.services;

import com.proiect2.user.exceptions.UserNotFound;
import com.proiect2.user.model.User;
import com.proiect2.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Override
    public User save(User user) {
        User userSave = userRepository.save(user);
        return userSave;
    }
    @Override
    public List<User> findAll(){
        List<User> users = new LinkedList<>();
        userRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    @Override
    public User delete(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (! userOptional.isPresent())
            throw new UserNotFound("User " + id + " not found!");
        userRepository.delete(userOptional.get());
        return userOptional.get();
    }
    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (! userOptional.isPresent())
            throw new UserNotFound("User " + id + " not found!");
        return userOptional.get();
    }
}
