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

public class Spider extends Creature {

	// respective radiuses of 5 parts of a leg
	final private float r1 = 0.03f;
	final private float r2 = 0.025f;
	final private float r3 = 0.02f;
	final private float r4 = 0.015f;
	final private float r5 = 0.01f;
	// respective lengths of 5 parts of the left first and right first legs
	final private float h11 = 0.15f;
	final private float h12 = 0.45f;
	final private float h13 = 0.6f;
	final private float h14 = 0.6f;
	final private float h15 = 0.23f;
	// respective lengths of 5 parts of the left second, right second, left fourth and right fourth legs
	final private float h21 = 0.1f;
	final private float h22 = 0.3f;
	final private float h23 = 0.4f;
	final private float h24 = 0.4f;
	final private float h25 = 0.15f;
	// respective lengths of 5 parts of the left third and right third legs
	final private float h31 = 0.06f;
	final private float h32 = 0.2f;
	final private float h33 = 0.26f;
	final private float h34 = 0.26f;
	final private float h35 = 0.1f;
	// respective radiuses of 2 parts of the pedipalps
	final private float r6 = 0.015f;
	final private float r7 = 0.015f;
	// respective lengths of 2 parts of the pedipalps
	final private float h6 = 0.25f;
	final private float h7 = 0.13f;

	// object list of the whole spider
	private int spider;
	// object lists of the thorax and abdomen
	private int thorax, abdomen;
	// object lists of the 5 parts of the left first leg
	private int l1Coxa, l1Femur, l1Tibia, l1Meta, l1Tarsi;
	// object lists of the 5 parts of the left second leg
	private int l2Coxa, l2Femur, l2Tibia, l2Meta, l2Tarsi;
	// object lists of the 5 parts of the left third leg
	private int l3Coxa, l3Femur, l3Tibia, l3Meta, l3Tarsi;
	// object lists of the 5 parts of the left fourth leg
	private int l4Coxa, l4Femur, l4Tibia, l4Meta, l4Tarsi;
	// object lists of the 5 parts of the right first leg
	private int r1Coxa, r1Femur, r1Tibia, r1Meta, r1Tarsi;
	// object lists of the 5 parts of the right second leg
	private int r2Coxa, r2Femur, r2Tibia, r2Meta, r2Tarsi;
	// object lists of the 5 parts of the right third leg
	private int r3Coxa, r3Femur, r3Tibia, r3Meta, r3Tarsi;
	// object lists of the 5 parts of the right fourth leg
	private int r4Coxa, r4Femur, r4Tibia, r4Meta, r4Tarsi;
	// object lists of the 4 parts of the two pedipalps
	private int l1Ped, l2Ped, r1Ped, r2Ped;

	// the angle of the leg about y axis
	private float legAngle;
	// the rotation speed of the leg about y axis
	private float legSpeed;
	// the rotation direction of the leg about y axis
	private int legDir;

	private GLUT glut = new GLUT();

	// constructor
	public Spider(float _x, float _y, float _z, float _vx, float _vy, float _vz) {
		super(_x, _y, _z, _vx, _vy, _vz);

		legAngle = 0;
		legSpeed = 0.5f;
		legDir = 1;

		// initialize all object lists to be 0
		spider = 0;
		thorax = abdomen = 0;
		l1Coxa = l1Femur = l1Tibia = l1Meta = l1Tarsi = 0;
		l2Coxa = l2Femur = l2Tibia = l2Meta = l2Tarsi = 0;
		l3Coxa = l3Femur = l3Tibia = l3Meta = l3Tarsi = 0;
		l4Coxa = l4Femur = l4Tibia = l4Meta = l4Tarsi = 0;
		r1Coxa = r1Femur = r1Tibia = r1Meta = r1Tarsi = 0;
		r2Coxa = r2Femur = r2Tibia = r2Meta = r2Tarsi = 0;
		r3Coxa = r3Femur = r3Tibia = r3Meta = r3Tarsi = 0;
		r4Coxa = r4Femur = r4Tibia = r4Meta = r4Tarsi = 0;
		l1Ped = l2Ped = r1Ped = r2Ped = 0;
	}

	// initialize the spider
	public void init (GL2 gl) {
		// create the thorax
		createThorax(gl);
		// create the abdomen
		createAbdomen(gl);
		// create the coxas of 8 legs
		createCoxa(gl);
		// create the femurs of 8 legs
		createFemur(gl);
		// create the tibias of 8 legs
		createTibia(gl);
		// create the metatibias of 8 legs
		createMeta(gl);
		// create the tarsis of 8 legs
		createTarsi(gl);
		// create the parts of the pedipalms connected to the head
		create1Ped(gl);
		// create the parts of the pedipalms far from the head
		create2Ped(gl);

		// create the spider object list
		spider = gl.glGenLists(1);
		gl.glNewList(spider, GL2.GL_COMPILE);
		constructDispList(gl);
		gl.glEndList();
	}

