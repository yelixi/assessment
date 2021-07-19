package com.sacc.assessment.service.impl;

import com.sacc.assessment.entity.Menu;
import com.sacc.assessment.entity.User;
import com.sacc.assessment.mapper.UserRepository;
import com.sacc.assessment.model.UserInfo;
import com.sacc.assessment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Describe: 用户逻辑
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
@Service
public class UserServiceImpl implements UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByStudentId(s);
        if(user == null){
            throw new UsernameNotFoundException(s);
        }
        //TODO: 获取角色，用户菜单

        return new UserInfo(user);


    }
}
