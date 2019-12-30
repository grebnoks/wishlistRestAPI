package com.sesh.demo.config;

import com.sesh.demo.filters.CORSFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/togglz**", "/togglz-console/**")
                .antMatchers("/mock/**")
                .antMatchers("/static/index.html")
//                .antMatchers("/index.html")
                .antMatchers("/static/**.js")
                .antMatchers("/")
                .antMatchers("/**.ico")
                .antMatchers("/assets/**")
                .antMatchers("/**.css")
                .antMatchers("/**.js")
                .antMatchers("/**.map")
                .antMatchers("/**.eot")
                .antMatchers("/**.svg")
                .antMatchers("/**.ttf")
                .antMatchers("/**.woff")
                .antMatchers("/**.woff2")
                .antMatchers("/log")
                .antMatchers("/stateLog")
                .antMatchers("/health")
                .antMatchers("/metrics")
                .antMatchers("/info")
                .antMatchers("/env")
                .antMatchers("/manage/**")
                .antMatchers("/swagger-ui.html")
                .antMatchers("/program")
                .antMatchers("/manage/prometheus");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
                .csrf().disable()
                .exceptionHandling().and()
                .anonymous().and()
                .servletApi().and()
                .headers().and()
                .authorizeRequests().anyRequest().permitAll();
//                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll();//allow CORS option calls
    }
}
