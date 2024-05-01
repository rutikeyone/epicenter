package com.example.organizerforlaserhairremovalsalon.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.R;

import java.util.List;

public class EventAdapter extends ArrayAdapter<EventEntity> {
    public EventAdapter(@NonNull Context context, List<EventEntity> eventEntities) {
        super(context, 0, eventEntities);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EventEntity eventEntity = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
        }

        TextView eventEventCell = convertView.findViewById(R.id.eventCellEventTextView);
        TextView eventContactCell = convertView.findViewById(R.id.eventCellContactNameTextView);


        String eventTitle = eventEntity.getName() + " " + CalendarUtils.timeFormat(eventEntity.getTime());
        eventEventCell.setText(eventTitle);

        DataBaseHelperContacts dataBaseHelperContacts = new DataBaseHelperContacts(getContext().getApplicationContext());
        ContactEntity contactEntity = dataBaseHelperContacts.getContactById(eventEntity.getContactId());
        eventContactCell.setText(contactEntity.getName());

        return convertView;
    }
}
