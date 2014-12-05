package muralufg.fabrica.inf.ufg.br.centralufg.util.view.cartao;

/**
 * Created by GAOliveira on 20/10/2014.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class CartoesListFragment extends Fragment implements AdapterView.OnItemClickListener {


    ListView listaDeCartoes;
    List<Cartao> cartoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View janelaDoFragment = inflater.inflate(R.layout.fragment_lista_cartoes, container, false);

        listaDeCartoes = (ListView) janelaDoFragment.findViewById(R.id.lista_cartoes);
        final ArrayAdapter<Cartao> cartoesAdapter = new ListaCartoesAdapter(this.getActivity(),  cartoes);
        listaDeCartoes.setAdapter(cartoesAdapter);

        listaDeCartoes.setOnItemClickListener(this);

        return janelaDoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartoes = geraCartoes();


    }

    private List<Cartao> geraCartoes() {
        List<Cartao> cartaoDados = new ArrayList<Cartao>();
        cartaoDados.add(criarcartao("Titulo 1", "21/10/2014", "Este é um texto de teste para o card numero 1", R.drawable.imagem));
        cartaoDados.add(criarcartao("Titulo 2", "21/10/2014", "Este é um texto de teste para o card numero 2", R.drawable.imagem));
        cartaoDados.add(criarcartao("Título 3", "21/10/2014", "Este é um texto de teste para o card numero 3", R.drawable.imagem));
        cartaoDados.add(criarcartao("Título 4", "21/10/2014", "Este é um texto de teste para o card numero 4", R.drawable.imagem));
        cartaoDados.add(criarcartao("Título 5", "21/10/2014", "Este é um texto de teste para o card numero 5", R.drawable.imagem));

        return cartaoDados;
    }

    private Cartao criarcartao(String titulo, String data, String descricao, int image) {
        Cartao cartao = new Cartao(titulo, data, descricao, image);
        return cartao;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicaoTocada, long l) {
        Cartao cartao = cartoes.get(posicaoTocada);
        Toast.makeText(getActivity(), R.string.menssage, Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(getActivity().getApplicationContext(), DetalheActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(DetalheActivity.EXTRA_CARTAO, cartao);
        intent.putExtras(extras);

        startActivity(intent);
    }
}