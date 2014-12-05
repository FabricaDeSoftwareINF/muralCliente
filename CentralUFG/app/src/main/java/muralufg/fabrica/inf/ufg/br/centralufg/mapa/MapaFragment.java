package muralufg.fabrica.inf.ufg.br.centralufg.mapa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import muralufg.fabrica.inf.ufg.br.centralufg.R;
import muralufg.fabrica.inf.ufg.br.centralufg.util.ServiceCompliant;

public class MapaFragment extends Fragment implements ServiceCompliant {

    private static final int TIME_TO_ZOOM = 2000; //em milisegundos
    private static final float ZOOM = 14f;

    public List<Ponto> getPontosAdicionados() {
        return pontosAdicionados;
    }

    private List<Ponto> pontosAdicionados;

    MapView mapView;
    GoogleMap googleMap;

    public MapaFragment() {
        this.pontosAdicionados = new ArrayList<Ponto>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getContextActivity());
        pontosAdicionados = new ArrayList<Ponto>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mapa, container, false);
        //captura o mapview no xml e cria o mesmo
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        initializeMap();

        return v;
    }

    /**
     * Adiciona um marcador a partir das coordenadas informadas em Ponto
     * Adiciona também a descrição do ponto.
     * Adiciona também o evento que faz com que um aplicativo de navegação seja aberto quando pedido.
     * @param ponto Objeto Ponto com cordenadas e descricao;
     */
    public void adicionarMarcador(final Ponto ponto) {

        LatLng latLng = new LatLng(ponto.getLatitude(), ponto.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(ponto.getDescricao()).position(latLng);
        pontosAdicionados.add(ponto);

        //adicionar evento ao marker.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //pergunta ao usuario em qual aplicativo de mapas ele quer abrir a posição.
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("geo://?ll=");
                stringBuilder.append(ponto.getLatitude());
                stringBuilder.append(",");
                stringBuilder.append(ponto.getLongitude());
                String url = stringBuilder.toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(getContextActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
                return true;
            }
        });
        googleMap.addMarker(markerOptions);
    }

    /**
     * Inicializa o mapa e focaliza no ponto.
     */
    private void initializeMap() {
        googleMap = mapView.getMap();
        googleMap.setMyLocationEnabled(true);
    }

    /**
     * Aproxima a camera do ponto desejado.
     *@param ponto ponto contendo latitude e logitude.
     */
    private void zoomInLocation(Ponto ponto) {

        LatLng latLng = new LatLng(ponto.getLatitude(), ponto.getLongitude());
        //move a camera para a posição atual
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //dá zoom na posição atual
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM), TIME_TO_ZOOM, null);
    }

    /**
     * Captura a ultima posição conhecida pelo gps.
     * POde ser utilizado para saber a posição atual do dispositivo.
     * @return objeto Ponto.
     */
    public Ponto getUltimoPonto() {
        LocationManager locationmanager = (LocationManager) getContextActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationmanager.getBestProvider(criteria, true);

        Location l = locationmanager.getLastKnownLocation(provider);
        Ponto p = new Ponto();
        p.setDescricao("Localização Atual");
        p.setLatitude(l.getLatitude());
        p.setLongitude(l.getLongitude());

        return p;
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void handleError(String error) {
        Crouton.makeText(this.getActivity(), error, Style.ALERT).show();
    }

    @Override
    public void readObject(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Activity getContextActivity() {
        return this.getActivity();
    }
}
