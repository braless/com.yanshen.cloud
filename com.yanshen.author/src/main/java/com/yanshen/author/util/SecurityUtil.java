package com.yanshen.author.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.yanshen.author.entity.BaseQueryPO;
import com.yanshen.author.entity.SystemInfoDTO;
import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 *  ECDSA签名算法
 *  1、获取私钥
 *  2、私钥签名
 *  3、公钥验证
* <p>Title: ECDSAUtil</p>  
* <p>Description: </p>  
* @author zhanghc 
* @date 2019年5月24日
 */
public class SecurityUtil {
	
	private static final String SECRET = "HBZDUUALlGq0NACTJ9Uxtb9sMz2Aa70r";
	
	private static final ThreadLocal<BaseQueryPO> ACTIVE_SYSTEM = new ThreadLocal<BaseQueryPO>();
	
	public static byte[] decryptBASE64(String key) {
		return Base64.decodeBase64(key);
	}

	public static String encryptBASE64(byte[] key) {
		return Base64.encodeBase64String(key);
	}
	
	/**
	 *	 获取公钥和私钥，返回方法，保存缓存，
	 *	1、私钥用于签名
	 *	2、公钥用于签名验证
	 *	
	 * <p>Title: getKey</p>  
	 * <p>Description: </p>  
	 * @author zhanghc
	 * @return
	 */
	public static void getECDSAKey(SystemInfoDTO systeminfo) throws Exception {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(256);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
			ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
			byte[] privateKeyByte = ecPrivateKey.getEncoded();//生成私钥
			byte[] publicKeyByte = ecPublicKey.getEncoded();//生成公钥
			systeminfo.setSecurity_key(encryptBASE64(privateKeyByte));
			systeminfo.setPublic_key(encryptBASE64(publicKeyByte));
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 
	 **使用私钥执行签名
	 * <p>Title: getPrivateKeySign</p>  
	 * <p>Description: </p>  
	 * @author 
	 * @param ecPrivateKey 	私钥
	 * @param plaintext			用于签名的明文
	 * @return sign			返回签名
	 * @throws Exception 
	 */
	public static String getPrivateKeySign(String ecPrivateKey,String plaintext) throws Exception {
		try {
			byte[] privateKeyByte = decryptBASE64(ecPrivateKey);
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature = Signature.getInstance("SHA1withECDSA");
			signature.initSign(privateKey);
			signature.update(plaintext.getBytes());
			byte[] res = signature.sign();//签名
			return encryptBASE64(res);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * ECDSA 验证签名
	 *	
	 * <p>Title: initVerify</p>  
	 * <p>Description: </p>  
	 * @author zhanghc
	 * @param plaintext 			用于签名的明文
	 * @param ecPublicKey 	公钥
	 * @param sign			私钥签名
	 * @return true 验证通过；false 验证失败
	 * @throws Exception 
	 */
	public static boolean initVerify(String ecPublicKey,String plaintext,String sign) throws Exception{
		boolean flag = false;
		try {
			byte[] publicKeyByte = decryptBASE64(ecPublicKey);
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			Signature signature = Signature.getInstance("SHA1withECDSA");
			signature.initVerify(publicKey);
			signature.update(plaintext.getBytes());
			flag = signature.verify(decryptBASE64(sign));
			return flag;
		} catch (Exception e) {
			throw e;
		}

	}
	
	/**
	 * 生成token
	 * @return
	 * @throws BaseException
	 */
	public static String createToken(BaseQueryPO systeminfo) throws Exception {
		Builder builder = JWT.create();
		builder.withClaim("app_id", systeminfo.getApp_id());
		builder.withClaim("saas_tenantid", systeminfo.getSaas_tenantid());
		builder.withClaim("saas_productid", systeminfo.getSaas_productid());
		builder.withClaim("saas_orderid", systeminfo.getSaas_orderid());
		if(systeminfo.getSaas_regionid()!=null) {
			builder.withClaim("saas_regionid", systeminfo.getSaas_regionid());
		}
		builder.withClaim("system_name", systeminfo.getSystem_name());
		String token = builder.sign(Algorithm.HMAC256(SECRET));
		return token;
	}
	
	/**
	 * 生成token
	 * @return
	 * @throws BaseException
	 */
	public static BaseQueryPO verifyToken(String token) throws Exception {
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
		DecodedJWT jwt = verifier.verify(token);
		Map<String, Claim> claims  = jwt.getClaims();
		BaseQueryPO systeminfo = new BaseQueryPO();
		systeminfo.setApp_id(claims.get("app_id").asLong());
		systeminfo.setSaas_tenantid(claims.get("saas_tenantid").asString());
		systeminfo.setSaas_productid(claims.get("saas_productid").asString());
		systeminfo.setSaas_orderid(claims.get("saas_orderid").asString());
		systeminfo.setSystem_name(claims.get("system_name").asString());
		Claim regionClaim = claims.get("saas_regionid");
		if(regionClaim!=null) {
			systeminfo.setSaas_regionid(regionClaim.asString());
		}
		return systeminfo;
	}
	
	public static void setActiveSystem(BaseQueryPO systeminfo) {
		ACTIVE_SYSTEM.set(systeminfo);
    }
	
    public static BaseQueryPO getActiveSystem() {
        return ACTIVE_SYSTEM.get();
    }
	
}