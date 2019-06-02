package com.example.demo.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTDemo {

	private String createTokenWithClaim() {
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    Map<String, Object> map = new HashMap<>();
		    Date nowDate = new Date();
		    Date expireDate = new Date(nowDate.getTime() + 2*60*60*1000L);//设置两小时过期
	        map.put("alg", "HS256");
	        map.put("typ", "JWT");
		    return JWT.create()
		    	/*设置头部信息 Header*/
		    	.withHeader(map)
		    	/*设置 载荷 Payload*/
		    	.withClaim("password", "ni_dong_de")
				.withClaim("loginId","235412")
		        .withIssuer("SERVICE")//签名是有谁生成 例如 服务器
		        .withSubject("this is test token")//签名的主题
		        //.withNotBefore(new Date())//定义在什么时间之前，该jwt都是不可用的.
		        .withAudience("APP")//签名的观众 也可以理解谁接受签名的
		        .withIssuedAt(nowDate) //生成签名的时间
		        .withExpiresAt(expireDate)//签名过期的时间
		        /*签名 Signature */
		        .sign(algorithm);
		} catch (JWTCreationException exception){
			exception.printStackTrace();
		}
		return null;
	}

	private void verifyToken(String token) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("SERVICE")
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		    String subject = jwt.getSubject();
		    Map<String, Claim> claims = jwt.getClaims();
		    Claim claim = claims.get("password");
			Claim claim1 = claims.get("loginId");
		    System.out.println(claim.asString());
		    System.out.println(claim1.asString());
		    List<String> audience = jwt.getAudience();
		    System.out.println(subject);
		    System.out.println(audience.get(0));
		} catch (JWTVerificationException exception){
			exception.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JWTDemo demo = new JWTDemo();
		//String createToken = demo.createToken();
		String createTokenWithClaim = demo.createTokenWithClaim();
		demo.verifyToken(createTokenWithClaim);
	}
}
