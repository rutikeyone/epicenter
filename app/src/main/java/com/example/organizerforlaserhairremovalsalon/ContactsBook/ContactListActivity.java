package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity implements ContactAdapter.OnItemListener {

    private static final String SEARCH_TEXT_CODE = "SEARCH_TEXT_CODE";

    private FloatingActionButton fab;
    private RecyclerView contactRecyclerView;
    private DataBaseHelperContacts dataBaseHelperContacts;
    private ContactAdapter contactAdapter;

    private String searchText = "";
    private TextInputEditText searchTextInputEditText;

    private boolean isChooseView;

    private final TextWatcher searchTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchContacts(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void searchContacts(String value) {
        searchText = value;
        ArrayList<ContactEntity> searchContacts = dataBaseHelperContacts.searchContactsByName(searchText);

        if(searchText != null && !searchText.isEmpty()) {
            contactAdapter.updateData(searchContacts);
        } else {
            ArrayList<ContactEntity> allContacts = dataBaseHelperContacts.getAllContacts();
            contactAdapter.updateData(allContacts);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        searchTextInputEditText = findViewById(R.id.searchTextInputEditText);

        Bundle arguments = getIntent().getExtras();
        this.isChooseView = arguments != null && arguments.containsKey("IsChooseView") && arguments.getBoolean("IsChooseView");

        this.initialiseWidgets();

        this.dataBaseHelperContacts = new DataBaseHelperContacts(getApplicationContext());

        this.fab.setOnClickListener(
                v -> startActivity(new Intent(ContactListActivity.this, Contact.class))
        );

        this.loadAllContacts();

        if(savedInstanceState != null && savedInstanceState.containsKey(SEARCH_TEXT_CODE)) {
            searchText = savedInstanceState.getString(SEARCH_TEXT_CODE);
            searchTextInputEditText.setText(searchText);
        }
    }

    private void initialiseWidgets() {
        this.fab = findViewById(R.id.fab);
        this.contactRecyclerView = findViewById(R.id.contact_RecyclerView);
        this.contactRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchTextInputEditText.addTextChangedListener(searchTextWatcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.searchContacts(searchText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchTextInputEditText.removeTextChangedListener(searchTextWatcher);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_TEXT_CODE, searchText);
    }

    private void loadAllContacts() {
        this.contactAdapter = new ContactAdapter(this, this);
        this.contactRecyclerView.setAdapter(this.contactAdapter);
    }

    @Override
    public void onItemClick(long id) {
        if (this.isChooseView) {
            Intent intent = new Intent();
            intent.putExtra("contactId", id);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(ContactListActivity.this, Contact.class);
            intent.putExtra("contactId", id);
            startActivity(intent);
        }
    }
}