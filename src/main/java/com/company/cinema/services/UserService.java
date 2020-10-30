package com.company.cinema.services;

import com.company.cinema.entity.Role;
import com.company.cinema.entity.User;
import com.company.cinema.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public User saveNewUser(User user){
        if (usersRepository.findByUsername(user.getUsername()) == null){
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            usersRepository.save(user);
            return user;
        }
        return null;
    }

    public User validateUser(User user){
        User receivedUser = usersRepository.findByUsername(user.getUsername());
        if(receivedUser != null && receivedUser.getPassword().equals(user.getPassword())){
            return receivedUser;
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByUsername(username);
    }
}
