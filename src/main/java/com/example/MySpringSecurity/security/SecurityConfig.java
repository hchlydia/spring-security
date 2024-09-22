package com.example.MySpringSecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private static final String NORMAL_MEMBER = "NORMAL_MEMBER";
    private static final String VIP_MEMBER = "VIP_MEMBER";
    private static final String MOVIE_MANAGER = "MOVIE_MANAGER";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //使用BCrypt演算法進行密碼加密
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //設定Session的創建機制 (Spring Security 6.X後要添加這一段才會去創建Session和Cookie)
                //登入後就可以透過Cookie的JSessionID通過需要認證的API
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) //在Cookie裡面回傳XSRF-TOKEN的值給前端
                        .csrfTokenRequestHandler(createCsrfHandler())
                        .ignoringRequestMatchers("/register", "/userLogin") //關閉這些API的CSRF防禦，這樣前端不用帶上X-XSRF-TOKEN也可以成功請求
                )

                .cors(cors -> cors
                        .configurationSource(createCorsConfig()))

                .addFilterBefore(new Filter(), BasicAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request

                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/userLogin").authenticated()

                        .requestMatchers(HttpMethod.GET, "/movie").hasAnyRole(NORMAL_MEMBER, MOVIE_MANAGER, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/movie/free").hasAnyRole(NORMAL_MEMBER, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/movie/vip").hasAnyRole(VIP_MEMBER, ADMIN)
                        .requestMatchers(HttpMethod.POST, "/movie/upload").hasAnyRole(MOVIE_MANAGER, ADMIN)
                        .requestMatchers(HttpMethod.DELETE, "/movie/delete").hasAnyRole(MOVIE_MANAGER, ADMIN)

                        .requestMatchers(HttpMethod.POST, "/subscribe", "/unsubscribe").hasAnyRole(NORMAL_MEMBER, ADMIN)

                        .anyRequest().denyAll()) //deny by default

                .build();
    }

    private CsrfTokenRequestAttributeHandler createCsrfHandler() {
        CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();
        csrfHandler.setCsrfRequestAttributeName(null);

        return csrfHandler;
    }

    private CorsConfigurationSource createCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
