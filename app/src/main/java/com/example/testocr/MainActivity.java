package com.example.testocr;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tesseract.R;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    final int MULTIPLE_PERMISSIONS = 10;
    Button btnStart;
    private TextToSpeech textToSpeech;
    final String contentWelcome = "Hello, Welcome to reading assitance application, please press screen to take photo";
    private RelativeLayout mh;
    TextView tvTest;
    /*    static {
            System.loadLibrary("Preprocess");
        }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  //      tvTest = findViewById(R.id.txtView);
//        tvTest.setText(stringFromJNI());
        mh = (RelativeLayout) findViewById(R.id.activity_main);
        mh.setBackgroundResource(R.drawable.anh2);

        String[] PERMISSIONS = { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET };

        textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak(contentWelcome, TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CaptureImage.class);
                startActivity(intent);

            }
        });
    }

    public void callLogin() {

        Intent recognizeActivity = new Intent(getApplicationContext(), CaptureImage.class);
        // Clears History of Activity
        Bundle b = new Bundle();
        // Information to validate. Enter any to pass
        b.putString("language", ((Spinner) findViewById(R.id.languageSpiner)).getSelectedItem().toString());
        recognizeActivity.putExtras(b);
        recognizeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(recognizeActivity);

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permissions granted.
                } else {
                    this.finish();
                }
                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
