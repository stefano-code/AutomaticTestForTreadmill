package com.android.inverter.test.testsuite.ui;

import android.app.Activity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.inverter.test.testsuite.framework.EngineLog;

public class TextViewLog implements EngineLog
{
    private final TextView tw;
    private final ScrollView sv;
    private Activity act;
    private ListenerResult listenerResult;

    public interface ListenerResult
    {
        void end(String s);
    }

    public TextViewLog(Activity a, TextView t, ScrollView s, ListenerResult lr)
    {
        act = a;
        tw = t;
        sv = s;
        listenerResult = lr;
    }

    public void clear()
    {
        tw.setText("");
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
                tw.setText(tw.getText() + "\r\n" + s);
                sv.scrollTo(0, tw.getBottom()+1);
                if(s.contains("wrong") || s.contains("assert failed") || s.contains("test completed"))
                    listenerResult.end(s);
            }
        };
        act.runOnUiThread(doLog);
    }
}

