package muralufg.fabrica.inf.ufg.br.centralufg.mapa;

/**
 * Created by Vinicius on 02/12/2014.
 */
public class Ponto {

    private String descricao;
    private double latitude;
    private double longitude;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
