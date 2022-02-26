//package ru.rodichev.webBlog.session;
//
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.context.annotation.*;
//import org.springframework.security.config.annotation.authentication.builders.*;
//import org.springframework.security.config.annotation.web.configuration.*;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("ADMIN");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/").hasRole("ADMIN")
//                .anyRequest().authenticated();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}