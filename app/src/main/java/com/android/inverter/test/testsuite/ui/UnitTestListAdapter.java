package com.android.inverter.test.testsuite.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.inverter.test.testsuite.R;
import com.android.inverter.test.testsuite.db.UnitTestTable;

import java.util.ArrayList;
import java.util.List;

public class UnitTestListAdapter extends RecyclerView.Adapter<UnitTestListAdapter.ViewHolder>
{
    private static final String TAG = "UnitTestListAdapter";
    private final EditTextLog log;

    private List<com.android.inverter.test.testsuite.db.Record> items = new ArrayList<>();

    private Context ctx;

    public UnitTestListAdapter(List<com.android.inverter.test.testsuite.db.Record> items, EditTextLog log)
    {
        this.items = items;
        this.log = log;
    }

    public void bind(List<com.android.inverter.test.testsuite.db.Record> items)
    {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ctx = parent.getContext();
        return new ViewHolder(
                LayoutInflater.from(ctx).inflate(R.layout.item_unittest, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView name;
        //SS public final TextView type;
        public final TextView script_startup;
        public final TextView script_body;
        public final TextView script_teardown;

        public final ImageButton btnRun;
        public final ImageButton btnEdit;
        public final ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            //SS type = itemView.findViewById(R.id.type);
            script_startup = itemView.findViewById(R.id.script_startup);
            script_body = itemView.findViewById(R.id.script_body);
            script_teardown = itemView.findViewById(R.id.script_teardown);
            btnRun = itemView.findViewById(R.id.unittest_run);
            btnEdit = itemView.findViewById(R.id.unittest_edit);
            btnDelete = itemView.findViewById(R.id.unittest_delete);
        }

        public void bind(com.android.inverter.test.testsuite.db.Record item)
        {
            name.setText(item.name);
            //SS type.setText(item.type);
            script_startup.setText(item.script_startup);
            script_body.setText(item.script_body);
            script_teardown.setText(item.script_teardown);

            btnRun.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
//                    TestEngine te = new TestEngine( ctx, log);
//                    te.setSetupScript( item.script_startup );
//                    te.setTearDownScript( item.script_teardown);
//                    log.clear();
//                    te.executeSingleTest(item.script_body, item.name);

                    NavController navController = Navigation.findNavController((Activity) ctx, R.id.nav_host_fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(FragmentEdit.NAME, item.name);
                    bundle.putString(FragmentEdit.TYPE, item.type);
                    bundle.putString(FragmentEdit.SCRIPT_STARTUP, item.script_startup);
                    bundle.putString(FragmentEdit.SCRIPT_BODY, item.script_body);
                    bundle.putString(FragmentEdit.SCRIPT_TEARDOWN, item.script_teardown);
                    navController.navigate(R.id.fragmentRun, bundle);
                    Log.e(TAG,"RUN " + item.name);
                }
            });
            btnEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    NavController navController = Navigation.findNavController((Activity) ctx, R.id.nav_host_fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(FragmentEdit.NAME, item.name);
                    bundle.putString(FragmentEdit.TYPE, item.type);
                    bundle.putString(FragmentEdit.SCRIPT_STARTUP, item.script_startup);
                    bundle.putString(FragmentEdit.SCRIPT_BODY, item.script_body);
                    bundle.putString(FragmentEdit.SCRIPT_TEARDOWN, item.script_teardown);
                    navController.navigate(R.id.fragmentEdit, bundle);
                    Log.e(TAG,"EDIT " + item.name);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.e(TAG,"DELETE " + item.name);
                    UnitTestTable table = new UnitTestTable(ctx);
                    int numRecordDeleted = table.clear(item.name, item.type);
                    Log.e(TAG,"numRecordDeleted " + numRecordDeleted);
                    items = table.getAll();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
