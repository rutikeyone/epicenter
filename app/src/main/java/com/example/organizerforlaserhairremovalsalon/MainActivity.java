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
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemCalendarListener {
    private TextView monthYearTextView;
    private RecyclerView calRecyclerView;
    private DrawerLayout manuLayout;
    private NavigationView menu;
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
        this.manuLayout = findViewById(R.id.manu_layout);
        this.menu = findViewById(R.id.nav_view);

        this.menu.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.event_list_btn_menu) {
                            manuLayout.close();
                            return true;
                        } else if (id == R.id.contact_list_btn_menu) {
                            startActivity(new Intent(MainActivity.this, ContactListActivity.class));
                            manuLayout.close();
                            return true;
                        } else if (id == R.id.about_btn_menu) {
                            Toast.makeText(MainActivity.this, "Органайзер для салона лазерной эпиляции. Ver. 1.0", Toast.LENGTH_LONG).show();
                            manuLayout.close();
                            return true;
                        }

                        return false;
                    }
                }
        );
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
}