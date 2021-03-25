package io.github.revelationgame.gateway.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@ConfigurationProperties(prefix = "revelation.security")
public class SecurityConfig {

    public static final String ROLE_ADMIN = "ADMIN";
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Bean
    MapReactiveUserDetailsService authentication() {
        return new MapReactiveUserDetailsService(
                User.withDefaultPasswordEncoder().username(username).password(password).roles(ROLE_ADMIN).build()
        );
    }

    @Bean
    SecurityWebFilterChain authorization(ServerHttpSecurity security) {
        return security.authorizeExchange()
                // Stupid workaround to make all security paths required admin privileges
                .pathMatchers("/sec/**").hasRole(ROLE_ADMIN)
                .pathMatchers("/*/sec/**").hasRole(ROLE_ADMIN)
                .pathMatchers("/*/*/sec/**").hasRole(ROLE_ADMIN)
                .pathMatchers("/*/*/*/sec/**").hasRole(ROLE_ADMIN)
                .pathMatchers("/**").permitAll()
                .and()
                .httpBasic()
                .and()
                .build();
    }

}
