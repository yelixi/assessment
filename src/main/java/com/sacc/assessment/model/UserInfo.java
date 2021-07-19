package com.sacc.assessment.model;

import com.sacc.assessment.entity.User;
import com.sacc.assessment.enums.UserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Describe: UserDetails实现类，授权对象
 * @Author: tyf
 * @CreateTime: 2021/7/8
 **/
public class UserInfo extends User implements UserDetails  {
    public UserInfo(User user){
        BeanUtils.copyProperties(user, this);
    }

    /**
     * 处理角色
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
        authority.add(new SimpleGrantedAuthority("ROLE_" + UserRole.values()[Integer.parseInt(getRole())].toString()));
        return authority;
    }

    @Override
    public String getUsername() {
        return getStudentId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getId()!=null;
    }
}
