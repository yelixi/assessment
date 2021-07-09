package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.UserRepository;
import com.sacc.assessment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 林夕
 * Date 2021/6/15 10:32
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService,UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("username="+username);
        User u = userRepository.queryUserByUsername(username);
        log.error(u.toString());
        return new UserDetail(u);
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
