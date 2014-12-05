package muralufg.fabrica.inf.ufg.br.centralufg.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by italogustavomirandamelo on 27/11/14.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        Intent intentGot = intent.setComponent(comp);
        startWakefulService(context, intentGot);
        setResultCode(Activity.RESULT_OK);
    }
}
