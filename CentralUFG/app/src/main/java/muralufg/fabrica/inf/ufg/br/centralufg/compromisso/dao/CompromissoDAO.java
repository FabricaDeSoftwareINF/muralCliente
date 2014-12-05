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

package muralufg.fabrica.inf.ufg.br.centralufg.compromisso.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import muralufg.fabrica.inf.ufg.br.centralufg.model.Compromisso;

public class CompromissoDAO {

    public static final String NOME_TABELA = "Compromisso";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_DESCRICAO = "descricao";
    public static final String COLUNA_DATA = "data";

    public static final String SCRIPT_CRIACAO_TABELA= "CREATE TABLE " + NOME_TABELA + "("
            + COLUNA_ID + " INTEGER PRIMARY KEY," + COLUNA_NOME + " TEXT," + COLUNA_DESCRICAO + " TEXT,"
            + COLUNA_DATA + " TEXT" +")";
 
    public static final String SCRIPT_DELECAO_TABELA =  "DROP TABLE IF EXISTS " + NOME_TABELA;

    private SQLiteDatabase dataBase = null;
 
    private static CompromissoDAO instance;
     
    public static CompromissoDAO getInstance(Context context) {
        if(instance == null) {
            instance = new CompromissoDAO(context);
        }
        return instance;
    }
 
    private CompromissoDAO(Context context) {
        PersistenceHelper persistenceHelper = PersistenceHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }
 
    public void salvar(Compromisso compromisso) {
        ContentValues values = gerarContentValues(compromisso);
        dataBase.insert(NOME_TABELA, null, values);
    }
 
    public List<Compromisso> recuperarTodos() {
        String queryReturnAll = "SELECT * FROM " + NOME_TABELA;
        Cursor cursor = dataBase.rawQuery(queryReturnAll, null);
        List<Compromisso> compromissos = construirCompromissoPorCursor(cursor);
        return compromissos;
    }

    public int contaDatasDistintas(){
        String query = "SELECT count(*) FROM Compromisso group by data";
        Cursor cursor = dataBase.rawQuery(query, null);
        return cursor.getCount();
    }

    public List<String> recuperaDatasDistintas(){
        String query = "SELECT data FROM " + NOME_TABELA + " group by data";
        Cursor cursor = dataBase.rawQuery(query, null);
        List<String> datas = construirDatasPorCursor(cursor);
        return datas;
    }

    public List<Compromisso> recuperarCompromissoPorData(String data) {
        String query = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_DATA + " = '" + data + "'";
        Cursor cursor = dataBase.rawQuery(query, null);
        List<Compromisso> compromissos = construirCompromissoPorCursor(cursor);
        return compromissos;
    }

    public void deletarTodosCompromissos(){
    	String query = "DELETE FROM " + NOME_TABELA;
    	dataBase.execSQL(query);
    }
 
    public void deletar(Compromisso compromisso) {
        String[] valoresParaSubstituir = {
                String.valueOf(compromisso.getId())
        };
        dataBase.delete(NOME_TABELA, COLUNA_ID + " =  ?", valoresParaSubstituir);
    }
 
    public void editar(Compromisso compromisso) {
        ContentValues valores = gerarContentValues(compromisso);
        String[] valoresParaSubstituir = {
                String.valueOf(compromisso.getId())
        };
        dataBase.update(NOME_TABELA, valores, COLUNA_ID + " = ?", valoresParaSubstituir);
    }
 
    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen()) {
            dataBase.close();
        }
    }
 
    private List<Compromisso> construirCompromissoPorCursor(Cursor cursor) {
        List<Compromisso> compromissos = new ArrayList<Compromisso>();
        if(cursor == null) {
            return compromissos;
        }
        try {
 
            if (cursor.moveToFirst()) {
                do {
                	int indexId = cursor.getColumnIndex(COLUNA_ID);
					int indexNome = cursor.getColumnIndex(COLUNA_NOME);
					int indexDescricao = cursor.getColumnIndex(COLUNA_DESCRICAO);
					int indexData = cursor.getColumnIndex(COLUNA_DATA);

					int id = cursor.getInt(indexId);
					String nome = cursor.getString(indexNome);
					String descricao = cursor.getString(indexDescricao);
					String data = cursor.getString(indexData);

                    Compromisso compromisso = new Compromisso(id, nome, descricao, data);
 
                    compromissos.add(compromisso);
 
                } while (cursor.moveToNext());
            }
             
        } finally {
            cursor.close();
        }
        return compromissos;
    }

    private List<String> construirDatasPorCursor(Cursor cursor) {
        List<String> datas = new ArrayList<String>();
        if(cursor == null) {
            return datas;
        }
        try {

            if (cursor.moveToFirst()) {
                do {
                    int indexData = cursor.getColumnIndex(COLUNA_DATA);
                    String data = cursor.getString(indexData);
                    datas.add(data);

                } while (cursor.moveToNext());
            }

        } finally {
            cursor.close();
        }
        return datas;
    }
 
    private ContentValues gerarContentValues(Compromisso compromisso) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_ID, compromisso.getId());
        values.put(COLUNA_NOME, compromisso.getNome());
        values.put(COLUNA_DESCRICAO, compromisso.getDescricao());
        values.put(COLUNA_DATA, compromisso.getStringData());
        return values;
    }
}
