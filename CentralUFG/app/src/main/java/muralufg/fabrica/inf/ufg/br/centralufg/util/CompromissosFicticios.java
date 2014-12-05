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

package muralufg.fabrica.inf.ufg.br.centralufg.util;

import android.content.Context;

import muralufg.fabrica.inf.ufg.br.centralufg.compromisso.dao.CompromissoDAO;
import muralufg.fabrica.inf.ufg.br.centralufg.model.Compromisso;

/* Classe com compromissos fictícios para popular o banco
 * enquanto ainda não temos o servidor, por enquanto
 * o servidor que envia a ordem dos compromissos.
 */
public class CompromissosFicticios {

    private CompromissosFicticios(){
    }

	public static void criaCompromissosFicticios(Context context) {

        Compromisso compromisso1 = new Compromisso(1, "Prova de TACS",
                "A prova de Técnicas avançadas de construção de software será na sala" +
                        "106, no centro de aulas A.", "17/10/2014");
        Compromisso compromisso2 = new Compromisso(2, "Verificar notas da prova TACS",
                "As notas da prova de Técnicas avançadas de construção de software já estão" +
                        "disponíveis no Moodle para consulta.", "21/10/2014");
        Compromisso compromisso3 = new Compromisso(3, "Lembrete de aula",
                "Lembrete que haverá aula normalmente hoje de Prática em Engenharia" +
                        "de software.", "21/10/2014");
        Compromisso compromisso4 = new Compromisso(4, "Não haverá aula",
                "Lembrete que não haverá aula de Técnicas avançadas de construção de software" +
                        "no dia 24 de outubro de 2014.", "24/10/2014");

		CompromissoDAO compromissoDAO = CompromissoDAO.getInstance(context);

        compromissoDAO.deletarTodosCompromissos();

        compromissoDAO.salvar(compromisso1);
        compromissoDAO.salvar(compromisso2);
        compromissoDAO.salvar(compromisso3);
        compromissoDAO.salvar(compromisso4);
	}

}
