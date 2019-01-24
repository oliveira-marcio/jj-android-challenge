package com.example.jjandroidchallenge.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.utils.Injector;
import com.example.jjandroidchallenge.viewmodel.AddDeviceActivityViewModel;
import com.example.jjandroidchallenge.viewmodel.AddDeviceViewModelFactory;

public class AddDeviceActivity extends AppCompatActivity {

    private AddDeviceActivityViewModel mViewModel;

    private boolean mDeviceHasChanged = false;

    private EditText mDeviceEditText;
    private EditText mOSEditText;
    private EditText mManufacturerEditText;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mDeviceHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        mDeviceEditText = findViewById(R.id.edt_device);
        mOSEditText = findViewById(R.id.edt_os);
        mManufacturerEditText = findViewById(R.id.edt_manufacturer);

        mDeviceEditText.setOnTouchListener(mTouchListener);
        mOSEditText.setOnTouchListener(mTouchListener);
        mManufacturerEditText.setOnTouchListener(mTouchListener);

        AddDeviceViewModelFactory factory = Injector.provideAddDeviceViewModelFactory();
        mViewModel = ViewModelProviders
                .of(this, factory)
                .get(AddDeviceActivityViewModel.class);
    }

    private void addDevice() {
        String device = mDeviceEditText.getText().toString();
        String os = mOSEditText.getText().toString();
        String manufacturer = mManufacturerEditText.getText().toString();

        if (device.isEmpty() || os.isEmpty() || manufacturer.isEmpty()) {
            Toast.makeText(this, R.string.add_device_empty_error, Toast.LENGTH_SHORT).show();
        } else {
            mViewModel.addDevice(device, os, manufacturer);
            mDeviceHasChanged = true;
            Toast.makeText(this, R.string.add_device_success, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                addDevice();
                return true;

            case android.R.id.home:
                if (!mDeviceHasChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(AddDeviceActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!mDeviceHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_discard_message);
        builder.setPositiveButton(R.string.dialog_discard_confirm, discardButtonClickListener);
        builder.setNegativeButton(R.string.dialog_discard_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
