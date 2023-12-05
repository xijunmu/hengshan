package com.hengshan.config;

import com.hengshan.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig{

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    //用Bcrypt算法加密密码
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //禁用csrf（跨站请求伪造）
                //.formLogin().disable().httpBasic().disable() //登录行为由自己实现
                //.logout().disable() //登出行为由自己实现
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不管理Session
                .and().authorizeRequests() //过滤请求
                .antMatchers("/login").anonymous() //登录接口允许匿名访问
                .anyRequest().authenticated(); //其余所有请求都需要认证
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);//添加自定义过滤器
        http.cors();//允许跨域
        return http.build();
    }

    //@Bean
    //public WebSecurityCustomizer webSecurityCustomizer() {
    //    return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    //}

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .inMemoryAuthentication().and().build();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encod1 = encoder.encode("123");
        String encod2 = encoder.encode("123");
        System.out.println(encod1);
        System.out.println(encod2);
        boolean matches = encoder.matches("123", encod1);
        boolean matches1 = encoder.matches("123", encod2);
        System.out.println(matches);
        System.out.println(matches1);
    }
}
