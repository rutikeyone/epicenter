package com.example.organizerforlaserhairremovalsalon.Services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.R;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

    private final Context context;
    OnItemListener onItemListener;

    private ArrayList<ServiceEntity> serviceList = new ArrayList<ServiceEntity>();


    public ServicesAdapter(Context context, ServicesAdapter.OnItemListener onItemListener) {
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ServiceEntity> data) {
        serviceList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.service_item, parent, false);

        return new ServiceViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceEntity service = serviceList.get(position);
        String name = service.getName();

        holder.bind(service);

        if (name != null && !name.isEmpty()) {
            holder.serviceNameTextView.setText(name);
            holder.serviceNameTextView.setVisibility(View.VISIBLE);
        } else {
            holder.serviceNameTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView serviceNameTextView;
        OnItemListener onItemListener;

        long id;

        public ServiceViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;
        }

        public void bind(ServiceEntity service) {
            this.id = service.getId();

            serviceNameTextView = itemView.findViewById(R.id.serviceNameTextView);

            String name = service.getName();

            if (name != null && !name.isEmpty()) {
                serviceNameTextView.setText(name);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(id);
        }
    }

    public interface OnItemListener {
        void onItemClick(Long id);
    }

}
