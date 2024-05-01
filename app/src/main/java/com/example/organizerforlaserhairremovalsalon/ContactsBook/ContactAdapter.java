package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactVewHolder>{
    private Context context;
    private ArrayList<ContactEntity> contactEntityList;
    private ContactAdapter contactAdapter;
    OnItemListener onItemListener;

    public ContactAdapter(Context context, ArrayList<ContactEntity> contactEntityList, OnItemListener onItemListener) {
        this.context = context;
        this.contactEntityList = contactEntityList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ContactVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.contact_item, parent, false);

        List<Long> contactIds = new ArrayList<>();
        for (ContactEntity contactEntity : this.contactEntityList) {
            contactIds.add(contactEntity.getId());
        }
        return new ContactVewHolder(view, this.onItemListener, contactIds);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactVewHolder holder, int position) {

        ContactEntity contactEntity = contactEntityList.get(position);

        long id = contactEntity.getId();
        String name = contactEntity.getName();
        String phone = contactEntity.getPhone();

        holder.contactName.setText(name);
//        holder.contactName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Contact.class);
//                intent.putExtra("contactId", id);
//                context.startActivity(intent);
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Contact.class);
//                intent.putExtra("contactId", id);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return contactEntityList.size();
    }


    static class ContactVewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        List<Long> contactIds;
        TextView contactName;
        OnItemListener onItemListener;
        public ContactVewHolder(@NonNull View itemView, OnItemListener onItemListener, List<Long> contactIds) {
            super(itemView);

            this.contactName = itemView.findViewById(R.id.contact_name);
            this.onItemListener = onItemListener;
            this.contactIds = contactIds;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.onItemListener.onItemClick(this.getAdapterPosition(), this.contactIds.get(getAdapterPosition()));
        }
    }

    public interface OnItemListener {
        void onItemClick(int pos, Long contactId);
    }
}
