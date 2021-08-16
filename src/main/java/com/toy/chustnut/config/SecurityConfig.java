package com.toy.chustnut.config;

import com.toy.chustnut.model.Role;
import com.toy.chustnut.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth-login").permitAll() // login URL에는 누구나 접근 가능하게 합니다.
                .anyRequest().authenticated() // 그 이외에는 인증된 사용자만 접근 가능하게 합니다.
                .and()
                .oauth2Login() // oauth2Login 설정 시작
                .userInfoEndpoint() // oauth2Login 성공 이후의 설정을 시작
                .userService(customOAuth2UserService); // customOAuth2UserService에서 처리하겠다.

        /* 설명
           1. @EnableWebSecurity - Spring Security 설정을 활성화 해준다.
           2. .csrf().disable().headers().frameOptions().disable()
           3. authorizeRequests - URL 별 권한 관리를 설정하는 옵션의 시작점, antMatchers 옵션을 사용가능하게해줌
           4. antMatchers - 권한 관리 대상을 지정하는 옵션, URL / HTTP 메소드 별로 관리가 가능
           5. anyRequest - 설정된 값들 이외 나머지 URL들을 나타냄
           6. logout().logoutSuccessUrl("/") - 로그아웃 기능에 대한 여러 설정의 진입점
           7. userInfoEndpoint - OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
         */
    }
}
