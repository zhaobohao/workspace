package com.gitee.easyopen.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.jwt.JwtService;
import com.gitee.easyopen.message.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author tanghc
 */
public class JwtServiceImpl implements JwtService {
    private static Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static Map<String, Object> headerClaims = new HashMap<>();
    static {
        headerClaims.put("typ", "JWT");
        headerClaims.put("alg", "HS256");
    }

    @Override
    public String createJWT(Map<String, String> data) {
        Builder builder = JWT.create().withHeader(headerClaims);
        Set<Entry<String, String>> entrySet = data.entrySet();
        for (Entry<String, String> entry : entrySet) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        ApiConfig apiConfig = ApiContext.getApiConfig();
        Calendar expiredTime = Calendar.getInstance();
        expiredTime.add(Calendar.SECOND, apiConfig.getJwtExpireIn());

        try {
            return builder
                    // 过期时间
                    .withExpiresAt(expiredTime.getTime())
                    // 创建时间
                    .withIssuedAt(new Date())
                    // 签名
                    .sign(Algorithm.HMAC256(apiConfig.getJwtSecret()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Errors.ERROR_OPT_JWT.getException();
        }
    }

    @Override
    public Map<String, Claim> verfiyJWT(String token) {
        JWTVerifier verifier = null;
        try {
            ApiConfig apiConfig = ApiContext.getApiConfig();
            verifier = JWT.require(Algorithm.HMAC256(apiConfig.getJwtSecret())).build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Errors.ERROR_OPT_JWT.getException();
        }

        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }
        /*
         * @throws AlgorithmMismatchException if the algorithm stated in the
         * token's header it's not equal to the one defined in the {@link
         * JWTVerifier}.
         * 
         * @throws SignatureVerificationException if the signature is invalid.
         * 
         * @throws TokenExpiredException if the token has expired.
         * 
         * @throws InvalidClaimException if a claim contained a different value
         * than the expected one.
         */
        catch (TokenExpiredException e) {
            throw Errors.ERROR_JWT_EXPIRED.getException();
        } 
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw Errors.ERROR_JWT.getException();
        }

        return jwt.getClaims();
    }

}
