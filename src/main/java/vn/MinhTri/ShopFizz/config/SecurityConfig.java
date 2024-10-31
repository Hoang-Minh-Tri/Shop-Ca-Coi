package vn.MinhTri.ShopFizz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.MinhTri.ShopFizz.services.CustomUserDetailsService;
import vn.MinhTri.ShopFizz.services.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSuccess();
    }

    // Dùng CustomUserDetailService để ghi đè lại UserDetailsService của spring
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new CustomUserDetailsService(userService);
    }

    // Cấu hình login
    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        // authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, // Cho phép truy cập tới view
                                DispatcherType.INCLUDE) // chặn các include
                        .permitAll()
                        .requestMatchers("/", "/login", "/products/**", "/client/**", "/css/**", "/js/**",
                                "/images/**", "/product/**", "/register", "/forgot")
                        .permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)// chưa có session thì tạo session mới
                        .invalidSessionUrl("/logout?expired")// hết hạn session thì logout
                        .maximumSessions(1)// số lượng thiết bị có thể đăng nhập cùng lúc
                        .maxSessionsPreventsLogin(false))// fasle: người sau đá người trước, true thì người sau phải đợi
                                                         // người trước hết hạn
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))
                .rememberMe((rememberMe) -> rememberMe.rememberMeServices(rememberMeServices()))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")// login faile thì trả về đường link này
                        .successHandler(authenticationSuccessHandler())
                        .permitAll())
                .exceptionHandling(ex -> ex.accessDeniedPage("/access"));// Nếu truy câp không đúng quyền hạn
        return http.build();
    }

}
