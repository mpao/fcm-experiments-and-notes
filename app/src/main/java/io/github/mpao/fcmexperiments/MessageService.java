package io.github.mpao.fcmexperiments;

import android.content.Intent;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessageService extends FirebaseMessagingService {

    private final String FCM = "logThis";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent intent = new Intent("listenToThis");

        Log.d(FCM, "type: " + remoteMessage.getMessageType() );
        Log.d(FCM, "from: " + remoteMessage.getFrom() );
        intent.putExtra("type", remoteMessage.getMessageType());
        intent.putExtra("from", remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            // It's a Map<String,String>
            Log.d(FCM, "payload: " + remoteMessage.getData().toString() );
            intent.putExtra("payload", remoteMessage.getData().toString() );
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(FCM, "message: " + remoteMessage.getNotification().getBody() );
            intent.putExtra("message", remoteMessage.getNotification().getBody());
        }

        /*
        *
        * NOTIFICATION MESSAGES
        *
        Se arriva un Notification Message e l'app è in background, viene mostrata una notifica con i
        parametri di default, a cui però si può cambiare l'icona, vedi stackoverflow
        https://stackoverflow.com/questions/37325051/notification-icon-with-the-new-firebase-cloud-messaging-system
        per il cambiamento icona, occorre inserire i meta-dati nel tag application, e creare l'icona come:
        new -> image asset -> notification icon

        Se l'app è in foreground, onMessageReceived viene comunque triggerato e posso inviare un broadcast message
        alla activity per aggiornare le proprie view, se necessario o creare una notifica a mio piacimento.

        Insomma le notifiche dalla Console di Firebase sono utili sono come ingaggio per l'utente, non hanno
        molta altra utilità.

        *
        * DATA MESSAGES
        *
        Implementando invece un notification server tutto segue la normale implementazione e posso farci
        quello che voglio.
         */
        sendBroadcast(intent);

    }

}
