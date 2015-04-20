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
import com.jogamp.opengl.util.*;

public class Creature {
	// float that stores the neighbor distances when simulating boids
	final private float dist = 1f;
	// float that stores half of the length of the bounding box
	final private float rangeDanger = 0.2f;
	final private float rangeSep = 0.03f;

	// the x, y, z coordinates of the creature
	protected float x, y, z;
	// the initial velocities of the creature
	protected float vx, vy, vz;
	// the x, y, z coordinate changes from frame to frame
	protected float dx, dy, dz;
	// the x, y, z coordinate changes computed by the potential function
	// used as temporary storages
	private float ddx, ddy, ddz;
	// the direction along x, y and z axes
	protected int dirX, dirY, dirZ;
	// angle1 is the angle from the projection of the right one onto xz plane to z axis
	// and angle2 is angle from the right one to xz plane
	protected float angle1, angle2;

	// coefficients of velocity changes due to potential function, border limits
	// separation, alignment and cohesion
	private float alpha1 = 0.009f;
	private float alpha2 = 0.018f;
	private float alpha3 = 0.15f;
	private float alpha4 = 0.014f;
	private float alpha5 = 0.01f;
	private float alpha6 = 0.5f;
	private float alpha7 = 0.013f;
	private float alpha8 = 0.9f;
	// random float generator
	private Random rand = new Random();

	// constructor given initial x, y, z coordinates and x, y, z speed
	public Creature(float _x, float _y, float _z, float _vx, float _vy, float _vz) {
		x = _x;
		y = _y;
		z = _z;

		vx = _vx;
		vy = _vy;
		vz = _vz;

		dirX = dirY = dirZ = 1;
	}

	// return the class name
	public String getClassName() { return "Creature"; }
	// return the x coordinate
	public float getX() { return x; }
	// return y coordinate
	public float getY() { return y; }
	// return z coordinate
	public float getZ() { return z; }
	// return x coordinate change from frame to frame
	public float getDx() { return dx; }
	// return y coordinate change from frame to frame
	public float getDy() { return dy; }
	// return z coordinate change from frame to frame
	public float getDz() { return dz; }

	// init function, implemeted by inherited class
	public void init(GL2 gl) {}
	// update function, implemeted by inherited class
	public void update(GL2 gl, Vector<Creature> v) {}
	// draw function, implemeted by inherited class
	public void draw(GL2 gl) {}

	// move the creature to the right location
	protected void translate(Vector<Creature> v) {
		// set x, y and z coordinate changes to 0 first
		dx = dy = dz = 0;

		// adjust them according to potential function
		// which involves border limits and prey-predator relationship animal-food relationship
		adjust(v);
		// handle separation behavior
		separation(v);
		// handle alignment behavior
		alignment(v);
		// handle cohesion behavior
		cohesion(v);

		// add the initial velocity along each axis
		dx += vx * dirX;
		dy += vy * dirY;
		dz += vz * dirZ;

		dx /= 1.2f;
		dy /= 1.2f;
		dz /= 1.2f;

		// add some random noise
		// because spider moves more slowly, the random part is also smaller
		if (this.getClassName().equals("Bee")) {
			x += dx + rand.nextFloat() * 0.011f * dirX;
			y += dy + rand.nextFloat() * 0.011f * dirY;
			z += dz + rand.nextFloat() * 0.011f * dirZ;
		} else if (this.getClassName().equals("Spider")) {
			x += dx + rand.nextFloat() * 0.004f * dirX;
			y += dy + rand.nextFloat() * 0.004f * dirY;
			z += dz + rand.nextFloat() * 0.004f * dirZ;
		}

		// change moving direction along each axis once out of space
		if (vx > 0) {
			if (x > 1.8) {
				dirX = -1;
				x = 1.8f;
			}
			if (x < -1.8) {
				dirX = 1;
				x = -1.8f;
			}
		} else {
			if (x > 1.8) {
				dirX = 1;
				x = 1.8f;
			}
			if (x < -1.8) {
				dirX = -1;
				x = -1.8f;
			}
		}
		if (vy > 0) {
			if (y > 1.8) {
				dirY = -1;
				y = 1.8f;
			}
			if (y < -1.8) {
				dirY = 1;
				y = -1.8f;
			}
		} else {
			if (y > 1.8) {
				dirY = 1;
				y = 1.8f;
			}
			if (y < -1.8) {
				dirY = -1;
				y = -1.8f;
			}
		}
		if (vz > 0) {
			if (z > 1.8) {
				dirZ = -1;
				z = 1.8f;
			}
			if (z < -1.8) {
				dirZ = 1;
				z = -1.8f;
			}
		} else {
			if (z > 1.8) {
				dirZ = 1;
				z = 1.8f;
			}
			if (z < -1.8) {
				dirZ = -1;
				z = -1.8f;
			}
		}
	}

