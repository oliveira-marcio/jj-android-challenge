package com.example.jjandroidchallenge.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.models.Device;
import com.example.jjandroidchallenge.utils.Injector;
import com.example.jjandroidchallenge.utils.Utils;
import com.example.jjandroidchallenge.viewmodel.DetailsActivityViewModel;
import com.example.jjandroidchallenge.viewmodel.DetailsViewModelFactory;

import java.util.Calendar;

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

    private void bindViews(final Device device) {

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
                device.getLastCheckedOutDate() != null ? Utils.TMZToDate(device.getLastCheckedOutDate()) : "-"));

        checkInOutTextView.setText(device.getIsCheckedOut() ? R.string.details_check_in : R.string.details_check_out);

        checkInOutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheckout = device.getIsCheckedOut();
                if (isCheckout) {
                    doCheckIn();
                } else {
                    doCheckOut();
                }
            }
        });
    }

    private void doCheckIn() {
        mViewModel.toggleCheckStatus(false, null, null);
        Toast.makeText(DetailsActivity.this, R.string.dialog_check_in_confirmation, Toast.LENGTH_SHORT).show();
    }

    private void doCheckOut() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_check_out, null);

        final EditText nameEditText = view.findViewById(R.id.name);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(true)
                .setPositiveButton(R.string.dialog_check_out_confirm, null)
                .setNegativeButton(R.string.dialog_check_out_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = nameEditText.getText().toString().trim();
                        if (name.isEmpty()) {
                            Toast.makeText(DetailsActivity.this, R.string.dialog_check_out_error, Toast.LENGTH_SHORT).show();
                        } else {
                            mViewModel.toggleCheckStatus(
                                    true,
                                    nameEditText.getText().toString(),
                                    Utils.dateToTMZ(Calendar.getInstance().getTime()));
                            dialog.dismiss();
                            Toast.makeText(DetailsActivity.this, R.string.dialog_check_out_confirmation, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}
