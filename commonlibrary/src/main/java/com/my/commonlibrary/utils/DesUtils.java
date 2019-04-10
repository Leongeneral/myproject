package com.my.commonlibrary.utils;
 
import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * DES
 * @author Administrator
 *
 */
public class DesUtils {
 
    private final static String DES = "DES";
 
    /**
     * Description DES加密指定字符串
     * @param data 
     * @param key  指定秘钥 只能是8位
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        return strs;
    }
 
    /**
     * Description DES解密指定字符串
     * @param data
     * @param key  指定秘钥 只能是8位
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf,key.getBytes());
//        if (Urls.getHost(false).contains("192.168.80.175")) {
////			return new String(bt, "UTF-8");
//        	return new String(bt, "GBK");
//		}
        String result = new String(bt, "GBK");
        if(result.contains("&lt;br/&gt;")){
            result = result.replace("&lt;br/&gt;","\n");
        }
        if(result.contains("<br/>")){
            result = result.replace("<br/>","\n");
        }
        return result;
//        return new String(bt, "GBK");
//        return new String(bt, "UTF-8");
    }
 
    /**
     * Description 对指定数组用指定秘钥数组加密
     * @param data
     * @param key  指定秘钥数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    	
        SecureRandom sr = new SecureRandom();
 
        DESKeySpec dks = new DESKeySpec(key);
 
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
 
        Cipher cipher = Cipher.getInstance(DES);
 
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 对指定数组用指定秘钥数组解密
     * @param data
     * @param key  指定秘钥数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        return cipher.doFinal(data);
    }
}