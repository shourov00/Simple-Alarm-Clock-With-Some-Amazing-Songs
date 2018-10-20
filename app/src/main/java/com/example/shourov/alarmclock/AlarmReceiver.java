package com.example.shourov.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    //creating intent
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the receiver","Yay!");

        //fetch extra string from the main activity intent
        //user pressed alarm on oralarm off button
        String getString = intent.getExtras().getString("extra");
        Log.e("What is the key?",getString);

        //fetch the extra long from the main activity intent
        //this tells which value user picked from spinner
        Long get_your_spinner_choice = intent.getExtras().getLong("spinner_choice");
        Log.e("Spinner choice is ",get_your_spinner_choice.toString());

        //create an intent to the ringtone service
        Intent service_intent = new Intent(context,RingtoneService.class);

        //pass the extra string from main activity to the ringtone service
        service_intent.putExtra("extra",getString);

        //pass the extra string from main activity to the rintone service
        service_intent.putExtra("spinner_choice",get_your_spinner_choice);

        //start the ringtone service
        context.startService(service_intent);

    }
}
