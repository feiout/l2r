package com.l2r.utils;

/**
 * Created by sean on 15/11/27.
 */

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class ThreeDESUtil {
    // 算法名称
    public static final String KEY_ALGORITHM = "desede";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";

    private static final String DES_KEY = "6C4E60E55552386C759569836DC0F83869836DC0F838C0F7";

    /**
     * CBC加密
     * @param data 明文
     * @return 密文
     * @throws Exception
     */
    public static String des3Encode(String data)
    {
        try
        {
            byte[] key = DES_KEY.getBytes();
            byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
            byte[] dataBytes = data.getBytes("UTF-8");
            System.out.println("data.length=" + dataBytes.length);
            System.out.println("CBC加密解密");
            byte[] encodeData = des3EncodeCBC(key, keyiv, dataBytes);
            return new BASE64Encoder().encode(encodeData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * CBC解密
     * @param data 密文
     * @return 明文
     * @throws Exception
     */
    public static String des3Decode(String data)
    {
        try
        {
            byte[] key = DES_KEY.getBytes();
            byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
            byte[]  dataBytes = new sun.misc.BASE64Decoder().decodeBuffer(data);
            byte[] decodeData = ThreeDESUtil.des3DecodeCBC(key, keyiv, dataBytes);
            return new String(decodeData,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * CBC加密
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        for (int k = 0; k < bOut.length; k++) {
            System.out.print(bOut[k] + " ");
        }
        System.out.println("");
        return bOut;
    }

    /**
     *
     * 生成密钥key对象
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws Exception
     */
    private static Key keyGenerator(String keyStr) throws Exception {
        byte input[] = HexString2Bytes(keyStr);
        DESedeKeySpec KeySpec = new DESedeKeySpec(input);
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }

    private static int parse(char c) {
        if (c >= 'a') return (c - 'a' + 10) & 0x0f;
        if (c >= 'A') return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }

    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    /**
     * CBC解密
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data) throws Exception {
        Key deskey = keyGenerator(new String(key));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }
    public static String aesECBDecrypt(String str) throws Exception {
        String key = "XOP@3Uav24M!VE^qO]wF2Ro)H7v$j@sD";
        byte[] sourceBytes = AndroidBase64Util.decode(str, 0);
        try
        {
            Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher localCipher = Cipher.getInstance("AES/ECB/NoPadding");
            localCipher.init(2, keySpec);
            localCipher.getBlockSize();
            String result = new String(localCipher.doFinal(sourceBytes),"UTF-8");
            return result;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
//        String data = "jiadian@2015";
        String data = "rootroot";
        System.out.println("data.length=" + data.length());
        System.out.println("CBC加密解密");
        String desData = des3Encode(data);
        System.out.println(desData);

        String encodeData = des3Decode(desData);
        System.out.println(encodeData);
    }
    /*ECB*/
        public static String Decrypt(String paramString)
    {
        try
        {
            byte[] bytes = deBase64(paramString);
            Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            localCipher.init(2, generateKey("Stark"));
            try
            {
                paramString = new String(localCipher.doFinal(bytes), "utf-8");
                return paramString;
            }
            catch (Exception ex)
            {
                return null;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
    }
    private static byte[] deBase64(String paramString)
    {
        return Base64Util.base64StrToByteArray(paramString);
    }

    private static String enBase64(byte[] paramArrayOfByte)
    {
        return Base64Util.byteArrayToBase64Str(paramArrayOfByte);
    }

    protected static Key generateKey(String paramString)
    {
        try
        {
            paramString = hexdigest(paramString).substring(2, 18);
            if (paramString == null)
            {
                return null;
            }
            if (paramString.length() != 16)
            {
                return null;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
        Key key = null;
        try {
            key = new SecretKeySpec(paramString.getBytes("utf-8"), "AES");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return key;
    }
    public static String Encrypt(String paramString)
    {
        try
        {
            Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            localCipher.init(1, generateKey("Stark"));
            paramString = enBase64(localCipher.doFinal(paramString.getBytes("utf-8")));
            return paramString;
        }
        catch (Exception ex)
        {
        }
        return null;
    }
    private static final char[] hexDigits = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

    public static String hexdigest(String paramString)
    {
        try
        {
            paramString = hexdigest(paramString.getBytes());
            return paramString;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public static String hexdigest(byte[] paramArrayOfByte)
    {
        int i = 0;
        try
        {
            Object localObject = MessageDigest.getInstance("MD5");
            ((MessageDigest)localObject).update(paramArrayOfByte);
            paramArrayOfByte = ((MessageDigest)localObject).digest();
            char[] localObjectByte = new char[32];
            int j = 0;
            for (;;)
            {
                if (i >= 16) {
                    return new String(localObjectByte);
                }
                int k = paramArrayOfByte[i];
                int m = j + 1;
                localObjectByte[j] = hexDigits[(k >>> 4 & 0xF)];
                j = m + 1;
                localObjectByte[m] = hexDigits[(k & 0xF)];
                i += 1;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
    }
}