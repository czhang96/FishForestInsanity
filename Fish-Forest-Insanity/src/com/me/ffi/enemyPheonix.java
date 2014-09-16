package com.me.ffi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class enemyPheonix {
  
    private static final int col=4;
    private static final int row=4;
    
    Animation animation;
    Texture texture;
    TextureRegion[] frames;
    float stateTime;  
    Vector2 position;
    TextureRegion currentFrame;
    Player player;
    Rectangle bound;

    public enemyPheonix(Vector2 position, Player player) {
        this.position = position;
        this.player = player;
        
        //creates an array of textures from the sprite sheet
        texture = new Texture(Gdx.files.internal("phoenixn.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture,texture.getWidth()/col,texture.getHeight()/row);   
        frames = new TextureRegion[col*row];
        int index = 0;
        
        //converts the 2d array holding texture to 1d array
        for (int i = 0 ; i <row; i++){
        	for (int j = 0 ; j <col; j++){
        		frames[index++] = tmp[i][j];
        	}
        }
        //so it can be implemented into built in animation class
        animation = new Animation(1f,frames);
        
        //rectangle for collision detection
        bound = new Rectangle(position.x,position.y,128,128);
        stateTime = 0f;
        
        //set the current frame as the first one in the animation class
        currentFrame = animation.getKeyFrame(0);
    }
    


	public void update() {
		//update time and rectangle
    	stateTime+= 2*Gdx.graphics.getDeltaTime();
    	bound.set(position.x,position.y,128,128);

    	if(position.x>player.getPosition().x){
    		//using the stateTime, I choose which frame to use
    		//moving horizontal frames are frame 4-12
    		//moving vertical frames are 1-4 and 12-16
    		currentFrame = animation.getKeyFrame(4 + stateTime%4);
    		position.x-=0.5f;
    		
    	}
    	if(position.x<player.getPosition().x){
    		currentFrame = animation.getKeyFrame(8+ stateTime%4);
    		position.x+=0.5f;
    	}
    	
    	if (position.y<player.getPosition().y){
    		currentFrame = animation.getKeyFrame(12+ stateTime%4);		
    		position.y+=0.5f;
    	}
    	
    	if (position.y>player.getPosition().y){
    		currentFrame = animation.getKeyFrame(0+ stateTime%4);
    		position.y-=0.5f;
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
	

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

    public void setPosition(Vector2 position) {
        this.position = position;
    }
    
}
