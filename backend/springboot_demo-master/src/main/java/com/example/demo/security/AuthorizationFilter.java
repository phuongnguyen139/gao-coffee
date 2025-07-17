package com.example.demo.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    
    public AuthorizationFilter(AuthenticationManager authManager, UserRepository userRepository) {
        super(authManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication;
        try {
            authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws Exception {

        String authorizationHeader = request.getHeader(SecurityConstants.HEADER_STRING);

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

        byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.getTockenSecrect().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        String user;
        JwtParser jwtParser2 = Jwts.parser().verifyWith(secretKey).build();
        try {
            Jws<Claims> jwsClaims = jwtParser2.parseClaimsJws(token);
            Claims claims = jwsClaims.getBody();
            user = claims.getSubject();

        } catch (Exception e) {
            throw new Exception("Could not verify JWT token integrity!", e);
        }

        if (user != null) {
            UserEntity userEntity = userRepository.findByEmail(user);
            UserPricipal userPricipal = new UserPricipal(userEntity);
            return new UsernamePasswordAuthenticationToken(user, null, userPricipal.getAuthorities());
        }

        return null;

    }

}
