package com.spf.common.arithmetic;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by SPF on 2017/7/20.
 */
public class DES {
    private static byte[] iv = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

    /**
     * 加密
     * @param encryptString 数据
     * @param encryptKey 钥匙
     * @return
     * @throws Exception
     */
    public static String encryptDESwithBase64(String encryptString, String encryptKey) {
        try {
            return new String(Base64.encodeBase64(encryptDES(encryptString, encryptKey)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static byte[] encryptDES(String encryptString, String encryptKey){
        byte[] encryptedData = null;
        try{
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        return encryptedData;

    }

    /**
     * 解密
     * @param encryptedString
     * @param decryptKey
     * @return
     */
    public static String decryptDESwithBase64(String encryptedString, String decryptKey) {
        String result = null;
        try {
            byte[] encryptedData = Base64.encodeBase64(encryptedString.getBytes());
            result = decryptDES(encryptedData, decryptKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decryptDES(byte[] encryptedData, String decryptKey) {
        String decryptedString = null;
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(iv);
            SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "DES");

            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

            byte decryptedData[] = cipher.doFinal(encryptedData);

            decryptedString = new String(decryptedData, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decryptedString;

    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Map<String,String> requestMap = Maps.newHashMap();
        requestMap.put("order_no","556561561655");
        requestMap.put("order_status","2000");
        requestMap.put("member_id","12345");
        requestMap.put("trade_no","12359654782");
        requestMap.put("total_fee","12155");
        String str = DES.encryptDESwithBase64(JSON.toJSONString(requestMap),"4bbd85de");
        System.out.println(str);
    }
}
