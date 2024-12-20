package com.location.voiture.services;

import com.location.voiture.models.AppUser;
import com.location.voiture.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = repository.findByUsername(username);
        if(user.isPresent()){
           var clientObj = user.get();
            return User.builder()
                    .username(clientObj.getUsername())
                    .password(clientObj.getPassword())
                    .roles(clientObj.getRole().name())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
