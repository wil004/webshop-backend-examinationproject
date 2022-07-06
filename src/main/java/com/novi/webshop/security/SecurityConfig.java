package com.novi.webshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtService jwtService;

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authProvider())
                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user/create-admin").permitAll()
                .antMatchers(HttpMethod.POST,"/user/create-employee").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/attachment/upload").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/attachment/product={productId}/file={fileId}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/download/{fileId}").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/customer").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/customer/id={id}").hasAnyAuthority("ADMIN", "EMPLOYEE", "CUSTOMER")
                .antMatchers(HttpMethod.GET,"/customer/orderhistory/customerid={id}").hasAnyAuthority("ADMIN", "EMPLOYEE", "CUSTOMER")
                .antMatchers(HttpMethod.POST, "/customer").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT,"/shoppingcart/id={customerId}/productid={productId}").hasAnyAuthority("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.DELETE,"shoppingcart/{id}").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/order").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/order/id={id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/order/processed-status={processed}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/order/{firstName}/{lastName}/{zipcode}/{houseNumber}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/order/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/order/paid-order/id={id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/order/change-order-processed-status={processed}/id={id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/order/customer={customerId}").hasAnyAuthority("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.POST,"/order/guest={customerId}").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/returns").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/returns/processed-status={processed}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/returns/{firstName}/{lastName}/{zipcode}/{houseNumber}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/returns/{firstName}/{lastName}/{zipcode}/{houseNumber}/{additionalHouseNumber}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/returns/change-processed-status={processed}/id={id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/returns/id={returnsId}/productid={productId}").permitAll()
                .antMatchers(HttpMethod.POST,"/returns/{orderId}").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/product").permitAll()
                .antMatchers(HttpMethod.GET,"/product/name/{productName}").permitAll()
                .antMatchers(HttpMethod.GET,"/product/category/{category}").permitAll()
                .antMatchers(HttpMethod.GET,"/product/price/{minimumPrice}/{maximumPrice}").permitAll()
                .antMatchers(HttpMethod.PUT,"/product/change/{id}/{type}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST,"/product").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/employee").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/employee/id={employeeId}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/employee/confirm-payment/employee-id={employeeId}/order-id={orderId}/ispaid={isPaid}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/employee/process-order/employee-id={employeeId}/order-id={orderId}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/employee/process-returns/employee-id={employeeId}/returns-id={returnsId}").hasAnyAuthority("ADMIN", "EMPLOYEE")
                .antMatchers(HttpMethod.PUT,"/employee/id={employeeId}/order-id={orderId}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/employee/id={employeeId}/returns-id={returnsId}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/employee/divide-orders").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/employee/divide-returns").hasAuthority("ADMIN")
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/pdf/generate-order/id={orderId}").permitAll()
                .antMatchers(HttpMethod.GET, "/pdf/generate-return/id={returnsId}").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

}
