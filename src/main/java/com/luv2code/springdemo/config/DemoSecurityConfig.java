package com.luv2code.springdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("EMPLOYEE"))
                .withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
                .withUser(users.username("susan").password("test123").roles("EMPLOYEE", "ADMIN"));
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // secures all REST endpoints under "/api/customers"
        // and adds following security authorizations
        //
        // EMPLOYEE role can perform following
        // 1. Get a list of all customers.  GET /api/customers
        // 2. Get a single customer.  GET /api/customers/{customerId}

        //
        // MANAGER role can perform following
        // 1. Add a new customer.  POST /api/customers
        // 2. Update an existing customer.  PUT /api/customers
        //

        //
        // ADMIN role can perform following
        // 1. Delete a customer.  DELETE /api/customers/{customerId}
        //

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/customers").hasRole("EMPLOYEE")
                .antMatchers(HttpMethod.GET, "/api/customers/**").hasRole("EMPLOYEE")
                .antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
