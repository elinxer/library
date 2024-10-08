package com.dbn.cloud.oss.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileHahUtil {

    /**
     * 计算文件hash值
     */
    public static String hashFile(File file) throws Exception {
        FileInputStream fis = null;
        String sha256 = null;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte buffer[] = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            byte[] digest = md.digest();
            sha256 = byte2hexLower(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("计算文件hash值错误");
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sha256;
    }

    private static String byte2hexLower(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0XFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    public static String md5HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值 
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


//    public static void main(String[] args) {
//
//        try {
////    		File file = new File("D:\\file\\practice\\Downloads.rar");
////    		File file1 = new File("D:\\file\\practice\\into\\Downloads.rar");
////    		File file2 = new File("D:\\file\\practice\\into\\Downloads(1).rar");
////    		System.out.println(md5HashCode(new FileInputStream(file)));
////    		System.out.println(md5HashCode(new FileInputStream(file)).equals(md5HashCode(new FileInputStream(file))) );
////    		System.out.println(hashFile(file));
////			System.out.println(hashFile(file).equals(hashFile(file1)));
////			System.out.println(hashFile(file).equals(hashFile(file2)));
////			File file3 = new File("D:\\file\\practice\\into\\Google.rar");
//
//            File file = new File("E:\\develop\\dbn\\dbn-cloud-admin\\dbn-cloud-admin-server\\target\\dbn-cloud-admin-server.jar");
//
//            String md5HashCode = FileHahUtil.md5HashCode(new FileInputStream(file));
//            System.out.println(md5HashCode);
//
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
