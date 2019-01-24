package com.example.jjandroidchallenge.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jjandroidchallenge.R;
import com.example.jjandroidchallenge.models.Device;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private List<Device> mDevices;
    private DeviceAdapterClickHandler mHandler;
    private Context mContext;

    public interface DeviceAdapterClickHandler {
        void onItemClick(Device clickedDevice);
    }

    public DeviceAdapter(List<Device> devices, DeviceAdapterClickHandler handler) {
        mHandler = handler;
        mDevices = devices;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.deviceTextView.setText(
                String.format(mContext.getString(R.string.list_item_title),
                        mDevices.get(position).getDevice(),
                        mDevices.get(position).getOs())
        );
        holder.statusTextView.setText(
                mDevices.get(position).getIsCheckedOut() ?
                        String.format(mContext.getString(R.string.list_item_status_checked_out),
                                mDevices.get(position).getLastCheckedOutBy()) :
                        mContext.getString(R.string.list_item_status_checked_in)
        );
    }

    @Override
    public int getItemCount() {
        return mDevices == null ? 0 : mDevices.size();
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView deviceTextView;
        private TextView statusTextView;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceTextView = itemView.findViewById(R.id.tv_name);
            statusTextView = itemView.findViewById(R.id.tv_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mHandler.onItemClick(mDevices.get(getAdapterPosition()));
        }
    }
}
