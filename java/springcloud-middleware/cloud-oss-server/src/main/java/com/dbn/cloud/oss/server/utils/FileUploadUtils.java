package com.dbn.cloud.oss.server.utils;

import com.dbn.cloud.platform.exception.AppException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class FileUploadUtils {

    public static Map<String, String> mFileTypes = new HashMap<>();// 记录各个文件头信息及对应的文件类型
    public static Set<String> legalFileType = new HashSet();// 所有合法的文件后缀

    static {
        // images
        mFileTypes.put("FFD8FFE0", ".jpg");
        mFileTypes.put("FFD8FFE1", ".jpeg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("47494638", ".gif");
        mFileTypes.put("3C3F786D", ".svg");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");

        //PS和CAD
        mFileTypes.put("38425053", ".psd");
        mFileTypes.put("41433130", ".dwg"); // CAD
        mFileTypes.put("252150532D41646F6265", ".ps");

        //办公文档类
        mFileTypes.put("D0CF11E0", ".doc"); //ppt、doc、xls
        mFileTypes.put("504B0304", ".docx");//pptx、docx、xlsx

        /**注意由于文本文档录入内容过多，则读取文件头时较为多变-START**/
        mFileTypes.put("0D0A0D0A", ".txt");//txt
        mFileTypes.put("0D0A2D2D", ".txt");//txt
        mFileTypes.put("0D0AB4B4", ".txt");//txt
        mFileTypes.put("B4B4BDA8", ".txt");//文件头部为汉字
        mFileTypes.put("73646673", ".txt");//txt,文件头部为英文字母
        mFileTypes.put("32323232", ".txt");//txt,文件头部内容为数字
        mFileTypes.put("0D0A09B4", ".txt");//txt,文件头部内容为数字
        mFileTypes.put("3132330D", ".txt");//txt,文件头部内容为数字
        /**注意由于文本文档录入内容过多，则读取文件头时较为多变-END**/


        mFileTypes.put("7B5C727466", ".rtf"); // 日记本

        mFileTypes.put("255044462D312E", ".pdf");

        //视频或音频类
        mFileTypes.put("00000020", ".mp4");
        mFileTypes.put("00000018", ".mp4");
        mFileTypes.put("49443303", ".mp3");
        mFileTypes.put("3026B275", ".wma");
        mFileTypes.put("57415645", ".wav");
        mFileTypes.put("3026b275", ".wmv");
        mFileTypes.put("41564920", ".avi");
        mFileTypes.put("4D546864", ".mid");
        mFileTypes.put("2E524D46", ".rm");
        mFileTypes.put("000001BA", ".mpg");
        mFileTypes.put("000001B3", ".mpg");
        mFileTypes.put("6D6F6F76", ".mov");
        mFileTypes.put("3026B2758E66CF11", ".asf");
        mFileTypes.put("2E7261FD", ".ram");

        //压缩包
        mFileTypes.put("52617221", ".rar");
        mFileTypes.put("1F8B08", ".gz");
        mFileTypes.put("377ABCAF", ".7z");

        //程序文件
//        mFileTypes.put("3C3F786D6C", ".xml");
//        mFileTypes.put("68746D6C3E", ".html");
//        mFileTypes.put("7061636B", ".java");
//        mFileTypes.put("3C254020", ".jsp");
//        mFileTypes.put("4D5A9000", ".exe");


        mFileTypes.put("44656C69766572792D646174653A", ".eml"); // 邮件
        mFileTypes.put("5374616E64617264204A", ".mdb");//Access数据库文件

        mFileTypes.put("46726F6D", ".mht");
        mFileTypes.put("4D494D45", ".mhtml");

        // images
        legalFileType.add(".jpg");
        legalFileType.add(".png");
        legalFileType.add(".gif");
        legalFileType.add(".jpeg");
        legalFileType.add(".tif");
        legalFileType.add(".bmp");
        legalFileType.add(".svg");

        //PS和CAD
        legalFileType.add(".psd");
        legalFileType.add(".dwg"); // CAD
        legalFileType.add(".ps");

        //办公文档类
        legalFileType.add(".doc");
        legalFileType.add(".ppt");
        legalFileType.add(".xls");
        legalFileType.add(".docx");
        legalFileType.add(".pptx");
        legalFileType.add(".xlsx");

        /**注意由于文本文档录入内容过多，则读取文件头时较为多变-START**/
        legalFileType.add(".txt");//txt
        /**注意由于文本文档录入内容过多，则读取文件头时较为多变-END**/

        legalFileType.add(".rtf"); // 日记本

        legalFileType.add(".pdf");

        //视频或音频类
        legalFileType.add(".mp4");
        legalFileType.add(".mp3");
        legalFileType.add(".wma");
        legalFileType.add(".wav");
        legalFileType.add(".wmv");
        legalFileType.add(".avi");
        legalFileType.add(".mid");
        legalFileType.add(".rm");
        legalFileType.add(".mpg");
        legalFileType.add(".mov");
        legalFileType.add(".asf");
        legalFileType.add(".ram");

        //压缩包
        legalFileType.add(".rar");
        legalFileType.add(".gz");
        legalFileType.add(".zip");
        legalFileType.add(".7z");

        //程序文件
//        legalFileType.add("3C3F786D6C", ".xml");
//        legalFileType.add("68746D6C3E", ".html");
//        legalFileType.add("7061636B", ".java");
//        legalFileType.add("3C254020", ".jsp");
//        legalFileType.add("4D5A9000", ".exe");

        legalFileType.add(".eml"); // 邮件
        legalFileType.add(".mdb");//Access数据库文件

    }

    /**
     * 根据文件的输入流获取文件头信息
     *
     * @param is 文件输入流
     * @return 文件头信息
     */
    public static String getFileType(InputStream is) {
        byte[] b = new byte[4];
        if (is != null) {
            try {
                is.read(b, 0, b.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return mFileTypes.get(getFileHeader(b));
    }


    /**
     * 根据文件转换成的字节数组获取文件头信息
     *
     * @param b
     * @return 文件头信息
     */
    public static String getFileHeader(byte[] b) {
        String value = bytesToHexString(b);
        return value;
    }

    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     * 下面这段代码就是用来对文件类型作验证的方法，
     * 将字节数组的前四位转换成16进制字符串，并且转换的时候，要先和0xFF做一次与运算。
     * 这是因为，整个文件流的字节数组中，有很多是负数，进行了与运算后，可以将前面的符号位都去掉，
     * 这样转换成的16进制字符串最多保留两位，如果是正数又小于10，那么转换后只有一位，
     * 需要在前面补0，这样做的目的是方便比较，取完前四位这个循环就可以终止了
     *
     * @param src 要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }

        System.out.println("获取文件头信息:" + builder.toString());

        return builder.toString();
    }


    /**
     * 判断上传的文件是否合法
     * （一）、第一：检查文件的扩展名，
     * (二）、 第二：检查文件的MIME类型 。
     *
     * @param attachDoc
     * @return boolean
     */
    public static boolean getUpFilelegitimacyFlag(FileItem attachDoc) {

        boolean upFlag = false;//为真表示符合上传条件，为假表标不符合

        if (attachDoc == null) {
            return false;
        }
        String attachName = attachDoc.getName();

        log.info("#######上传的文件:" + attachName);

        if (attachName == null || attachName.isEmpty()) {
            return false;
        }

        /**返回在此字符串中最右边出现的指定子字符串的索引   **/
        String sname = attachName.substring(attachName.lastIndexOf("."));

        /**统一转换为小写**/
        sname = sname.toLowerCase();

        /**第一步：检查文件扩展名，是否符合要求范围**/
        if (!legalFileType.contains(sname)) {
            return false;
        }

        /**
         * 第二步：获取上传附件的文件头，判断属于哪种类型,并获取其扩展名
         * 直接读取文件的前几个字节，来判断上传文件是否符合格式
         * 防止上传附件变更扩展名绕过校验
         ***/

//        byte[] b = new byte[4];

        String req_fileType = null;
        try {
            req_fileType = getFileType(attachDoc.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("///////用户上传的文件类型///////////" + req_fileType);
        /**第三步：检查文件扩展名，是否符合要求范围**/
        if (req_fileType != null && !"".equals(req_fileType) && !"null".equals(req_fileType)) {
            /**第四步：校验上传的文件扩展名，是否在其规定范围内**/
            if (legalFileType.contains(req_fileType)) {
                upFlag = true;
            } else {
                upFlag = false;
            }
        } else {
            /**特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-START**/
            if (sname.indexOf(".txt") != -1) {
                upFlag = true;
            } else {
                upFlag = false;
            }
            /**特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-END**/
        }

        return upFlag;
    }


    /**
     * 判断上传的文件是否合法
     * （一）、第一：检查文件的扩展名，
     * (二）、 第二：检查文件的MIME类型 。
     *
     * @param attachDoc
     * @return boolean
     */
    public static boolean getUpFilelegitimacyFlag(MultipartFile attachDoc) {

        boolean upFlag = false;//为真表示符合上传条件，为假表标不符合

        if (attachDoc == null) {
            return false;
        }
        String attachName = attachDoc.getOriginalFilename();

        log.info("#######上传的文件:" + attachName);

        if (attachName == null || attachName.isEmpty()) {
            return false;
        }
        /**第一步：检查文件扩展名，是否符合要求范围**/
        if (attachName.lastIndexOf(".") < 0) {
            throw new AppException(String.valueOf(500), "文件后缀不符合规范");
        }

        /**返回在此字符串中最右边出现的指定子字符串的索引   **/
        String sname = attachName.substring(attachName.lastIndexOf("."));

        /**统一转换为小写**/
        sname = sname.toLowerCase();

        if (!legalFileType.contains(sname)) {
            throw new AppException(String.valueOf(500), "文件后缀不符合规范");
        }

        /**
         * 第二步：获取上传附件的文件头，判断属于哪种类型,并获取其扩展名
         * 直接读取文件的前几个字节，来判断上传文件是否符合格式
         * 防止上传附件变更扩展名绕过校验
         ***/

//        byte[] b = new byte[4];

        String req_fileType;
        try {
            req_fileType = getFileType(attachDoc.getInputStream());
        } catch (IOException e) {
            log.error("无法判断文件头" + e);
            throw new AppException(String.valueOf(500), "文件后缀不符合规范");
        }

        log.info("///////用户上传的文件类型///////////" + req_fileType);
        /**第三步：检查文件扩展名，是否符合要求范围**/
        if (req_fileType != null && !"".equals(req_fileType) && !"null".equals(req_fileType)) {
            /**第四步：校验上传的文件扩展名，是否在其规定范围内**/
            if (legalFileType.contains(req_fileType)) {
                upFlag = true;
            } else {
                upFlag = false;
            }
        } else {
            /**特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-START**/
            if (sname.indexOf(".txt") != -1) {
                upFlag = true;
            } else {
                upFlag = false;
            }
            /**特殊情况校验,如果用户上传的扩展名为,文本文件,则允许上传-END**/
        }

        return upFlag;
    }

    /**
     * 判断上传的文件是否合法
     * （一）、第一：检查文件的扩展名，
     * (二）、 第二：检查文件的MIME类型 。
     *
     * @param attachDoc
     * @return boolean
     */
    public static boolean checkSuffixLegal(MultipartFile attachDoc) {


        if (attachDoc == null) {
            return false;
        }
        String attachName = attachDoc.getOriginalFilename();

        log.info("#######上传的文件:" + attachName);

        if (attachName == null || attachName.isEmpty()) {
            return false;
        }
        /**第一步：检查文件扩展名，是否符合要求范围**/
        if (attachName.lastIndexOf(".") < 0) {
            throw new AppException(String.valueOf(500), "文件后缀不符合规范");
        }

        /**返回在此字符串中最右边出现的指定子字符串的索引   **/
        String sname = attachName.substring(attachName.lastIndexOf("."));

        /**统一转换为小写**/
        sname = sname.toLowerCase();

        if (!legalFileType.contains(sname)) {
            return false;
        }

        return true;
    }

    /**
     * 主函数，测试用
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        final String fileType = getFileType("D:/BICP-HUAWEI.mht");

        FileInputStream is = null;
        String value = null;

        String filePath = "C:\\Users\\Administrator\\Downloads\\20211113\\100_0028_0360.jpg";
        try {
            is = new FileInputStream(filePath);
            System.out.println(getImgInfo(is));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }

        //    System.out.println(value);
    }

    /**
     * 读取照片里面的信息
     */
    private static Map<String, Object> getImgInfo(InputStream is) throws Exception {
        Map<String, Object> map = new HashMap<>();
        Metadata metadata = ImageMetadataReader.readMetadata(is);
        //     Metadata metadata = ImageMetadataReader.readMetadata(file);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
                if (tagName.equals("Image Height")) {
                    System.out.println("图片高度: " + desc);
                    map.put("imageHeight", desc);

                } else if (tagName.equals("Image Width")) {
                    System.out.println("图片宽度: " + desc);
                    map.put("imageWidth", desc);

                } else if (tagName.equals("Date/Time Original")) {
                    System.out.println("拍摄时间: " + desc);
                    map.put("shootTime", desc);

                } else if (tagName.equals("GPS Latitude")) {
                    System.err.println("纬度 : " + desc);
                    System.err.println("纬度(度分秒格式) : " + pointToLatlong(desc));
                    map.put("latitude", pointToLatlong(desc));

                } else if (tagName.equals("GPS Longitude")) {
                    System.err.println("经度: " + desc);
                    System.err.println("经度(度分秒格式): " + pointToLatlong(desc));
                    map.put("longitude", pointToLatlong(desc));
                }
            }
        }
        return map;
    }

    /**
     * 经纬度格式  转换为  度分秒格式
     *
     * @param point 坐标点
     * @return
     */
    public static String pointToLatlong(String point) {
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
        Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
        Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
        Double duStr = du + fen / 60 + miao / 60 / 60;
        return duStr.toString();
    }
}
