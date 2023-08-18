package com.company.proxyproject.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtUtil {
    /**
     * @param token  Json Web Token which is we created
     * @param secret JWT created using <em>secret</em>
     * @return a String which is a JWT subject
     */
    public String getSubject(String token, String secret) {
        Function<Claims, String> function = Claims::getSubject;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return function.apply(claims);
    }

    /**
     * @param subject identifies the principal that is the subject of the JWT.
     * @param secret  JWT is created with a secret key and that secret key is private to you which means you will never reveal that to the public or inject inside the JWT token.
     *                When you receive a JWT from the client, you can verify that JWT with this that secret key stored on the server.
     * @return a String which is a specific Json Web Token
     */
    public String jwt(@NonNull final String subject, @NonNull final String secret) {
        Instant now = Instant.now(Clock.systemDefaultZone());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(7300, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * @param token  Json Web Token
     * @param secret JWT created using secret
     * @return <code>true</code> if JWT subject is not null, otherwise <code>false</code>
     */
    public boolean isTokenValid(String token, String secret) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        String subject = getSubject(token, secret);
        return Objects.nonNull(subject);
    }
}
