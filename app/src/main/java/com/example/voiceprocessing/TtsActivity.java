package com.example.voiceprocessing;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class TtsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Button btn_Speak;
    private EditText txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);

        tts = new TextToSpeech(this, this);
        btn_Speak = findViewById(R.id.btnSpeak);
        txtText = findViewById(R.id.txtText);

        btn_Speak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    private void speakOut() {
        CharSequence text = txtText.getText();
        tts.setPitch((float) 0.6);
        tts.setSpeechRate((float) 1.0);
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.KOREA);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btn_Speak.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Initialization Failed");
        }
    }
}