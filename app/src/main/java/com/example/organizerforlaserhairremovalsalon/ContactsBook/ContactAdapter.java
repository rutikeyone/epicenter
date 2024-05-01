package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVewHolder> {
    private final Context context;
    OnItemListener onItemListener;

    private ArrayList<ContactEntity> contactEntityList = new ArrayList<>();

    public ContactAdapter(Context context, OnItemListener onItemListener) {
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(ArrayList<ContactEntity> data) {
        contactEntityList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.contact_item, parent, false);

        return new ContactVewHolder(view, this.onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVewHolder holder, int position) {

        ContactEntity contactEntity = contactEntityList.get(position);

        long id = contactEntity.getId();
        String name = contactEntity.getName();
        String phone = contactEntity.getPhone();
        Bitmap image = contactEntity.getImage();

        holder.contactName.setText(name);
        holder.contactPhone.setText(phone);

        holder.bind(id);

        if (image != null) {
            holder.contactImage.setImageBitmap(image);
        } else {
            holder.contactImage.setImageResource(R.drawable.baseline_person_24);
        }
    }

    @Override
    public int getItemCount() {
        return contactEntityList.size();
    }


    static class ContactVewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contactName;
        TextView contactPhone;
        CircleImageView contactImage;
        OnItemListener onItemListener;

        long id;

        public ContactVewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            this.contactName = itemView.findViewById(R.id.contact_name);
            this.contactPhone = itemView.findViewById(R.id.contact_phone);
            this.contactImage = itemView.findViewById(R.id.contact_image);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        public void bind(long id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            this.onItemListener.onItemClick(id);
        }
    }

    public interface OnItemListener {
        void onItemClick(long id);
    }
}
