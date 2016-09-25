package com.segunfamisa.notification.directreply;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class NotificationService extends IntentService {
    public static String KEY_REPLY = "key_reply_message";

    private int mNotificationId;
    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            showNotification();
        }
    }

    private void showNotification() {
        mNotificationId = 1;

        // 1. Build label
        String replyLabel = getString(R.string.notif_action_reply);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                .setLabel(replyLabel)
                .build();

        // 2. Build action
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_notif_action_reply, replyLabel, getReplyPendingIntent())
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        // 3. Build notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notif_smile)
                .setContentTitle(getString(R.string.notif_title))
                .setContentText(getString(R.string.notif_content))
                .setShowWhen(true)
                .addAction(replyAction);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(this);
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }

    private PendingIntent getReplyPendingIntent() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // start a
            // (i)  broadcast receiver which runs on the UI thread or
            // (ii) service for a background task to b executed , but for the purpose of this codelab, will be doing a broadcast receiver
            intent = NotificationBroadcastReceiver.getReplyMessageIntent(this, mNotificationId);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            intent = MainActivity.getReplyMessageIntent(this, "");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}
