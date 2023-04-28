package com.yanshen.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class ECDSAUtil {
    private final static Logger logger = LoggerFactory.getLogger(ECDSAUtil.class);

    public static void main(String[] args) {

        String ecPrivateKey ="MEECAQAwEwYHKoZIzj0CAQYIKoZIzj0DAQcEJzAlAgEBBCActkKHpaXsfQY1JL3UhPY7Bp3wgj5bAoflIgP8ist31A==";
        String str = "1559704980";
        String sgin = getPrivateKeySign(ecPrivateKey, str);
        System.out.println(sgin);
    }

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
    public static ECDSAKeyEntity getECDSAKey() {
        ECDSAKeyEntity keyEntity = new ECDSAKeyEntity();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
            ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
            byte[] privateKeyByte = ecPrivateKey.getEncoded();//生成私钥
            byte[] publicKeyByte = ecPublicKey.getEncoded();//生成公钥

            keyEntity.setPrivateKey(encryptBASE64(privateKeyByte));
            keyEntity.setPublicKey(encryptBASE64(publicKeyByte));
            return keyEntity;
        } catch (NoSuchAlgorithmException e) {
            logger.error("获取公钥私钥错误",e);
        }
        return null;
    }

    /**
     *
     *	1、使用私钥执行签名
     * <p>Title: getPrivateKeySign</p>
     * <p>Description: </p>
     * @author
     * @param ecPrivateKey 	私钥
     * @param plaintext			用于签名的明文
     * @return sign			返回签名
     */
    public static String getPrivateKeySign(String ecPrivateKey,String plaintext) {
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
            logger.error("获取数字签名",e);
        }
        return null;
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
     */
    public static boolean initVerify(String ecPublicKey,String plaintext,String sign){
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
        } catch (Exception e) {
            logger.error("数字签名验证错误",e);
        }
        return flag;
    }


}
