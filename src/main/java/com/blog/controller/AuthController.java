package com.blog.controller;


import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.JWTAuthResponse;
import com.blog.payload.LoginDto;
import com.blog.payload.SignUpDto;
import com.blog.repository.RoleRepository;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){                   //To get data from database we use custom userDetailsService class( This has the data that comes from user)

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());      //Username & passwod entered by user

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);          //this will automatically calls the loadUserByUsername method by using Spring Secrity(it will works like if condition)

        SecurityContextHolder.getContext().setAuthentication(authentication);                   //this will acts as a session variable (here we set the email/username & password)
        // If value is valid/success eligible to use application otherwise not eligible to use application
//        get Token from jwtTokenProvider
        String token= jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));                                  //Here it will send that back as a response
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email id exists: "+signUpDto.getEmail(), HttpStatus.INTERNAL_SERVER_ERROR);         //here we will check whether Email id exists or not
        }

        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username exists: "+signUpDto.getUsername(), HttpStatus.INTERNAL_SERVER_ERROR);      //here we will check whether Username exists or not
        }

        User user= new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles= roleRepository.findByName("ROLE_ADMIN").get();              //here we will defining the role for it.
        user.setRoles(Collections.singleton(roles));

        User savedUser = userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!!", HttpStatus.OK);
    }

}
