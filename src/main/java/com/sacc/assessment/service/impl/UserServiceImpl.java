package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.Role;
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
        User u = userRepository.queryUserByUsername(username);
        u.setRole(role);
        userRepository.save(u);
        return true;
    }

    /*@Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByStudentId(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }
        //TODO: 获取角色，用户菜单

        return new UserInfo(user);


    }*/
}
