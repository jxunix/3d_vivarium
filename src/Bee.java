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

public class Bee extends Creature {
	// the object list of the whole bee
	private int bee;
	// the object lists of the thorax, head and abdomen
	private int thorax, head, abdomen;
	// the object lists of the 4 parts of the left first leg
	private int l1Coxa, l1Femur, l1Tibia, l1Tarsi;
	// the object lists of the 4 parts of the left second leg
	private int l2Coxa, l2Femur, l2Tibia, l2Tarsi;
	// the object lists of the 4 parts of the left third leg
	private int l3Coxa, l3Femur, l3Tibia, l3Tarsi;
	// the object lists of the 4 parts of the right first leg
	private int r1Coxa, r1Femur, r1Tibia, r1Tarsi;
	// the object lists of the 4 parts of the right second leg
	private int r2Coxa, r2Femur, r2Tibia, r2Tarsi;
	// the object lists of the 4 parts of the right third leg
	private int r3Coxa, r3Femur, r3Tibia, r3Tarsi;
	// the object lists of the wings
	private int l1Wing, l2Wing, r1Wing, r2Wing;
	// the object lists of the antennas
	private int l1Ant, l2Ant, r1Ant, r2Ant;
	// the object lists of two eyes
	private int lEye, rEye;
	// the object list of the sting
	private int sting;

	// wing rotation speed
	private float wingSpeed;
	// wing rotation angle
	private float wingAngle;
	// wing rotation direction
	private int wingDir;

	private GLUT glut = new GLUT();

	// constructor
	public Bee(float _x, float _y, float _z, float _vx, float _vy, float _vz) {
		super(_x, _y, _z, _vx, _vy, _vz);

		// initialize all object lists to be 0
		bee = 0;
		thorax = head = abdomen = 0;
		l1Coxa = l1Femur = l1Tibia = l1Tarsi = 0;
		l2Coxa = l2Femur = l2Tibia = l2Tarsi = 0;
		l3Coxa = l3Femur = l3Tibia = l3Tarsi = 0;
		r1Coxa = r1Femur = r1Tibia = r1Tarsi = 0;
		r2Coxa = r2Femur = r2Tibia = r2Tarsi = 0;
		r3Coxa = r3Femur = r3Tibia = r3Tarsi = 0;
		l1Wing = l2Wing = r1Wing = r2Wing = 0;
		l1Ant = l2Ant = r1Ant = r2Ant = 0;
		lEye = rEye = 0;
		sting = 0;

		// initialize the wing rotation speed, angle and direction
		wingSpeed = 4;
		wingAngle = 0;
		wingDir = 1;
	}

	public void init (GL2 gl) {
		// create the thorax of a bee
		createThorax(gl);
		// create the head of a bee
		createHead(gl);
		// create the abdomen of a bee
		createAbdomen(gl);
		// create the coxa of the left first leg
		createL1Coxa(gl);
		// create the femur of the left first leg
		createL1Femur(gl);
		// create the tibia of the left first leg
		createL1Tibia(gl);
		// create the tarsi of the left first leg
		createL1Tarsi(gl);
		// create the coxa of the left second leg
		createL2Coxa(gl);
		// create the femur of the left second leg
		createL2Femur(gl);
		// create the tibia of the left second leg
		createL2Tibia(gl);
		// create the tarsi of the left second leg
		createL2Tarsi(gl);
		// create the coxa of the left third leg
		createL3Coxa(gl);
		// create the femur of the left third leg
		createL3Femur(gl);
		// create the tibia of the left third leg
		createL3Tibia(gl);
		// create the tarsi of the left third leg
		createL3Tarsi(gl);
		// create the coxa of the right first leg
		createR1Coxa(gl);
		// create the femur of the right first leg
		createR1Femur(gl);
		// create the tibia of the right fisrt leg
		createR1Tibia(gl);
		// create the tarsi of the right first leg
		createR1Tarsi(gl);
		// create the coxa of the right second leg
		createR2Coxa(gl);
		// create the femur of the right second leg
		createR2Femur(gl);
		// create the tibia of the right second leg
		createR2Tibia(gl);
		// create the tarsi of the right second leg
		createR2Tarsi(gl);
		// create the coxa of the right third leg
		createR3Coxa(gl);
		// create the femur of the right third leg
		createR3Femur(gl);
		// create the tibia of the right third leg
		createR3Tibia(gl);
		// create the tarsi of the right third leg
		createR3Tarsi(gl);
		// create the left forewing
		createL1Wing(gl);
		// create the left hindwing
		createL2Wing(gl);
		// create the right forewing
		createR1Wing(gl);
		// create the right hindwing
		createR2Wing(gl);
		// create the part of the left antenna connected to the head
		createL1Ant(gl);
		// create the part of the left antenna far away from the head
		createL2Ant(gl);
		// create the part of the right antenna connected to the head
		createR1Ant(gl);
		// create the part of the right antenna far away from the head
		createR2Ant(gl);
		// create the left eye
		createLEye(gl);
		// create the right eye
		createREye(gl);
		// create the sting
		createSting(gl);

		// initialize the object list bee
		bee = gl.glGenLists(1);
		gl.glNewList(bee, GL2.GL_COMPILE);
		constructDispList(gl);
		gl.glEndList();
	}