	private void adjust(Vector<Creature> v) {
		// index of food in the object vector that is consumed
		int idx = -1;

		// if this creature is a bee, it will go for food and escape from a spider
		// also it is a boid among all bees
		// if this creature is a spider, it goes for both food and bees
		// otherwise do nothing
		switch(this.getClassName()) {
		case "Bee":
			for (int i = 0; i < v.size(); i++)
			{
				// if the other one is itself, do nothing
				if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < 0.00001f)
					continue;

				// compute the potential, results stored in ddx, ddy and ddz
				getPotential(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ());

				// if the other one is food, go for it, 
				// else if the other one is a spider, avoid it
				// eles if the other one is a bee, avoid collision
				switch(v.get(i).getClassName()) {
				case "Food":
					// if the bee is close enough to the food, the food is eaten and removed from the object list
					if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < 0.1f)
						idx = i;
					dx += alpha1 * ddx;
					dy += alpha1 * ddy;
					dz += alpha1 * ddz;
					break;
				case "Spider":
					// if a bee is within the bounding box of a spider, change its x, y and z direction and run away
					if (x > v.get(i).getX() - rangeDanger && x < v.get(i).getX() + rangeDanger &&
							y > v.get(i).getY() - rangeDanger && y < v.get(i).getY() + rangeDanger &&
							z > v.get(i).getZ() - rangeDanger && z < v.get(i).getZ() + rangeDanger) {
						dirX = -dirX;
						dirY = -dirY;
						dirZ = -dirZ;
					}
					dx -= alpha2 * ddx;
					dy -= alpha2 * ddy;
					dz -= alpha2 * ddz;
					break;
				case "Bee":
					// if a bee is within the bounding box of another bee, change its x, y and z direction
					if (x > v.get(i).getX() - rangeSep && x < v.get(i).getX() + rangeSep &&
							y > v.get(i).getY() - rangeSep && y < v.get(i).getY() + rangeSep &&
							z > v.get(i).getZ() - rangeSep && z < v.get(i).getZ() + rangeSep) {
						dirX = -dirX;
						dirY = -dirY;
						dirZ = -dirZ;
					}
					break;
				default:
					break;
				}
			}

			// remove the food if it is consumed
			if (idx >= 0) {
				v.remove(idx);
				idx = -1;
			}

			// compute the potential due to border limits
			getPotential(2, y, z);
			dx -= alpha3 * ddx;
			getPotential(-2, y, z);
			dx -= alpha3 * ddx;
			getPotential(x, 2, z);
			dy -= alpha3 * ddy;
			getPotential(x, -2, z);
			dy -= alpha3 * ddy;
			getPotential(x, y, 2);
			dz -= alpha3 * ddz;
			getPotential(x, y, -2);
			dz -= alpha3 * ddz;
			break;

