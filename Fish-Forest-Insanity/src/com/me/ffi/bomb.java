package com.me.ffi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class bomb {
	Vector2 position;
	Texture texture;
	float stateTime;
	Rectangle bound;
	Player player;
	boolean det;
	boolean det1;
	public bomb (Vector2 position,Player player){
		//Initializes texture, position, timer, rectangle used for collision detection, and variable
		//used to see if it has detonated or not
		this.position=position;
		texture = new Texture(Gdx.files.internal("bombIcon.png"));
		stateTime=0f;
		bound = new Rectangle(position.x,position.y,64,64);
		this.player = player;
		det= false;
		det1=true;
	}
	public void update(){
		//if player touches it, causes detonation
    	if (bound.overlaps(player.getBound())){
    		if (det1){
    			//set the new texture and rectangle (it will be larger)
	    		texture = new Texture(Gdx.files.internal("bomb.png"));
	    		//turns first detonation variable to false so to not 
	    		//re render this over and over for the next second
	    		det= true;
	    		det1=false;
	    		position.y-=100;
	    		position.x-=100;
	    		bound.set(position.x,position.y,256,256);
    		}
    	}
    	if (det){
    		//starts keeping track of time detonated
    		//bomb will be removed after a second in the main class file
    		stateTime += Gdx.graphics.getDeltaTime();
    	}
	}
	public boolean getDet(){
		return det;
	}
	public float getStateTime(){
		return stateTime;
	}
	public Texture getTexture(){
		return texture;
	}
	public Vector2 getPosition(){
		return position;
	}
	public Rectangle getBound(){
		return bound;
	}
}
