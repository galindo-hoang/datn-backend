package com.example.backendservice.common.configure;

import com.example.backendservice.common.utils.Constants;
import com.example.backendservice.common.utils.JwtTokenUtil;
import com.example.backendservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isRestrict(request)) {
            final String requestTokenHeader = request.getHeader("Authorization");
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                String jwtToken = requestTokenHeader.substring("Bearer ".length());
                String userName = jwtTokenUtil.getUserNameFromToken(jwtToken);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null && jwtTokenUtil.validateToken(jwtToken, userName)) {
                    UserDetails userDetails = this.userService.loadUserByUsername(userName);
                    filterChain.doFilter(request, response);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // After setting the Authentication in the context, we specify
                    // that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    new ObjectMapper().writeValue(response.getOutputStream(), Constants.TOKEN + Constants.IN_VALID);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), Constants.TOKEN + Constants.IN_VALID);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isRestrict(HttpServletRequest request) {
        String path = request.getServletPath();
        if(path.contains("prescription") || (path.contains("feedback") && path.contains("add"))) return false;
        return path.contains("/update") || path.contains("/delete") || path.contains("/add");
    }

}
