package com.me.ffi;


import android.os.Bundle;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
//the game runs on this activity
public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        cfg.useAccelerometer = true;
        cfg.useCompass = false;
        
        //using libgdx framework, initialze rendering of the main class file
        //code can be found under Fish-Forest-Insanity folder
    	initialize(new FishForestInsanity(), cfg);
    	
    }
    
    

}