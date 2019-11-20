package com.l2r.utils;


import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

/**
 * @author sealyu on 4/9/16.
 */
public class ObjectUtil {
    private static Map<String, OP> OPMap;
    public static Boolean isInstance(Class targetClazz, Object obj)
    {
        return Boolean.valueOf(targetClazz.isInstance(obj));
    }
    public static Boolean isPrimitive(Object obj) {
        return Boolean.valueOf(obj.getClass().isPrimitive());
    }

    public static Boolean isNumber(Object obj) {
        return Boolean.valueOf(Number.class.isInstance(obj));
    }

    public static Boolean isDate(Object obj) {
        return Boolean.valueOf((java.util.Date.class.isInstance(obj)) || (Calendar.class.isInstance(obj)));
    }

    public static Boolean isString(Object obj) {
        return isInstance(String.class, obj);
    }

    public static boolean isArray(Object obj)
    {
        return (obj != null) && (obj.getClass().isArray());
    }

    public static boolean isCollection(Object obj) {
        return (obj != null) && ((obj instanceof Collection));
    }

    public static boolean isMap(Object obj) {
        return (obj != null) && ((obj instanceof Map));
    }

    public static boolean isCheckedException(Throwable ex)
    {
        return (!(ex instanceof RuntimeException)) && (!(ex instanceof Error));
    }

    public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions)
    {
        if (!isCheckedException(ex)) {
            return true;
        }
        if (declaredExceptions != null) {
            int i = 0;
            while (i < declaredExceptions.length) {
                if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object obj)
    {
        if (obj == null)
            return true;
        if ((obj instanceof String))
            return ((String)obj).isEmpty();
        if ((obj instanceof Collection))
            return ((Collection)obj).isEmpty();
        if (obj.getClass().isArray())
            return Array.getLength(obj) < 1;
        if ((obj instanceof Map)) {
            return ((Map)obj).isEmpty();
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    public static Boolean isNullOrEmpty(String target)
    {
        return Boolean.valueOf((target == null) || (target.trim().length() < 1));
    }

    public static Boolean NotEmpty(String target) {
        return Boolean.valueOf(!isNullOrEmpty(target).booleanValue());
    }

    public static boolean isNullOrEmpty(Object[] array)
    {
        return (array == null) || (array.length == 0);
    }

    public static boolean isNotEmpty(Object[] array) {
        return !isNullOrEmpty(array);
    }

    public static boolean isNullOrEmpty(Collection collection)
    {
        return (collection == null) || (collection.isEmpty());
    }

    public static boolean isNotEmpty(Collection collection)
    {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNullOrEmpty(Map map)
    {
        return (map == null) || (map.isEmpty());
    }

    public static boolean isNotEmpty(Map map)
    {
        return !isNullOrEmpty(map);
    }
    public static OP getOP(String operation)
    {
        if ((operation != null) && (operation.trim().length() > 0)) {
            String key = operation.trim().toUpperCase();
            if (OPMap.containsKey(key)) {
                return (OP)OPMap.get(key);
            }
        }
        return OP.EQ;
    }
    public static <T> T ifNullValue(T value, T convertValue)
    {
        if (value == null) {
            return convertValue;
        }
        return value;
    }
}
