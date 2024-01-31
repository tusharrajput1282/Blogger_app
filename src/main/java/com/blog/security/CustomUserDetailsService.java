package com.blog.security;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //SEARCH THE RECORD IN DATABASE BASED ON EMAIL OR USERNAME

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)             //this has the data which will coming from database & this will actually compare username & password in database.
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),               //here we use class name becuz, it will comes from this package( ex:-same class name diff packages( p1.A, p2.A))
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));                         //here Role is Set
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList ());
    }
}

