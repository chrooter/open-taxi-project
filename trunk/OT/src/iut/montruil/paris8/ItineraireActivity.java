package iut.montruil.paris8;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import iut.montruil.paris8.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;




import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;




public class ItineraireActivity extends MapActivity  implements LocationListener {
    /** Called when the activity is first created. */
	 LinearLayout linearLayout;
     MapView mapView;
     private Road mRoad;
     private LocationManager lManager;
     private Location location;
     private double tolat;
     private double tolon;
     @Override
     public void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
             setContentView(R.layout.maps);
           //On récupère le service de localisation
             
             Intent thisIntent = getIntent();
             String add = thisIntent.getExtras().getString("adresse");
             
             Geocoder coder = new Geocoder(this);
             List<Address> address;

             
                 try {
					address = coder.getFromLocationName(add,5);
					if (address == null) {
	                     System.out.println("probleme , adresse null");
	                 }
	                 Address loc = address.get(0);
	                 tolat=loc.getLatitude();
	                 tolon=loc.getLongitude();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 System.out.println(tolat);

               /*  p1 = new GeoPoint((int) (loc.getLatitude() * 1E6),
                                   (int) (loc.getLongitude() * 1E6));
*/
             
             
             String context = Context.LOCATION_SERVICE;
             lManager = (LocationManager)getSystemService(context);
             String provider = LocationManager.GPS_PROVIDER;
           //on démarre le cercle de chargement
     		setProgressBarIndeterminateVisibility(true);
      
     		//On demande au service de localisation de nous notifier tout changement de position
     		//sur la source (le provider) choisie, toute les minutes (60000millisecondes).
     		//Le paramètre this spécifie que notre classe implémente LocationListener et recevra
     		//les notifications.
   		lManager.requestLocationUpdates(provider, 60000, 0, this);
             location = lManager.getLastKnownLocation(provider);
            mapView = (MapView) findViewById(R.id.mapview);
            mapView.setBuiltInZoomControls(true);
            

             new Thread() {
                     @Override
                     public void run() {
                    	 
                             double fromLat = location.getLatitude(), fromLon = location.getLongitude(), toLat = tolat, toLon = tolon;
                             String url = RoadProvider
                                             .getUrl(fromLat, fromLon, toLat, toLon);
                             InputStream is = getConnection(url);
                             mRoad = RoadProvider.getRoute(is);
                             mHandler.sendEmptyMessage(0);
                     }
             }.start();
     }
     
     Handler mHandler = new Handler() {
             public void handleMessage(android.os.Message msg) {
                     TextView textView = (TextView) findViewById(R.id.description);
                     textView.setText(mRoad.mName + " " + mRoad.mDescription);
                     MapOverlay mapOverlay = new MapOverlay(mRoad, mapView);
                     List<Overlay> listOfOverlays = mapView.getOverlays();
                     listOfOverlays.clear();
                     listOfOverlays.add(mapOverlay);
                     mapView.invalidate();
             };
     };

     private InputStream getConnection(String url) {
             InputStream is = null;
             try {
                     URLConnection conn = new URL(url).openConnection();
                     is = conn.getInputStream();
             } catch (MalformedURLException e) {
                     e.printStackTrace();
             } catch (IOException e) {
                     e.printStackTrace();
             }
             return is;
     }

     @Override
     protected boolean isRouteDisplayed() {
             return false;
     }

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	
}



class MapOverlay extends com.google.android.maps.Overlay {
     Road mRoad;
     ArrayList<GeoPoint> mPoints;

     public MapOverlay(Road road, MapView mv) {
             mRoad = road;
             if (road.mRoute.length > 0) {
                     mPoints = new ArrayList<GeoPoint>();
                     for (int i = 0; i < road.mRoute.length; i++) {
                             mPoints.add(new GeoPoint((int) (road.mRoute[i][1] * 1000000),
                                             (int) (road.mRoute[i][0] * 1000000)));
                     }
                     int moveToLat = (mPoints.get(0).getLatitudeE6() + (mPoints.get(
                                     mPoints.size() - 1).getLatitudeE6() - mPoints.get(0)
                                     .getLatitudeE6()) / 2);
                     int moveToLong = (mPoints.get(0).getLongitudeE6() + (mPoints.get(
                                     mPoints.size() - 1).getLongitudeE6() - mPoints.get(0)
                                     .getLongitudeE6()) / 2);
                     GeoPoint moveTo = new GeoPoint(moveToLat, moveToLong);

                     MapController mapController = mv.getController();
                     mapController.animateTo(moveTo);
                     mapController.setZoom(7);
             }
     }

     @Override
     public boolean draw(Canvas canvas, MapView mv, boolean shadow, long when) {
             super.draw(canvas, mv, shadow);
             drawPath(mv, canvas);
             return true;
     }

     public void drawPath(MapView mv, Canvas canvas) {
             int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
             Paint paint = new Paint();
             paint.setColor(Color.GREEN);
             paint.setStyle(Paint.Style.STROKE);
             paint.setStrokeWidth(3);
             for (int i = 0; i < mPoints.size(); i++) {
                     Point point = new Point();
                     mv.getProjection().toPixels(mPoints.get(i), point);
                     x2 = point.x;
                     y2 = point.y;
                     if (i > 0) {
                             canvas.drawLine(x1, y1, x2, y2, paint);
                     }
                     x1 = x2;
                     y1 = y2;
             }
     }

}