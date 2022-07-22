package vip.lsjscl.braveman.json;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 工具类
 *
 * @author zxj
 * @Date 2022/6/21
 */
public class MapUtil
{
    private final Log log = LogFactory.get(MapUtil.class);

    private List<String> names;

    private JSONArray array = new JSONArray();

    public MapUtil() {
    }

    public MapUtil(String... names) {
        this.names = Arrays.asList(names);
    }

    /**
     * 设置字段名
     */
    public MapUtil setName(String... names) {
        this.names = Arrays.asList(names);
        return this;
    }

    /**
     * 设置字段值
     *
     * @param values 值
     */
    public <T> void put(T... values) {
        try {
            JSONObject jsonObj = new JSONObject();
            if (CollectionUtil.isNotEmpty(names)) {
                for (int i = 0; i < names.size(); i++) {
                    jsonObj.put(names.get(i), values[i]);
                }
            }
            else {
                for (int i = 0; i < values.length; i++) {
                    jsonObj.put("value" + i, values[i]);
                }
            }
            array.add(jsonObj);
        }
        catch (Exception e) {
            log.error("调用SzjsMapUtil工具类,传递的字段名,字段值数量不对应", e);
        }

    }

    /**
     * 获取JSONObject
     *
     * @param values 值
     * @return
     */
    public <T> JSONObject getJSON(T... values) {
        JSONObject resultJson = new JSONObject();
        try {
            for (int i = 0; i < names.size(); i++) {
                resultJson.put(names.get(i), values[i]);
            }
        }
        catch (Exception e) {
            log.error("调用SzjsMapUtil工具类,传递的字段名,字段值数量不对应", e);
        }
        return resultJson;
    }

    /**
     * 获取JSONArray
     *
     * @return
     */
    public JSONArray getArray() {
        return array;
    }
}
