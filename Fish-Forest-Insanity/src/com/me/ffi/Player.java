package com.me.ffi;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player  {

  

    float stateTime;
    Vector2 position;
    Texture texture;
    Rectangle bound;
    
    public Player(Vector2 position) {
        this.position = position;
        this.texture =  new Texture(Gdx.files.internal("fish.jpg"));
        //initializes rectangle for collision detection
        bound = new Rectangle(position.x,position.y,36,32);
        
    }
    
    public void update() {

    	//updates rectangle 
    	bound.set(position.x, position.y,36 , 32);
    	
    	//moving based on accelerometer values
    	if(Gdx.input.getAccelerometerY()<0){
    		if (position.x>0){
    			position.x+=2*Gdx.input.getAccelerometerY();
    		}
    	}
    	if(Gdx.input.getAccelerometerY()>0){
    		if (position.x<Gdx.graphics.getWidth()-64){
    			position.x+=2*Gdx.input.getAccelerometerY();
    		}
    	}
    	if (Gdx.input.getAccelerometerX()>0){
    		if (position.y>0){
    			position.y-=2*Gdx.input.getAccelerometerX();
    		}
    	}
    	if (Gdx.input.getAccelerometerX()<0){
    		if (position.y<Gdx.graphics.getHeight()-64){
    			position.y-=2*Gdx.input.getAccelerometerX();
    		}
    	}
    	
    }
    	

    public Rectangle getBound() {
		return bound;
	}

	public void setBound(Rectangle bound) {
		this.bound = bound;
	}

	public Vector2 getPosition() {
        return position;
    }
    public Texture getTexture(){
    	return texture;
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    

}
