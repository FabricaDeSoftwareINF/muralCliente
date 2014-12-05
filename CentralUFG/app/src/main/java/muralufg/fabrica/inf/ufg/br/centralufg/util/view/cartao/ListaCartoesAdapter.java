package muralufg.fabrica.inf.ufg.br.centralufg.util.view.cartao;

/**
 * Created by GAOliveira on 20/10/2014.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

public class ListaCartoesAdapter extends ArrayAdapter<Cartao> {

    private Context context;
    private List<Cartao> cartoes = null;
    private View viewcartao;

    public ListaCartoesAdapter(Context context, List<Cartao> cartoes) {
        super(context,0, cartoes);
        this.cartoes = cartoes;
        this.context = context;
    }

    @Override
    public View getView(int posicao,View viewcartao, ViewGroup parent) {
        Cartao cartao = this.cartoes.get(posicao);

        this.viewcartao = viewcartao;

        if (this.viewcartao == null) {
            this.viewcartao = LayoutInflater.from(context).inflate(R.layout.item_list_cartao, null);

            ImageView imageViewcartao = (ImageView) this.viewcartao.findViewById(R.id.image_view_cartao);
            imageViewcartao.setImageResource(cartao.getImagem());

            TextView textViewTituloCartao = (TextView) this.viewcartao.findViewById(R.id.text_view_titulo_cartao);
            textViewTituloCartao.setText(cartao.getTitulo());

            TextView textViewDataCartao = (TextView) this.viewcartao.findViewById(R.id.text_view_data_cartao);
            textViewDataCartao.setText(cartao.getData());

            TextView textViewDescricaoCartao = (TextView) this.viewcartao.findViewById(R.id.text_view_descricao_cartao);
            textViewDescricaoCartao.setText(cartao.getDescricao());
        }
            return this.viewcartao;
        }



}