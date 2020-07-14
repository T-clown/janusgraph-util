package com.janusgraph.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 日起格式化
     */
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转Json格式字符串
     *
     * @param t 对象
     * @return Json格式字符串
     */
    public static <T> String writeValueAsString(T t) {
        if (t == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 对象转Json格式字符串(格式化的Json字符串)
     *
     * @param t 对象
     * @return 美化的Json格式字符串
     */
    public static <T> String writeValueAsStringPretty(T t) {
        if (t == null) {
            return null;
        }
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.warn("Parse Object to String error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 字符串转换为自定义对象
     *
     * @param data  要转换的字符串
     * @param clazz 自定义对象的class对象
     * @return 自定义对象
     */
    public static <T> T parseObject(String data, Class<T> clazz) {
        if (StringUtils.isBlank(data) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)data : objectMapper.readValue(data, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析list
     * JsonUtil.parseArray(userListJson, new TypeReference<List<User>>() {});
     *
     * @param data
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T parseArray(String data, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(data) || typeReference == null) {
            return null;
        }
        try {
            return objectMapper.readValue(data, typeReference);
        } catch (IOException e) {
            log.warn("Parse String to Object error :{}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析list
     *
     * @param data
     * @param collectionClazz
     * @param elementClazzes
     * @param <T>
     * @return
     */
    public static <T> T parseArray(String data, Class<?> collectionClazz, Class<?>... elementClazzes) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClazz, elementClazzes);
        try {
            return objectMapper.readValue(data, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error : {}", e.getMessage());
            return null;
        }
    }
}
