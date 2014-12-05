package muralufg.fabrica.inf.ufg.br.centralufg.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.exception.MyException;

/**
 * Classe para o desregistro do dispositivo do GCM.
 * Created by italogustavomirandamelo on 01/12/14.
 */
public class GCMUnregister{

    GoogleCloudMessaging gcm;
    SharedPreferences sharedPreferences;
    String properyRegId;
    String appVersionProperty = "";
    int playServicesResolutionRequest;
    Context context;

    public GCMUnregister(Context context) {
        this.context = context;
        this.properyRegId = context.getResources().getString(R.string.property_reg_id);
        this.sharedPreferences = context.getSharedPreferences(context.getClass().getSimpleName(), Context.MODE_PRIVATE);
        this.playServicesResolutionRequest = context.getResources().getInteger(R.integer.play_services_resolution_request);
    }

    /**
     * Faz o "desregistro da aplicação no GCM, retorna true caso o desregistro ocorra com sucesso,
     * ou lança uma exceção caso ocorra algum erro.
     * @return booleano true caso o desregistro ocorra com sucesso.
     * 
     * @throws MyException caso ocorra algum erro no desregistro.
     */
    public boolean unregisterGcm() throws MyException{

        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            gcm.unregister();
            deleteRegistrationId();

        } catch (IOException ex) {
            throw new MyException("Erro ao desregistrar GCM", ex);
        }

        return true;
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
     * Delete o id de registro no GCM nas preferências do dispositivo.
     *
     */
    private void deleteRegistrationId() {

        int versaoApp = getAppVersion();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(properyRegId, null);
        editor.putInt(appVersionProperty, versaoApp);
        editor.commit();
    }
}
