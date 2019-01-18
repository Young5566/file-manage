package com.young.filemanage.utils;

import com.young.filemanage.entity.User;
import com.young.filemanage.exception.YoungException;
import com.young.filemanage.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;
import java.util.Date;
import java.util.Map;

/**
 * @Author: Young
 * @QQ: 403353323
 * @Date: 2019/1/18 11:43
 */
public class JWT {
    private static final String SECRET_KEY = "itisacerettttsss";

    /**
     * 生成token
     * @param payload 用户信息体
     * @return 返回token字符串
     */
    public static String createToken(User payload){
        String compactJws = Jwts.builder()
                // 设置头部
                .setHeaderParam("type", "JWT")
                //jwt 标注中的申明
                .setIssuedAt(new Date()) //签发时间
                .setExpiration(new Date(new Date().getTime() + 1000*60*120L)) // 设置过期时间
                .setIssuer("Young") // jwt签发者

                // 设置jwt携带信息
                .claim("id", payload.getUuid())
                .claim("username", payload.getUsername())

                // 签证
                .setSubject("rkapi") //设置主题
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()) //设置算法
                .compact(); // 这是全部设置完成后拼接JWT串的方法

        return compactJws;
    }

    /**
     * 验证token字符串
     * @param token token字符串
     * @return 返回解析信息
     */
    public static Map verifiyToken(String token){
        try {
            // 验证jwt
            Jws<Claims> claimsJwt = Jwts.parser()
                    // 验证签发者字段iss，必须是Young
                    .require("iss", "Young")
                    // 验证私钥
                    .setSigningKey(SECRET_KEY.getBytes())
                    // 解析jwt字符串
                    .parseClaimsJws(token);
            // 获取头部信息
            JwsHeader header = claimsJwt.getHeader();
            // 获取消息体
            Claims payload = claimsJwt.getBody();
            return payload;
        } catch (Exception e){
            e.printStackTrace();
            throw new YoungException(ResultEnum.NO_TOKEN, "token错误");
        }
    }
}
