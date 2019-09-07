package com.liyz.common.base.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释: bean copy util
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/8/28 16:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConverterUtil {

    /**
     * 通用BeanList转换
     * @param sourceList 源List
     * @param targetClass 目标类
     * @return
     */
    public static <T,Y> List<Y> ListConverter(List<T> sourceList, Class<Y> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Y> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            Y targetObject = BeanUtils.instantiateClass(targetClass);
            BeanUtils.copyProperties(o,targetObject);
            resultList.add(targetObject);
        }
        if (sourceList instanceof Page) {
            Page<T> sourcePage = (Page<T>) sourceList;
            Page<Y> page = new Page<>();
            BeanUtils.copyProperties(sourcePage, page);
            page.clear();
            page.addAll(resultList);
            return page;
        }
        return resultList;
    }

    public static <T,Y> PageInfo<Y> PageConverter(PageInfo<T> sourcePage, Class<Y> targetClass) {
        if (sourcePage == null) {
            return null;
        }
        return new PageInfo<>(ListConverter(sourcePage.getList(), targetClass));
    }

    public static <T,Y> Y beanConverter(T source, Class<Y> targetClass) {
        if (source == null) {
            return null;
        }
        Y target = BeanUtils.instantiateClass(targetClass);
        BeanUtils.copyProperties(source,target);
        return target;
    }

    public static Map<String, Object> objectToMap(Object obj) throws Exception {
        if(obj == null){
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(obj));
        }
        return map;
    }
}
