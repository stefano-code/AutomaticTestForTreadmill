package com.android.inverter.test.testsuite.db;

public class Record
{
    public String name;
    public String type;
    public String script_startup;
    public String script_body;
    public String script_teardown;

    public Record(String name, String type, String script_startup, String script_body, String script_teardown){
        this.name = name;
        this.type = (type != null) ? type : "";
        this.script_startup = script_startup;
        this.script_body = script_body;
        this.script_teardown = script_teardown;
    }
}
