package com.dbn.cloud.oss.server.validation;

import com.dbn.cloud.platform.exception.AppException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileValid {

    /**
     * 文件名过滤非法字符
     *
     * @param fileName
     */
    public static void fileNameValid(String fileName) {
        Pattern filePattern = Pattern.compile("[\\\\/:*?\"<>|]");
        Matcher m = filePattern.matcher(fileName);
        if (m.matches()) {
            throw new AppException(String.valueOf(500), "文件名称字符非法");
        }
    }


}
