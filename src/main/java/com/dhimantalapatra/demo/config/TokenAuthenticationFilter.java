package com.dhimantalapatra.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDetailsService userDetailsService;

    public TokenAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("Filtering using jwt token .......");
        String authHeader = request.getHeader("Authorization");
        if(null == authHeader || authHeader.trim().isEmpty() || !authHeader.startsWith("Bearer")){
            response.setStatus(403);
            throw new ServletException("Authorization Header not found");
        }
        logger.debug("Auth header {}",authHeader);
        final String tokenString = authHeader.split(" ")[1];
        logger.debug("tokenString {}",tokenString);
        final String decodedString = new String(Base64.getDecoder().decode(tokenString));
        logger.debug("decodedString {}",decodedString);
        final String username = decodedString.split(", ")[1].split("=")[1].trim();
        logger.debug("username {}",username);
        UserDetails userDetails= userDetailsService.loadUserByUsername(username);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        logger.info("Filtering all OK!");
        filterChain.doFilter(request,response);

    }
}
