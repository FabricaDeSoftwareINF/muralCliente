package muralufg.fabrica.inf.ufg.br.centralufg.util.view.cartao;

import java.io.Serializable;

/**
 * Created by GAOliveira on 20/10/2014.
 */
public class Cartao implements Serializable {

    private String titulo;
    private String data;
    private String descricao;
    private int imagem;


    public Cartao() {
    }

    public Cartao(String titulo, String data, String descricao, int imagem) {
        super();
        this.titulo = titulo;
        this.data = data;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    @Override
    public String toString() {
        return titulo;
    }
}