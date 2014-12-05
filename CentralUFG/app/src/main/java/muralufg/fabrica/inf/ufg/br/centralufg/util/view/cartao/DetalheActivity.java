package muralufg.fabrica.inf.ufg.br.centralufg.util.view.cartao;

/**
 * Created by GAOliveira on 21/10/2014.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import muralufg.fabrica.inf.ufg.br.centralufg.R;

/**
 * Created by GAOliveira on 21/10/2014.
 */
public class DetalheActivity extends Activity {

    public static final String EXTRA_CARTAO = "cartao_key";

    TextView mTitulocartao;
    TextView mDescricaocartao;

    Cartao mCartao;

    @Override
    protected void onCreate(Bundle saveInstanceState){

        super.onCreate(saveInstanceState);
        setContentView(R.layout.detalhes_activity_cartao);

        initView();

        mCartao = (Cartao) getIntent().getSerializableExtra(EXTRA_CARTAO);
        if(mCartao != null){
            mTitulocartao.setText(mCartao.getTitulo());
            mDescricaocartao.setText(mCartao.getDescricao());
        }

    }

    private void initView(){
        mTitulocartao = (TextView) findViewById(R.id.text_view_titulo_cartao);
        mDescricaocartao = (TextView) findViewById(R.id.text_view_descricao_cartao);
    }
}


