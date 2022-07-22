package com.epoint.szjs.zhrq.ngas.rest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.epoint.szjs.zhrq.ngas.comment.ChineseField;
import vip.lsjscl.braveman.entity.Record;
import vip.lsjscl.braveman.service.EnumInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口基类
 *
 * @author zxj
 * @Date 2022/6/17
 */
public class BaseController
{
    private final Log log = LogFactory.get(BaseController.class);

    /**
     * 获取接口化数据,应用于袋鼠云中点位详情
     * 获取对象获取 字段中文名称:字段值
     * [{
     * "text": "企业名称：",
     * "value": "XX企业名称内容"
     * },....]
     *
     * @param obj 对象
     * @return
     */
    public <T extends Record> JSONArray getChineseField(T obj)
            throws InvocationTargetException, IllegalAccessException {
        JSONArray resultArray = new JSONArray();
        if (ObjectUtil.isNull(obj)) {
            return resultArray;
        }
        Class<?> clz = obj.getClass();
        Method[] declaredMethods = clz.getDeclaredMethods();

        JSONObject fieldJson;
        for (Method method : declaredMethods) {
            //获取所有带ChineseField注解的方法
            ChineseField annotation = method.getDeclaredAnnotation(ChineseField.class);
            if (annotation != null) {
                fieldJson = new JSONObject();
                //字段中文
                fieldJson.put("text", annotation.name());
                //获取值
                Object value = method.invoke(obj);
                if (ObjectUtil.isNotNull(value)) {
                    //如果是时间格式
                    if (value instanceof Date) {
                        String format = annotation.format();
                        String date2String = DateUtil.format((Date) value, format);
                        //字段值
                        fieldJson.put("value", date2String);
                    }
                    //如果代码项不为空
                    else if (StrUtil.isNotBlank(annotation.codeItem())) {
                        //TODO
                        //字段值
                        fieldJson.put("value", value);
                    }
                    else {
                        fieldJson.put("value", value);
                    }
                }
                else {
                    fieldJson.put("value", "");
                }
                //字段排序
                fieldJson.put("order", annotation.order());

                resultArray.add(fieldJson);
            }
        }
        //排序,如果需要倒序加上.reversed()
        resultArray.sort(Comparator.comparing(o -> ((JSONObject) o).getIntValue("order")));
        return resultArray;
    }

    /**
     * 获取结构化数据,应用于袋鼠云列表
     *
     * @param list    数据列表
     * @param values  枚举值集合
     * @param type    分类字段
     * @param url     详情页面的查询接口
     * @param columns 要查询的字段
     * @return
     */
    public <T extends Record> JSONArray getStructuredData(List<T> list, EnumInterface[] values, String type, String url,
            String... columns) {
        //对象集合
        JSONArray objArray = new JSONArray();
        //单个对象
        JSONObject obj;

        for (Record record : list) {
            for (EnumInterface value : values) {
                if (value.getValue().equals(record.getStr(type))) {
                    obj = new JSONObject();
                    //获取所有字段
                    for (String column : columns) {
                        obj.put(column, record.get(column));
                    }
                    obj.put("url", url);
                    objArray.add(obj);
                }
            }
        }
        return objArray;
    }

    /**
     * 将Map数据转为JsonArray
     *
     * @param map
     * @return
     */
    public <T> JSONArray getJsonArray(Map<String, T> map) {
        JSONArray array = new JSONArray();
        JSONObject obj;
        for (Map.Entry<String, T> entry : map.entrySet()) {
            obj = new JSONObject();
            obj.put("name", entry.getKey());
            obj.put("value", entry.getValue());
            array.add(obj);
        }
        return array;
    }

    /**
     * 将Map数据转为JsonArray
     *
     * @param map
     * @return
     */
    public JSONArray getJsonArrayByMList(Map<String, List<Object>> map) {
        JSONArray array = new JSONArray();
        JSONObject obj;
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            obj = new JSONObject();
            obj.put("name", entry.getKey());
            List<Object> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                if (i == 0) {
                    obj.put("value", values.get(i));
                }
                else {
                    obj.put("value" + i, values.get(i));
                }
            }
            array.add(obj);
        }
        return array;
    }

}
