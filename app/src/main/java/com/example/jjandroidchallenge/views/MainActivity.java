package com.example.jjandroidchallenge.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.utils.Injector;
import com.example.jjandroidchallenge.viewmodel.MainActivityViewModel;
import com.example.jjandroidchallenge.viewmodel.MainViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeviceAdapter.DeviceAdapterClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private List<Device> mDevices;
    private FloatingActionButton mFab;
    private SwipeRefreshLayout mSwipeRefreshLayout;
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
        mEmptyView = findViewById(R.id.empty_view);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                showDeleteConfirmationDialog(mDevices.get(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(mRecyclerView);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDevice();
            }
        });

        MainViewModelFactory factory = Injector
                .provideMainViewModelFactory(this.getApplicationContext());

        mViewModel = ViewModelProviders
                .of(this, factory)
                .get(MainActivityViewModel.class);

        mViewModel.getAllDevices().observe(this, new Observer<List<Device>>() {
            @Override
            public void onChanged(@Nullable List<Device> devices) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (devices.size() == 0) {
                    showEmptyView();
                } else {
                    mDevices.clear();
                    mDevices.addAll(devices);
                    mAdapter.notifyDataSetChanged();
                    showDeviceList();
                }
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshDevices();
            }
        });
    }

    private void showDeviceList() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
    }

    private void showEmptyView() {
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }

    private void addDevice() {
        Intent intent = new Intent(this, AddDeviceActivity.class);
        startActivity(intent);
    }

    private void removeDevice(Device device) {
        mViewModel.removeDevice(device);
        Toast.makeText(this, R.string.delete_device_success, Toast.LENGTH_SHORT).show();
    }

    private void showDeleteConfirmationDialog(final Device device) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                removeDevice(device);
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
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("device", clickedDevice);
        startActivity(intent);
    }
}
