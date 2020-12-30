package de.othr.bib48218.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] ALLOW_ACCESS_WITHOUT_AUTHENTICATION = {"/css/**", "/img/**", "/fonts/**", "/login", "/register"};

    @Autowired
    private UserDetailsService userSecurityService;

    @Autowired
    private WebSecurityUtilities securityUtilities;

    private BCryptPasswordEncoder passwordEncoder() {
        return securityUtilities.passwordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(ALLOW_ACCESS_WITHOUT_AUTHENTICATION).permitAll()
            .anyRequest().authenticated();
        http.formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error=true")
            .permitAll();
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/?logout")
            .deleteCookies("remember-me")
            .permitAll();
        http.rememberMe();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userSecurityService)
            .passwordEncoder(passwordEncoder());
    }
}