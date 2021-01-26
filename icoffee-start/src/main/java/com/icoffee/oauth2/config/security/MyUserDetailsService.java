package com.icoffee.oauth2.config.security;

import com.icoffee.oauth2.model.UserDO;
import com.icoffee.oauth2.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name MyUserDetailsService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 11:42
 */
@Slf4j
@Component(value = "myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDO userDO = userService.getByUsername(username);

        // 查询数据库操作
        if(userDO == null){
            throw new UsernameNotFoundException("the user is not found");
        }else{
            // 用户角色也应在数据库中获取
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("/api/user/admin"));
            authorities.add(new SimpleGrantedAuthority("/api/user/coffee"));

            String password = userDO.getPassword();
            return new org.springframework.security.core.userdetails.User(username,password, authorities);
        }
    }
}
