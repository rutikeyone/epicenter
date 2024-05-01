package com.example.organizerforlaserhairremovalsalon.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactAdapter;
import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;
import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactListActivity;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperEvents;
import com.example.organizerforlaserhairremovalsalon.R;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity{
    private EditText eventNameEditText;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventContactTextView;
    private LocalTime localTime;
    private EventEntity eventEntity;
    private DataBaseHelperEvents dataBaseHelperEvents;
    private DataBaseHelperContacts dataBaseHelperContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        this.initialiseWidgets();
        this.dataBaseHelperEvents = new DataBaseHelperEvents(getApplicationContext());
        this.dataBaseHelperContacts = new DataBaseHelperContacts(getApplicationContext());

        Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey("OPEN_SELECTED")) {
            this.eventEntity = CalendarUtils.eventEntitySelected;
            this.localTime = this.eventEntity.getTime();
            this.eventEntity.setContactEntity(this.dataBaseHelperContacts.getContactById(this.eventEntity.getContactId()));

            this.setTextWidgets();
        } else {
            this.localTime = LocalTime.now();
            this.eventEntity = new EventEntity(
                "",
                CalendarUtils.dateSelected,
                LocalTime.now()
            );
        }

        this.eventDateTextView.setText("Дата: " + CalendarUtils.dateFormat(CalendarUtils.dateSelected));
        this.setTimeText();

    }

    private void initialiseWidgets() {
        this.eventDateTextView = findViewById(R.id.eventDatePickTV);
        this.eventNameEditText = findViewById(R.id.eventNameEditTxt);
        this.eventTimeTextView = findViewById(R.id.eventTimePickTV);
        this.eventContactTextView = findViewById(R.id.eventContactPickTV);
    }

    private void setTextWidgets() {
        this.eventNameEditText.setText(this.eventEntity.getName());
        if (this.eventEntity.getContactEntity() != null) {
            this.eventContactTextView.setText("Клиент: " + this.eventEntity.getContactEntity().getName());
        }
    }

    public void saveEventAction(View view) {
        this.eventEntity.setName(this.eventNameEditText.getText().toString());
        this.eventEntity.setTime(this.localTime);

        if (this.eventEntity.getId() == 0) {
            this.dataBaseHelperEvents.  addEvent(this.eventEntity);
            EventEntity.eventsArrayList.add(this.eventEntity);
        } else {
            this.dataBaseHelperEvents.editEventById(this.eventEntity);
        }

        this.finish();
    }

    public void deleteEventAction(View view) {
        this.dataBaseHelperEvents.deleteEventById(this.eventEntity.getId());
        this.finish();
    }

    public void setContact(View view) {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.putExtra("IsChooseView", true);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}

        if (data.getExtras().containsKey("contactId")) {
            this.eventEntity.setContactId(data.getExtras().getLong("contactId"));
            ContactEntity contactEntity = this.dataBaseHelperContacts.getContactById(this.eventEntity.getContactId());
            this.eventEntity.setContactEntity(contactEntity);

            this.eventContactTextView.setText("Клиент: " + this.eventEntity.getContactEntity().getName());
        }
    }

    public void setTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
            this,
            R.style.DialogTimeTheme,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    localTime = LocalTime.of(hourOfDay, minute);
                    setTimeText();
                }
            },
            this.localTime.getHour(),
            this.localTime.getMinute(),
            true
        );

        timePickerDialog.show();
    }

    private void setTimeText() {
        this.eventTimeTextView.setText("Время: " + CalendarUtils.timeFormat(this.localTime));
    }
}