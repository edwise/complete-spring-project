package com.edwise.completespring.config;

import com.edwise.completespring.entities.UserAccount;
import com.edwise.completespring.repositories.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@Slf4j
public class SpringSecurityAuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            UserAccount userAccount = userAccountRepository.findByUsername(username);
            if (userAccount != null) {
                return User.withUsername(userAccount.getUsername())
                        .password(userAccount.getPassword())
                        .roles(userAccount.getUserType().toString())
                        .build();
            } else {
                log.warn("Not existing user: {}", username);
                throw new UsernameNotFoundException("Could not find the user '" + username + "'");
            }
        };
    }
}
