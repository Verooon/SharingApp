package com.example.sharingapp;
import android.content.Context;


public class AddContactCommand extends Command{

    private ContactList contact_ist;
    private Contact contact;
    private Context context;

    public AddContactCommand(ContactList contact_ist, Contact contact, Context context) {
        this.contact_ist = contact_ist;
        this.contact = contact;
        this.context = context;
    }

    public void execute(){
        contact_ist.addContact(contact);
        setIsExecuted(contact_ist.saveContacts(context));
    }
}
