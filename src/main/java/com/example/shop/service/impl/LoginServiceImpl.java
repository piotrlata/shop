package com.example.shop.service.impl;

import com.example.shop.domain.dto.SuccessfulLoginDto;
import com.example.shop.service.LoginService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AuthenticationManager authenticationManager;

    @Override
    public SuccessfulLoginDto login(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        var claims = new DefaultClaims()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .setSubject(authenticate.getName());
        claims.put("authorities", authenticate.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        var token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "qwertyyuiop")
                .compact();
        return new SuccessfulLoginDto(token);
    }
}
