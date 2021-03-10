package com.icoffee.security.config.authentication;

import com.icoffee.common.dto.AuthorityDto;
import com.icoffee.system.domain.User;
import com.icoffee.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name UserDetailsServiceImpl
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 11:42
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userDto = userService.getByUsername(username);

        // 查询数据库操作
        if (userDto == null) {
            throw new UsernameNotFoundException("the user is not found");
        } else {
            //获取用户授权信息
            List<AuthorityDto> userGrantedAuthorities = userService.getUserGrantedAuthorities(userDto.getRoleId());

            // 用户角色也应在数据库中获取
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            for (AuthorityDto authorityDto : userGrantedAuthorities) {
                authorities.add(new SimpleGrantedAuthority(authorityDto.getPermission()));
            }

            String password = userDto.getPassword();
            return new org.springframework.security.core.userdetails.User(username, password, authorities);
        }
    }
}
