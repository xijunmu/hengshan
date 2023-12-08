package com.hengshan.config;

import com.hengshan.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 认证过滤器
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // 注入AuthenticationManager进行用户认证
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码机密处理器
     * 将BCryptPasswordEncoder对象注入到spring容器中,更换掉原来的 PasswordEncoder加密方式
     * 原PasswordEncoder密码格式为：{id}password。它会根据id去判断密码的加密方式。
     * 如果没替换原来的加密方式，数据库中想用明文密码做测试，将密码字段改为{noop}123456这样的格式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 不使用加密方案，明文存储
        return NoOpPasswordEncoder.getInstance();
        // return new BCryptPasswordEncoder();
    }

    /**
     * 认证配置
     * anonymous()：匿名访问：未登录可以访问
     * permitAll()：有没有认证都能访问：登录或未登录都能访问
     * denyAll(): 拒绝
     * authenticated()：认证之后才能访问
     * hasAuthority（）：包含权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 允许跨域
                .cors()
                // 关闭csrf(前后端分离项目要关闭此功能）
                .and().csrf().disable()
                // 禁用session (前后端分离项目，不通过Session获取SecurityContext)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 请求认证配置
                .authorizeRequests()
                // 允许匿名访问：未登录可以访问
                .antMatchers("/login").anonymous()
                .antMatchers("/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
                // .antMatchers("/login").permitAll()// 登录或未登录都能访问
                // .antMatchers("/textMybatis").hasAuthority("system:dept:list22")
                // 任意用户，认证之后才可以访问（除上面外的）
                .anyRequest().permitAll();

        // 添加token过滤器
        // http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置认证失败和授权失败异常处理器
        // http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }

    //@Bean
    //public WebSecurityCustomizer webSecurityCustomizer() {
    //    return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    //}


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
