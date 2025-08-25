package com.adote.security;

import com.adote.repo.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository users;
    public JwtAuthFilter(JwtService jwtService, UserRepository users) { this.jwtService = jwtService; this.users = users; }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String h = request.getHeader("Authorization");
        if(h!=null && h.startsWith("Bearer ")){
            String token = h.substring(7);
            try{
                var jws = jwtService.parse(token);
                String userId = jws.getBody().getSubject();
                var opt = users.findById(userId);
                if(opt.isPresent()){
                    UserDetails ud = org.springframework.security.core.userdetails.User
                            .withUsername(opt.get().getEmail())
                            .password(opt.get().getPasswordHash())
                            .authorities(Collections.emptyList())
                            .build();
                    var auth = new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }catch(Exception ignored){}
        }
        filterChain.doFilter(request, response);
    }
}
