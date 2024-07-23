package cn.mydemo.simpleblogsystem.config;

import cn.mydemo.simpleblogsystem.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class webSecurityConfig {

    @Autowired
    private MyAuthorizationManager myAuthorizationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(("/api/auth/login"))
                    .permitAll()
                    .requestMatchers("/api/auth/register")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/posts")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/posts/{id}")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
                  //  .access(myAuthorizationManager);

        })
                .csrf(AbstractHttpConfigurer :: disable);


        httpSecurity.addFilterBefore(new MyAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();


    }

    @Bean
    AuthenticationManager authenticationManager(){

        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider();

        jwtAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        ProviderManager pm = new ProviderManager(jwtAuthenticationProvider);

        return pm;

    }
}
