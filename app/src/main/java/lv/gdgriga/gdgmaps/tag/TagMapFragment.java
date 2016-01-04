package lv.gdgriga.gdgmaps.tag;

import android.graphics.Bitmap;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.*;

import lv.gdgriga.gdgmaps.Location;
import lv.gdgriga.gdgmaps.Photo;

public class TagMapFragment extends MapFragment {
    private GoogleMap map;
    private Photo photo;

    private final OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.setOnMapClickListener(onMapClick);
            if (photo.hasLocation()) {
                drawPhoto();
            } else {
                focusOnLocation(Location.RIGA);
            }
        }
    };

    private final OnMapClickListener onMapClick = new OnMapClickListener() {
        @Override
        public void onMapClick(LatLng clickedLocation) {
            photo.location = clickedLocation;
            drawPhoto();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        getMapAsync(onMapReady);
    }

    public void setPhotoToTag(Photo photo) {
        this.photo = photo;
    }

    private void addPhotoMarker(Photo photo) {
        map.addMarker(markerFrom(photo));
    }

    private MarkerOptions markerFrom(Photo photo) {
        return new MarkerOptions().position(photo.location)
                                  .icon(iconFrom(photo.thumbnail))
                                  .draggable(true);
    }

    private BitmapDescriptor iconFrom(Bitmap thumbnail) {
        if (thumbnail != null) {
            return BitmapDescriptorFactory.fromBitmap(thumbnail);
        }
        return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
    }

    private void drawPhoto() {
        map.clear();
        addPhotoMarker(photo);
        focusOnLocation(photo.location);
    }

    private void focusOnLocation(LatLng location) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, Location.DEFAULT_ZOOM));
    }
}
