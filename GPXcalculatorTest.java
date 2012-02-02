/**
 * @author Sriram Radhakrishnan
 * 
 * CIS350 Homework 2
 * February 2, 2012
 * 
 */


package edu.upenn.cis350.gpx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class GPXcalculatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNullTrack() {
		try {
			assertEquals("Invalid Return for Null Input", -1, GPXcalculator.calculateDistanceTraveled(null), 0);
		} catch (Exception e) {
			fail("Should not throw exception for null input");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEmptyTrack() {
		try {
			assertEquals("Invalid return for null trkseg list", -1, GPXcalculator.calculateDistanceTraveled(new GPXtrk("test", null)), 0);
			assertEquals("Invalid return for empty trkseg list", -1, GPXcalculator.calculateDistanceTraveled(new GPXtrk("test", new ArrayList<GPXtrkseg>())), 0);
		} catch (Exception e) {
			fail("Should not throw exception for null/empty trkseg list");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidSegments() {
		try {
			
			ArrayList<GPXtrkseg> a;
			GPXtrk g;
			ArrayList<GPXtrkpt> pts;
						
			//Null GPXtrkseg
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			a.add(null);
			assertEquals("Invalid return for null GPXtrkseg", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
		
			//Empty GPXtrkseg
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for empty GPXtrkseg", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//Single pt. GPXtrkseg
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(45.54, -75.50, new Date()));
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for single pt. GPXtrkseg", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//GPXtrkseg with null pt.
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(null);
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with null pt.", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//GPXtrkseg with null pt. (with another correct pt)
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(45.54, -75.50, new Date()));
			pts.add(null);
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with null pt.", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
		} catch (Exception e) {
			fail("Should not throw exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInvalidPoints () {
		try {
			
			ArrayList<GPXtrkseg> a;
			GPXtrk g;
			ArrayList<GPXtrkpt> pts;
						
			
			//GPXtrkseg with lat > 90 pt. (only element)
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(100, -75.50, new Date()));
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with pt. with lat>90", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//GPXtrkseg with lat < -90 pt. (only element)
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(-100, -75.50, new Date()));
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with pt. with lat<-90", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//GPXtrkseg with long > 180 pt. (only element)
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(50, 200, new Date()));
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with pt. with long>180", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
			//GPXtrkseg with long < -180 pt. (only element)
			a = new ArrayList<GPXtrkseg>();
			g = new GPXtrk("test", a);
			pts = new ArrayList<GPXtrkpt>();
			pts.add(new GPXtrkpt(50, -200, new Date()));
			a.add(new GPXtrkseg(pts));
			assertEquals("Invalid return for GPXtrkseg with pt. with long<-180", 0, GPXcalculator.calculateDistanceTraveled(g), 0);
			
		} catch (Exception e) {
			fail("Should not throw exception");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCorrectFunctionality () {
		try {
			ArrayList<GPXtrkseg> a = new ArrayList<GPXtrkseg>();
			GPXtrk g = new GPXtrk("test", a);

			ArrayList<GPXtrkpt> ptsa = new ArrayList<GPXtrkpt>();
			ArrayList<GPXtrkpt> ptsb = new ArrayList<GPXtrkpt>();
			
			ptsa.add(new GPXtrkpt(50, 70, new Date()));
			ptsa.add(new GPXtrkpt(60, 60, new Date()));
			ptsa.add(new GPXtrkpt(30, 25, new Date()));
			
			a.add(new GPXtrkseg(ptsa));
			assertEquals("Answer did not match expected", 60.2399, GPXcalculator.calculateDistanceTraveled(g), 0.0001);
			
			ptsb.add(new GPXtrkpt(-25, 50, new Date()));
			ptsb.add(new GPXtrkpt(-75, -25, new Date()));
			
			a.add(new GPXtrkseg(ptsb));
			
			assertEquals("Answer did not match expected", 150.3786, GPXcalculator.calculateDistanceTraveled(g), 0.0001);
			
		} catch (Exception e) {
			fail("Shouldn't through exception here");
			e.printStackTrace();
		}
	}
	
	
	

}
