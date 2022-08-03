package com.koddev.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class tmp extends AppCompatActivity {
    ImageView forum_b,main_b,coo;
    Button  demoBtn;
    private Button write;
    //進入"撰寫文章"頁面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);

        //進入"撰寫文章"頁面
        write = (Button) findViewById(R.id.write_b);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_w();
            }
        });

        coo = (ImageView) findViewById(R.id.cookie);
        coo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });
        forum_b = (ImageView) findViewById(R.id.forum_b);
        forum_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_back();
            }
        });

        main_b = (ImageView) findViewById(R.id.chat_b);
        main_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_tmp();
            }
        });

        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .build();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.codeBox);
        String text = editText.getText().toString();
        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }

        // This is a demo app  You can add share button Functionality
/*
        demoBtn= (Button) findViewById(R.id.demoBtn);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(tmp.this , LoginActivity.class));
            }
        });*/
    }

    private void open_back() {
        Intent intent = new Intent(this, forum.class);
        startActivity(intent);

    }
    private void open_tmp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    private void open_coo() {
        Intent intent = new Intent(this, cookie.class);
        startActivity(intent);

    }
    private void open_w() {
        Intent intent = new Intent(this, write.class);
        startActivity(intent);

    }
}