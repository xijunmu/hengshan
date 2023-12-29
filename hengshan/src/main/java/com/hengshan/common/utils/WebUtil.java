package com.hengshan.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WebUtil {

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     * @return String
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置Excel下载的请求头
     * @param fileName 文件名称
     * @param response 响应
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    public static void setDownLoadHeader(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("application/x-download");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
    }
}
