package com.example.deneme;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.panoramagl.PLIView;
import com.panoramagl.PLImage;
import com.panoramagl.PLSpherical2Panorama;
import com.panoramagl.PLView;
import com.panoramagl.PLViewListener;
import com.panoramagl.hotspots.PLHotspot;
import com.panoramagl.hotspots.PLIHotspot;
import com.panoramagl.ios.structs.CGPoint;
import com.panoramagl.loaders.PLJSONLoader;
import com.panoramagl.structs.PLPosition;
import com.panoramagl.transitions.PLTransitionBlend;
import com.panoramagl.utils.PLUtils;

public class PanoActivity extends PLView {

	PLSpherical2Panorama panorama;
	PLHotspot h1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		h1=new PLHotspot(10, new PLImage(PLUtils.getBitmap(getApplicationContext(), R.raw.hotspot), false), 10.0f, 0.0f, 0.05f, 0.05f);
		panorama = new PLSpherical2Panorama();
	    panorama.setImage(new PLImage(PLUtils.getBitmap(getApplicationContext(),R.raw.a), false));
	    panorama.addHotspot(h1);
	    this.setPanorama(panorama);
	    
	    this.setListener(new PLViewListener()
        {
        	@Override
    		public void onDidClickHotspot(PLIView view, PLIHotspot hotspot, CGPoint screenPoint, PLPosition scene3DPoint)
        	{
        		if(hotspot.getIdentifier()==10)
        		{
        			panorama.getCamera().lookAt(10.0f, 0.0f,true);
        			load(new PLJSONLoader("res://raw/json_spherical"), true, new PLTransitionBlend(2.0f,1.0f));
        		}
        	}
        });
	    
	    
	      
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pano, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_pano, container,
					false);
			return rootView;
		}
	}

}
