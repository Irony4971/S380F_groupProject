package groupProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login","/register","/login/**","/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/WEB-INF/jsp/**").permitAll()
                        .requestMatchers("/course380F").permitAll()
                        .requestMatchers("/course380F/lecture/**").authenticated()
                        .requestMatchers("/course380F/createPage/**","/user/**","/delete/**").hasRole("TEACHER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/course380F/lecture/[0-9]+/delete/[0-9]+")).hasRole("TEACHER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/course380F/lecture/[0-9]+/create/[0-9]+")).hasRole("TEACHER")
                        .requestMatchers(RegexRequestMatcher.regexMatcher("/course380F/lecture/[0-9]+/edit/[0-9]+")).hasRole("TEACHER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/course380F")
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                )
                .anonymous(anonymous -> anonymous
                        .principal("guest")
                        .authorities("ROLE_GUEST")
                );
        return http.build();
    }
}