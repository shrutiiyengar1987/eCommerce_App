package com.c9.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.okta.spring.config.OktaOAuth2Properties;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	private final OktaOAuth2Properties oktaOAuth2Properties;
	
	public SecurityConfig(OktaOAuth2Properties oktaOAuth2Properties) {
		this.oktaOAuth2Properties = oktaOAuth2Properties;
	}

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		//.exceptionHandling()
       // .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(oktaOAuth2Properties.getRedirectUri()))
       // .accessDeniedHandler((req, res, e) -> res.sendRedirect("/403"))
		.oauth2Login()
			.loginPage("/login").and()
            .authorizeRequests()
            .antMatchers("/", "/login", "/css/**","/add-user").permitAll()
            .antMatchers("/**").authenticated()

    // send the user back to the root page when they logout
    .and()
        .logout().logoutSuccessUrl("/");
		/*http
        // add our SSO Filter in place
       // .addFilterAfter(oktaSsoFilter, AbstractPreAuthenticatedProcessingFilter.class)
        .exceptionHandling()
            .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(oktaOAuth2Properties.getRedirectUri()))
            .accessDeniedHandler((req, res, e) -> res.sendRedirect("/403"))

        // allow anonymous users to access the root page
        .and()
            .authorizeRequests()
                .antMatchers("/", "/login", "/css/**","add-user").permitAll()
                .antMatchers("/**").authenticated()

        // send the user back to the root page when they logout
        .and()
            .logout().logoutSuccessUrl("/");*/
	}
	

}
