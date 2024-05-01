package com.example.organizerforlaserhairremovalsalon.Services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizerforlaserhairremovalsalon.Calendar.EventEditActivity;
import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperServices;
import com.example.organizerforlaserhairremovalsalon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ServiceListActivity extends AppCompatActivity implements View.OnClickListener, ServicesAdapter.OnItemListener {

    public static final String IS_CHOOSE_VIEW = "IS_CHOOSE_VIEW";
    public static final int SERVICE_REQUEST_CODE = 1;

    FloatingActionButton fabButton;

    private RecyclerView servicesRecyclerView;
    private ServicesAdapter servicesAdapter;

    private DataBaseHelperServices dataBaseHelperServices;

    boolean isChooseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);

        Bundle arguments = getIntent().getExtras();
        isChooseView = arguments != null && arguments.containsKey(IS_CHOOSE_VIEW) && arguments.getBoolean(IS_CHOOSE_VIEW);

        dataBaseHelperServices = new DataBaseHelperServices(getApplicationContext());

        fabButton = findViewById(R.id.addServiceFabButton);

        fabButton.setOnClickListener(this);

        setupViews();
        setupAdapter();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addServiceFabButton) {
            Intent launchEditServiceIntent = new Intent(ServiceListActivity.this, ServiceEditActivity.class);
            startActivity(launchEditServiceIntent);
        }
    }

    private void setupViews() {
        this.servicesRecyclerView = findViewById(R.id.servicesRecyclerView);
        this.servicesRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        this.servicesAdapter.updateData(dataBaseHelperServices.getAllServices());
        super.onResume();
    }

    private void setupAdapter() {
        this.servicesAdapter = new ServicesAdapter(this, this);
        this.servicesRecyclerView.setAdapter(this.servicesAdapter);
    }

    @Override
    public void onItemClick(Long id) {
        if (!isChooseView) {
            Intent launchEditServiceIntent = new Intent(ServiceListActivity.this, ServiceEditActivity.class);
            launchEditServiceIntent.putExtra(ServiceEditActivity.ID, id);

            startActivity(launchEditServiceIntent);
        } else {
            Intent intent = new Intent();
            intent.putExtra(EventEditActivity.SERVICE_ID_RESULT, id);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}