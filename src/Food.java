/*
 * Filename:		Bee.java
 * Author:			Jun Xu
 * Version:			1.0
 * Created Time:	Nov. 1st, 2014
 *
 * CS680 Introduction to Computer Graphics
 * Assignment 2
 * Due Date:		Nov 5th, 2014
 */

import java.util.*;
import javax.media.opengl.*;
import com.jogamp.opengl.util.gl2.GLUT;

public class Food extends Creature {
	// object list of the food
	private int food;
	private GLUT glut = new GLUT();

	// constructor assuming food cannot move
	public Food(float _x, float _y, float _z) {
		super(_x, _y, _z, 0, 0, 0);
	}

	// create the food object and store it to an object list
	public void init(GL2 gl) {
		food = gl.glGenLists(1);
		gl.glNewList(food, GL2.GL_COMPILE);
		gl.glScalef(1f, 0.5f, 1f);
		glut.glutSolidSphere(0.06, 36, 18);
		gl.glEndList();
	}

	// make food the right shape
	public void update(GL2 gl, Vector<Creature> v) {
		gl.glNewList(food, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		gl.glScalef(1f, 0.5f, 1f);
		glut.glutSolidSphere(0.06, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// draw the food
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(food);
		gl.glPopMatrix();
	}

	// return the class name of the food, i.e., "Food"
	public String getClassName() {
		return "Food";
	}
}
