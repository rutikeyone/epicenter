package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.OnItemListener {
    private FloatingActionButton fab;
    private RecyclerView contactRecyclerView;
    private DataBaseHelperContacts dataBaseHelperContacts;
    private ContactAdapter contactAdapter;
    private boolean isChooseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Bundle arguments = getIntent().getExtras();
        this.isChooseView = arguments != null && arguments.containsKey("IsChooseView") && arguments.getBoolean("IsChooseView");

        this.initialiseWidgets();

        this.dataBaseHelperContacts = new DataBaseHelperContacts(getApplicationContext());

        this.fab.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(ContactListActivity.this, Contact.class));
                }
            }
        );

        this.loadAllContacts();
    }

    private void initialiseWidgets() {
        this.fab = findViewById(R.id.fab);
        this.contactRecyclerView = findViewById(R.id.contact_RecyclerView);
        this.contactRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadAllContacts();
    }

    private void loadAllContacts() {
        this.contactAdapter = new ContactAdapter(this, dataBaseHelperContacts.getAllContacts(), this);
        this.contactRecyclerView.setAdapter(this.contactAdapter);
    }

    @Override
    public void onItemClick(int pos, Long contactId) {
        if (this.isChooseView) {
            Intent intent = new Intent();
            intent.putExtra("contactId", contactId);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(ContactListActivity.this, Contact.class);
            intent.putExtra("contactId", contactId);
            startActivity(intent);
        }
    }
}