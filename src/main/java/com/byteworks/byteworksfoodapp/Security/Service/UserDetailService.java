package com.byteworks.byteworksfoodapp.Security.Service;

import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Repositories.RestaurantRepository;
import com.byteworks.byteworksfoodapp.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(userRepository.existsByEmail(email)){
            return UserDetailsImpl.build(userRepository.getByEmail(email));
        }else if(restaurantRepository.existsByEmail(email)){
            return UserDetailsImpl.build(restaurantRepository.getByEmail(email));
        }
        else {
            throw new ResourceNotFoundException("Incorrect parameter: email " + email + " does not exist");
        }
    }
}