	// create the thorax of a bee
	private void createThorax(GL2 gl) {
		thorax = gl.glGenLists(1);
		gl.glNewList(thorax, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(1f, 0.95f, 1.2f);
		glut.glutSolidSphere(0.25, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the head of a bee
	private void createHead(GL2 gl) {
		head = gl.glGenLists(1);
		gl.glNewList(head, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.7f, 0.7f, 0.8f);
		glut.glutSolidSphere(0.25, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the abdomen of a bee
	private void createAbdomen(GL2 gl) {
		abdomen = gl.glGenLists(1);
		gl.glNewList(abdomen, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(1f, 0.9f, 1.6f);
		glut.glutSolidSphere(0.25, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the left first leg
	private void createL1Coxa(GL2 gl) {
		l1Coxa = gl.glGenLists(1);
		gl.glNewList(l1Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femur of the left first leg
	private void createL1Femur(GL2 gl) {
		l1Femur = gl.glGenLists(1);
		gl.glNewList(l1Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the left first leg
	private void createL1Tibia(GL2 gl) {
		l1Tibia = gl.glGenLists(1);
		gl.glNewList(l1Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the left first leg
	private void createL1Tarsi(GL2 gl) {
		l1Tarsi = gl.glGenLists(1);
		gl.glNewList(l1Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the left second leg
	private void createL2Coxa(GL2 gl) {
		l2Coxa = gl.glGenLists(1);
		gl.glNewList(l2Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femu of the left second leg
	private void createL2Femur(GL2 gl) {
		l2Femur = gl.glGenLists(1);
		gl.glNewList(l2Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the left second leg
	private void createL2Tibia(GL2 gl) {
		l2Tibia = gl.glGenLists(1);
		gl.glNewList(l2Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the left second leg
	private void createL2Tarsi(GL2 gl) {
		l2Tarsi = gl.glGenLists(1);
		gl.glNewList(l2Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the left third leg
	private void createL3Coxa(GL2 gl) {
		l3Coxa = gl.glGenLists(1);
		gl.glNewList(l3Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femur of the left third leg
	private void createL3Femur(GL2 gl) {
		l3Femur = gl.glGenLists(1);
		gl.glNewList(l3Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the left third leg
	private void createL3Tibia(GL2 gl) {
		l3Tibia = gl.glGenLists(1);
		gl.glNewList(l3Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the left third leg
	private void createL3Tarsi(GL2 gl) {
		l3Tarsi = gl.glGenLists(1);
		gl.glNewList(l3Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the right first leg
	private void createR1Coxa(GL2 gl) {
		r1Coxa = gl.glGenLists(1);
		gl.glNewList(r1Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femur of the right first leg
	private void createR1Femur(GL2 gl) {
		r1Femur = gl.glGenLists(1);
		gl.glNewList(r1Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the right fisrt leg
	private void createR1Tibia(GL2 gl) {
		r1Tibia = gl.glGenLists(1);
		gl.glNewList(r1Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the right first leg
	private void createR1Tarsi(GL2 gl) {
		r1Tarsi = gl.glGenLists(1);
		gl.glNewList(r1Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the right second leg
	private void createR2Coxa(GL2 gl) {
		r2Coxa = gl.glGenLists(1);
		gl.glNewList(r2Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femur of the right second leg
	private void createR2Femur(GL2 gl) {
		r2Femur = gl.glGenLists(1);
		gl.glNewList(r2Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the right second leg
	private void createR2Tibia(GL2 gl) {
		r2Tibia = gl.glGenLists(1);
		gl.glNewList(r2Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the right second leg
	private void createR2Tarsi(GL2 gl) {
		r2Tarsi = gl.glGenLists(1);
		gl.glNewList(r2Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxa of the right third leg
	private void createR3Coxa(GL2 gl) {
		r3Coxa = gl.glGenLists(1);
		gl.glNewList(r3Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.02, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.02, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femur of the right third leg
	private void createR3Femur(GL2 gl) {
		r3Femur = gl.glGenLists(1);
		gl.glNewList(r3Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.026, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.026, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibia of the right third leg
	private void createR3Tibia(GL2 gl) {
		r3Tibia = gl.glGenLists(1);
		gl.glNewList(r3Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.015, 0.4, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		glut.glutSolidSphere(0.015, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsi of the right third leg
	private void createR3Tarsi(GL2 gl) {
		r3Tarsi = gl.glGenLists(1);
		gl.glNewList(r3Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.1, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the part of the left antenna connected to the head
	private void createL1Ant(GL2 gl) {
		l1Ant = gl.glGenLists(1);
		gl.glNewList(l1Ant, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.013, 0.15, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.15f);
		glut.glutSolidSphere(0.013, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the part of the left antenna far away from the head
	private void createL2Ant(GL2 gl) {
		l2Ant = gl.glGenLists(1);
		gl.glNewList(l2Ant, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.013, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.013, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the part of the right antenna connected to the head
	private void createR1Ant(GL2 gl) {
		r1Ant = gl.glGenLists(1);
		gl.glNewList(r1Ant, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.013, 0.15, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.15f);
		glut.glutSolidSphere(0.013, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the part of the right antenna far away from the head
	private void createR2Ant(GL2 gl) {
		r2Ant = gl.glGenLists(1);
		gl.glNewList(r2Ant, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.013, 0.25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		glut.glutSolidSphere(0.013, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the left forewing
	private void createL1Wing(GL2 gl) {
		l1Wing = gl.glGenLists(1);
		gl.glNewList(l1Wing, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.09f, 2f);
		glut.glutSolidSphere(0.3, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the left hindwing
	private void createL2Wing(GL2 gl) {
		l2Wing = gl.glGenLists(1);
		gl.glNewList(l2Wing, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.09f, 2f);
		glut.glutSolidSphere(0.2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the right forewing
	private void createR1Wing(GL2 gl) {
		r1Wing = gl.glGenLists(1);
		gl.glNewList(r1Wing, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.09f, 2f);
		glut.glutSolidSphere(0.3, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the right hindwing
	private void createR2Wing(GL2 gl) {
		r2Wing = gl.glGenLists(1);
		gl.glNewList(r2Wing, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.09f, 2f);
		glut.glutSolidSphere(0.2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the left eye
	private void createLEye(GL2 gl) {
		lEye = gl.glGenLists(1);
		gl.glNewList(lEye, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.7f, 1.2f);
		glut.glutSolidSphere(0.1, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the right eye
	private void createREye(GL2 gl) {
		rEye = gl.glGenLists(1);
		gl.glNewList(rEye, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.7f, 1.2f);
		glut.glutSolidSphere(0.1, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the sting
	private void createSting(GL2 gl) {
		sting = gl.glGenLists(1);
		gl.glNewList(sting, GL2.GL_COMPILE);
		glut.glutSolidCylinder(0.01, 0.2, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.2f);
		glut.glutSolidSphere(0.01, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// connect all parts of a bee hierarchically
	private void constructDispList(GL2 gl) {
		gl.glPushMatrix();

		// thorax is the top component
		gl.glPushMatrix();
		gl.glCallList(thorax);
		gl.glPopMatrix();

		// head is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(head);
		gl.glPopMatrix();

		// abdomen is conencted to the thorax
		gl.glPushMatrix();
		gl.glCallList(abdomen);
		gl.glPopMatrix();

		// the left first leg is connected to the thorax
		// and the 5 parts of the left first leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(l1Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l1Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l1Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l1Tarsi);
		gl.glPopMatrix();

		// the left second leg is connected to the thorax
		// and the 5 parts of the left second leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(l2Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Tarsi);
		gl.glPopMatrix();

		// the left third leg is connected to the thorax
		// and the 5 parts of the left third leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(l3Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l3Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l3Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l3Tarsi);
		gl.glPopMatrix();

		// the right first leg is connected to the thorax
		// and the 5 parts of the right first leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(r1Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r1Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r1Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r1Tarsi);
		gl.glPopMatrix();

		// the right second leg is connected to the thorax
		// and the 5 parts of the right second leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(r2Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Tarsi);
		gl.glPopMatrix();

		// the right third leg is connected to the thorax
		// and the 5 parts of the right third leg is connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(r3Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r3Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r3Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r3Tarsi);
		gl.glPopMatrix();

		// the left forewing is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(l1Wing);
		gl.glPopMatrix();

		// the left hindwing is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(l2Wing);
		gl.glPopMatrix();

		// the right forewing is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(r1Wing);
		gl.glPopMatrix();

		// the right hindwing is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(r2Wing);
		gl.glPopMatrix();

		// the left antenna is connected to the head
		gl.glPushMatrix();
		gl.glCallList(l1Ant);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Ant);
		gl.glPopMatrix();

		// the right antenna is connected to the head
		gl.glPushMatrix();
		gl.glCallList(r1Ant);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Ant);
		gl.glPopMatrix();

		// the left eye is connected to the head
		gl.glPushMatrix();
		gl.glCallList(lEye);
		gl.glPopMatrix();

		// the right eye is connected to the head
		gl.glPushMatrix();
		gl.glCallList(rEye);
		gl.glPopMatrix();

		// the sting is connected to the abdomen
		gl.glPushMatrix();
		gl.glCallList(sting);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	public void update(GL2 gl, Vector<Creature> v) {
		// change the location of the bee
		super.translate(v);
		// flap the wings of a bee
		flap_wings();
		// make the bee face the moving direction
		super.setDirection();

		gl.glNewList(bee, GL2.GL_COMPILE);
		gl.glPushMatrix();
		// translate the bee to the right position
		gl.glTranslatef(x, y, z);
		// rotate the bee to the right angle
		gl.glRotatef(angle1, 0, 1, 0);
		gl.glRotatef(angle2, 1, 0, 0);
		gl.glScalef(0.12f, 0.12f, 0.12f);
		gl.glPushAttrib(GL2.GL_CURRENT_BIT);
		gl.glColor3f(0.8f, 0.5f, 0.2f);
		gl.glCallList(thorax);

		// make antennas and eyes connected to the head
		// and head connected to the thorax
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.45f);
		gl.glRotatef(60, 1, 0, 0);
		gl.glCallList(head);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0.08f, 0.1f);
		gl.glRotatef(-100, 1, 0, 0);
		gl.glRotatef(30, 0, 1, 0);
		gl.glCallList(l1Ant);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.15f);
		gl.glRotatef(60, 1, 0, 0);
		gl.glRotatef(30, 0, 1, 0);
		gl.glCallList(l2Ant);
		gl.glPopMatrix();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0, 0.08f, 0.1f);
		gl.glRotatef(-100, 1, 0, 0);
		gl.glRotatef(-30, 0, 1, 0);
		gl.glCallList(r1Ant);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.15f);
		gl.glRotatef(60, 1, 0, 0);
		gl.glRotatef(-30, 0, 1, 0);
		gl.glCallList(r2Ant);
		gl.glPopMatrix();
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(0.13f, 0, -0.05f);
		gl.glRotatef(15, 0, 1, 0);
		gl.glCallList(lEye);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glTranslatef(-0.13f, 0, -0.05f);
		gl.glRotatef(-15, 0, 1, 0);
		gl.glCallList(rEye);
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make abdomen connected to the thorax
		// and sting connected to the abdomen
		gl.glPushMatrix();
		gl.glRotatef(-wingAngle / 4, 1, 0, 0);
		gl.glTranslatef(0, 0, -0.65f);
		gl.glRotatef(-15, 1, 0, 0);
		gl.glCallList(abdomen);
		gl.glPushMatrix();
		gl.glRotatef(-wingAngle / 4, 1, 0, 0);
		gl.glTranslatef(0, 0, -0.5f);
		gl.glCallList(sting);
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make left first leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(0.2f, -0.1f, 0.1f);
		gl.glRotatef(60, 0, 1, 0);
		gl.glCallList(l1Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l1Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(l1Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l1Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make left second leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(0.22f, -0.1f, 0f);
		gl.glRotatef(90, 0, 1, 0);
		gl.glCallList(l2Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l2Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(l2Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l2Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make left third leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(0.2f, -0.1f, -0.1f);
		gl.glRotatef(140, 0, 1, 0);
		gl.glCallList(l3Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l3Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(l3Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(l3Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make right first leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(-0.2f, -0.1f, 0.1f);
		gl.glRotatef(-60, 0, 1, 0);
		gl.glCallList(r1Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r1Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(r1Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r1Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make right second leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(-0.22f, -0.1f, 0f);
		gl.glRotatef(-90, 0, 1, 0);
		gl.glCallList(r2Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r2Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(r2Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r2Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make right third leg connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glTranslatef(-0.2f, -0.1f, -0.1f);
		gl.glRotatef(-140, 0, 1, 0);
		gl.glCallList(r3Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.1f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r3Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.25f);
		gl.glRotatef(120, 1, 0, 0);
		gl.glCallList(r3Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0.4f);
		gl.glRotatef(-60, 1, 0, 0);
		gl.glCallList(r3Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// make left wings connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glRotatef(wingAngle, 0, 0, 1);
		gl.glTranslatef(0.4f, 0.3f, -0.35f);
		gl.glRotatef(-30, 0, 1, 0);
		gl.glRotatef(25, 1, 0, 0);
		gl.glRotatef(20, 0, 0, 1);
		gl.glCallList(l1Wing);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(wingAngle, 0, 0, 1);
		gl.glTranslatef(0.25f, 0.2f, -0.4f);
		gl.glRotatef(-10, 0, 1, 0);
		gl.glRotatef(15, 1, 0, 0);
		gl.glRotatef(18, 0, 0, 1);
		gl.glCallList(l2Wing);
		gl.glPopMatrix();

		// make right wings connected to the thorax hierarchically
		gl.glPushMatrix();
		gl.glRotatef(-wingAngle, 0, 0, 1);
		gl.glTranslatef(-0.4f, 0.3f, -0.35f);
		gl.glRotatef(30, 0, 1, 0);
		gl.glRotatef(25, 1, 0, 0);
		gl.glRotatef(-20, 0, 0, 1);
		gl.glCallList(r1Wing);
		gl.glPopMatrix();

		gl.glPushMatrix();
		gl.glRotatef(-wingAngle, 0, 0, 1);
		gl.glTranslatef(-0.25f, 0.2f, -0.4f);
		gl.glRotatef(10, 0, 1, 0);
		gl.glRotatef(15, 1, 0, 0);
		gl.glRotatef(-18, 0, 0, 1);
		gl.glCallList(r2Wing);
		gl.glPopMatrix();

		gl.glPopAttrib();
		gl.glPopMatrix();
		gl.glEndList();
	}

	// flap the wing of a bee
	private void flap_wings() {
		wingAngle += wingSpeed * wingDir;
		if (wingAngle > 25 || wingAngle < -15)
			wingDir = -wingDir;
	}

	// draw the whole bee
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(bee);
		gl.glPopMatrix();
	}

	// return the class name of this bee, i.e., "Bee"
	public String getClassName() {
		return "Bee";
	}
}