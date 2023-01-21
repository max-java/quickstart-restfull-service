package com.tutrit.quickstart.restfull.servise.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class IssueAccessFilter extends OncePerRequestFilter {
    private final String chatIdHeader = "X-TelegramChatId";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        Optional.ofNullable(request.getHeader(chatIdHeader)).ifPresent(System.out::println);
        filterChain.doFilter(request, response);
    }
}
