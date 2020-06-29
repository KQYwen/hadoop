package com.wen.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//AOP:
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //链式编程
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问，但是功能页只有对应有权限的人才能访问
        //请求授权的规则
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限默认会到登录页面
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login");

        //注销.跳转到主页
        http.logout().logoutSuccessUrl("/");

        //记住我,默认保存两周
        http.rememberMe().rememberMeParameter("remember");

    }

    //认证  springboot 2.1.x 可以直接使用
    //密码编码：PasswordEncoder
    //在spring Secutiry 5。0+中  新增了很多加密方法
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //正常应该从数据库中读


        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("wenbin").password(new BCryptPasswordEncoder().encode("120831")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("120831")).roles("vip1","vip2","vip3")
                .and()
                .withUser("wen").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }
}
