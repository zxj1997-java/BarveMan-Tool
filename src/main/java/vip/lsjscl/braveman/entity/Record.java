package vip.lsjscl.braveman.entity;

import java.io.Serializable;
import java.util.HashMap;

public class Record extends HashMap<String, Object> implements Serializable
{
    private static final long serialVersionUID = 905784513600884082L;

    public String getStr(String key) {
        return get(key) == null ? "" : String.valueOf(get(key));
    }
}
