package com.hengshan.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {
    private BeanCopyUtil() {
    }

    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <V,T> List<T> copyBeanList(List<V> list, Class<T> clazz) {
        return list.stream().map(obj -> copyBean(obj, clazz)).collect(Collectors.toList());
    }

}