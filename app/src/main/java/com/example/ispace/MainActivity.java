package com.example.ispace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        String[] infoList={"Satellite Tracker","ISS Tracker","ISRO","NASA","Planets","Comets","Galaxies","Pluto","Venus"};
        int[] ispace_info_images = {R.drawable.pngwing_com,R.drawable.pngwing_com_1,R.drawable.indian_space_research_organisation_logo_svg,R.drawable.nasa_logo_svg,R.drawable.all_planet_png_hd,R.drawable.pngimg_com_meteor_png33,R.drawable.galaxy_color_desktop_wallpaper_galaxy_e577a1f399e4ae3ad39f5dc70cf8e9bf,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background,R.drawable.ic_launcher_background};
        SpaceInfoAdapter spaceInfoAdapter = new SpaceInfoAdapter(getApplicationContext(), infoList,ispace_info_images);
        GridView gridView = (GridView) findViewById(R.id.SpaceInfoGrid);
        gridView.setAdapter(spaceInfoAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("INFO",String.valueOf(i));
                if (i==0)
                {
                    Intent intent = new Intent(getApplicationContext(),SatelliteTracking.class);
                    startActivity(intent);
                }

            }



        });



    }
}