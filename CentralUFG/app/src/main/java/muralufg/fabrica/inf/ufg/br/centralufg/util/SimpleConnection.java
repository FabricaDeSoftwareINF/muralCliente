package muralufg.fabrica.inf.ufg.br.centralufg.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import muralufg.fabrica.inf.ufg.br.centralufg.exception.GeneralException;
import android.util.Log;
import com.github.kevinsawicki.http.HttpRequest;
import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class SimpleConnection extends AsyncTask<Void, Void, Void> {

    protected static final int OK = 200;
    protected static final int ERROR = 400;
    static final String TAG = "SimpleConnection";
    private int httpStatus;
    private String response;
    protected ServiceCompliant handler;
    private ProgressDialog dialog;
    private String url;


    public SimpleConnection(ServiceCompliant handler, String url){
        this.handler = handler;
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            HttpRequest request = HttpRequest.get(url);
            httpStatus = request.code();
            response = request.body();
        }catch (HttpRequest.HttpRequestException e){
            Log.i(TAG, "Erro de Registro.");
            throw new GeneralException("context", e);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(handler.getContextActivity());
        dialog.setMessage(handler.getContextActivity().getResources().getString(
                R.string.label_aguarde));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        dialog.dismiss();
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int status) {
        this.httpStatus = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
