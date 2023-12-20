package com.android.inverter.test.testsuite.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.inverter.test.testsuite.R;
import com.android.inverter.test.testsuite.db.UnitTestTable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class FragmentList extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton btn = (FloatingActionButton) view.findViewById(R.id.btnAdd);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                NavController navController = Navigation.findNavController((Activity) getContext(), R.id.nav_host_fragment);
                navController.navigate(R.id.fragmentAdd);
            }
        });


        EditText logText = (EditText) view.findViewById(R.id.scriptLog);
        ScrollView scrollView = (ScrollView )view.findViewById(R.id.logScroll);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_unittest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        UnitTestTable table = new UnitTestTable(getContext());
        ArrayList<com.android.inverter.test.testsuite.db.Record> items = table.getAll();

//        ArrayList<com.android.inverter.test.testsuite.db.Record> items = new ArrayList<com.android.inverter.test.testsuite.db.Record>();

        EditTextLog log = new  EditTextLog(getActivity(), logText, scrollView );

        recyclerView.setAdapter(new UnitTestListAdapter(items, log));
    }
}