package com.example.dbdemo.config;

import com.example.dbdemo.filter.ApplicationHttpServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class WebMvcConfig {

    @Bean
	public FilterRegistrationBean customHttpServletFilterRegister() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new ApplicationHttpServletFilter());
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registration;
	}
}
