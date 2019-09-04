

package com.spring.web.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.spring.web.core.util.UUIDUtil;
import com.spring.web.security.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  jwt工具类
 * </p>
 * @auth geekidea
 * @date 2019-05-15
 **/
@Slf4j
@Component
public class JwtUtil {

    private static final String UTF8 = "UTF-8";

    private static JwtProperties jwtProperties;

    public JwtUtil(JwtProperties jwtProperties){
        JwtUtil.jwtProperties = jwtProperties;
        log.info(JSON.toJSONString(JwtUtil.jwtProperties));
    }

    public static String create() {
        return create(null,null,null);
    }

    /**
     * 生成jwt token
     * @param expireDate token过期时间
     * @param headerClaims header数据
     * @param payloadClaims body数据
     * @return jwt token
     */
    public static String create(Date expireDate,Map<String,Object> headerClaims, Map<String, Object> payloadClaims) {
        try {
            // 默认过期时间为1小时
            if (expireDate == null) {
                expireDate = DateUtil.offsetMinute(new Date(), jwtProperties.getExpireMinutes());
            }
            log.debug("expireDate:{}", expireDate);

            if (MapUtil.isEmpty(headerClaims)) {
                headerClaims = new HashMap<>();
            }
            log.debug("headerClaims:{}", headerClaims);

            if (MapUtil.isEmpty(payloadClaims)) {
                payloadClaims = new HashMap<>();
            }
            log.debug("payloadClaims:{}", payloadClaims);

            Header header = new DefaultJwsHeader();
            header.setContentType(Header.CONTENT_TYPE);
            header.setType(Header.JWT_TYPE);
            headerClaims.putAll(header);

            // 构建token
            String token = Jwts.builder()
                    .setHeader(headerClaims)
                    .setClaims(payloadClaims)
                    .setId(UUIDUtil.getUUID())                  // jwt唯一id
                    .setIssuer(jwtProperties.getIssuer())       // 签发人.setSubject(jwtProperties.getSubject())     // 主题
                    .setAudience(jwtProperties.getAudience())   // 签发的目标
                    .setIssuedAt(new Date())                    // 签名时间
                    .setExpiration(expireDate)                  // token过期时间
                    // 签名
                    .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret().getBytes(UTF8))
                    .compact();
            return token;
        } catch (Exception e) {
           log.error("create token exception",e);
        }
        return null;
    }

    /**
     * 验证jwt token
     * @param token jwt token
     * @return Jws对象
     */
    public static Jws<Claims> verify(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret().getBytes(UTF8))
                    .parseClaimsJws(token);
            return jws;
        } catch (Exception e) {
            log.error("verify token exception",e);
        }
        return null;
    }

}
