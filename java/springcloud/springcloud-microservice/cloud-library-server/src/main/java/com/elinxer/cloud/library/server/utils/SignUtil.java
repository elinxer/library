package com.elinxer.cloud.library.server.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class SignUtil {
    private static final String DEFAULT_SECRET = "1qaz@WSX#$%&";

    public static String sign(String body, Map<String, String[]> params, String[] paths) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(body)) {
            //sb.append(body).append('#');
        }

        if (params != null) {
            params.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(paramEntry -> {
                        String paramValue = String.join(",", Arrays.stream(paramEntry.getValue()).sorted().toArray(String[]::new));
                        sb.append(paramEntry.getKey()).append("=").append(paramValue).append('#');
                    });
        }

        if (ArrayUtils.isNotEmpty(paths)) {
            String pathValues = String.join(",", Arrays.stream(paths).sorted().toArray(String[]::new));
            //sb.append(pathValues);
        }

        System.out.println(sb);

        return new HmacUtils(HmacAlgorithms.HMAC_MD5, DEFAULT_SECRET).hmacHex(sb.toString().getBytes());
    }

    public static String signParams(Map<String, String[]> params, String key) {
        String sb = mapSortedByKeyArr(params);

        System.out.println(key.toString());
        System.out.println(sb.toString());

        return DigestUtils.md5Hex(sb + key);
    }

    public static String mapSortedByKey(Map<String, String> param) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> keyList = new ArrayList<>(param.keySet());
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if (param.get(key) == null) {
                continue;
            }
            if (i == keyList.size() - 1) {
                stringBuilder.append(key).append("=").append(param.get(key));
            } else {
                stringBuilder.append(key).append("=").append(param.get(key)).append("#");
            }
        }
        return stringBuilder.toString();
    }

    public static String mapSortedByKeyArr(Map<String, String[]> param) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<String> keyList = new ArrayList<>(param.keySet());
        Collections.sort(keyList);
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i);
            if (param.get(key) == null) {
                continue;
            }
            String paramValue = String.join(",", Arrays.stream(param.get(key)).sorted().toArray(String[]::new));
            if (i == keyList.size() - 1) {
                stringBuilder.append(key).append("=").append(paramValue);
            } else {
                stringBuilder.append(key).append("=").append(paramValue).append("#");
            }
        }
        return stringBuilder.toString();
    }


//    public static void main(String[] args) {
//        String body = "{\n" +
//                "\t\"name\": \"hjzgg\",\n" +
//                "\t\"age\": 26\n" +
//                "}";
//        Map<String, String[]> params = new HashMap<>();
//        params.put("var3", new String[]{"3"});
//        params.put("var4", new String[]{"4"});
//
//        String[] paths = new String[]{"1", "2"};
//
//        System.out.println(signParams(params));
//    }

}
