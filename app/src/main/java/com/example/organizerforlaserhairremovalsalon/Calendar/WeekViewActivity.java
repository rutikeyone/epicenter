package com.example.organizerforlaserhairremovalsalon.Calendar;

import static com.example.organizerforlaserhairremovalsalon.Calendar.CalendarUtils.getDaysOfWeekArray;
import static com.example.organizerforlaserhairremovalsalon.Calendar.CalendarUtils.getMonthAndYearOfDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperEvents;
import com.example.organizerforlaserhairremovalsalon.Interfaces.OnItemCalendarListener;
import com.example.organizerforlaserhairremovalsalon.R;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekViewActivity extends AppCompatActivity implements OnItemCalendarListener {
    private TextView monthYearTextView;
    private RecyclerView calRecyclerView;
    private ListView eventListView;
    private DataBaseHelperEvents dataBaseHelperEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        this.dataBaseHelperEvents = new DataBaseHelperEvents(getApplicationContext());

        this.initialiseWidgets();
        this.setWeekView();
    }

    private void setWeekView() {
        this.monthYearTextView.setText(getMonthAndYearOfDate(CalendarUtils.dateSelected));
        List<LocalDate> daysOfWeek = getDaysOfWeekArray(CalendarUtils.dateSelected);

        CalendarAdapter calAdapter = new CalendarAdapter(daysOfWeek, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

        this.calRecyclerView.setLayoutManager(layoutManager);
        this.calRecyclerView.setAdapter(calAdapter);
        this.setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<EventEntity> dailyEvents = this.dataBaseHelperEvents.getEventsForDay(CalendarUtils.dateSelected);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        this.eventListView.setAdapter(eventAdapter);
    }

    private void initialiseWidgets() {
        this.calRecyclerView = findViewById(R.id.weekRecyclerView);
        this.monthYearTextView = findViewById(R.id.monthYearTextView);
        this.eventListView = findViewById(R.id.eventListViev);

        this.eventListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CalendarUtils.eventEntitySelected = (EventEntity) eventListView.getItemAtPosition(position);

                    Intent intent = getIntent();
                    intent.putExtra("OPEN_SELECTED", true);
                    startActivity(intent);
                }
            }
        );
    }

    public Intent getIntent() {
        return new Intent(this, EventEditActivity.class);
    }
    
    public void prevWeekAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.minusWeeks(1);
        this.setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.dateSelected = CalendarUtils.dateSelected.plusWeeks(1);
        this.setWeekView();
    }

    public void newEventAction(View view) {
        this.startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.setEventAdapter();
    }

    @Override
    public void onItemClick(int pos, LocalDate localDate) {
        CalendarUtils.dateSelected = localDate;
        this.setWeekView();
    }
}