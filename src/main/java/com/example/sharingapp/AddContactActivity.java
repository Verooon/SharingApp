package com.example.sharingapp;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Add a new contact
 */
public class AddContactActivity extends AppCompatActivity {

    private ContactList contact_list = new ContactList();
    private Context context;

    private ContactListController contact_list_controller = new
            ContactListController( contact_list );

    private EditText username;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);

        context = getApplicationContext();
        contact_list.loadContacts(context);
    }

    public void saveContact(View view) {

        String username_str = username.getText().toString();
        String email_str = email.getText().toString();

        if(!validateInput()){
            return;
        }

        Contact contact = new Contact(username_str, email_str, null);

        ContactController contact_controller = new ContactController(contact);


        boolean success = contact_list_controller.addContact(contact, context);
        if (!success){
            return;
        }

        // End AddContactActivity
        finish();
    }

    public boolean validateInput(){

        if (username.equals("")) {
            username.setError("Empty field!");
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
