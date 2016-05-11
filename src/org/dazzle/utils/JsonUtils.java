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

/**本软件为开源项目，最新项目发布于github，可提交您的代码到本开源软件，项目网址：<a href="https://github.com/hcqt/dazzle">https://github.com/hcqt/dazzle</a><br />
 * 本软件内的大多数方法禁止Override，原因是作者提倡组合，而非继承，如果您确实需要用到继承，而又希望用本软件提供的方法名称与参数列表，建议您自行采用适配器设计模式，逐个用同名方法包裹本软件所提供的方法，这样您依然可以使用继承
 * @see #toObj(String)
 * @author hcqt@qq.com*/
public class JsonUtils {

    /**把json字符串转换成集合类<br />
     * array将转换为List<Object>对象<br />
     * key-value将转换为Map<String, Object>对象<br />
     * 普通类型将转换为String<br />
     * 若json为数组与键值对嵌套，将会转换为list与map嵌套，嵌套不限层级
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

    /**一些dom4j、response等流对象无法转换
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
