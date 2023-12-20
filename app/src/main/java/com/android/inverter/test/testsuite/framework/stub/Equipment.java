package com.android.inverter.test.testsuite.framework.stub;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class Equipment {

    private static Equipment instance = null;

    private Map cache = new HashMap<String, Double>();


    public static synchronized Equipment getInstance(Context ctx)
    {
        if (instance == null)
        {
            instance = new Equipment(ctx);
        }
        return instance;
    }

    private Equipment(Context ctx)
    {
        instance = this;
    }

    public void start()
    {
    }

    public void stop()
    {
    }

    public void set(String property, Double val)
    {
        cache.put(property, val);
    }

    public double get(String property)
    {
        return (Double)(cache.get(property));
    }
}
