package iti.mobile.touropia.Screens.Map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;

import iti.mobile.touropia.R;

import com.google.maps.android.PolyUtil;
//import com.google.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String LNG = "LNG";
    private static final String LAT = "LAT";
    private GoogleMap mMap;
    private double location_lat, location_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void setLat_Lng() {
        location_lat = getIntent().getDoubleExtra(LAT, 31.0413814);
        location_lng = getIntent().getDoubleExtra(LNG, 31.3478199);
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(getResources().
                        getString(R.string.google_maps_key)).build();
        return geoApiContext;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setLat_Lng();

        LatLng startlatLng = new LatLng(location_lat, location_lng);
        LatLng endlatLng = new LatLng(30.0711192, 31.0185634);

        //googleMap.addPolyline( new PolylineOptions().add(startlatLng , endlatLng).width(5));

        markePoints(startlatLng, endlatLng);
        drawRoute(startlatLng, endlatLng);
    }

    private void markePoints(LatLng startlatLng, LatLng endlatLng) {
        mMap.addMarker(new MarkerOptions().position(startlatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startlatLng, 12.0f));
        mMap.addMarker(new MarkerOptions().position(endlatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(endlatLng, 12.0f));
    }


    private void drawRoute(LatLng startlatLng, LatLng endlatLng) {
        DirectionsResult result = null;
        com.google.maps.model.LatLng start = new com.google.maps.model.LatLng(startlatLng.latitude, startlatLng.longitude);
        com.google.maps.model.LatLng end = new com.google.maps.model.LatLng(endlatLng.latitude, endlatLng.longitude);

        try {
            result = DirectionsApi.newRequest(getGeoContext()).mode(
                    TravelMode.DRIVING).origin(start) //31.0413814,31.3478199)
                    .destination(end).await(); //30.0711192,31.0185634
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<LatLng> decodePath = PolyUtil.decode(result.routes[0].overviewPolyline.getEncodedPath());
        mMap.addPolyline(new PolylineOptions().addAll(decodePath));
    }


}
