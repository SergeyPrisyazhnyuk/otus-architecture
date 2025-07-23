package ru.otus.gameauth.impl;

import static java.util.Optional.ofNullable;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import ru.otus.gameauth.service.GameAuthorizationService;
import ru.otus.gameauth.service.GameAuthorizerService;
import ru.otus.gameauth.service.JwtCreator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtService implements JwtCreator, GameAuthorizerService {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("arch");
    private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM).build();
    private final GameAuthorizationService gameAuthorizationService;

    public String create(String gamerId, String gameId){
        return JWT.create()
                .withIssuer("Authorization module")
                .withSubject("Gamer details")
                .withClaim("gamerId", gamerId)
                .withClaim("gameId", gameId)
                .withClaim("roles", gameAuthorizationService.getGamersRoles(gamerId, gameId))
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis() + 1000L))
                .sign(ALGORITHM);
    }

    public boolean authorize(String token, String gameId, String role) {
        DecodedJWT decodedJWT = VERIFIER.verify(token);
        Claim claimRoles = decodedJWT.getClaim("roles");
        List<String> roles = claimRoles.asList(String.class);
        Claim claimGameId = decodedJWT.getClaim("gameId");
        String authGameId = claimGameId.asString();
        return ofNullable(roles).orElse(new ArrayList<>()).contains(role) && Objects.equals(authGameId, gameId);
    }
}
