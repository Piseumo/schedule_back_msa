package com.example.dailyLog.service;

import com.example.dailyLog.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.dailyLog.entity.User;
import com.example.dailyLog.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(
                user.getIdx(),
                user.getEmail(),
                user.getPassword(),
                user.getUserName(),
                user.getProfileImage(),
                user.getCalendars()
        );
    }
}