	// create the thorax
	private void createThorax(GL2 gl) {
		thorax = gl.glGenLists(1);
		gl.glNewList(thorax, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(0.8f, 0.65f, 0.9f);
		glut.glutSolidSphere(0.25, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the abdomen
	private void createAbdomen(GL2 gl) {
		abdomen = gl.glGenLists(1);
		gl.glNewList(abdomen, GL2.GL_COMPILE);
		gl.glPushMatrix();
		gl.glScalef(1.2f, 1.05f, 1.6f);
		glut.glutSolidSphere(0.25, 36, 18);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the coxas of 8 legs
	private void createCoxa(GL2 gl) {
		l1Coxa = gl.glGenLists(1);
		gl.glNewList(l1Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h11, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h11);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l2Coxa = gl.glGenLists(1);
		gl.glNewList(l2Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h21, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l3Coxa = gl.glGenLists(1);
		gl.glNewList(l3Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h31, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h31);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l4Coxa = gl.glGenLists(1);
		gl.glNewList(l4Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h21, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Coxa = gl.glGenLists(1);
		gl.glNewList(r1Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h11, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h11);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Coxa = gl.glGenLists(1);
		gl.glNewList(r2Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h21, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r3Coxa = gl.glGenLists(1);
		gl.glNewList(r3Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h31, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h31);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r4Coxa = gl.glGenLists(1);
		gl.glNewList(r4Coxa, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r1, h21, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		glut.glutSolidSphere(r1, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the femurs of 8 legs
	private void createFemur(GL2 gl) {
		l1Femur = gl.glGenLists(1);
		gl.glNewList(l1Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h12, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h12);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l2Femur = gl.glGenLists(1);
		gl.glNewList(l2Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h22, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l3Femur = gl.glGenLists(1);
		gl.glNewList(l3Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h32, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h32);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l4Femur = gl.glGenLists(1);
		gl.glNewList(l4Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h22, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Femur = gl.glGenLists(1);
		gl.glNewList(r1Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h12, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h12);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Femur = gl.glGenLists(1);
		gl.glNewList(r2Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h22, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r3Femur = gl.glGenLists(1);
		gl.glNewList(r3Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h32, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h32);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r4Femur = gl.glGenLists(1);
		gl.glNewList(r4Femur, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r2, h22, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		glut.glutSolidSphere(r2, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tibias of 8 legs
	private void createTibia(GL2 gl) {
		l1Tibia = gl.glGenLists(1);
		gl.glNewList(l1Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h13, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h13);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l2Tibia = gl.glGenLists(1);
		gl.glNewList(l2Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h23, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l3Tibia = gl.glGenLists(1);
		gl.glNewList(l3Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h33, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h33);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l4Tibia = gl.glGenLists(1);
		gl.glNewList(l4Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h23, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Tibia = gl.glGenLists(1);
		gl.glNewList(r1Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h13, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h13);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Tibia = gl.glGenLists(1);
		gl.glNewList(r2Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h23, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r3Tibia = gl.glGenLists(1);
		gl.glNewList(r3Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h33, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h33);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r4Tibia = gl.glGenLists(1);
		gl.glNewList(r4Tibia, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r3, h23, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		glut.glutSolidSphere(r3, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the metatibias of 8 legs
	private void createMeta(GL2 gl) {
		l1Meta = gl.glGenLists(1);
		gl.glNewList(l1Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h14, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h14);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l2Meta = gl.glGenLists(1);
		gl.glNewList(l2Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h24, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l3Meta = gl.glGenLists(1);
		gl.glNewList(l3Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h34, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h34);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l4Meta = gl.glGenLists(1);
		gl.glNewList(l4Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h24, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Meta = gl.glGenLists(1);
		gl.glNewList(r1Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h14, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h14);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Meta = gl.glGenLists(1);
		gl.glNewList(r2Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h24, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r3Meta = gl.glGenLists(1);
		gl.glNewList(r3Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h34, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h34);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r4Meta = gl.glGenLists(1);
		gl.glNewList(r4Meta, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r4, h24, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		glut.glutSolidSphere(r4, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the tarsis of 8 legs
	private void createTarsi(GL2 gl) {
		l1Tarsi = gl.glGenLists(1);
		gl.glNewList(l1Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h15, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h15);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l2Tarsi = gl.glGenLists(1);
		gl.glNewList(l2Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h25);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l3Tarsi = gl.glGenLists(1);
		gl.glNewList(l3Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h35, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h35);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		l4Tarsi = gl.glGenLists(1);
		gl.glNewList(l4Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h25);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Tarsi = gl.glGenLists(1);
		gl.glNewList(r1Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h15, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h15);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Tarsi = gl.glGenLists(1);
		gl.glNewList(r2Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h25);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r3Tarsi = gl.glGenLists(1);
		gl.glNewList(r3Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h35, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h35);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r4Tarsi = gl.glGenLists(1);
		gl.glNewList(r4Tarsi, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r5, h25, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h25);
		glut.glutSolidSphere(r5, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the parts of the pedipalms connected to the head
	private void create1Ped(GL2 gl) {
		l1Ped = gl.glGenLists(1);
		gl.glNewList(l1Ped, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r6, h6, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h6);
		glut.glutSolidSphere(r6, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r1Ped = gl.glGenLists(1);
		gl.glNewList(r1Ped, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r6, h6, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h6);
		glut.glutSolidSphere(r6, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// create the parts of the pedipalms far away from the head
	private void create2Ped(GL2 gl) {
		l2Ped = gl.glGenLists(1);
		gl.glNewList(l2Ped, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r7, h7, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h7);
		glut.glutSolidSphere(r7, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();

		r2Ped = gl.glGenLists(1);
		gl.glNewList(r2Ped, GL2.GL_COMPILE);
		glut.glutSolidCylinder(r7, h7, 36, 28);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h7);
		glut.glutSolidSphere(r7, 36, 28);
		gl.glPopMatrix();
		gl.glEndList();
	}

	// connect all body parts together hierarchically
	private void constructDispList(GL2 gl) {
		gl.glPushMatrix();

		// thorax is the top component
		gl.glPushMatrix();
		gl.glCallList(thorax);
		gl.glPopMatrix();

		// abdomen is connected to the thorax
		gl.glPushMatrix();
		gl.glCallList(abdomen);
		gl.glPopMatrix();

		// left first leg is connected to the thorax
		// and the 5 parts of the left first leg are connected hierarchically
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
		gl.glCallList(l1Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l1Tarsi);
		gl.glPopMatrix();

		// left second leg is connected to the thorax
		// and the 5 parts of the left second leg are connected hierarchically
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
		gl.glCallList(l2Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Tarsi);
		gl.glPopMatrix();

		// left third leg is connected to the thorax
		// and the 5 parts of the left third leg are connected hierarchically
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
		gl.glCallList(l3Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l3Tarsi);
		gl.glPopMatrix();

		// left fourth leg is connected to the thorax
		// and the 5 parts of the left fourth leg are connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(l4Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l4Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l4Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l4Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l4Tarsi);
		gl.glPopMatrix();

		// right first leg is connected to the thorax
		// and the 5 parts of the right first leg are connected hierarchically
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
		gl.glCallList(r1Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r1Tarsi);
		gl.glPopMatrix();

		// right second leg is connected to the thorax
		// and the 5 parts of the right second leg are connected hierarchically
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
		gl.glCallList(r2Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Tarsi);
		gl.glPopMatrix();

		// right third leg is connected to the thorax
		// and the 5 parts of the right third leg are connected hierarchically
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
		gl.glCallList(r3Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r3Tarsi);
		gl.glPopMatrix();

		// right fourth leg is connected to the thorax
		// and the 5 parts of the right fourth leg are connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(r4Coxa);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r4Femur);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r4Tibia);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r4Meta);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r4Tarsi);
		gl.glPopMatrix();

		// two pedipalms are connected to the thorax
		// and the 2 parts of the pedipalms are connected hierarchically
		gl.glPushMatrix();
		gl.glCallList(l1Ped);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(l2Ped);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r1Ped);
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glCallList(r2Ped);
		gl.glPopMatrix();

		gl.glPopMatrix();
	}

	// update the spider body parts once any one of them is changed
	public void update(GL2 gl, Vector<Creature> v) {
		// change its location
		super.translate(v);
		// rotate its legs along y axis
		moveLegs();
		// make the spider face its moving direction
		super.setDirection();

		gl.glNewList(spider, GL2.GL_COMPILE);
		gl.glPushMatrix();
		// translate the spider to the right location
		gl.glTranslatef(x, y, z);
		// rotate the spider to the right angle
		gl.glRotatef(angle1, 0, 1, 0);
		gl.glRotatef(angle2, 1, 0, 0);
		// scale the spider
		gl.glScalef(0.2f, 0.2f, 0.2f);
		gl.glPushAttrib(GL2.GL_CURRENT_BIT);
		gl.glColor3f(0.3f, 0.2f, 0.2f);
		gl.glCallList(thorax);

		// draw the abdomen
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -0.6f);
		gl.glRotatef(-10, 1, 0, 0);
		gl.glCallList(abdomen);
		gl.glPopMatrix();

		// draw the left first leg
		gl.glPushMatrix();
		gl.glTranslatef(0.1f, -0.1f, 0.1f);
		gl.glRotatef(15, 0, 1, 0);
		gl.glRotatef(legAngle, 0, 1, 0);
		// simulate the moving gesture of a spider
		if (legDir < 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(l1Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h11);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l1Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h12);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(l1Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h13);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(l1Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h14);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l1Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the left second leg
		gl.glPushMatrix();
		gl.glTranslatef(0.1f, -0.1f, 0);
		gl.glRotatef(45, 0, 1, 0);
		gl.glRotatef(-legAngle, 0, 1, 0);
		if (legDir > 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(l2Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l2Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(l2Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(l2Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l2Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the left third leg
		gl.glPushMatrix();
		gl.glTranslatef(0.1f, -0.1f, -0.1f);
		gl.glRotatef(130, 0, 1, 0);
		gl.glRotatef(legAngle, 0, 1, 0);
		if (legDir < 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(l3Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h31);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l3Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h32);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(l3Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h33);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(l3Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h34);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l3Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the left fourth leg
		gl.glPushMatrix();
		gl.glTranslatef(0.1f, -0.1f, -0.15f);
		gl.glRotatef(150, 0, 1, 0);
		gl.glRotatef(-legAngle, 0, 1, 0);
		if (legDir > 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(l4Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l4Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(l4Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(l4Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(l4Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the right first leg
		gl.glPushMatrix();
		gl.glTranslatef(-0.1f, -0.1f, 0.1f);
		gl.glRotatef(-15, 0, 1, 0);
		gl.glRotatef(legAngle, 0, 1, 0);
		if (legDir > 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(r1Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h11);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r1Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h12);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(r1Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h13);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(r1Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h14);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r1Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the right second leg
		gl.glPushMatrix();
		gl.glTranslatef(-0.1f, -0.1f, 0);
		gl.glRotatef(-45, 0, 1, 0);
		gl.glRotatef(-legAngle, 0, 1, 0);
		if (legDir < 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(r2Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r2Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(r2Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(r2Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r2Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the right third leg
		gl.glPushMatrix();
		gl.glTranslatef(-0.1f, -0.1f, -0.1f);
		gl.glRotatef(-130, 0, 1, 0);
		gl.glRotatef(legAngle, 0, 1, 0);
		if (legDir > 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(r3Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h31);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r3Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h32);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(r3Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h33);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(r3Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h34);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r3Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the right fourth leg
		gl.glPushMatrix();
		gl.glTranslatef(-0.1f, -0.1f, -0.15f);
		gl.glRotatef(-150, 0, 1, 0);
		gl.glRotatef(-legAngle, 0, 1, 0);
		if (legDir < 0) {
			if (legAngle <= 0)
				gl.glRotatef(-6 - legAngle, 1, 0, 0);
			else
				gl.glRotatef(-6 + legAngle, 1, 0, 0);
		}
		gl.glCallList(r4Coxa);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h21);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r4Femur);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h22);
		gl.glRotatef(40, 1, 0, 0);
		gl.glCallList(r4Tibia);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h23);
		gl.glRotatef(20, 1, 0, 0);
		gl.glCallList(r4Meta);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h24);
		gl.glRotatef(-30, 1, 0, 0);
		gl.glCallList(r4Tarsi);
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the left pedipalm
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);
		gl.glRotatef(-10, 1, 0, 0);
		gl.glRotatef(10, 0, 1, 0);
		gl.glCallList(l1Ped);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h6);
		gl.glRotatef(60, 1, 0, 0);
		gl.glCallList(l2Ped);
		gl.glPopMatrix();
		gl.glPopMatrix();

		// draw the right pedipalm
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, 0);
		gl.glRotatef(-10, 1, 0, 0);
		gl.glRotatef(-10, 0, 1, 0);
		gl.glCallList(r1Ped);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, h6);
		gl.glRotatef(60, 1, 0, 0);
		gl.glCallList(r2Ped);
		gl.glPopMatrix();
		gl.glPopMatrix();

		gl.glPopAttrib();
		gl.glPopMatrix();
		gl.glEndList();
	}

	// change the rotation angle about y axis
	private void moveLegs() {
		legAngle += legSpeed * legDir;
		if (legAngle > 6 || legAngle < -6)
			legDir = -legDir;
	}

	// draw the whole spider
	public void draw(GL2 gl) {
		gl.glPushMatrix();
		gl.glCallList(spider);
		gl.glPopMatrix();
	}

	// get the class name of the spider, i.e., "Spider"
	public String getClassName() {
		return "Spider";
	}
}
