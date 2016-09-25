package com.segunfamisa.notification.directreply;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String ARG_REPLY = "arg_reply";
    private Button mButtonNotif;

    /**
     * Gets an intent to reply message
     * @param context
     * @param reply
     * @return
     */
    public static Intent getReplyMessageIntent(Context context, String reply) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(ARG_REPLY, reply);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonNotif = (Button) findViewById(R.id.button_show_notification);

        mButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        // TODO: 24/09/2016 add notification logic here
        startService(new Intent(this, NotificationService.class));
    }
}
