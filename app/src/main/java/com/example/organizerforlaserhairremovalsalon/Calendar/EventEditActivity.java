package com.example.organizerforlaserhairremovalsalon.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactEntity;
import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactListActivity;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperEvents;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperServices;
import com.example.organizerforlaserhairremovalsalon.R;
import com.example.organizerforlaserhairremovalsalon.Services.ServiceEntity;
import com.example.organizerforlaserhairremovalsalon.Services.ServiceListActivity;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String SERVICE_ID_RESULT = "SERVICE_ID_RESULT";

    private EditText eventNameEditText;
    private TextView eventDateTextView;
    private TextView eventTimeTextView;
    private TextView eventServiceTextView;
    private TextView eventContactTextView;
    private ImageButton deleteEventImageButton;

    private LocalTime localTime;
    private EventEntity eventEntity;

    private DataBaseHelperEvents dataBaseHelperEvents;
    private DataBaseHelperContacts dataBaseHelperContacts;
    private DataBaseHelperServices dataBaseHelperServices;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        this.initialiseWidgets();
        this.dataBaseHelperEvents = new DataBaseHelperEvents(getApplicationContext());
        this.dataBaseHelperContacts = new DataBaseHelperContacts(getApplicationContext());
        this.dataBaseHelperServices = new DataBaseHelperServices(getApplicationContext());

        Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey("OPEN_SELECTED")) {
            this.eventEntity = CalendarUtils.eventEntitySelected;
            this.localTime = this.eventEntity.getTime();

            long contactId = eventEntity.getContactId();
            ContactEntity contact = dataBaseHelperContacts.getContactById(contactId);

            this.eventEntity.setContactEntity(contact);

            long serviceId = eventEntity.getServiceId();
            ServiceEntity service = dataBaseHelperServices.getServiceById(serviceId);

            this.eventEntity.setServiceEntity(service);

            this.setContactTextWidgets();
            this.setServiceTextWidgets();

            checkBeforeDate(arguments.containsKey("OPEN_SELECTED"));

        } else {
            this.localTime = LocalTime.now();
            this.eventEntity = new EventEntity(
                    "",
                    CalendarUtils.dateSelected,
                    LocalTime.now()
            );
            deleteEventImageButton.setVisibility(View.GONE);
        }

        String dateFormatted = CalendarUtils.dateFormat(CalendarUtils.dateSelected);

        this.eventDateTextView.setText("Дата: " + dateFormatted);
        this.setTimeText();

        this.eventServiceTextView.setOnClickListener(this);
    }

    private void checkBeforeDate(boolean openSelected) {
        ImageView saveEventButton = findViewById(R.id.save_event);

        LocalDate dateSelected = CalendarUtils.dateSelected;
        LocalDate dateNow = LocalDate.now();

        boolean isBefore = dateSelected.isBefore(dateNow);

        if (isBefore && openSelected) {
            eventNameEditText.setEnabled(false);
            eventContactTextView.setEnabled(false);
            eventServiceTextView.setEnabled(false);
            eventDateTextView.setEnabled(false);
            eventTimeTextView.setEnabled(false);

            saveEventButton.setEnabled(false);
            saveEventButton.setVisibility(View.GONE);
        }
    }

    private void initialiseWidgets() {
        this.eventDateTextView = findViewById(R.id.eventDatePickTV);
        this.eventNameEditText = findViewById(R.id.eventNameEditTxt);
        this.eventTimeTextView = findViewById(R.id.eventTimePickTV);
        this.eventContactTextView = findViewById(R.id.eventContactPickTV);
        this.eventServiceTextView = findViewById(R.id.eventServicePickTV);
        this.deleteEventImageButton = findViewById(R.id.delete_event);
    }

    private void setContactTextWidgets() {
        String name = this.eventEntity.getContactEntity().getName();

        this.eventNameEditText.setText(this.eventEntity.getName());

        if (name != null && !name.isEmpty()) {
            this.eventContactTextView.setText("Клиент: " + name);
        } else {
            this.eventContactTextView.setVisibility(View.GONE);
        }
    }

    private void setServiceTextWidgets() {
        String name = this.eventEntity.getServiceEntity().getName();

        if(name != null && !name.isEmpty()) {
            this.eventServiceTextView.setText("Услуга: " + name);
        } else {
            this.eventServiceTextView.setVisibility(View.GONE);
        }
    }

    public void saveEventAction(View view) {
        this.eventEntity.setName(this.eventNameEditText.getText().toString());
        this.eventEntity.setTime(this.localTime);

        if (this.eventEntity.getId() == 0) {

            boolean validateResult = validateEventEntityForAdding();

            if (validateResult) {
                this.dataBaseHelperEvents.addEvent(this.eventEntity);
                EventEntity.eventsArrayList.add(this.eventEntity);
                this.finish();
            }
        } else {
            this.dataBaseHelperEvents.editEventById(this.eventEntity);
            this.finish();
        }
    }

    private boolean validateEventEntityForAdding() {
        if (eventEntity.getName() == null || eventEntity.getName().isEmpty()) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_fill_in_the_name), Toast.LENGTH_LONG).show();
            return false;
        } else if (eventEntity.getContactEntity() == null) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_select_a_client), Toast.LENGTH_LONG).show();
            return false;
        } else if(eventEntity.getServiceEntity() == null) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_choose_a_service), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
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

    public void setService() {
        Intent intent = new Intent(this, ServiceListActivity.class);
        intent.putExtra(ServiceListActivity.IS_CHOOSE_VIEW, true);
        startActivityForResult(intent, ServiceListActivity.SERVICE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        Bundle extras = data.getExtras();
        boolean containsContactId = extras.containsKey("contactId");
        boolean containsServiceId = extras.containsKey(SERVICE_ID_RESULT);

        if (containsContactId) {
            long id = extras.getLong("contactId");

            this.eventEntity.setContactId(id);

            ContactEntity contactEntity = this.dataBaseHelperContacts.getContactById(this.eventEntity.getContactId());

            this.eventEntity.setContactEntity(contactEntity);

            String contactName = this.eventEntity.getContactEntity().getName();

            if (contactName != null && !contactName.isEmpty()) {
                this.eventContactTextView.setText("Клиент: " + contactName);
            } else {
                this.eventContactTextView.setVisibility(View.GONE);
            }
        } else if (containsServiceId) {
            long id = extras.getLong(SERVICE_ID_RESULT);

            this.eventEntity.setServiceId(id);

            ServiceEntity service = this.dataBaseHelperServices.getServiceById(id);

            this.eventEntity.setServiceEntity(service);

            String serviceName = this.eventEntity.getServiceEntity().getName();

            if (serviceName != null && !serviceName.isEmpty()) {
                this.eventServiceTextView.setText("Услуга: " + serviceName);
            } else {
                this.eventServiceTextView.setVisibility(View.GONE);
            }
        }
    }

    public void setTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                R.style.DialogTimeTheme,
                (view1, hourOfDay, minute) -> {
                    localTime = LocalTime.of(hourOfDay, minute);
                    setTimeText();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.eventServicePickTV) {
            setService();
        }
    }
}