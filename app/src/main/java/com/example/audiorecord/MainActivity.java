package com.example.audiorecord;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Environment;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private TextView mTextField;
    private Button button;
    private Map<Integer, Integer> keys = new HashMap<>();
    final Button b = new Button(getApplicationContext());

    //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    //String date= sdf.format(new Date());/

    List<Integer> inr = new ArrayList<>();

    private int countID = 0;
    private int audioID = 0;

    private MediaRecorder recorder;
    private MediaPlayer mMediaPlayer;
    private String fileName = null;

    private LinearLayout linearLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextField = (TextView) findViewById(R.id.mTextField);
        button = (Button) findViewById(R.id.button);
        linearLayout = (LinearLayout) findViewById(R.id.liner);

        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();

        button.setOnTouchListener(new View.OnTouchListener() {

            //event button
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
                    fileName += "/id"+audioID+".3gp";
                    audioID++;
                    try {
                      startRecording();

                    } catch (IOException e) {
                      e.printStackTrace();

                        //timer
                      /*new CountDownTimer(5000, 1000) {

                          public void onTick(long millisUntilFinished) {

                              mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                          }

                          public void onFinish() {
                              mTextField.setText("done!");
                          }
                      }.start();*/

                    }

                    CountDownTimer waitTimer;
                    waitTimer = new CountDownTimer(5000, 300) {

                        public void onTick(long millisUntilFinished) {
                            System.out.println("WHILE OK");
                        }

                        public void onFinish() {
                            try{
                                recorder.stop();
                            }catch(RuntimeException stopException){

                            }
                            recorder.reset();
                            recorder.release();
                            b.setText("Play" + Integer.toString(countID));
                            b.setLayoutParams(
                                    new LinearLayout.LayoutParams(

                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT)
                            );
                            b.setId(countID);
                            writer(Environment.getExternalStorageDirectory().getAbsolutePath()+"/str.txt", countID);
                            linearLayout.addView(b);
                            fileName = "";
                            clickable();
                            keys.put(audioID, countID);
                            countID++;
                        }
                    }.start();

                }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                    try{
                        recorder.stop();
                    }catch(RuntimeException stopException){

                    }
                    recorder.reset();
                    recorder.release();

                    b.setText("Play" + Integer.toString(countID));
                    b.setLayoutParams(
                            new LinearLayout.LayoutParams(

                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT)
                    );
                    b.setId(countID);
                    writer(Environment.getExternalStorageDirectory().getAbsolutePath()+"/str.txt", countID);
                    linearLayout.addView(b);
                    fileName = "";
                    clickable();
                    keys.put(audioID, countID);
                    countID++;

                }

                return false;
            }
        });

    }

    public void clickable(){
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                path+="/id"+b.getId()+".3gp";
                b.setText(path);
                try {
                    play(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                path = "";
            }
        });


    }

    private void writer(String path, String text){
        try{
            FileWriter writer = new FileWriter(path, false);

            writer.write(text);

            writer.append('\n');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    private void writer(String path, int number){
        try{
            FileWriter writer = new FileWriter(path, false);

            writer.write(Integer.toString(number));

            writer.append('\n');
            writer.append('E');

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    private String reader(String path){
        String str = null;
        try{
            FileReader reader = new FileReader(path);
            int c;
            while((c=reader.read())!=-1){

                str = String.valueOf(c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());

        }

        return str;
    }

    private void startRecording() throws IOException {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.prepare();
        recorder.start();
    }

    private void play(String path) throws IOException {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
    }

}
