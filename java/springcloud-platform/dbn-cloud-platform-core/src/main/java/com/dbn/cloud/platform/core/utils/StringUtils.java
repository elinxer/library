
package com.dbn.cloud.platform.core.utils;


import org.apache.commons.collections4.CollectionUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtils包装字符串处理类
 *
 * @author elinx
 * @since 1.0
 */
@SuppressWarnings("all")
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 编译后的正则表达式缓存
     */
    private static final Map<String, Pattern> PATTERN_CACHE = new ConcurrentHashMap<>();
    /**
     * mybatis use
     */
    public static final String ITEM_PREFIX = "__frch_";

    private static final String pingYin = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
    private static Map<String, String> pinYinMap = new HashMap<String, String>();

    /**
     * 访问设置的合法ip
     */
    private static final String[] IPLegalList = {"127.0.0.1", "127.0.0.2"};

    static {
        String[] pinYins = pingYin.split(",");
        for (int i = 0; i < pinYins.length; i++) {
            pinYinMap.put(pinYins[i], pinYins[i]);
        }
    }

    /**
     * 字符串值 “1”
     */
    public final static String COMMON_VALUE_YES = "1";
    /**
     * 字符串值 “0”
     */
    public final static String COMMON_VALUE_NO = "0";
    /**
     * 字符串默认值 0
     */
    public final static String COMMON_VALUE_0 = "0";
    public final static String COMMON_VALUE_1 = "1";
    public final static String COMMON_VALUE_2 = "2";
    public final static String COMMON_VALUE_3 = "3";
    public final static String COMMON_VALUE_4 = "4";
    public final static String COMMON_VALUE_5 = "5";
    public final static String COMMON_VALUE_6 = "6";
    public final static String COMMON_VALUE_7 = "7";
    public final static String COMMON_VALUE_8 = "8";
    public final static String COMMON_VALUE_9 = "9";
    public final static String COMMON_VALUE_10 = "10";
    public final static String COMMON_VALUE_11 = "11";
    public final static String COMMON_VALUE_12 = "12";
    public final static String COMMON_VALUE_13 = "13";
    public final static String COMMON_VALUE_14 = "14";
    public final static String COMMON_VALUE_15 = "15";
    public final static String COMMON_VALUE_16 = "16";
    public final static String COMMON_VALUE_17 = "17";
    public final static String COMMON_VALUE_18 = "18";
    public final static String COMMON_VALUE_19 = "19";
    public final static String COMMON_VALUE_20 = "20";

    /**
     * 字符串常量 -1
     */
    public final static String COMMON_VALUE_M1 = "-1";
    /**
     * 字符串常量 -2
     */
    public final static String COMMON_VALUE_M2 = "-2";

    /**
     * 字符串常量 -3
     */
    public final static String COMMON_VALUE_M3 = "-3";

    /**
     * 空字符串 " "
     */
    public final static String BLANK = " ";

    /**
     * 字符串常量 默认id
     */
    public final static String COMMON_VALUE_DEFAULTID = "defaultId";

    /**
     * 字符串常量 默认系统用户id：10000
     */
    public final static String COMMON_VALUE_DEFAULT_USER = "10000";

    /**
     * 字符串常量
     */
    public final static String COMMON_VALUE_DEFAULT_DATA = "DEFAULT_DATA";

    /**
     * 字符串常量
     */
    public final static String COMMON_VALUE_VERSION_DEFAULT_DATA = "DEFAULT_DATA_FREE_VERSION";

    /**
     * 字符串常量
     */
    public final static String COMMON_VALUE_VERSION_DEFAULT_FREE = "DEFAULT_FREE_VERSION";

    /**
     * 服务类型
     */
    public final static String SYS_TYPE_SERVICE = "SYS_TYPE_SERVICE";

    /**
     * 流程设置动态业务id
     */
    public final static String SYS_FLOW_SETTING_DYNAMIC_BIZID = "SYS_FLOW_SETTING_DYNAMIC_BIZID";

    /**
     * 文本框
     */
    public final static String COMMON_VALUE_100 = "100";

    /**
     * 附件框
     */
    public final static String COMMON_VALUE_101 = "101";

    /**
     * 下拉框
     */
    public final static String COMMON_VALUE_102 = "102";

    /**
     * 单选框
     */
    public final static String COMMON_VALUE_103 = "103";

    /**
     * 时间限制 不超过
     */
    public final static String COMMON_VALUE_TIMELIMIT_0 = "不超过";

    /**
     * 时间限制 超过
     */
    public final static String COMMON_VALUE_TIMELIMIT_1 = "超过";

    /**
     * 时间单位(0:时 1:天 2:周 3:月)
     */
    public final static String COMMON_VALUE_DATE_0 = "时";

    /**
     * 时间单位(0:时 1:天 2:周 3:月)
     */
    public final static String COMMON_VALUE_DATE_1 = "天";
    /**
     * 时间单位(0:时 1:天 2:周 3:月)
     */
    public final static String COMMON_VALUE_DATE_2 = "周";
    /**
     * 时间单位(0:时 1:天 2:周 3:月)
     */
    public final static String COMMON_VALUE_DATE_3 = "月";

    /**
     * 当前时间
     */
    public final static String COMMON_VALUE_CURR_TIME = "#CURR_TIME";

    public final static String COMMON_VALUE_STATE_SUCCESS = "SUCCESS";

    public final static String COMMON_VALUE_STATE_FAIL = "FAIL";


    /**
     * 连字符号：“-”
     */
    public static final String HYPHEN = "-";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 图片类型
     */
    public static final String imgType = "bmp,jpg,png,jpeg,gif";


    /**
     * 取定长字符串,不足的在前面补指定字符
     *
     * @param str     目标字符串
     * @param length  返回字符串长度
     * @param preChar 不足前补字符
     * @return
     */
    public static String getFixStr(String str, int length, String preChar) {
        if (str == null) {
            str = "";
        }
        int addLen = length - str.length();
        if (addLen > 0) {
            for (int i = 0; i < addLen; i++) {
                str = preChar + str;
            }
        }
        return str;
    }

    /**
     * Convert a String to int.
     *
     * @param intString A String contains an int value.
     * @return int The int value parsed from the string as parameter, 0 is
     * returned if cannot parse an int value from the given string.
     */
    public static int toInt(String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    /**
     * 判断图片类型是否正确
     *
     * @param fileEnd
     * @param fileType
     * @return
     */
    public static boolean checkImgFileType(String fileEnd, String fileType) {
        boolean isRealType = false;
        if (StringUtils.isEmpty(fileType)) {
            fileType = StringUtils.imgType;
        }
        if (fileType.indexOf(",") != -1) {
            String[] arrType = fileType.split(",");
            for (String str : arrType) {
                if (fileEnd.equals(str.toLowerCase())) {
                    isRealType = true;
                    break;
                }
            }
        } else {
            if (fileEnd.equals(fileType.toLowerCase())) {
                isRealType = true;
            }
        }
        return isRealType;
    }

    /**
     * 判断集合非空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /***
     *
     * @describe：判断一个集合中的项是否有空
     * @param str
     */
    public static boolean isEmpty(String[] str) {
        boolean temp = true;
        for (String s : str) {
            temp = isEmpty(s);
            if (temp) {
                break;
            } else {
                continue;
            }
        }
        return temp;
    }


    /**
     * @param map
     * @param key
     * @param defaultValue
     * @describe：获取指定Map指定Key的值,当没有的时候返回给定的默认值
     */
    public static String getString(Map map, String key, String defaultValue) {
        if (map == null) {
            return defaultValue;
        }
        if (map.containsKey(key)) {
            Object obj = map.get(key);
            if (obj == null) {
                return defaultValue;
            } else {
                return obj.toString();
            }
        }
        return defaultValue;
    }

    /**
     * 将一个对象转换为字符串，如果字符串为空直接设定的返回默认字符串
     *
     * @param obj
     * @param defaultValue
     * @describe：TODO
     */
    public static String getString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (isEmpty(obj.toString())) {
            return defaultValue;
        } else {
            return obj.toString();
        }
    }


    /**
     * 取List中不为空的指定索引对象
     *
     * @param list
     * @param index
     * @return
     */
    public static <T> T getInList(List list, int index) {

        if (CollectionUtils.isNotEmpty(list) && list.size() > index) {
            return (T) list.get(index);

        } else {
            return null;
        }

    }


    /**
     * 邮箱正则表达式true匹配
     *
     * @param yx
     * @return
     */
    public static boolean matchYx(String yx) {
        boolean temp = false;
        if (StringUtils.isNotEmpty(yx)) {
            temp = yx
                    .matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        }
        return temp;
    }


    /**
     * @param str  第一个字符串
     * @param str2 第二个字符串
     * @return 返回 true|false
     * @describe：比较两个字符串是否相同(为空返回false)
     */
    public static boolean equals(String str, String str2) {
        if (isEmpty(str2) || isEmpty(str)) {
            return false;
        }
        return str.equals(str2);
    }


    public static boolean isLetter(char c) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     *
     * @param s
     * @return
     */
    public static int getStringlength(String s) {
        if (s == null)
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for (int i = 0; i < c.length; i++) {
            len++;
            if (!isLetter(c[i])) {
                len++;
            }
        }
        return len;
    }


    /**
     * 获取异常的String
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } catch (Exception e) {
            return "getStackTrace error:" + e.getMessage();
        } finally {
            pw.close();
        }
    }

    /**
     * 判断某值是否在列表中
     *
     * @param inValues
     * @param value
     * @return
     */
    public static boolean isInStringValue(String[] inValues, String value) {
        if (value == null) {
            return false;
        }
        boolean isIn = false;
        for (String v : inValues) {
            if (value.equals(v)) {
                isIn = true;
                break;
            }
        }
        return isIn;
    }


    /**
     * 根据字节类型截取字符串
     * Java语言中，采用ISO8859-1编码方式时，一个中文字符与一个英文字符一样只占1个字节；
     * 采用GB2312或GBK编码方式时，一个中文字符占2个字节；
     * 而采用UTF-8编码方式时，一个中文字符会占3个字节。
     *
     * @param targetString
     * @param byteCode     (ISO8859-1,GB2312或GBK,UTF-8)
     * @param byteIndex
     * @return
     * @throws Exception
     */
    public static String getSubString(String targetString, String byteCode, int byteIndex)
            throws Exception {
        if (targetString.getBytes(byteCode).length < byteIndex) {
            return targetString;
        }
        String temp = targetString;
        for (int i = 0; i < targetString.length(); i++) {
            if (temp.getBytes(byteCode).length <= byteIndex) {
                break;
            }
            temp = temp.substring(0, temp.length() - 1);
        }
        return temp;
    }

    /**
     * 截取字符串
     *
     * @param str   待截取的字符串
     * @param start 截取起始位置 （ 1 表示第一位 -1表示倒数第1位）
     * @param end   截取结束位置 （如上index）
     * @return
     */
    public static String sub(String str, int start, int end) {
        String result = null;

        if (str == null || str.equals(""))
            return "";

        int len = str.length();
        start = start < 0 ? len + start : start - 1;
        end = end < 0 ? len + end + 1 : end;

        return str.substring(start, end);
    }

    /**
     * 随机产生一个4位数的数字密码
     *
     * @return
     */
    public static String generateRamdomNum() {
        String[] beforeShuffle = new String[]{"0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9"};
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(3, 7);
        return result;
    }


    /**
     * 正则判断手机号
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    // 手机号码前三后四脱敏
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    //身份证前三后四脱敏
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    //护照前2后3位脱敏，护照一般为8或9位
    public static String idPassport(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.substring(0, 2) + new String(new char[id.length() - 5]).replace("\0", "*") + id.substring(id.length() - 3);
    }


    /**
     * 编译一个正则表达式，并且进行缓存,如果换成已存在则使用缓存
     *
     * @param regex 表达式
     * @return 编译后的Pattern
     */
    public static final Pattern compileRegex(String regex) {
        Pattern pattern = PATTERN_CACHE.get(regex);
        if (pattern == null) {
            pattern = Pattern.compile(regex);
            PATTERN_CACHE.put(regex, pattern);
        }
        return pattern;
    }

    /**
     * 将字符串的第一位转为小写
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 将字符串的第一位转为大写
     *
     * @param str 需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else {
            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            return new String(chars);
        }
    }

    /**
     * 下划线命名转为驼峰命名
     *
     * @param str 下划线命名格式
     * @return 驼峰命名格式
     */
    public static final String underScoreCase2CamelCase(String str) {
        if (!str.contains("_")) return str;
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        boolean hitUnderScore = false;
        sb.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                hitUnderScore = true;
            } else {
                if (hitUnderScore) {
                    sb.append(Character.toUpperCase(c));
                    hitUnderScore = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 驼峰命名法转为下划线命名
     *
     * @param str 驼峰命名格式
     * @return 下划线命名格式
     */
    public static final String camelCase2UnderScoreCase(String str) {
        StringBuilder sb = new StringBuilder();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (Character.isUpperCase(c)) {
                sb.append("_").append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 将异常栈信息转为字符串
     *
     * @param e 字符串
     * @return 异常栈
     */
    public static String throwable2String(Throwable e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    /**
     * 字符串连接，将参数列表拼接为一个字符串
     *
     * @param more 追加
     * @return 返回拼接后的字符串
     */
    public static String concat(Object... more) {
        return concatSpiltWith("", more);
    }

    public static String concatSpiltWith(String split, Object... more) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < more.length; i++) {
            if (i != 0) buf.append(split);
            buf.append(more[i]);
        }
        return buf.toString();
    }

    /**
     * 将字符串转移为ASCII码
     *
     * @param str 字符串
     * @return 字符串ASCII码
     */
    public static String toASCII(String str) {
        StringBuffer strBuf = new StringBuffer();
        byte[] bGBK = str.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    public static String toUnicode(String str) {
        StringBuffer strBuf = new StringBuffer();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            strBuf.append("\\u").append(Integer.toHexString(chars[i]));
        }
        return strBuf.toString();
    }

    public static String toUnicodeString(char[] chars) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            strBuf.append("\\u").append(Integer.toHexString(chars[i]));
        }
        return strBuf.toString();
    }

    static final char CN_CHAR_START = '\u4e00';
    static final char CN_CHAR_END = '\u9fa5';

    /**
     * 是否包含中文字符
     *
     * @param str 要判断的字符串
     * @return 是否包含中文字符
     */
    public static boolean containsChineseChar(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= CN_CHAR_START && chars[i] <= CN_CHAR_END) return true;
        }
        return false;
    }

    /**
     * 对象是否为无效值
     *
     * @param obj 要判断的对象
     * @return 是否为有效值（不为null 和 "" 字符串）
     */
    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || "".equals(obj.toString());
    }

    /**
     * 参数是否是有效数字 （整数或者小数）
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是数字
     */
    public static boolean isNumber(Object obj) {
        if (obj instanceof Number) return true;
        return isInt(obj) || isDouble(obj);
    }

    public static String matcherFirst(String patternStr, String text) {
        Pattern pattern = compileRegex(patternStr);
        Matcher matcher = pattern.matcher(text);
        String group = null;
        if (matcher.find()) {
            group = matcher.group();
        }
        return group;
    }

    /**
     * 参数是否是有效整数
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是整数
     */
    public static boolean isInt(Object obj) {
        if (isNullOrEmpty(obj))
            return false;
        if (obj instanceof Integer)
            return true;
        return obj.toString().matches("[-+]?\\d+");
    }

    /**
     * 字符串参数是否是double
     *
     * @param obj 参数（对象将被调用string()转为字符串类型）
     * @return 是否是double
     */
    public static boolean isDouble(Object obj) {
        if (isNullOrEmpty(obj))
            return false;
        if (obj instanceof Double || obj instanceof Float)
            return true;
        return compileRegex("[-+]?\\d+\\.\\d+").matcher(obj.toString()).matches();
    }

    /**
     * 判断一个对象是否为boolean类型,包括字符串中的true和false
     *
     * @param obj 要判断的对象
     * @return 是否是一个boolean类型
     */
    public static boolean isBoolean(Object obj) {
        if (obj instanceof Boolean) return true;
        String strVal = String.valueOf(obj);
        return "true".equalsIgnoreCase(strVal) || "false".equalsIgnoreCase(strVal);
    }

    /**
     * 对象是否为true
     *
     * @param obj
     * @return
     */
    public static boolean isTrue(Object obj) {
        return "true".equals(String.valueOf(obj));
    }

    /**
     * 判断一个数组里是否包含指定对象
     *
     * @param arr 对象数组
     * @param obj 要判断的对象
     * @return 是否包含
     */
    public static boolean contains(Object arr[], Object... obj) {
        if (arr == null || obj == null || arr.length == 0) return false;
        return Arrays.asList(arr).containsAll(Arrays.asList(obj));
    }

    /**
     * 将对象转为int值,如果对象无法进行转换,则使用默认值
     *
     * @param object       要转换的对象
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static int toInt(Object object, int defaultValue) {
        if (object instanceof Number)
            return ((Number) object).intValue();
        if (isInt(object)) {
            return Integer.parseInt(object.toString());
        }
        if (isDouble(object)) {
            return (int) Double.parseDouble(object.toString());
        }
        return defaultValue;
    }

    /**
     * 将对象转为int值,如果对象不能转为,将返回0
     *
     * @param object 要转换的对象
     * @return 转换后的值
     */
    public static int toInt(Object object) {
        return toInt(object, 0);
    }

    /**
     * 将对象转为long类型,如果对象无法转换,将返回默认值
     *
     * @param object       要转换的对象
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static long toLong(Object object, long defaultValue) {
        if (object instanceof Number)
            return ((Number) object).longValue();
        if (isInt(object)) {
            return Long.parseLong(object.toString());
        }
        if (isDouble(object)) {
            return (long) Double.parseDouble(object.toString());
        }
        return defaultValue;
    }

    /**
     * 将对象转为 long值,如果无法转换,则转为0
     *
     * @param object 要转换的对象
     * @return 转换后的值
     */
    public static long toLong(Object object) {
        return toLong(object, 0);
    }

    /**
     * 将对象转为Double,如果对象无法转换,将使用默认值
     *
     * @param object       要转换的对象
     * @param defaultValue 默认值
     * @return 转换后的值
     */
    public static double toDouble(Object object, double defaultValue) {
        if (object instanceof Number)
            return ((Number) object).doubleValue();
        if (isNumber(object)) {
            return Double.parseDouble(object.toString());
        }
        if (null == object) return defaultValue;
        return 0;
    }

    /**
     * 将对象转为Double,如果对象无法转换,将使用默认值0
     *
     * @param object 要转换的对象
     * @return 转换后的值
     */
    public static double toDouble(Object object) {
        return toDouble(object, 0);
    }

    /**
     * 分隔字符串,根据正则表达式分隔字符串,只分隔首个,剩下的的不进行分隔,如: 1,2,3,4 将分隔为 ['1','2,3,4']
     *
     * @param str   要分隔的字符串
     * @param regex 分隔表达式
     * @return 分隔后的数组
     */
    public static String[] splitFirst(String str, String regex) {
        return str.split(regex, 2);
    }

    /**
     * 将对象转为字符串,如果对象为null,则返回null,而不是"null"
     *
     * @param object 要转换的对象
     * @return 转换后的对象
     */
    public static String toString(Object object) {
        return toString(object, null);
    }

    /**
     * 将对象转为字符串,如果对象为null,则使用默认值
     *
     * @param object       要转换的对象
     * @param defaultValue 默认值
     * @return 转换后的字符串
     */
    public static String toString(Object object, String defaultValue) {
        if (object == null) return defaultValue;
        return String.valueOf(object);
    }

    /**
     * 将对象转为String后进行分割，如果为对象为空或者空字符,则返回null
     *
     * @param object 要分隔的对象
     * @param regex  分隔规则
     * @return 分隔后的对象
     */
    public static final String[] toStringAndSplit(Object object, String regex) {
        if (isNullOrEmpty(object)) return null;
        return String.valueOf(object).split(regex);
    }

    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = 0;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
                chLength++;
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
    }


}
