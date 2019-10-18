package com.liyz.service.third.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.apache.commons.lang3.StringUtils;

public class XStreamUtil {
private static final String XML_GBK_TAG = "<?xml version=\"1.0\" encoding=\"GBK\"?>";

private static final String XML_UTF8_TAG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

public static final String UTF8_ENCODE = "UTF-8";

public static final String GBK_ENCODE = "GBK";

/**
 * Description: 私有化构造
 */
private XStreamUtil() {
    super();
}

/**
 * @return
 * @Description 为每次调用生成一个XStream
 * @Title getInstance
 */
private static XStream getInstance() {
    XStream xStream = new XStream(new DomDriver("UTF-8")) {
        /**
         * 忽略xml中多余字段
         */
        @Override
        protected MapperWrapper wrapMapper(MapperWrapper next) {
            return new MapperWrapper(next) {
                @SuppressWarnings("rawtypes")
                @Override
                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                    if (definedIn == Object.class) {
                        return false;
                    }
                    return super.shouldSerializeMember(definedIn, fieldName);
                }
            };
        }
    };

    // 设置默认的安全校验
    XStream.setupDefaultSecurity(xStream);

    // 使用本地的类加载器
    xStream.setClassLoader(XStreamUtil.class.getClassLoader());
    // 允许所有的类进行转换
    xStream.addPermission(AnyTypePermission.ANY);
    return xStream;
}

/**
 * @param xml   xml字符串
 * @param clazz 转换成类的class
 * @return 类
 * @Description 将xml字符串转化为java对象
 * @Title xmlToBean
 */
public static <T> T xmlToBean(String xml, Class<T> clazz) {
    XStream xStream = getInstance();
    xStream.processAnnotations(clazz);
    Object object = xStream.fromXML(xml);
    T cast = clazz.cast(object);
    return cast;
}

/**
 * @param object   传入实体类
 * @param isFormat 是否xml格式化，就是为换行，否则无换行
 * @return xml格式
 * @Description 将java对象转化为xml字符串
 * @Title beanToXml
 */
public static String beanToXml(Object object, Boolean isFormat) {
    XStream xStream = getInstance();
    xStream.processAnnotations(object.getClass());
    xStream.aliasSystemAttribute(null, "class");
    // 剔除所有tab、制表符、换行符
    String xml = "";
    if (isFormat) {
        xml = xStream.toXML(object);
    } else {
        xml = xStream.toXML(object).replaceAll("\\s+", " ");
    }
    xml = xml.replace("__", "_");
    return xml;
}


/**
 * @param object   实体类
 * @param encode   头部以什么编码上传
 * @param isFormat 是否格式化
 * @return
 * @Description 将java对象转化为xml字符串（包含xml头部信息）
 * @Title beanToXml
 */
public static String beanToXmlWithTag(Object object, String encode, Boolean isFormat) {
    String xml = "";
    if (StringUtils.isBlank(encode)) {
        xml = XML_UTF8_TAG;
    } else if (UTF8_ENCODE.equals(encode)) {
        xml = XML_UTF8_TAG;
    } else if (GBK_ENCODE.equals(encode)) {
        xml = XML_GBK_TAG;
    }

    if (isFormat) {
        xml = xml + "\n" + beanToXml(object, isFormat);
    } else {
        xml = xml + beanToXml(object, isFormat);
    }
    return xml;
}

}
