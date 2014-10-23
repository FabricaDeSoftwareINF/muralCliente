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

import java.io.IOException;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

/**
 * Created by italogustavomirandamelo on 17/10/14.
 */
public class GCMRegister extends AsyncTask<Void, Void, String> {

    GoogleCloudMessaging gcm;
    SharedPreferences sharedPreferences;
    String properyRegId;
    String appVersionProperty = "";
    String appVersion = "";
    int playServicesResolutionRequest;
    Context context;
    String idRegistroGCM;
    String senderId;

    public GCMRegister(){}

    public GCMRegister(Context context, SharedPreferences sharedPreferences){
        this.context = context;
        this.properyRegId = context.getResources().getString(R.string.property_reg_id);
        this.senderId = context.getResources().getString(R.string.gcm_sender_id);
        this.sharedPreferences = context.getSharedPreferences(context.getClass().getSimpleName(), Context.MODE_PRIVATE);
        this.playServicesResolutionRequest = context.getResources().getInteger(R.integer.play_services_resolution_request);
    }

    @Override
    public String doInBackground(Void... params) {

        String msg = "";

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            idRegistroGCM = gcm.register(senderId);
            storeRegistrationId(context, idRegistroGCM);

        } catch (IOException ex) {
            // CRON ERRO
        }

        return idRegistroGCM;
    }


    @Override
    protected void onPostExecute(String msg) {
    }


    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            return false;
        }
        return true;
    }


    public String getRegistrationId(Context context) {

        String registrationId = sharedPreferences.getString(properyRegId, "");
        if (registrationId.isEmpty()) {
            return "";
        }

        int registeredVersion = sharedPreferences.getInt(appVersion, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }

        return registrationId;
    }


    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    private void storeRegistrationId(Context context, String regId) {

        int appVersion = getAppVersion(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(properyRegId, regId);
        editor.putInt(appVersionProperty, appVersion);
        editor.commit();

    }


    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
