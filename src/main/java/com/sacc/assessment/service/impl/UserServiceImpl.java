package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.Role;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.UserRepository;
import com.sacc.assessment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/6/15 10:32
 */
@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService,UserService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error("username=" + username);
        List<User> u = userRepository.findByUsername(username);
        log.error(u.toString());
        return new UserDetail(u.get(0));
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
        return true;
    }

    @Override
    public List<User> getAllUser() {
        log.error(userRepository.findAll().toString());
        return userRepository.findAll();
    }

    @Override
    public boolean updateRole(String username, Role role) {
        List<User> u = userRepository.findByUsername(username);
        u.get(0).setRole(role);
        userRepository.save(u.get(0));
        return true;
    }
}

