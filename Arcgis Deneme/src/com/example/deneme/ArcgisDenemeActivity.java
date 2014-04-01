package com.example.deneme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnLongPressListener;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;


public class ArcgisDenemeActivity extends Activity {
	
	MapView mMapView;
	ArcGISDynamicMapServiceLayer GungorenMap;
	boolean show=true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		// Retrieve the map and initial extent from XML layout
		mMapView = (MapView)findViewById(R.id.map);
		mMapView.enableWrapAround(true);
		
		// Add dynamic layer to MapView
		ArcGISTiledMapServiceLayer tl = new ArcGISTiledMapServiceLayer(
				"http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer");
		mMapView.addLayer(tl);
		int[] layers = { 0,1,2 };

		GungorenMap = new ArcGISDynamicMapServiceLayer("http://81.215.201.236/arcgis/rest/services/AnkaraTest/AnkaraTest/MapServer",layers);
		mMapView.addLayer(GungorenMap);
		
		final DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		// Menu Button
		Button b=(Button)findViewById(R.id.button1);
	    final LinearLayout ll=(LinearLayout)findViewById(R.id.ll);
	    b.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!show){
					
	            	ll.animate().translationXBy(metrics.widthPixels);
	            	show=true;
	            }
	            else{
	            	
	            	ll.animate().translationXBy(metrics.widthPixels*-1);
	            	show=false;
	            }
			}
		});
	    // Menu Button

	    final GraphicsLayer graphicsLayer = new GraphicsLayer(mMapView.getSpatialReference(), new Envelope(-180, -90, 180, 90));
	    mMapView.addLayer(graphicsLayer);

	    mMapView.setOnLongPressListener(new OnLongPressListener() {
			
			public boolean onLongPress(float x, float y) {
				graphicsLayer.removeAll();
				Drawable d = getResources().getDrawable(R.drawable.icon);
			    PictureMarkerSymbol sym = new PictureMarkerSymbol(d);
			    int[]location = {(int)x,(int)y};
			    mMapView.getLocationOnScreen(location);
			    Log.e("x", Double.toString(mMapView.toMapPoint(x, y).getX()));
			    Log.e("y", Double.toString(mMapView.toMapPoint(x, y).getY()));
			    Point p = new Point(mMapView.toMapPoint(x, y).getX(),mMapView.toMapPoint(x, y).getY());
			    //sym.setOffsetY(50);
			    Graphic g = new Graphic(p, sym);
			    graphicsLayer.addGraphic(g);
				
				Intent i = new Intent(getApplicationContext(), PanoActivity.class);
				startActivity(i);
				
				return false;
			}
		});

	    
    }

    public void onClick(View v){
    	CheckBox c=(CheckBox)v;
    	int id=0;
    	switch(c.getId()){
    		case R.id.checkBox1:
    			id=0;
    			break;
    		case R.id.checkBox2:
    			id=1;
    			break;
    		case R.id.checkBox3:
    			id=2;
    			break;
    	}
    	if(c.isChecked()){
    		GungorenMap.getLayers()[id].setVisible(true);
        	mMapView.zoomToResolution(mMapView.getCenter(), mMapView.getResolution());
    	}
    	else{
    		GungorenMap.getLayers()[id].setVisible(false);
        	mMapView.zoomToResolution(mMapView.getCenter(), mMapView.getResolution());    		
    	}
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
 }
	@Override
	protected void onPause() {
		super.onPause();
		mMapView.pause();
 }
	@Override
	protected void onResume() {
		super.onResume();
		mMapView.unpause();
	}

}