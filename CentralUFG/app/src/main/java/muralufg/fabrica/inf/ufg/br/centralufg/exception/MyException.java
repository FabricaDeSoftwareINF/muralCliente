package muralufg.fabrica.inf.ufg.br.centralufg.exception;

import android.util.Log;

/**
 * Created by Bruno on 14/11/2014.
 */
public class MyException extends RuntimeException{

    public MyException(String message, Exception e){
        Log.i(message, e.getMessage());
        super.getStackTrace();
    }


}
