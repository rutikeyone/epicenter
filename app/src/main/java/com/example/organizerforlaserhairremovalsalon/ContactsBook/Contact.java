package com.example.organizerforlaserhairremovalsalon.ContactsBook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.organizerforlaserhairremovalsalon.Database.DataBaseHelperContacts;
import com.example.organizerforlaserhairremovalsalon.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Contact extends AppCompatActivity implements View.OnClickListener {
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText commentEditText;
    private CircleImageView contractImage;
    private ImageButton deleteContactImageButton;

    private ContactEntity contactEntity;
    private long contactId;
    private DataBaseHelperContacts dataBaseHelper;

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
        if (uri != null) {
            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                this.contactEntity.setImage(image);
                contractImage.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    });


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
            deleteContactImageButton.setVisibility(View.GONE);
        }

        contractImage.setOnClickListener(this);


    }

    private void loadContactById() {
        this.contactEntity = this.dataBaseHelper.getContactById(this.contactId);
        this.setContactByEntity();

        Bitmap image = this.contactEntity.getImage();

        if (image != null) {
            contractImage.setImageBitmap(image);
        }
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
        this.contractImage = findViewById(R.id.contact_image);
        this.deleteContactImageButton = findViewById(R.id.delete_contact);
    }

    public void saveContact(View view) {
        String nameText = nameEditText.getText().toString();
        String phoneText = phoneEditText.getText().toString();
        String commentText = commentEditText.getText().toString();

        boolean validateResult = validateData(nameText, phoneText, commentText);

        if (validateResult) {
            this.contactEntity.setName(nameText);
            this.contactEntity.setPhone(phoneText);
            this.contactEntity.setComment(commentText);

            if (!this.contactEntity.getName().isEmpty() && !this.contactEntity.getPhone().isEmpty()) {
                if (this.contactId == 0) {
                    this.contactId = this.dataBaseHelper.addContact(this.contactEntity);
                } else {
                    this.dataBaseHelper.editContactById(this.contactEntity);
                }
            }

            this.finish();
        }
    }

    private boolean validateData(@Nullable String name, @Nullable String phone, @Nullable String comment) {
        if (name == null || name.isEmpty()) {
            Toast.makeText(this, getText(R.string.it_is_necessary_to_fill_in_the_name), Toast.LENGTH_LONG).show();
            return false;
        } else if (phone == null || phone.isEmpty()) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_fill_in_the_phone), Toast.LENGTH_LONG).show();
            return false;
        } else if (comment == null || comment.isEmpty()) {
            Toast.makeText(this, getString(R.string.it_is_necessary_to_fill_in_a_note), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void deleteContact(View view) {
        this.dataBaseHelper.deleteContactById(this.contactId);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        long id = v.getId();
        if (id == R.id.contact_image) {
            PickVisualMediaRequest request = new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build();

            pickMedia.launch(request);
        }
    }
}