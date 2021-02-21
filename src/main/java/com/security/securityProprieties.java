package com.security;

import com.Filtres.JwtAuthorizationFilter;
import com.Filtres.jwtFilter;
import com.Service.UserDetailsServiceImp;
import com.Service.serviceRoleUserImp;
import com.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class securityProprieties extends WebSecurityConfigurerAdapter {
    @Autowired
    serviceRoleUserImp serviceRoleUserImp;
    @Autowired
    UserDetailsServiceImp userDetailsServiceImp;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();

        // http.formLogin();
        //************************** Secure Resources
        http.authorizeRequests().antMatchers("/h2-console/**", "/RefreshToken/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/userApps/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/saveUser/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/saveUser/**").hasAnyAuthority("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/userApps/**").hasAnyAuthority("USER");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new jwtFilter(authenticationManagerBean()));
        //************************** add Filters
        http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //**************************  Load user in the context
        auth.userDetailsService(userDetailsServiceImp);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
