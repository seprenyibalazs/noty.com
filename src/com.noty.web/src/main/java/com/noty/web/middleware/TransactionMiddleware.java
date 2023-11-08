package com.noty.web.middleware;

import com.noty.web.services.TrackingResult;
import com.noty.web.services.TransactionTracker;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class TransactionMiddleware {

    private final TransactionTracker tracker;

    @Bean
    public FilterRegistrationBean<TransactionFilter> transactionFilter() {
        FilterRegistrationBean<TransactionFilter> registration = new FilterRegistrationBean<>();
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Integer.MAX_VALUE);
        registration.setFilter(new TransactionFilter(tracker));
        registration.setName("Request transaction filter");

        return registration;
    }

    @RequiredArgsConstructor
    public static class TransactionFilter extends OncePerRequestFilter {

        public static final String TRANSACTION_HEADER = "Transaction";
        private final TransactionTracker tracker;

        private boolean doTransactionFilter(
                HttpServletRequest request,
                HttpServletResponse response
        ) {
            String transaction = request.getHeader(TRANSACTION_HEADER);
            if (StringUtils.hasText(transaction))
                response.setHeader("Transaction", transaction);
            else
                return false;

            if ("GET".equalsIgnoreCase(request.getMethod()))
                return false;

            String serial = (String) request.getAttribute("serial");
            if (!StringUtils.hasText(serial))
                return false;

            tracker.purgeTransactions();
            TrackingResult result = tracker.trackTransaction(serial, transaction);

            return result == TrackingResult.FOUND;
        }

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain
        ) throws ServletException, IOException {
            if (doTransactionFilter(request, response))
                response.setStatus(HttpStatus.NOT_MODIFIED.value());
            else
                filterChain.doFilter(request, response);
        }
    }

}
