package com.me.ffi;



import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
public class FishForestInsanity implements ApplicationListener {
	
	//declaring variables
	SpriteBatch batch;
	SpriteBatch text;
	Texture fish;
	Vector2 position;
	Player player;
	ArrayList <enemyPheonix> ep;
	ArrayList <enemy> es;
	ArrayList <projectile> p;
	ArrayList <bomb> bombs;
	float stateTime;
	float bombRender;
	Integer screen;
	BitmapFont font;
	
	public void create() {
		restart();
		stateTime = 0f;
		screen = 0;
		font = new BitmapFont();
		font.scale(3);
	}

	public void render() {
		if (screen== 0){
			
			//getDeltaTime returns change in time from previous frame
			//stateTime and bombRender are used to keep track of time
			//for the spawning of enemies, items, score, etc.
			stateTime += Gdx.graphics.getDeltaTime();
			bombRender += Gdx.graphics.getDeltaTime();
			
			//clears screen
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			//used to store which projectiles need to be removed
			//they are removed when they get off the screen. will be explained later
			ArrayList<Integer> removeP = new ArrayList<Integer>();
			
			//update enemies, see their respective classes
			int count = 0;
			for (enemyPheonix e:ep)
				e.update();
			for (enemy e: es)
				e.update();
			
			//if projectile is off screen, get ready to remove it
			for (int i = 0 ; i<p.size();i++){
				p.get(i).update();
				if (p.get(i).getPosition().x<0 ||p.get(i).getPosition().x>Gdx.graphics.getWidth()||p.get(i).getPosition().y<0||p.get(i).getPosition().y>Gdx.graphics.getHeight())
					removeP.add(i);
			}
			//remove it
			for (int i = 0 ; i<removeP.size();i++){
				p.remove(removeP.get(i)-i);
			}
			
			// more updates
			for (bomb b: bombs)
				b.update();
			player.update();
			
			//goes through all red circles on screen
			for (int i = 0 ; i <es.size() ; i++){
				count=0;
				ArrayList <Integer> indices = new ArrayList<Integer>();
				
				// find out what circles they are overlapping with
				// and which circles they are overlapping with
				for (int j = i+1 ; j<es.size() ; j++){
					if (es.get(i).getBound().overlaps(es.get(j).getBound())){
						count++;
						indices.add(j);
					}
					
				}
				// if more than 5 are overlapping, then a pheonix is spawned
				// and the circles are removed
				if (count>5){
					ep.add (new enemyPheonix (new Vector2(es.get(i).getPosition().x,es.get(i).getPosition().y),player));
					for (int j = 0; j<indices.size(); j++){
						es.remove(indices.get(j)-j);	
					}
				}	
			}
			//add next wave of enemies 
			if (stateTime % 2 >1.9){
				boolean valid = false;
				int x= (int)player.getPosition().x;
				int y= (int)player.getPosition().y;
				while (!valid){
					x= (int)(Math.random()*Gdx.graphics.getWidth());
					y= (int)(Math.random()*Gdx.graphics.getHeight());
					if (Math.abs(x-player.getPosition().x)>500 && Math.abs(y-player.getPosition().y)>400){
						valid = true;
					}
				}
				es.add(new enemy (new Vector2(x,y),player));
				
				


			}
			//adds wave of projectiles to be shot by pheonix
			if (stateTime%2 > 1.8){
				for (enemyPheonix e : ep){
					p.add(new projectile (new Vector2((float)(Math.random()*200)+e.getPosition().x,(float)(Math.random()*200)+e.getPosition().y),player));
				}
			}
			// every five seconds, bomb is rendered, and the timer is reset
			if (bombRender>5){
				bombs.add(new bomb(new Vector2((int)(Math.random()*Gdx.graphics.getWidth()),(int)(Math.random()*Gdx.graphics.getHeight())),player));
				bombRender=0;
			}
			ArrayList <Integer> removeBomb = new ArrayList<Integer>();
			//goes through all bombs
			//if they are detonated, then checks for collisions with all other enemies on screen
			//if collision, add to list to be ready to removed
			for (int b =0 ;b <bombs.size();b++){
				if (bombs.get(b).getDet()){
					ArrayList <ArrayList <Integer>> list = new ArrayList<ArrayList<Integer>>();
					for (int i = 0; i<3;i++){
						list.add(new ArrayList<Integer>());
					}
					for (int i = 0; i<es.size();i++){
						if (bombs.get(b).getBound().overlaps(es.get(i).getBound())){
							list.get(0).add(i);
				
						}
					}
					for (int i=0;i<ep.size();i++){
						if (bombs.get(b).getBound().overlaps(ep.get(i).getBound())){
							list.get(1).add(i);
						}
					}
					for (int i=0;i<p.size();i++){
						if (bombs.get(b).getBound().overlaps(p.get(i).getBound())){
							list.get(2).add(i);
						}
					}
					//actually remove them here
					for (int e = 0;e<list.get(0).size(); e++){
						es.remove(list.get(0).get(e)-e);
					}
					for (int e = 0;e<list.get(1).size(); e++){
						ep.remove(list.get(1).get(e)-e);
					}
					for (int e = 0;e<list.get(2).size(); e++){
						p.remove(list.get(2).get(e)-e);
					}
					
				}
				//bomb detonation lasts one second
				//afterwards, add to list to be removed
				if (bombs.get(b).getStateTime()>1){
					removeBomb.add(b);
				}
			}
			//remove the bomb
			for (int i = 0 ; i<removeBomb.size();i++){
				bombs.remove(removeBomb.get(i)-i);
			}
			
			
			//checks for collisions with player
			//collided, you lose, change screen to 1
			for (enemy e: es){
		    	if (e.getBound().overlaps(player.getBound())){
		    	    screen=1;
		    	}
			}
			for (enemyPheonix e: ep){
		    	if (e.getBound().overlaps(player.getBound())){
		    	    screen=1;
		    	}
			}
			for (projectile e: p){
		    	if (e.getBound().overlaps(player.getBound())){
		    	    screen=1;
		    	}
			}
			
			//draws everything. see method
			draw(batch);
		}
		
		
		if (screen==1){
			//clears screen
			Gdx.gl.glClearColor(1, 1, 1, 1);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			//display losing text, and score (stateTime)
			text.begin();
			System.out.println("123");
			font.draw(text,"You lose.",300,300);
			font.draw(text,"Score: "+String.valueOf((int)stateTime),300,600);
			text.end();
			
			// resumes game by calling restart
			// and changing stateTime back to 0 and screen to 0
			if (Gdx.input.isTouched()){
				restart();
				screen=0;
				stateTime=0f;
			}	
		}
	}
	//empties all arrays storing player, enemies, etc
	public void restart() {
		batch = new SpriteBatch();
		player = new Player(new Vector2(500,450));

		text= new SpriteBatch();
		
		ep =new ArrayList<enemyPheonix>();
		es = new ArrayList<enemy>();
		p = new ArrayList <projectile>();
		bombs = new ArrayList <bomb>();
		for (int i = 0 ; i <10 ; i++){
			int x= (int)(Math.random()*Gdx.graphics.getWidth());
			int y= (int)(Math.random()*Gdx.graphics.getHeight());
			if (Math.abs(x-player.getPosition().x)>500 && Math.abs(y-player.getPosition().y)>400){
				es.add(new enemy (new Vector2(x,y),player));
			}
		}
		bombRender= 0f;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	@Override
	public void dispose() {

	}

	@Override
	public void resize(int width, int height) {
		
	}
	public void draw(SpriteBatch batch){
		//draws all of the enemies, projectiles and player with their 
		//corresponding texture and position
		batch.begin();
		batch.draw(player.getTexture(),player.getPosition().x,player.getPosition().y);
		for (enemyPheonix e: ep){
			batch.draw(e.getCurrentFrame(),e.getPosition().x,e.getPosition().y);
		}
		for (enemy e: es){
			batch.draw(e.getTexture(),e.getPosition().x,e.getPosition().y);
		}
		for (projectile e :p){
			batch.draw(e.getTexture(),e.getPosition().x,e.getPosition().y);
		}
		for (bomb b: bombs){
			batch.draw(b.getTexture(),b.getPosition().x,b.getPosition().y);
		}
		batch.end();
	}
}
