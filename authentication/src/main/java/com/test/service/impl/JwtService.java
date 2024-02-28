package com.test.service.impl;

import com.test.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.test.constant.CommonConstant.*;

@Service
public class JwtService {

    /**
     * Retrieves the signing key for JWT token validation.
     *
     * @return JWT signing key
     */
    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    /**
     * Generates a JWT token for a user based on UserDetails.
     *
     * @param userDetails UserDetails of the user
     * @return Generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims for a user based on UserDetails.
     *
     * @param extractClaims Additional claims to include in the token
     * @param userDetails   UserDetails of the user
     * @return Generated JWT token
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(DateUtils.getCurrentTimeMillis()))
                .setExpiration(new Date(DateUtils.getCurrentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token JWT token
     * @return Extracted username
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token using the provided resolver function.
     *
     * @param token         JWT token
     * @param claimResolver Resolver function for extracting a specific claim
     * @param <T>           Type of the extracted claim
     * @return Extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token JWT token
     * @return All extracted claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
