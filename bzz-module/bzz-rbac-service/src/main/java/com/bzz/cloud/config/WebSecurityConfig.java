package com.bzz.cloud.config;


import com.bzz.cloud.rbac.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.formLogin().loginPage("/login").permitAll()

                .authorizeRequests().antMatchers("/api/getCaptcha").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers( "/api/getCaptcha")
               // .antMatchers("/users/facebook/**")
               // .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**")
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth
                .inMemoryAuthentication()
                .withUser("root")
                .password("root")
                .roles("USER");*/
/*        auth.inMemoryAuthentication()
                .withUser("root")
                .password("root")
                .roles("USER")
                .and()
                .withUser("admin").password("admin")
                .roles("ADMIN", "USER")
                .and()
                .withUser("user").password("user")
                .roles("USER");*/
        auth.userDetailsService(userDetailServiceImpl);


    }



}
