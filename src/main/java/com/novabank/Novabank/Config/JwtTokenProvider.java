package com.novabank.Novabank.Config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

        @Value("${app.jwt-secret}")
        private String jwtSecret;

        @Value("${app.jwt-expiration}")
        private long jwtExpirationDate;

        public String generateToken(Authentication authentication){
            String userName=authentication.getName();
            Date currDate=new Date();
            Date expire=new Date(currDate.getTime()+jwtExpirationDate);

            return Jwts.builder().setSubject(userName)
                    .setIssuedAt(currDate)
                    .setExpiration(expire)
                    .signWith(key())
                    .compact();

        }

        private Key key(){

            byte[] bytes= Decoders.BASE64.decode(jwtSecret);
            return Keys.hmacShaKeyFor(bytes);
        }
        public String getUsername(String token){
           Claims claims=Jwts.parser().setSigningKey(key())
                   .build().parseClaimsJws(token).getPayload();

           return claims.getSubject();
        }

        public boolean validateToken(String token){
            try{
                Jwts.parser().setSigningKey(key())
                        .build().parse(token);
                return true;
            }catch (Exception e){
                throw new RuntimeException();
            }
        }

}
