package com.example.jjandroidchallenge.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.utils.Injector;
import com.example.jjandroidchallenge.viewmodel.DetailsActivityViewModel;
import com.example.jjandroidchallenge.viewmodel.DetailsViewModelFactory;

public class DetailsActivity extends AppCompatActivity {

    private DetailsActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Device device = getIntent().getParcelableExtra("device");

        DetailsViewModelFactory factory = Injector.provideDetailsViewModelFactory(device.getId());
        mViewModel = ViewModelProviders
                .of(this, factory)
                .get(DetailsActivityViewModel.class);

        mViewModel.getDevice().observe(this, new Observer<Device>() {
            @Override
            public void onChanged(@Nullable Device device) {
                bindViews(device);
            }
        });

    }

    private void bindViews(Device device) {

        TextView deviceTextView = findViewById(R.id.tv_device);
        TextView osTextView = findViewById(R.id.tv_os);
        TextView manufacturerTextView = findViewById(R.id.tv_manufacturer);
        TextView lastUserTextView = findViewById(R.id.tv_last_user);
        TextView lastDateTextView = findViewById(R.id.tv_last_date);
        TextView checkInOutTextView = findViewById(R.id.tv_check_in_out);

        deviceTextView.setText(getString(R.string.details_device, device.getDevice()));
        osTextView.setText(getString(R.string.details_os, device.getOs()));
        manufacturerTextView.setText(getString(R.string.details_manufacturer, device.getManufacturer()));
        lastUserTextView.setText(getString(R.string.details_last_user,
                device.getLastCheckedOutBy() != null ? device.getLastCheckedOutBy() : "-"));
        lastDateTextView.setText(getString(R.string.details_last_date,
                device.getLastCheckedOutDate() != null ? device.getLastCheckedOutDate() : "-"));

        checkInOutTextView.setText(device.getIsCheckedOut() ? R.string.details_check_in : R.string.details_check_out);

        checkInOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.toggleCheckStatus();
            }
        });
    }
}