		case "Spider":
			for (int i = 0; i < v.size(); i++)
			{
				// if the other creature is itself, do nothing
				if (v.get(i).getClassName().equals("Spider"))
					continue;

				// compute the potential and store the results to ddx, ddy and ddz
				getPotential(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ());

				// if the other creature is either food or bees, go for it
				switch(v.get(i).getClassName()) {
				case "Food":
					// remember the index of the consumed food, remove the food later
					if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < 0.1f)
						idx = i;
					dx += alpha4 * ddx;
					dy += alpha4 * ddy;
					dz += alpha4 * ddz;
					break;
				case "Bee":
					dx += alpha1 * ddx;
					dy += alpha1 * ddy;
					dz += alpha1 * ddz;
					break;
				default:
					break;
				}
			}

			// remove the consumed food
			if (idx >= 0) {
				v.remove(idx);
				idx = -1;
			}

			// compute the potential due to border limits
			getPotential(2, y, z);
			dx -= alpha3 * ddx;
			getPotential(-2, y, z);
			dx -= alpha3 * ddx;
			getPotential(x, 2, z);
			dy -= alpha3 * ddy;
			getPotential(x, -2, z);
			dy -= alpha3 * ddy;
			getPotential(x, y, 2);
			dz -= alpha3 * ddz;
			getPotential(x, y, -2);
			dz -= alpha3 * ddz;
			break;

		default:
			break;
		}
	}

	private void separation(Vector<Creature> v) {
		// boids are considered for all bees, and hence simply do nothing if this creature is not a bee
		if (!getClassName().equals("Bee")) return;

		// for any other bee within its horizon,
		// compute the potential and void collision
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i).getClassName().equals("Bee")) {
				if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < dist) {
					getPotential(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ());
					dx -= alpha5 * ddx;
					dy -= alpha5 * ddy;
					dz -= alpha5 * ddz;
				}
			}
		}
	}

	private void alignment(Vector<Creature> v) {
		// boids are considered for all bees, and hence simply do nothing if this creature is not a bee
		if (!getClassName().equals("Bee")) return;

		float tempDx = 0;
		float tempDy = 0;
		float tempDz = 0;
		int cnt = 0;

		// for any other bees within its horizon, compute their mean speed
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i).getClassName().equals("Bee")) {
				if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < dist) {
					tempDx += v.get(i).getDx();
					tempDy += v.get(i).getDy();
					tempDz += v.get(i).getDz();
					cnt++;
				}
			}
		}

		// compute the weighted average between the current position change and the mean speed
		dx = (1f - alpha8) * dx + alpha8 * alpha6 * tempDx / (float) cnt;
		dy = (1f - alpha8) * dy + alpha8 * alpha6 * tempDy / (float) cnt;
		dz = (1f - alpha8) * dz + alpha8 * alpha6 * tempDz / (float) cnt;
	}

	private void cohesion(Vector<Creature> v) {
		// boids are considered for all bees, and hence simply do nothing if this creature is not a bee
		if (!getClassName().equals("Bee")) return;

		// for any other bees within its horizon, compute their mean position
		float tempDx = 0;
		float tempDy = 0;
		float tempDz = 0;
		int cnt = 0;

		// compute the mean position
		for (int i = 0; i < v.size(); i++) {
			if (v.get(i).getClassName().equals("Bee")) {
				if (getMag(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ()) < dist) {
					getPotential(v.get(i).getX(), v.get(i).getY(), v.get(i).getZ());

					tempDx += ddx;
					tempDy += ddy;
					tempDz += ddz;
					cnt++;
				}
			}
		}

		// compute the potential due to cohesion		
		dx += alpha7 * tempDx / (float) cnt;
		dy += alpha7 * tempDy / (float) cnt;
		dz += alpha7 * tempDz / (float) cnt;
	}

	// compute the facing direction
	// angle1 is the one between the projection of the position change vector onto xz plane and z axis
	// angle2 is the one between the position change vector and y axis
	protected void setDirection() {
		float norm = (float) Math.sqrt(dx * dx + dz * dz);
		angle1 = (float) Math.toDegrees(Math.atan2(dx, dz));
		angle2 = (float) Math.toDegrees(Math.atan2(norm, dy)) - 90f;
	}

	private void getPotential(float x2, float y2, float z2) {
		// if this creature or that creature is out of space, do nothing
		// this is because we don't want to push creatures outwards
		if (x > 2 || x < -2 || y > 2 || y < -2 || z > 2 || z < -2)
			return;
		if (x2 > 2 || x2 < -2 || y2 > 2 || y2 < -2 || z2 > 2 || z2 < -2)
			return;

		// if this creature is within the bounding box of that creature, also do nothing
		if (x > x2 - rangeSep && x < x2 + rangeSep &&
				y > y2 - rangeSep && y < y2 + rangeSep &&
				z > z2 - rangeSep && z < z2 + rangeSep)
			return;

		// compute the magnitude, if there is no difference in position, do nothing
		//System.out.println(x2 + "," + y2 + "," + z2);
		float temp = getMag(x2, y2, z2);
		if (temp < 0.0001f) return;

		// scale results to make them more proper
		ddx = -2 * (x - x2) * (float) Math.exp(-temp);
		ddy = -2 * (y - y2) * (float) Math.exp(-temp);
		ddz = -2 * (z - z2) * (float) Math.exp(-temp);
		//System.out.println(ddx + "," + ddy + "," + ddz);

		ddx *= 100000f;
		ddy *= 100000f;
		ddz *= 100000f;

		// normalization
		temp = (float) Math.sqrt(ddx * ddx + ddy * ddy + ddz * ddz);
		ddx /= temp;
		ddy /= temp;
		ddz /= temp;
		//System.out.println(ddx + "," + ddy + "," + ddz);
	}

	// compute squared euclidean distance of two points
	private float getMag(float x2, float y2, float z2) {
		return (x - x2) * (x - x2) + (y - y2) * (y - y2) + (z- z2) * (z - z2);
	}
}
