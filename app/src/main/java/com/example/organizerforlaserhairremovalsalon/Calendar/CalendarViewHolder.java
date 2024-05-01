package com.example.organizerforlaserhairremovalsalon.Calendar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.Interfaces.OnItemCalendarListener;
import com.example.organizerforlaserhairremovalsalon.R;

import java.time.LocalDate;
import java.util.List;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private final List<LocalDate> days;
    private final TextView dayTextView;
    private final View parrentView;
    private final OnItemCalendarListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, OnItemCalendarListener onItemListener, List<LocalDate> days) {
        super(itemView);
        this.dayTextView = itemView.findViewById(R.id.dayTxt);
        this.parrentView = itemView.findViewById(R.id.parentVew);
        this.onItemListener = onItemListener;
        this.days = days;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.onItemListener.onItemClick(this.getAdapterPosition(), this.days.get(getAdapterPosition()));
    }

    public TextView getDayTextView() {
        return dayTextView;
    }

    public View getParrentView() {
        return parrentView;
    }
}
