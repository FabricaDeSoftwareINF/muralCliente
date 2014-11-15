package muralufg.fabrica.inf.ufg.br.centralufg.exception;

import android.util.Log;

public class GeneralException extends RuntimeException{
   public GeneralException(String message, Exception e){
        Log.i(message, e.getMessage());
        super.getStackTrace();
            }
        }
