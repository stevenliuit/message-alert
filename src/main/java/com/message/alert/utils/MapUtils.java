package com.message.alert.utils;
import java.util.*;

abstract public class MapUtils {

  public static boolean isEmpty(final Map<?, ?> map) {
    return MapUtils.isEmpty(map);
  }

  public static boolean isNotEmpty(final Map<?, ?> map) {
    return MapUtils.isNotEmpty(map);
  }

  public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key, final Boolean defaultValue) {
    return MapUtils.getBoolean(map, key, defaultValue);
  }

  public static <K> String getString(final Map<? super K, ?> map, final K key) {
    return MapUtils.getString(map, key);
  }

  /**
   * Resort specified map according to keys and return LinkedHashMap.
   *
   * @param map  the source map to resort
   * @param keys the keys to provide sequence, null will be ignored
   * @param <K>  key type
   * @param <V>  value type
   * @return resorted LinkedHashMap
   */
  public static <K, V> Map<K, V> resort(Map<K, V> map, Collection<K> keys) {
    return resort(map, keys, null);
  }

  /**
   * Resort specified map according to keys and return LinkedHashMap. If the value
   * of found of specified key, use default value in case of it's not null.
   *
   * @param map          the source map to resort
   * @param keys         the keys to provide sequence, null will be ignored
   * @param defaultValue the default value
   * @param <K>          key type
   * @param <V>          value type
   * @return resorted LinkedHashMap
   */
  public static <K, V> Map<K, V> resort(Map<K, V> map, Collection<K> keys, V defaultValue) {
    Map<K, V> result = new LinkedHashMap<>();
    if (map == null) {
      return result;
    }
    if (keys == null) {
      result.putAll(map);
      return result;
    }
    keys.stream()
        .filter(Objects::nonNull)
        .forEach(k -> {
          V v = map.get(k);
          if (v != null) {
            result.putIfAbsent(k, v);
          } else {
            if (defaultValue != null) {
              result.putIfAbsent(k, defaultValue);
            }
          }
        });
    return result;
  }

  @SuppressWarnings("unchecked")
  static public <K, V> HashMap<K, V> map(Object... args) {
    if (args == null || args.length % 2 != 0) {
      throw new IllegalArgumentException();
    }
    HashMap<K, V> m = new HashMap<>();
    for (int i = 0; i < args.length; i += 2) {
      m.put((K) args[i], (V) args[i + 1]);
    }
    return m;
  }
}