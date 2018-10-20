package com.example.shourov.alarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

public class RingtoneService extends Service {

    MediaPlayer mediaPlayer;
    boolean isRunning;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("LocalService","Received start id "+startId + ":"+intent);

        //fetch the extra values
        String states = intent.getExtras().getString("extra");

        //fetch the spinner choice long values from alarm on/alarm off
        long spinnerSoundChoice = intent.getExtras().getLong("spinner_choice");

        Log.e("ringtone: extra is ",states);

        //put the notification here test it out
        //notification
        //set up the notification service
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //set up an intent that goes to main activity
        Intent notification_intent = new Intent(this.getApplicationContext(),MainActivity.class);

        //setup a pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notification_intent,0);

        //notification perameters
        Notification notification =new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Click me")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .build();


        //this converts the extra string from the intent to start ids values 0 or 1
        assert states != null;//if there is any null pointer exception
        switch (states) {
            case "alarm on":

                startId = 1;
                break;
            case "alarm off":

                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        //if else statement
        //if there is no music playing and the user pressed alarm on
        //music should start playing
        if( !this.isRunning && startId == 1 ){
            Log.e("Music not playing","you press alarm on");

            this.isRunning = true;
            this.startId = 0;

            //setup the notification call command
            notificationManager.notify(0,notification);

            //play the spinner song depending on the passed spinner id

            if(spinnerSoundChoice == 0){
                //randomly picked auto file
                int minimumNumber = 1;
                int maximum = 7;

                Random random = new Random();
                int randomnumber = random.nextInt(maximum + minimumNumber);
                Log.e("Random number is: ",String.valueOf(randomnumber));

                if(randomnumber == 1){
                    mediaPlayer = MediaPlayer.create(this,R.raw.breaking_the_habit);
                    mediaPlayer.start();
                }
                else if(randomnumber == 2){
                    mediaPlayer = MediaPlayer.create(this,R.raw.castle_of_glass);
                    mediaPlayer.start();
                }
                else if(randomnumber==3){
                    mediaPlayer = MediaPlayer.create(this,R.raw.closer);
                    mediaPlayer.start();

                }
                else if(randomnumber==4){
                    mediaPlayer = MediaPlayer.create(this,R.raw.love_me_like_you_do);
                    mediaPlayer.start();

                }
                else if(randomnumber==5){
                    mediaPlayer = MediaPlayer.create(this,R.raw.my_heart_will_go_on);
                    mediaPlayer.start();

                }
                else if(randomnumber==6){
                    mediaPlayer = MediaPlayer.create(this,R.raw.perfect);
                    mediaPlayer.start();

                }
                else if(randomnumber==7){
                    mediaPlayer = MediaPlayer.create(this,R.raw.photograph);
                    mediaPlayer.start();

                }
                else{

                }

            }
            else if(spinnerSoundChoice==1){
                //create as instance of the mediaplayer
                mediaPlayer = MediaPlayer.create(this,R.raw.breaking_the_habit);
                //start the ringtone
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==2){
                mediaPlayer = MediaPlayer.create(this,R.raw.castle_of_glass);
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==3){
                mediaPlayer = MediaPlayer.create(this,R.raw.closer);
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==4){
                mediaPlayer = MediaPlayer.create(this,R.raw.love_me_like_you_do);
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==5){
                mediaPlayer = MediaPlayer.create(this,R.raw.my_heart_will_go_on);
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==6){
                mediaPlayer = MediaPlayer.create(this,R.raw.perfect);
                mediaPlayer.start();

            }
            else if(spinnerSoundChoice==7){
                mediaPlayer = MediaPlayer.create(this,R.raw.photograph);
                mediaPlayer.start();

            }
            else{

            }





        }

        //if there is music playing and user pressed alarm off
        //music should stop playing
        else if(this.isRunning && startId == 0){
            Log.e("Music playing","you press alarm off");
            //stop the ringtone
            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;

        }

        //if the user presses random buttons
        //just to bug-proof the app
        //if there is no music playing and the user press alarm off
        else if(!this.isRunning && startId == 0){
            Log.e("Music not playing","you press alarm off");
            this.isRunning = false;
            this.startId = 0;

        }

        //if there is music playing user press alarm on
        //do nothing
        else if(this.isRunning && startId == 1){
            Log.e("Music playing","you press alarm on");
            this.isRunning = true;
            this.startId = 1;
        }

        //nothing
        else{
            Log.e("else","somehow you reached this");
        }

        return START_NOT_STICKY;//if service off it wont automatically restart
    }

    @Override
    public void onDestroy() {
        //just to be continue when user out from app
        super.onDestroy();
        this.isRunning = false;
    }
}
