package com.lcc.kafkaUI.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class JsonUtils {
	private static final ObjectMapper INSTANCE = new ObjectMapper();

	/**
	 * 读取集合
	 *
	 * @param content content
	 * @return 集合
	 */
	public static Map<String, Object> readMap(@Nullable String content) {
		return readMap(content, String.class, Object.class);
	}

	/**
	 * 读取集合
	 *
	 * @param content    content
	 * @param valueClass 值类型
	 * @param <V>        泛型
	 * @return 集合
	 */
	public static <V> Map<String, V> readMap(@Nullable String content, Class<?> valueClass) {
		return readMap(content, String.class, valueClass);
	}

	/**
	 * 读取集合
	 *
	 * @param content    bytes
	 * @param keyClass   key类型
	 * @param valueClass 值类型
	 * @param <K>        泛型
	 * @param <V>        泛型
	 * @return 集合
	 */
	public static <K, V> Map<K, V> readMap(@Nullable String content, Class<?> keyClass, Class<?> valueClass) {
		if (!StringUtils.hasText(content)) {
			return Collections.emptyMap();
		}
		MapType mapType = INSTANCE.getTypeFactory()
			.constructMapType(Map.class, keyClass, valueClass);
		try {
			return INSTANCE.readValue(content, mapType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
