package muralufg.fabrica.inf.ufg.br.centralufg.notificacao;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import muralufg.fabrica.inf.ufg.br.centralufg.Config;
import muralufg.fabrica.inf.ufg.br.centralufg.main.MainActivity;

public class NotificacaoAlerta extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager notificacaoManager;
    NotificationCompat.Builder builder;
    public static final String TAG = "GCMNotificationIntentService";

    public NotificacaoAlerta() {
        super("Notificacao");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String mensagem = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(mensagem)) {
                enviarNotificacao("Erro: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(mensagem)) {
                enviarNotificacao("Mensagens Apagadas no Servidor: "
                        + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(mensagem)) {

                for (int i = 0; i < 3; i++) {
                    Log.i(TAG,
                            "... " + (i + 1) + "/5 @ "
                                    + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        Log.i(TAG, "Erro" + e.toString());
                    }

                }
                Log.i(TAG, "Finalizado @ " + SystemClock.elapsedRealtime());

                enviarNotificacao("Mensagem: "
                        + extras.get(Config.MESSAGE_KEY));
                Log.i(TAG, "Recebido: " + extras.toString());
            }
        }
        Notificacao.completeWakefulIntent(intent);
    }

    private void enviarNotificacao(String msg) {
        Log.d(TAG, "Preparando para enviar notificações...: " + msg);
        notificacaoManager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setContentTitle("Nova Mensagem")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        notificacaoManager.notify(NOTIFICATION_ID, mBuilder.build());
        Log.d(TAG, "Sucesso.");
    }
}

