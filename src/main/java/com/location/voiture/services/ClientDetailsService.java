package com.location.voiture.services;

import com.location.voiture.models.Client;
import com.location.voiture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientRepository.findByUsername(username);
        if(client.isPresent()) {
            var clientObj = client.get();
//            return User.builder()
//                    .username(clientObj.getUsername())
//                    .password(clientObj.getPassword())
//                    .roles(clientObj.getRole().name())
//                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
        return null;
    }

//    private String[] getRoles(Client client) {
//        if (client.getRole() == null) {
//            return new String[]{"CLIENT"};
//        }
//        return client.getRole().split(",");
//    }


}
