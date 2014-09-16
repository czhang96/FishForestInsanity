package com.me.ffi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class enemy {

    Texture texture;
    Vector2 position;
    Player player;
    Rectangle bound;
   
    public enemy(Vector2 position, Player player) {
    	
    	//initializes position, texture, and rectangle (used for collision detection)
        this.position = position;
        this.player = player;
        texture = new Texture(Gdx.files.internal("enemy.png"));
        bound = new Rectangle(position.x,position.y,32,32);

    }
    


	public void update() {
		//updates rectangle
    	bound.set(position.x,position.y,32,32);

    	//simple algorithm to follow player
    	//inevitably causes clumping of the enemies
    	//which is why a pheonix is spanwed when more than 5 clump together
    	if(position.x>player.getPosition().x){
    		position.x-=1f;
    	}
    	if(position.x<player.getPosition().x){
    		position.x+=1f;
    	}
    	if (position.y<player.getPosition().y){	
    		position.y+=1f;
    	}
    	if (position.y>player.getPosition().y){
    		position.y-=1f;
    	}
    }
    	

    public Rectangle getBound() {
		return bound;
	}
	public Vector2 getPosition() {
        return position;
    }
	public Texture getTexture(){
		return texture;
	}


}
