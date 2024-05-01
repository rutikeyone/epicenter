package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.R;

public class Contact extends AppCompatActivity {
    private EditText nameEditText;
    private  EditText phoneEditText;
    private  EditText commentEditText;
    private RelativeLayout activityContact;
    private ContactEntity contactEntity;
    private long contactId;
    private DataBaseHelperContacts dataBaseHelper;

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.dataBaseHelper = new DataBaseHelperContacts(getApplicationContext());
        setContentView(R.layout.activity_contact);

        this.initialiseWidgets();

        Intent intent = getIntent();

        if (intent.getExtras() != null && intent.getExtras().containsKey("contactId")) {
            this.contactId = intent.getExtras().getLong("contactId");
            this.loadContactById();
        } else {
            this.contactEntity = new ContactEntity();
        }
    }

    private void loadContactById() {
        this.contactEntity = this.dataBaseHelper.getContactById(this.contactId);
        this.setContactByEntity();
    }

    private void setContactByEntity() {
        this.nameEditText.setText(this.contactEntity.getName());
        this.phoneEditText.setText(this.contactEntity.getPhone());
        this.commentEditText.setText(this.contactEntity.getComment());
    }

    private void initialiseWidgets() {
        this.nameEditText = findViewById(R.id.NameEditText);
        this.phoneEditText = findViewById(R.id.PhoneEditText);
        this.commentEditText = findViewById(R.id.CommentContactEditText);
        this.activityContact = findViewById(R.id.activity_contact);
    }

    public void saveContact(View view) {
        this.contactEntity.setName(this.nameEditText.getText().toString());
        this.contactEntity.setPhone(this.phoneEditText.getText().toString());
        this.contactEntity.setComment(this.commentEditText.getText().toString());

        if (!this.contactEntity.getName().isEmpty() && !this.contactEntity.getPhone().isEmpty()) {
            if (this.contactId == 0) {
                this.contactId = this.dataBaseHelper.addContact(this.contactEntity);
            } else {
                this.dataBaseHelper.editContactById(this.contactEntity);
            }
        }

        this.finish();
    }

    public void deleteContact(View view) {
        this.dataBaseHelper.deleteContactById(this.contactId);
        this.finish();
    }
}