package com.example.nestco.config;

import com.example.nestco.config.oauth.OAuth2UserService;
import com.example.nestco.config.oauth.loginsuccesshandler.LoginSuccessHandler;
import com.example.nestco.config.local.loginfailhandler.CustomLocalLoginFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Bean
    BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests)
                        -> authorizeHttpRequests
                        .requestMatchers("/logined/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                ).formLogin(formLogin ->
                        formLogin.loginPage("/loginForm")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/nestco")
                                .failureHandler(customLocalLoginFailHandler())
                ).logout(logout ->
                        logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/loginForm")
                                .invalidateHttpSession(true)
                ).oauth2Login(oauth ->
                        oauth.loginPage("/loginForm")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(oAuth2UserService))
                                .successHandler(new LoginSuccessHandler())
                )
        ;
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler customLocalLoginFailHandler() {
        return new CustomLocalLoginFailHandler();
    }
}
