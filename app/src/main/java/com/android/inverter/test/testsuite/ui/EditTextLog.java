package com.android.inverter.test.testsuite.ui;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ScrollView;

import com.android.inverter.test.testsuite.framework.EngineLog;

public class EditTextLog implements EngineLog
{
    private final EditText et;
    private final ScrollView sv;
    private Activity act;

    public EditTextLog(Activity a, EditText t, ScrollView s)
    {
        act = a;
        et = t;
        sv = s;
    }

    public void clear()
    {
        et.setText("");
    }


    @Override
    public void add(String s)
    {
        Runnable doLog = new Runnable()
        {
            @Override
            public void run()
            {
                Log.e("SCRIPT_", s);
                et.setText(et.getText() + "\r\n" + s);
                sv.scrollTo(0, et.getBottom());
            }
        };
        act.runOnUiThread(doLog);
    }
}
