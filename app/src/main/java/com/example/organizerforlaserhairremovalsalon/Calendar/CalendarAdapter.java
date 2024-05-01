package com.example.organizerforlaserhairremovalsalon.Calendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.Interfaces.OnItemCalendarListener;
import com.example.organizerforlaserhairremovalsalon.MainActivity;
import com.example.organizerforlaserhairremovalsalon.R;

import java.time.LocalDate;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final List<LocalDate> days;
    private final OnItemCalendarListener onItemListener;

    public CalendarAdapter(List<LocalDate> days, OnItemCalendarListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_item, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15) {
            layoutParams.height = (int) (parent.getHeight() / 6L);
        } else {
            layoutParams.height = parent.getHeight();
        }
        return new CalendarViewHolder(view, this.onItemListener, this.days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate localDate = days.get(position);

        if (localDate == null) {
            holder.getDayTextView().setText("");
        } else {
            holder.getDayTextView().setText(String.valueOf(localDate.getDayOfMonth()));

            if (localDate.equals(CalendarUtils.dateSelected)) {
                holder.getParrentView().setBackgroundColor(Color.parseColor("#D8A48B"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.days.size();
    }
}
