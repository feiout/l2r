package com.l2r.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by ChenLing on 14-11-14.
 */
public class ZipUtils
{
    /**
     * gZip压缩方法
     * */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }

    /**
     * gZip解压方法
     * */
    public static byte[] unGZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    public static String gZip(String rawText)
    {
        String result = "";
        try
        {
            byte[] zipBuffer = gZip(rawText.getBytes("UTF-8"));
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(zipBuffer);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
    public static String unGZip(String zipText)
    {
        String result = "";
        try
        {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buffer = decoder.decodeBuffer(zipText);

            byte[] unzipBuffer = unGZip(buffer);
            result = new String(unzipBuffer,"UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
}
