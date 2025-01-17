package com.location.voiture.services;

import com.location.voiture.dto.ReqRes;
import com.location.voiture.models.Client;
import com.location.voiture.models.Manager;
import com.location.voiture.models.OurUser;
import com.location.voiture.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagementService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


//    public ReqRes register(ReqRes registrationRequest){
//        ReqRes resp = new ReqRes();
//
//        try {
//            OurUser ourUser = new OurUser();
//            ourUser.setEmail(registrationRequest.getEmail());
//            ourUser.setNom(registrationRequest.getNom());
//            ourUser.setPrenom(registrationRequest.getPrenom());
//            ourUser.setRole(registrationRequest.getRole());
//            ourUser.setTelephone(registrationRequest.getTelephone());
//            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
//            OurUser ourUsersResult = userRepository.save(ourUser);
//            if (ourUsersResult.getId()>0) {
//                resp.setOurUser((ourUsersResult));
//                resp.setMessage("User Saved Successfully");
//                resp.setStatusCode(200);
//            }
//
//        }catch (Exception e){
//            resp.setStatusCode(500);
//            resp.setError(e.getMessage());
//        }
//        return resp;
//    }

    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            OurUser ourUser;

            if ("CLIENT".equalsIgnoreCase(registrationRequest.getRole())) {
                ourUser = Client.builder()
                        .email(registrationRequest.getEmail())
                        .nom(registrationRequest.getNom())
                        .prenom(registrationRequest.getPrenom())
                        .password(passwordEncoder.encode(registrationRequest.getPassword()))
                        .role("CLIENT")
                        .telephone(registrationRequest.getTelephone())
                        .adresse(registrationRequest.getAdresse())
                        .permisConduireValide(registrationRequest.getPermisConduireValide())
                        .build();
            } else if ("MANAGER".equalsIgnoreCase(registrationRequest.getRole())) {
                ourUser = Manager.builder()
                        .email(registrationRequest.getEmail())
                        .nom(registrationRequest.getNom())
                        .prenom(registrationRequest.getPrenom())
                        .password(passwordEncoder.encode(registrationRequest.getPassword()))
                        .role("MANAGER")
                        .build();
            } else {
                throw new IllegalArgumentException("Invalid role provided");
            }

            OurUser savedUser = userRepository.save(ourUser);
            resp.setOurUser(savedUser);
            resp.setMessage("User registered successfully");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setOurUser(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            OurUser users = userRepository.findByEmail(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<OurUser> result = userRepository.findAll();
            if (!result.isEmpty()) {
                reqRes.setOurUsersList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            OurUser usersById = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setOurUser(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                userRepository.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, OurUser updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                OurUser existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
//                existingUser.setName(updatedUser.getName());
//                existingUser.setCity(updatedUser.getCity());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                OurUser savedUser = userRepository.save(existingUser);
                reqRes.setOurUser(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String email){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<OurUser> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setOurUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}
