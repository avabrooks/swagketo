package com.nighthawk.csa.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //RequiredArgsConstructor makes the line below be able to pass that variable in on the fly
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService; //constructor that gets past in below
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //password encoder from spring

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder); //
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        /* web resources ignored from security rules...
            ... added "/uploads/**" as custom resource outside of other with are application standards
            ... removed "/css/**", "/js/**", "/fonts/**" until they are used
         */
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**",  "/images/**", "/scss/**", "/uploads/**")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* security rules ...
            ... initial implementation is focused on protecting database information
            ... "DELETE" is primary concern in authority rules, ADMIN only
            ... "POST", actions desire STUDENT role
         */
        http
                .authorizeRequests()
                .antMatchers(POST, "/api/user/post/**").hasAnyAuthority("ROLE_STUDENT")
                .antMatchers(DELETE, "/api/user/delete/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/database/userupdate/**").hasAnyAuthority("ROLE_STUDENT")
                .antMatchers("/database/userdelete/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers( "/api/user/**").permitAll()
                .antMatchers( "/api/refresh/token/**").permitAll()
                .antMatchers("/", "/starters/**", "/frontend/**", "/mvc/**", "/database/user/**", "/database/usercreate", "/database/scrum/**", "/course/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/process", true)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/userpages/profile")
                .permitAll()
        ;
        // Cross-Site Request Forgery needs to be disabled to allow activation of JS Fetch URIs
        http.csrf().disable();
    }
}