package com.example.sharingapp;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Editing a pre-existing contact consists of deleting the old contact and adding a new contact with the old
 * contact's id.
 * Note: You will not be able contacts which are "active" borrowers
 */
public class EditContactActivity extends AppCompatActivity {

    private ContactList contact_list = new ContactList();
    private Contact contact;
    private EditText email;
    private EditText username;
    private Context context;

    private ContactController contact_controller;
    private ContactListController contact_list_controller = new ContactListController(contact_list);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        context = getApplicationContext();
        contact_list_controller.loadContacts(context);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position", 0);

        contact = contact_list.getContact(pos);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        username.setText(contact.getUsername());
        email.setText(contact.getEmail());
        
    }

    public void saveContact(View view) {

        String email_str = email.getText().toString();
        String username_str = username.getText().toString();
        String id = contact_controller.getId(); // Reuse the contact id

        if(!validateInput()){
            return;
        }

        Contact updated_contact = new Contact(username_str, email_str, id);

        ContactController updated_contact_controller = new
                ContactController(updated_contact);

        boolean success = contact_list_controller.editContact(contact, updated_contact, context);
        if (!success){
            return;
        }

        // End EditContactActivity
        finish();
    }

    public void deleteContact(View view) {

        DeleteContactCommand delete_contact_command = new DeleteContactCommand(contact_list, contact, context);
        delete_contact_command.execute();

        boolean success = delete_contact_command.isExecuted();
        if (!success){
            return;
        }

        // End EditContactActivity
        finish();
    }

    public boolean validateInput()
    {
        if(username.equals("")){
             username.setError("Empty field!");
             return false;
        }

        if(email.equals("")){
            email.setError("Empty field!");
            return false;
        }

        if (email.equals("")) {
            email.setError("Empty field!");
            return false;
        }

        if (!email.toString().contains("@")){
            email.setError("Must be an email address!");
            return false;
        }

        if (!contact_list.isUsernameAvailable(username.toString())){
            username.setError("Username already taken!");
            return false;
        }

        return true;
    }
}
