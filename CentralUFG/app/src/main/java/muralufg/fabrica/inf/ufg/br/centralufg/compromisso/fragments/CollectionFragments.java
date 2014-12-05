/*
 * ====================================================================
 * Licença da Fábrica de Software (UFG)
 *
 * Copyright (c) 2014 Fábrica de Software
 * Instituto de Informática (Universidade Federal de Goiás)
 * Todos os direitos reservados.
 *
 * Redistribuição e uso, seja dos fontes ou do formato binário
 * correspondente, com ou sem modificação, são permitidos desde que
 * as seguintes condições sejam atendidas:
 *
 * 1. Redistribuição do código fonte deve conter esta nota, em sua
 *    totalidade, ou seja, a nota de copyright acima, as condições
 *    e a observação sobre garantia abaixo.
 *
 * 2. Redistribuição no formato binário deve reproduzir o conteúdo
 *    desta nota, em sua totalidade, ou seja, o copyright acima,
 *    esta lista de condições e a observação abaixo na documentação
 *    e/ou materiais fornecidos com a distribuição.
 *
 * 3. A documentação fornecida com a redistribuição,
 *    qualquer que seja esta, deve incluir o seguinte
 *    texto, entre aspas:
 *       "Este produto inclui software desenvolvido pela Fábrica
 *       de Software do Instituto de Informática (UFG)."
 *
 * 4. Os nomes Fábrica de Software, Instituto de Informática e UFG
 *    não podem ser empregados para endoçar ou promover produtos
 *    derivados do presente software sem a explícita permissão
 *    por escrito.
 *
 * 5. Produtos derivados deste software não podem ser chamados
 *    "Fábrica de Software", "Instituto de Informática", "UFG",
 *    "Universidade Federal de Goiás" ou contê-los em seus nomes,
 *    sem permissão por escrito.
 *
 * ESTE SOFTWARE É FORNECIDO "COMO ESTÁ". NENHUMA GARANTIA É FORNECIDA,
 * EXPLÍCITA OU NÃO. NÃO SE AFIRMA QUE O PRESENTE SOFTWARE
 * É ADEQUADO PARA QUALQUER QUE SEJA O USO. DE FATO, CABE AO
 * INTERESSADO E/OU USUÁRIO DO PRESENTE SOFTWARE, IMEDIATO OU NÃO,
 * ESTA AVALIAÇÃO E A CONSEQUÊNCIA QUE O USO DELE OCASIONAR. QUALQUER
 * DANO QUE DESTE SOFTWARE DERIVAR DEVE SER ATRIBUÍDO AO INTERESSADO
 * E/OU USUÁRIO DESTE SOFTWARE.
 * ====================================================================
 *
 * Este software é resultado do trabalho de voluntários, estudantes e
 * professores, ao realizar atividades no âmbito da Fábrica de Software
 * do Instituto de Informática (UFG). Consulte <http://fs.inf.ufg.br>
 * para detalhes.
 */

package muralufg.fabrica.inf.ufg.br.centralufg.compromisso.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.compromisso.dao.CompromissoDAO;
import muralufg.fabrica.inf.ufg.br.centralufg.util.CompromissosFicticios;

public class CollectionFragments extends Fragment {
    
    CollectionPagerAdapter mCollectionPagerAdapter;

    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompromissosFicticios.criaCompromissosFicticios(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CompromissoDAO compromissoDAO = CompromissoDAO.getInstance(getActivity());
        int qtdeDatasDistintas = compromissoDAO.contaDatasDistintas();
        List<String> datas = compromissoDAO.recuperaDatasDistintas();

        List<Fragment> fragments = criaFragments(qtdeDatasDistintas, datas);

        mCollectionPagerAdapter = new CollectionPagerAdapter(getActivity().getSupportFragmentManager(), fragments);

        mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mViewPager.setAdapter(mCollectionPagerAdapter);
    }

    public List<Fragment> criaFragments(int qtdeFragments, List<String> datas){
        List<Fragment> fragments = new ArrayList<Fragment>();
        for (int i = 0; i < qtdeFragments; i++){
            String dataDesteFragment = datas.get(i);
            Fragment fragment = new CompromissoFragment();
            Bundle args = new Bundle();
            args.putString(CompromissoFragment.ARG_DATA, dataDesteFragment);
            fragment.setArguments(args);
            fragments.add(fragment);
        }
        return fragments;
    }

    public static class CollectionPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> listaFragments = new ArrayList<Fragment>();

        public CollectionPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            listaFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return listaFragments.get(i);
        }
        
        @Override
        public int getCount() {
            return listaFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Bundle args = listaFragments.get(position).getArguments();
            String data = args.getString(CompromissoFragment.ARG_DATA);
            return data;
        }
    }

}
