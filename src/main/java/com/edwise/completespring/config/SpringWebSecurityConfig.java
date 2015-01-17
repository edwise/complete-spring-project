package com.edwise.completespring.config;

import com.edwise.completespring.entities.UserAccountType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@Slf4j
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/**").hasAnyRole(UserAccountType.REST_USER.toString(), UserAccountType.ADMIN_USER.toString())
                .antMatchers("/admin/**").hasRole(UserAccountType.ADMIN_USER.toString())
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
