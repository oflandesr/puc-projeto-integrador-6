package br.com.pucc.projetointegradorvi.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DataSource ds;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(ds)
            .authoritiesByUsernameQuery(rolesQuery)
            .usersByUsernameQuery(usersQuery);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                //.requestMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll() // Permitir acesso ao Swagger UI
                .requestMatchers(HttpMethod.GET, "/api-docs/**").permitAll() // Permitir acesso à documentação da API
                .requestMatchers(HttpMethod.GET, "/pi_vi**").permitAll() // Permitir acesso à documentação da API
                .requestMatchers(HttpMethod.GET, "/api/user").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/user").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .httpBasic((httpBasic) -> {}); // Configurando httpBasic sem usar o método deprecado

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

}
