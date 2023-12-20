package com.android.inverter.test.testsuite.framework;

import android.content.Context;

import com.android.inverter.test.testsuite.framework.stub.Equipment;


public abstract class Setting
{
    private String readableName;
    protected final Context ctx;
    Equipment eq;


    protected Setting(Context ctx, String name)
    {
        this.ctx = ctx;
        readableName = name;
        eq = Equipment.getInstance( ctx );
    }

    public String name()
    {
        return readableName ;
    }

    public abstract void setValue(double value);
    public abstract double getValue();
}
