package org.dazzle.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dazzle.common.exception.BaseException;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/** @author hcqt@qq.com */
public class JsonUtils {

    /** 把json字符串转换成集合类  
     * @author hcqt@qq.com */
    public static Object toObj(final String jsonString) {
        if(jsonString != null) {
            return toObj0(new JsonParser().parse(jsonString), null, null);
        } else {
            return null;
        }
    }

    /** hcqt@qq.com */
    public static Object toObj(String jsonString, @SuppressWarnings("rawtypes") Class listClazz, @SuppressWarnings("rawtypes") Class mapClazz) {
        return toObj0(new JsonParser().parse(jsonString), listClazz, mapClazz);
    }

    /** 一些dom4j、response等流对象无法打印，如果猿友们知道怎么解决这个问题请邮件联系我，不胜感谢
     * @author hcqt@qq.com */
    public static String toJson(Object obj) {
        if(obj == null) {
            throw new BaseException("json_convert_3ghc", "obj参数不能为空");
        }
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().create().toJson(obj);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Object toObj0(JsonElement jsonElement, Class listClazz, Class mapClazz) {
        if(jsonElement != null) {
            if(jsonElement.isJsonArray()) {
                List list = null;
                if(listClazz == null) {
                    list = new ArrayList();
                } else {
                    try {
                        list = (List) listClazz.newInstance();
                    } catch (Exception e) {
                        throw new BaseException("json_convert_8h3Xv", "您传入的类型\"{0}\"在实例化并赋值的过程中发生异常，详情:", e, listClazz.getName(), EU.out(e));
                    }
                }
                JsonArray jsonArray = (JsonArray) jsonElement;
                for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
                    list.add(toObj0((JsonElement)iterator.next(), listClazz, mapClazz));
                }
                return list;
            } else if(jsonElement.isJsonObject()) {
                Map map = null;
                if(mapClazz == null) {
                    map = new LinkedHashMap();
                } else {
                    try {
                        map = (Map) mapClazz.newInstance();
                    } catch (Exception e) {
                        throw new BaseException("json_convert_3x7cG", "您传入的类型\"{0}\"在实例化并赋值的过程中发生异常，详情:", e, mapClazz.getName(), EU.out(e));
                    }
                }
                for (Entry<String, JsonElement> entry : ((JsonObject) jsonElement).entrySet()) {
                    map.put(entry.getKey(), toObj0(entry.getValue(), listClazz, mapClazz));
                }
                return map;
            } else if(jsonElement.isJsonPrimitive()) {
                return ((JsonPrimitive) jsonElement).getAsString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
//    public static void main(String[] args) {
//    String json = 
//              ""
//            + "{"
//            + "    '1': {"
//            + "        '1.1': {"
//            + "            '1.1.1': ["
//            + "                'aaaa',"
//            + "                'bbbb'"
//            + "            ],"
//            + "            '1.1.2': ["
//            + "                'cccc',"
//            + "                'dddd'"
//            + "            ]"
//            + "        },"
//            + "        '1.2': ["
//            + "            'eeee',"
//            + "            'ffff'"
//            + "        ],"
//            + "        '1.3': 'jjjj'"
//            + "    }"
//            + "}"
//            + "";
//    @SuppressWarnings("rawtypes")
//    Map map = (Map) toObj(json);
//    @SuppressWarnings("rawtypes")
//    Map map2 = (Map) toObj(json, LinkedList.class, ConcurrentSkipListMap.class);// ConcurrentHashMap
//    System.out.println("json字符串转成对象-->"+map);// 如果是转自定义对象的代码，直接使用google的代码即可
//    System.out.println("json字符串转成对象-->"+map2);
//    System.out.println("对象转成json字符串-->"+toJson(map));
//}
}
