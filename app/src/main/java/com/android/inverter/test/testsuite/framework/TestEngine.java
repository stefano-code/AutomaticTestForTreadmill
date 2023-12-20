package com.android.inverter.test.testsuite.framework;

import android.content.Context;

import bsh.Interpreter;
import bsh.TargetError;
import util.Pair;

public class TestEngine {
    private final Context ctx;
    private final EngineLog log;
    private String setupScript;
    private String tearDownScript;

    public void setSetupScript(String script)
    {
        setupScript = script;
    }
    public void setTearDownScript(String script)
    {
        tearDownScript = script;
    }
    public TestEngine(Context ctx, EngineLog log ) { this.ctx = ctx; this.log = log; }

    public void executeSingleTest(String script, String name) {
        Thread th = new Thread(() -> {
            Interpreter i = new Interpreter();

            try  {
                TestState.state = TestState.State.Progress;
                log.add("script: \"" + name  +"\"");
                i.set("eq", new Treadmill(ctx, log));
                i.set("Speed", new SettingSpeed(ctx));
                i.set("MaxSpeed", new SettingMaxSpeed(ctx));
                i.set("Acceleration", new SettingAcceleration(ctx));
                log.add("executing startup script");
                i.eval(setupScript);
                log.add("executing script body");
                i.eval(script);
            } catch (TargetError te) {
                Throwable t = te.getTarget();
                log.add("assert failed: " + t.getMessage());
                TestState.state = TestState.State.Idle;
                return;
            } catch (Exception e) {
                log.add("wrong script: " + e.getMessage());
                TestState.state = TestState.State.Idle;
                return;
            } finally {
                try {
                    log.add("executing teardown script");
                    i.eval(tearDownScript);
                } catch (Exception e) {
                    log.add("wrong teardown script: " + e.getMessage());
                    TestState.state = TestState.State.Idle;
                    return;
                }
            }
            TestState.state = TestState.State.Idle;
            log.add("test completed (OK) ");
        });
        th.start();
    }
}

/*

                    i.set("display", new Display(ctx, log));
                    i.set("START_NAVIGATION_BUTTON", new Pair<Integer, Integer>(10, 10));
 */