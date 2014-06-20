//package com.melnik.odesktest.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.actionbarsherlock.app.SherlockFragment;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//import com.google.android.gms.maps.MapsInitializer;
//import com.google.android.gms.maps.model.LatLng;
//import com.melnik.odesktest.R;
//
//public class GoogleMapFragment extends SherlockFragment {
//
//    private MapView mapView;
//    private GoogleMap map;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.activity_store_finder, container, false);
//
//        // Gets the MapView from the XML layout and creates it
//        mapView = (MapView) v.findViewById(R.id.mapview);
//        mapView.onCreate(savedInstanceState);
//
//        // Gets to GoogleMap from the MapView and does initialization stuff
//        map = mapView.getMap();
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.setMyLocationEnabled(true);
//
//        MapsInitializer.initialize(this.getActivity());
//
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        map.animateCamera(cameraUpdate);
//
//        return v;
//    }
//
//    @Override
//    public void onResume() {
//        mapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//}
