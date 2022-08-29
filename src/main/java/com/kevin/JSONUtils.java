package com.kevin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.odps.utils.StringUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * @author kevin
 * @version 1.0
 * @Email kevin_dw@163.com
 * @date 2022/4/22 11:00
 * @desc
 */

public class JSONUtils {
    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Gson gson = new Gson();

    private JSONUtils() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).setTimeZone(TimeZone.getDefault());
    }

    /**
     * Object转json字符串
     * @param object object
     * @return
     */
    public static String toJson(Object object) {
        try{
            return JSON.toJSONString(object,false);
        } catch (Exception e) {
            logger.error("object to json exception!",e);
        }

        return null;
    }


    /**
     *将json字符串转成指定类型对象
     *
     * @param json json字符串
     * @param clazz 类
     * @param <T> T
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            logger.error("parse object exception!",e);
        }
        return null;
    }


    /**
     * 将Json字符串转成指定类型的List集合
     *
     * @param json Json字符串
     * @param clazz 类
     * @param <T> T
     * @return list
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {

        if (StringUtils.isEmpty(json)) {
            return new ArrayList<>();
        }
        try {
            return JSONArray.parseArray(json, clazz);
        } catch (Exception e) {
            logger.error("JSONArray.parseArray exception!",e);
        }

        return new ArrayList<>();
    }



    /**
     * 检查Json是否有效
     *
     * @param json json
     * @return
     */
    public static boolean checkJsonValid(String json) {

        if (StringUtils.isEmpty(json)) {
            return false;
        }

        try {
            objectMapper.readTree(json);
            return true;
        } catch (IOException e) {
            logger.error("check json object valid exception!",e);
        }

        return false;
    }


    /**
     * 用于在此节点或其子节点中查找具有指定名称的JSON对象字段，并返回值
     * 在此节点或其子代中找不到匹配字段，返回null
     *
     * @param jsonNode json节点
     * @param fieldName JSON对象字段
     *
     * @return
     */
    public static String findValue(JsonNode jsonNode, String fieldName) {
        JsonNode node = jsonNode.findValue(fieldName);

        if (node == null) {
            return null;
        }

        return node.toString();
    }


    /**
     * json转map
     *
     * {@link #toMap(String, Class, Class)}
     *
     * @param json json
     * @return json to map
     */
    public static Map<String, String> toMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.parseObject(json, new TypeReference<HashMap<String, String>>(){});
        } catch (Exception e) {
            logger.error("json to map exception!",e);
        }

        return null;
    }

    /**
     *
     * json转map
     *
     * @param json json
     * @param classK classK
     * @param classV classV
     * @param <K> K
     * @param <V> V
     * @return
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> classK, Class<V> classV) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.parseObject(json, new TypeReference<HashMap<K, V>>() {});
        } catch (Exception e) {
            logger.error("json to map exception!",e);
        }

        return null;
    }
    /**
     *  json转map
     *
     * @param json
     * @param
     */
    public static <T> T toMap(String json, Class<?> cls) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.parseObject(json, new TypeReference<T>() {});
        } catch (Exception e) {
            logger.error("json to map exception!",e);
        }

        return null;
    }
    /**
     * object转json string
     * @param object
     * @return json
     */
    public static String toJsonString(Object object) {
        if (object==null){
            return null;
        }
        try{
            return JSON.toJSONString(object,false);
        } catch (Exception e) {
            throw new RuntimeException("Object json deserialization exception.", e);
        }
    }

    public static JSONObject parseObject(String text) {
        try{
            return JSON.parseObject(text);
        } catch (Exception e) {
            throw new RuntimeException("json string to  JSONObject exception.", e);
        }
    }

    /**
     * json
     * @param text
     * @return
     */
    public static JSONArray parseArray(String text) {
        try{
            return JSON.parseArray(text);
        } catch (Exception e) {
            throw new RuntimeException("json string to JSONArray exception.", e);
        }
    }



    /**
     * json serializer
     */
    public static class JsonDataSerializer extends JsonSerializer<String> {

        @Override
        public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeRawValue(value);
        }

    }

    /**
     * json data deserializer
     */
    public static class JsonDataDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            return node.toString();
        }

    }
}
