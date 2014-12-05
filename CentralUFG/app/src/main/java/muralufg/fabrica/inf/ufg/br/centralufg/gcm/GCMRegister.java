package muralufg.fabrica.inf.ufg.br.centralufg.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.log4j.Logger;

import java.io.IOException;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.exception.MyException;

/**
 * Created by italogustavomirandamelo on 17/10/14.
 */
public class GCMRegister extends AsyncTask<Void, Void, String> {

    GoogleCloudMessaging gcm;
    SharedPreferences sharedPreferences;
    String properyRegId;
    String appVersionProperty = "";
    String appVersion = "";
    Context context;
    String idRegistroGCM;
    String senderId;
    static final String TAG = "GCMRegister";
    private static final Logger LOGGER = Logger.getLogger("GCMRegister");


    public GCMRegister(Context context) {
        this.context = context;
        this.properyRegId = context.getResources().getString(R.string.property_reg_id);
        this.senderId = context.getResources().getString(R.string.gcm_sender_id);
        this.sharedPreferences = context.getSharedPreferences(context.getClass().getSimpleName(), Context.MODE_PRIVATE);
    }


    /**
     * Registra o dispositivo no GCM em background.
     *
     * @return id de registro no GCM.
     */
    @Override
    public String doInBackground(Void... params) {

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            idRegistroGCM = gcm.register(senderId);
            storeRegistrationId(idRegistroGCM);

        } catch (IOException ex) {
            Log.i(TAG, "Erro ao registrar GCM");
            throw new MyException("Erro ao registrar GCM", ex);
        }

        return idRegistroGCM;
    }


    /**
     * Verifica se os serviços do google (Play) estão disponíveis.
     *
     * @return boolean representando a disponibilidade da Google Play.
     */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }

    /**
     * Busca o id de registro no GCM nas preferências do dispositivo.
     *
     * @return String representando o id de registro ou string vazia caso não possua o id armazenado.
     */
    public String getRegistrationId() {

        LOGGER.info("Informacao do registro " + context.getPackageName());
        String registrationId = sharedPreferences.getString(properyRegId, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        int registeredVersion = sharedPreferences.getInt(appVersion, Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            return "";
        }

        return registrationId;
    }


    /**
     * Busca a versão do aplicativo instalado.
     *
     * @return int representando a versão do aplicativo instalada.
     */
    private int getAppVersion() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new MyException("Could not get package name:", e);
        }
    }


    /**
     * Salva o id de registro no GCM nas preferências do dispositivo.
     *
     * @param idRegistroGCM Id de registro no GCM
     */
    private void storeRegistrationId(String idRegistroGCM) {

        int versaoApp = getAppVersion();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(properyRegId, idRegistroGCM);
        editor.putInt(appVersionProperty, versaoApp);
        editor.commit();

    }
}
