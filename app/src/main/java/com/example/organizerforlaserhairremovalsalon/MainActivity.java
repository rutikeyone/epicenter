package com.example.organizerforlaserhairremovalsalon;

import static com.example.organizerforlaserhairremovalsalon.Calendar.CalendarUtils.getDaysOfMonthArray;
import static com.example.organizerforlaserhairremovalsalon.Calendar.CalendarUtils.getMonthAndYearOfDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizerforlaserhairremovalsalon.Calendar.CalendarAdapter;
import com.example.organizerforlaserhairremovalsalon.Calendar.CalendarUtils;
import com.example.organizerforlaserhairremovalsalon.Calendar.WeekViewActivity;
import com.example.organizerforlaserhairremovalsalon.ContactsBook.ContactListActivity;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperEvents;
import com.example.organizerforlaserhairremovalsalon.Interfaces.OnItemCalendarListener;
import com.example.organizerforlaserhairremovalsalon.Services.ServiceListActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemCalendarListener {
    private TextView monthYearTextView;
    private RecyclerView calRecyclerView;
    private boolean recreateContactsTable = false;
    private boolean recreateEventsTable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (recreateContactsTable) {
            DataBaseHelperContacts dataBaseHelperContacts = new DataBaseHelperContacts(getApplicationContext());
            dataBaseHelperContacts.recreateContactsTable();
        }

        if (recreateEventsTable) {
            DataBaseHelperEvents dataBaseHelperEvents = new DataBaseHelperEvents(getApplicationContext());
            dataBaseHelperEvents.recreateEventsTable();
        }

        CalendarUtils.dateSelected = LocalDate.now();

        this.setContentView(R.layout.activity_main);
        this.initialiseWidgets();
        this.setMonthView();
        this.setupToolBar();
    }

    private void setupToolBar() {
        MaterialToolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }

    private void setMonthView() {
        this.monthYearTextView.setText(getMonthAndYearOfDate(CalendarUtils.dateSelected));
        List<LocalDate> daysOfMonth = getDaysOfMonthArray(CalendarUtils.dateSelected);

        CalendarAdapter calAdapter = new CalendarAdapter(daysOfMonth, this);
        this.calRecyclerView.setAdapter(calAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        this.calRecyclerView.setLayoutManager(layoutManager);

    }

    private void initialiseWidgets() {
        this.calRecyclerView = findViewById(R.id.calendarRecyclerView);
        this.monthYearTextView = findViewById(R.id.monthYearTextView);
    }

    public void prevMonthAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.minusMonths(1);
        this.setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.plusMonths(1);
        this.setMonthView();
    }

    @Override
    public void onItemClick(int pos, LocalDate localDate) {
        if (localDate != null) {
            CalendarUtils.dateSelected = localDate;
            startActivity(new Intent(this, WeekViewActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navig_menu,  menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        long id = item.getItemId();

        if (id == R.id.contact_list_btn_menu) {
            startActivity(new Intent(MainActivity.this, ContactListActivity.class));
            return true;
        }
        else if(id == R.id.services) {
            Intent launchServicesIntent = new Intent(MainActivity.this, ServiceListActivity.class);
            startActivity(launchServicesIntent);
            return true;
        }
        else if (id == R.id.about_btn_menu) {
            Toast.makeText(MainActivity.this, "Органайзер для салона лазерной эпиляции. Ver. 1.0", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}