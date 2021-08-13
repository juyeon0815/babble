package com.babble.config;

import com.babble.api.service.UserService;
import com.babble.common.auth.JwtAuthenticationFilter;
import com.babble.common.auth.BabbleUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;

/**
 * 인증(authentication) 와 인가(authorization) 처리를 위한 스프링 시큐리티 설정 정의.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BabbleUserDetailService BabbleUserDetailService;

    @Autowired
    private UserService userService;

    // Password 인코딩 방식에 BCrypt 암호화 방식 사용
    // 암호화에 필요한 passwordEncoder bean등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DAO 기반으로 Authentication Provider를 생성
    // BCrypt Password Encoder와 UserDetailService 구현체를 설정
    // 클라이언트로부터 인증 의뢰를 받으면 DaoAuthenticationProvider에 인증 처리를 위임
    // DaoAuthenticationProvider에서 해당 사용자 정보 가져와 클라이언트가 제공한 인증정보를 대조해 이용자가 정당한 사용권한을 가지고 있는지 확인
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.BabbleUserDetailService);
        return daoAuthenticationProvider;
    }

    // DAO 기반의 Authentication Provider가 적용되도록 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) { //111111111111
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { //22222222222
        http
                .httpBasic().disable()//rest api만을 고려해 기본 설정 해제
                .csrf().disable() //csrf 보안 토큰 disable처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 하지않음
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userService)) //HTTP 요청에 JWT 토큰 인증 필터를 거치도록 필터를 추가
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/api/v1/users/me").authenticated()   // api/v1/uesrs/me 요청은 인증되어야함
                //.antMatchers(HttpMethod.GET,"/api/v1/users/**").authenticated()
//                .antMatchers(HttpMethod.PATCH,"/api/v1/users/**").authenticated() // 회원 정보 수정
//                .antMatchers(HttpMethod.DELETE,"/api/v1/users/**").authenticated() // 회원 탈퇴
                //.antMatchers("/api/v1/room/create").authenticated() // 방 생성
                .antMatchers("/ws/pub/**").authenticated()
                .anyRequest().permitAll() // 그 외 나머지 요청은 누구나 접근 가능
                .and().cors();
    }
}