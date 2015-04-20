//************************************************************************
//  Vivarium Class. 
//    This is the main object that coordinates the update among the
//    creatures of the vivarium.
//************************************************************************
// Comments :
//    This is meant for demonstration. PLEASE plan out your own vivarium
//    creatures, display hierarchy, data structures, etc. No teapots 
//    allowed :).
//
// History :
//    5 Mar 2008. Translated from c code by Tai-Peng Tian. Original by
//    Stan Sclaroff.


import javax.media.opengl.*;
import com.jogamp.opengl.util.*;

import java.util.*;

public class Vivarium
{
	// number of bees in the tank
	final private int beeNum = 15;
	// the space on the screen
	private Tank tank;
	// a bee object
	private Bee bee;
	// a spider object
	private Spider spider;
	// a food object
	private Food food;
	// the object vector storing any object in the space
	private Vector<Creature> v;
	// random float generator
	private Random rand = new Random();

	public Vivarium()
	{
		// initialize the vector
		v = new Vector<Creature>();

		// initialize the bees one by one and put them into the vector
		// the initial position of all bees are random
		for (int i = 0; i < beeNum; i++) {
			bee = new Bee( rand.nextFloat() * 3.6f - 1.8f,
					rand.nextFloat() * 3.6f - 1.8f,
					rand.nextFloat() * 3.6f - 1.8f, 0.01f, 0.008f, 0.006f );
			v.addElement(bee);
		}

		// initialize the spider and add it to the vector
		spider = new Spider( 0, 0, 0.5f, 0.005f, -0.004f, 0.01f );
		v.addElement(spider);

		// initialize the tank
		tank = new Tank( 4.0f, 4.0f, 4.0f );
	}

	public void init( GL2 gl )
	{
		// initialize all graphical objects involved
		tank.init(gl);
		for (int i = 0; i < v.size(); i++)
			v.get(i).init(gl);
	}

	public void update( GL2 gl )
	{
		// update all graphical objects involved
		tank.update(gl);
		for (int i = 0; i < v.size(); i++)
			v.get(i).update(gl, v);
	}

	public void draw( GL2 gl )
	{
		// draw all graphical objects involved
		tank.draw(gl);
		for (int i = 0; i < v.size(); i++) {
			v.get(i).draw(gl);
		}
	}

	// add food to the space once 'F'/'f' is pressed
	public void addFood() {
		float randX, randY, randZ;

		// set random position
		randX = rand.nextFloat() * 4f - 2f;
		randY = rand.nextFloat() * 4f - 2f;
		randZ = rand.nextFloat() * 4f - 2f;
		food = new Food(randX, randY, randZ);
		// add the food to the vector
		v.addElement(food);
	}
}
