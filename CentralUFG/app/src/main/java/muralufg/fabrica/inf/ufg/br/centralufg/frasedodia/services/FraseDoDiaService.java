package muralufg.fabrica.inf.ufg.br.centralufg.frasedodia.services;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.exception.MyException;
import muralufg.fabrica.inf.ufg.br.centralufg.model.FraseDoDia;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;
import muralufg.fabrica.inf.ufg.br.centralufg.util.SimpleConnection;

public class FraseDoDiaService extends SimpleConnection {

    private static final String URL = "http://fabrica2014.apiary-mock.com/frasedodia";
    static final String TAG = "FraseDoDiaService";

    public FraseDoDiaService(ServiceCompliant handler) {
        super(handler,URL);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return super.doInBackground();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        switch (getHttpStatus()){
            case OK:
                trataHttpStatusOk();
                break;
            case ERROR:
                handler.handleError("Ocorreu um erro");
                break;
            default:
                handler.handleError(handler.getContextActivity().getResources().getString(
                        R.string.alerta_server_error));
                break;
        }
    }

    private void trataHttpStatusOk() {
        try {
            JSONObject object = new JSONObject(getResponse());
            String conteudo = object.getString("quote");
            String autor = object.getString("author");

            FraseDoDia frase = new FraseDoDia(conteudo,autor);
            handler.readObject(frase);
        } catch (JSONException e) {
            handler.handleError("Ocorreu um erro com "+ getResponse() + ": " + e.getLocalizedMessage());
            throw new MyException("context", e);
        }
    }
}
