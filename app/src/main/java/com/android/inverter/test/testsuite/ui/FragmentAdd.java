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

public class FragmentAdd extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton btnSave = (FloatingActionButton) view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v)
            {
                TextInputEditText edName = ((TextInputEditText) view.findViewById(R.id.edName));
                String name = edName.getText().toString();
//                TextInputEditText edClass = ((TextInputEditText) view.findViewById(R.id.edClass));
//                String type = edClass.getText().toString();
                TextInputEditText edScriptStartup = ((TextInputEditText) view.findViewById(R.id.edScriptStartup));
                String scriptStartup = edScriptStartup.getText().toString();
                TextInputEditText edScriptBody = ((TextInputEditText) view.findViewById(R.id.edScriptBody));
                String scriptBody = edScriptBody.getText().toString();
                TextInputEditText edScriptTearDown = ((TextInputEditText) view.findViewById(R.id.edScriptTearDown));
                String scriptTearDown = edScriptTearDown.getText().toString();
                if(name != null && !name.isEmpty()
                        && scriptStartup != null && !scriptStartup.isEmpty()
                        && scriptBody != null && !scriptBody.isEmpty()
                        && scriptTearDown != null && !scriptTearDown.isEmpty())
                {
                    Log.e("TEST_TEST","name " + name + " scriptStartup " + scriptStartup + " scriptBody " + scriptBody + " scriptTearDown " + scriptTearDown);
                    UnitTestTable table = new UnitTestTable(getContext());
                    table.add(new com.android.inverter.test.testsuite.db.Record(name, "", scriptStartup, scriptBody, scriptTearDown));
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