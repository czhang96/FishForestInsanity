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
    float speed;
    public enemy(Vector2 position, Player player) {
    	
    	//initializes position, texture, and rectangle (used for collision detection)
        this.position = position;
        this.player = player;
        this.speed = 2.5f;
        texture = new Texture(Gdx.files.internal("enemy.png"));
        bound = new Rectangle(position.x,position.y,32,32);

    }
    


	public void update() {
		//updates rectangle
    	bound.set(position.x,position.y,32,32);
    	
    	//simple algorithm to follow player
    	float delta_x = position.x-player.getPosition().x;
    	float delta_y = position.y-player.getPosition().y;
    	float distance = (float) Math.sqrt(delta_x*delta_x + delta_y*delta_y);
    	
    	position.x-=delta_x/(distance/speed);
    	position.y-=delta_y/(distance/speed);
    	

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
