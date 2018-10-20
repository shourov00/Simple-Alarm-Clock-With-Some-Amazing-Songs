package com.example.shourov.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //to make out alarm manager
    AlarmManager alarmManager;
    TimePicker timePicker;
    TextView updateText;
    Button startAlarm, endAlarm;
    Context context;
    Calendar calendar;
    PendingIntent pendingIntent;
    Intent mintent;
    Spinner spinner;
    String[] songs;
    long chooseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        //alarm manager initialization
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //initial time picker
        timePicker = findViewById(R.id.timePickerId);

        //initial textview and buttons
        updateText = findViewById(R.id.updateAlarm);
        startAlarm = findViewById(R.id.startAlarmId);
        endAlarm = findViewById(R.id.endAlarm);


        //initial spinner
        spinner = findViewById(R.id.spinnerId);
        //getting songs
        songs = getResources().getStringArray(R.array.songs);
        //create arraylist adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,songs);
        //specify the layout to use when the list of choice appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply adapter to the spinner
        spinner.setAdapter(adapter);


        //create a instance of calender that will send pending intent
        calendar = Calendar.getInstance();

        //create an intent to the alarm receiver
        mintent = new Intent(MainActivity.this,AlarmReceiver.class);

        //listener with buttons
        startAlarm.setOnClickListener(this);
        endAlarm.setOnClickListener(this);

        //onitemselected listener for spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //an item was selected you can retrieve the selected item using parent.getitempositon
                //outputting whatever id user selected
                chooseSpinner = id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //another interface callback

            }
        });


    }
    //onclick listener for start alarm and end alarm buttons
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.startAlarmId){

            //setting calender instance with the hour and minute on the time picker
            calendar.set(Calendar.HOUR_OF_DAY,timePicker.getHour());
            calendar.set(Calendar.MINUTE,timePicker.getMinute());

            //get the setting value of the hour and minute
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            //String converts the int values to string
            String hourString = String.valueOf(hour);
            String minuteString = String.valueOf(minute);

            //converts 24H to 12H time
            if(hour > 12){
                hourString = String.valueOf(hour-12);
            }
            if(minute < 10){
                /*10:7 --> 10:07*/
                minuteString = "0"+String.valueOf(minute);
            }

            //method that changes the update text
            setAlarmText("Alarm set to "+hourString+":"+minuteString);

            //put in extra string into my intent tells the clock that you press alarm on button
            mintent.putExtra("extra","alarm on");

            //put in an extra long into mintent tells the clock that you want a certain value from the dropdown menu
            mintent.putExtra("spinner_choice",chooseSpinner);

            //create a pending intent that delays the intent until the specified calender time
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,mintent,PendingIntent.FLAG_UPDATE_CURRENT);

            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);


        }
        else if(v.getId() == R.id.endAlarm){
            //work for endalarm
            setAlarmText("Alarm Off");

            //cancel alarm
            alarmManager.cancel(pendingIntent);

            //put in extra string into my intent tells the clock that you press alarm off button
            mintent.putExtra("extra","alarm off");

            //also put an extra long the alarm off section to prevent null pointer expression
            mintent.putExtra("spinner_choice",chooseSpinner);

            //stop the ringtone
            sendBroadcast(mintent);

        }

    }

    //setalamtext method
    private void setAlarmText(String output) {
        updateText.setText(output);
    }
}
