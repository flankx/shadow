package com.github.shadow.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xujianrong@leateckgroup.com
 * @date 2023/7/12
 */
@Slf4j
public class JsonUtils {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 允许pojo中有在json串中不存在的字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许有注释
        // OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 时间格式化
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * Parse json 2 object t.
     *
     * @param <T> the type parameter
     * @param json the json
     * @param tClass the t class
     * @return the t
     */
    public static <T> T parse2Object(String json, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (IOException e) {
            log.error("JsonUtils.parse2Object failed! ex ", e);
        }
        return null;
    }

    public static <T> T parse2Object(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("JsonUtils.parse2Object failed! ex ", e);
        }
        return null;
    }

    /**
     * Parse json 2 objects list.
     *
     * @param <T> the type parameter
     * @param jsonArray the json array
     * @param tClass the t class
     * @return the list
     */
    public static <T> T parse2Objects(String jsonArray, Class<?> collectionClass, Class<?> tClass) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, tClass);
            return OBJECT_MAPPER.readValue(jsonArray, javaType);
        } catch (IOException e) {
            log.error("JsonUtils.parseJson2Objects failed! ex ", e);
        }
        return null;
    }

    public static <T> T convertValue(Object data, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.convertValue(data, typeReference);
        } catch (Exception e) {
            log.error("JsonUtils.convertValue failed! ex ", e);
        }
        return null;
    }

    public static <T> T convertValue(Object data, Class<T> tClass) {
        try {
            return OBJECT_MAPPER.convertValue(data, tClass);
        } catch (Exception e) {
            log.error("JsonUtils.convertValue failed! ex ", e);
        }
        return null;
    }

    public static <T> T convertValues(Object data, Class<?> collectionClass, Class<?> tClass) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, tClass);
            return OBJECT_MAPPER.convertValue(data, javaType);
        } catch (Exception e) {
            log.error("JsonUtils.convertValues failed! ex ", e);
        }
        return null;
    }

    /**
     * Parse json 2 json node.
     *
     * @param json the json
     * @return the json node
     */
    public static JsonNode parse2JsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            log.error("JsonUtils.parse2JsonNode failed! ex ", e);
        }
        return null;
    }

    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("JsonUtils.toJsonString failed! ex ", e);
        }
        return null;
    }
}
