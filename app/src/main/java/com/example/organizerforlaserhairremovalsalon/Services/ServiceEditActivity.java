package com.example.organizerforlaserhairremovalsalon.Services;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperServices;
import com.example.organizerforlaserhairremovalsalon.R;

public class ServiceEditActivity extends AppCompatActivity implements View.OnClickListener {

    public static String ID = "ID";

    private EditText serviceNameEditText;

    private DataBaseHelperServices dataBaseHelperServices;

    boolean isEdit;
    ServiceEntity editService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_edit);

        dataBaseHelperServices = new DataBaseHelperServices(getApplicationContext());

        setupViews();

    }

    private void setupViews() {
        serviceNameEditText = findViewById(R.id.serviceNameEditText);

        ImageButton saveServiceImageButton = findViewById(R.id.saveServiceImageButton);
        ImageButton deleteServiceImageButton = findViewById(R.id.deleteServiceImageButton);

        deleteServiceImageButton.setOnClickListener(this);
        saveServiceImageButton.setOnClickListener(this);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null && arguments.containsKey(ID)) {
            long id = arguments.getLong(ID);
            editService = dataBaseHelperServices.getServiceById(id);
            String name = editService.getName();

            deleteServiceImageButton.setVisibility(View.VISIBLE);
            isEdit = true;

            if (name != null && !name.isEmpty()) {
                serviceNameEditText.setText(name);
            }
        } else {
            deleteServiceImageButton.setVisibility(View.GONE);
            isEdit = false;
            editService = null;
        }
    }

    @Override
    public void onClick(View v) {
        long id = v.getId();

        if (id == R.id.saveServiceImageButton) {
            String text = serviceNameEditText.getText().toString();
            boolean result = validateResult(text);

            if (result && !isEdit && editService == null) {
                ServiceEntity service = new ServiceEntity();
                service.setName(text);

                dataBaseHelperServices.addService(service);

                finish();
            } else if (result && isEdit && editService != null) {
                editService.setName(text);
                dataBaseHelperServices.editServiceById(editService);
                finish();
            }
        } else if(id == R.id.deleteServiceImageButton) {
            if(isEdit && editService != null) {
                dataBaseHelperServices.deleteServiceById(editService.getId());
                finish();
            }
        }
    }

    private boolean validateResult(String text) {
        if (text == null || text.isEmpty()) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_fill_in_the_name), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}