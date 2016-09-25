package com.segunfamisa.notification.directreply;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.widget.Toast;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private static String REPLY_ACTION = "com.segunfamisa.notification.directreply.REPLY_ACTION";
    private static String KEY_NOTIFICATION_ID = "key_noticiation_id";

    public static Intent getReplyMessageIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, NotificationBroadcastReceiver.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_NOTIFICATION_ID, notificationId);
        return intent;
    }
    public NotificationBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REPLY_ACTION.equals(intent.getAction())) {
            // do whatever you want with the message. Send to the server or add to the db.
            // for this tutorial, we'll just show it in a toast;
            CharSequence message = getReplyMessage(intent);
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            // update notification
            int notifyId = intent.getIntExtra(KEY_NOTIFICATION_ID, 1);
            updateNotification(context, notifyId);
        }
    }

    private CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(NotificationService.KEY_REPLY);
        }
        return null;
    }

    private void updateNotification(Context context, int notifyId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notif_smile)
                .setContentText(context.getString(R.string.notif_content_sent));

        notificationManager.notify(notifyId, builder.build());
    }
}
