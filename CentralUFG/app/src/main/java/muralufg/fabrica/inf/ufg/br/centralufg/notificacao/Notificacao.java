package muralufg.fabrica.inf.ufg.br.centralufg.notificacao;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class Notificacao extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                NotificacaoAlerta.class.getName());
        startWakefulService(context, intent.setComponent(comp));
        setResultCode(Activity.RESULT_OK);
    }
}