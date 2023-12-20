package com.android.inverter.test.testsuite.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.inverter.test.testsuite.R;
import com.android.inverter.test.testsuite.db.UnitTestTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class FragmentEdit extends Fragment
{
    private static final String TAG = "FragmentEdit";

    public static final String NAME = "Name";
    public static final String TYPE = "Type";
    public static final String SCRIPT_STARTUP = "ScriptStartup";
    public static final String SCRIPT_BODY = "ScriptBody";
    public static final String SCRIPT_TEARDOWN = "ScriptTeardown";

    private String Name;
    private String Type;
    private String ScriptStartup;
    private String ScriptBody;
    private String ScriptTeardown;

    private TextInputEditText edName;
//    private TextInputEditText edClass;
    private TextInputEditText edScriptStartup;
    private TextInputEditText edScriptBody;
    private TextInputEditText edScriptTearDown;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Name = getArguments().getString(NAME);
            Type = getArguments().getString(TYPE);
            ScriptStartup = getArguments().getString(SCRIPT_STARTUP);
            ScriptBody = getArguments().getString(SCRIPT_BODY);
            ScriptTeardown = getArguments().getString(SCRIPT_TEARDOWN);

            Log.e(TAG, "Name " + Name + " Type " + Type);
            Log.e(TAG, "ScriptStartup " + ScriptStartup);
            Log.e(TAG, "ScriptBody " + ScriptBody);
            Log.e(TAG, "ScriptTeardown " + ScriptTeardown);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        edName = ((TextInputEditText) view.findViewById(R.id.edName));
        edName.setText(Name);
//        edClass = ((TextInputEditText) view.findViewById(R.id.edClass));
//        edClass.setText(Type);
        edScriptStartup = ((TextInputEditText) view.findViewById(R.id.edScriptStartup));
        edScriptStartup.setText(ScriptStartup);
        edScriptBody = ((TextInputEditText) view.findViewById(R.id.edScriptBody));
        edScriptBody.setText(ScriptBody);
        edScriptTearDown = ((TextInputEditText) view.findViewById(R.id.edScriptTearDown));
        edScriptTearDown.setText(ScriptTeardown);

        FloatingActionButton btnSave = (FloatingActionButton) view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v)
            {
                String name = edName.getText().toString();
//                String type = edClass.getText().toString();
                String scriptStartup = edScriptStartup.getText().toString();
                String scriptBody = edScriptBody.getText().toString();
                String scriptTearDown = edScriptTearDown.getText().toString();

                if(name != null && !name.isEmpty()
                        && scriptStartup != null && !scriptStartup.isEmpty()
                        && scriptBody != null && !scriptBody.isEmpty()
                        && scriptTearDown != null && !scriptTearDown.isEmpty())
                {
                    Log.e("TEST_TEST","name " + name + " scriptStartup " + scriptStartup + " scriptBody " + scriptBody + " scriptTearDown " + scriptTearDown);
                    UnitTestTable table = new UnitTestTable(getContext());
                    table.update(new com.android.inverter.test.testsuite.db.Record(name, "", scriptStartup, scriptBody, scriptTearDown));
                    NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
                    navController.navigate(R.id.fragmentList);
                }
                else
                    Snackbar.make(requireView(), "Enter all fields", Snackbar.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton btnBack = (FloatingActionButton) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
                navController.navigate(R.id.fragmentList);
            }
        });
    }
}
