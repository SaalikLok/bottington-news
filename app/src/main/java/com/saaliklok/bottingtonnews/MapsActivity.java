package com.saaliklok.bottingtonnews;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float mapZoom = 14f;
    private Marker TechCrunch;
    private Marker IGN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in San Francisco and move the camera to the correct zoom location
        LatLng sanFran = new LatLng(37.782149,-122.391123);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.782149,-122.391123), mapZoom));

        //Add a marker for the TechCrunch location
        TechCrunch = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.782149,-122.391123))
                .title("TechCrunch")
                .snippet("A magazine that discusses the latest in tech.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1)));

        //Add a marker for the IGN location
        IGN = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.775432,-122.397764))
                .title("IGN")
                .snippet("A media company for the latest trends in gaming.")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker1)));
    }
}
