package com.noty.web.middleware;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
public class TransactionMiddleware {

    @Bean
    public FilterRegistrationBean<TransactionFilter> transactionFilter() {
        FilterRegistrationBean<TransactionFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TransactionFilter());
        registration.setName("Request transaction filter");

        return registration;
    }

    public static class TransactionFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {
            String transaction = request.getHeader("Transaction");
            if (StringUtils.hasText(transaction))
                response.setHeader("Transaction", transaction);

            filterChain.doFilter(request, response);
        }
    }

}
