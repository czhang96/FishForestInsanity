package com.me.ffi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
public class projectile {

    Texture texture;
    Integer screen;
    Player player;
    Rectangle bound;
    Vector2 position;
    Vector2 target;
    Vector2 initial;
    public projectile(Vector2 position,Player player) {
    	//Initializes position, texture, rectangle used for collision
    	this.position=position;
        initial  = new Vector2(position.x,position.y);
        this.player = player;
        texture = new Texture(Gdx.files.internal("proj.png"));
        bound = new Rectangle(position.x,position.y,32,32);
        
        //target is the player, but some randomness is added
        //to give some spread to the attack
        target = new Vector2 ((float)(Math.random()*200-100)+player.getPosition().x,(float)(Math.random()*200-100)+player.getPosition().y);

    }
    

	public void update() {

		//speed of the projectile depends on the distance it was fired from
    	bound.set(position.x,position.y,32,32);
    	position.x+= (target.x-initial.x)/70;
    	position.y+= (target.y-initial.y)/70;
    	
    }
    public Texture getTexture() {
		return texture;
	}


    public Rectangle getBound() {
		return bound;
	}

	
	public Vector2 getPosition() {
        return position;
    }




    
}
