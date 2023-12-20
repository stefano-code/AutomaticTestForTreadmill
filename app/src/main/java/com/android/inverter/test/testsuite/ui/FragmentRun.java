package com.android.inverter.test.testsuite.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.inverter.test.testsuite.R;
import com.android.inverter.test.testsuite.framework.TestEngine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.FileUtil;

public class FragmentRun extends Fragment implements TextViewLog.ListenerResult
{
    private static final String TAG = "FragmentRun";

    public static final String NAME = "Name";
    public static final String TYPE = "Type";
    public static final String SCRIPT_STARTUP = "ScriptStartup";
    public static final String SCRIPT_BODY = "ScriptBody";
    public static final String SCRIPT_TEARDOWN = "ScriptTeardown";

    private String Name;
//    private String Type;
    private String ScriptStartup;
    private String ScriptBody;
    private String ScriptTeardown;

    private TextView textResult;
    private TextView name;

//    private TextInputEditText edName;
//    private TextInputEditText edClass;
//    private TextInputEditText edScriptStartup;
//    private TextInputEditText edScriptBody;
//    private TextInputEditText edScriptTearDown;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Name = getArguments().getString(NAME);
//            Type = getArguments().getString(TYPE);
            ScriptStartup = getArguments().getString(SCRIPT_STARTUP);
            ScriptBody = getArguments().getString(SCRIPT_BODY);
            ScriptTeardown = getArguments().getString(SCRIPT_TEARDOWN);

            Log.e(TAG, "Name " + Name);
            Log.e(TAG, "ScriptStartup " + ScriptStartup);
            Log.e(TAG, "ScriptBody " + ScriptBody);
            Log.e(TAG, "ScriptTeardown " + ScriptTeardown);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_run, container, false);
    }

//    Button button;
//    LinearLayout ll;
//    ImageButton b;
//    RelativeLayout r;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        name = (TextView) view.findViewById(R.id.name);
        name.setText(Name);

        textResult = (TextView) view.findViewById(R.id.textResult);
        textResult.setText("PROGRESS..");

//        EditText logText = (EditText) view.findViewById(R.id.scriptLog);
//        ScrollView scrollView = (ScrollView )view.findViewById(R.id.logScroll);
//
//        EditTextLog log = new  EditTextLog(getActivity(), logText, scrollView );

        TextView logText = (TextView) view.findViewById(R.id.scriptLog);
        ScrollView scrollView = (ScrollView )view.findViewById(R.id.logScroll);

        TextViewLog log = new  TextViewLog(getActivity(), logText, scrollView, this );

        //SS logText.setEnabled(false);

//        edName = ((TextInputEditText) view.findViewById(R.id.edName));
//        edName.setText(Name);
//        edClass = ((TextInputEditText) view.findViewById(R.id.edClass));
//        edClass.setText(Type);

        TestEngine te = new TestEngine( getActivity(), log);
        te.setSetupScript( ScriptStartup );
        te.setTearDownScript( ScriptTeardown );
        log.clear();
        te.executeSingleTest(ScriptBody, Name);

        FloatingActionButton btnSaveOnFile = (FloatingActionButton) view.findViewById(R.id.btnSaveOnFile);

        btnSaveOnFile.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v)
            {
                //SS save on file
                saveOnFile(Name, "", logText.getText().toString());
                NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
                navController.navigate(R.id.fragmentList);
            }
        });

        FloatingActionButton btnBack = (FloatingActionButton) view.findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v)
            {
                NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
                navController.navigate(R.id.fragmentList);
            }
        });

    }

    private void saveOnFile(String name, String tag, String toString)
    {
        new Thread (new Runnable(){
            @Override
            public void run()
            {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                String strDate = formatter.format(date);
                String fileName = "/data/data/" + getContext().getPackageName() + "/" +name + "_" + ((tag != null && !tag.isEmpty() ? tag : "") + "_" + strDate+ ".txt");
                fileName = fileName.replace(" ", "_");

                FileUtil.writeToFile(fileName, toString);
            }
        }).start();


    }

    @Override
    public void end(String s)
    {
        textResult.setText(s);
    }
}
