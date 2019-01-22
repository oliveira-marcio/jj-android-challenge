package com.example.jjandroidchallenge.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeviceAdapter.DeviceAdapterClichHandler {

    private RecyclerView mRecyclerView;
    private List<Device> mDevices;
    private FloatingActionButton mFab;
    private DeviceAdapter mAdapter;

    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDevices = new ArrayList<>();

        mAdapter = new DeviceAdapter(mDevices, this);

        mRecyclerView = findViewById(R.id.rv_devices);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                showDeleteConfirmationDialog();
            }
        }).attachToRecyclerView(mRecyclerView);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDevice();
            }
        });

        mViewModel = ViewModelProviders
                .of(this)
                .get(MainActivityViewModel.class);

        mViewModel.getAllDevices().observe(this, new Observer<List<Device>>() {
            @Override
            public void onChanged(@Nullable List<Device> devices) {
                mDevices.clear();
                mDevices.addAll(devices);
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    
    private void addDevice(){
        Toast.makeText(this, R.string.toast_wip, Toast.LENGTH_SHORT).show();
    }

    private void removeDevice(){
        Toast.makeText(MainActivity.this, R.string.toast_wip, Toast.LENGTH_SHORT).show();;
        mAdapter.notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeDevice();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onItemClick(Device clickedDevice) {
        Toast.makeText(this, clickedDevice.getDevice(), Toast.LENGTH_SHORT).show();
    }
}