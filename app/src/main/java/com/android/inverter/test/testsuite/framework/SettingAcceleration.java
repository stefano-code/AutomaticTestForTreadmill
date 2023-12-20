package com.android.inverter.test.testsuite.framework;

import android.content.Context;

public class SettingAcceleration extends Setting
{
    public SettingAcceleration(Context ctx)
    {
        super(ctx, "Acceleration");
    }

    @Override
    public void setValue(double value)
    {
        eq.set("Acceleration", value);
    }

    @Override
    public double getValue()
    {
        return eq.get("Acceleration");
    }
}
