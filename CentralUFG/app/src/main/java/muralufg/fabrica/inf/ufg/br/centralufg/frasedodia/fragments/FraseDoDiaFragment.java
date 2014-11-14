package muralufg.fabrica.inf.ufg.br.centralufg.frasedodia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.frasedodia.services.FraseDoDiaService;
import muralufg.fabrica.inf.ufg.br.centralufg.model.FraseDoDia;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;

public class FraseDoDiaFragment extends Fragment implements ServiceCompliant {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frasedodia, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FraseDoDiaService service = new FraseDoDiaService(this);
        service.execute();
    }

    @Override
    public void handleError(String error) {
        Crouton.makeText(this.getActivity(), error, Style.ALERT).show();
    }

    @Override
    public void readObject(Object object) {
        FraseDoDia frase = (FraseDoDia) object;

        TextView labelFrase = (TextView) getView().findViewById(R.id.quote);
        labelFrase.setText(frase.getFrase());

        TextView labelAutor = (TextView) getView().findViewById(R.id.author);
        labelAutor.setText(frase.getAutor());
    }

    public Activity getContextActivity(){
        return this.getActivity();
    }
}
