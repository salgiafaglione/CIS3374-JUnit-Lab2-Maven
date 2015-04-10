package com.lab2.transit.src.test.java;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.lab2.transit.FareCalculator;

@RunWith(Parameterized.class)
public class FareCalculatorTest {

	private static final double DELTA = 1e-15;
	private double expected;
	private int age;
	private String time;
	private boolean isHoliday;
	
	public FareCalculatorTest(double expected, int age, String time, boolean isHoliday){
		this.expected = expected;
		this.age = age;
		this.time = time;
		this.isHoliday = isHoliday;
	}
	
	@Parameters
	public static Collection<Object[]> testParams(){
		return Arrays.asList(new Object[][]{
				
			//Age tests
			{2.5, 6, "10:00", false}, //on, out point for lower age boundary
			{0.0, 5, "6:00", false}, //off, in point for lower age boundary
			{0.0, 65, "11:00", false}, //on, in point for upper age boundary
			{2.5, 64, "12:00", false}, //off, out point for upper age boundary
			{2.5, 5, "7:10", false}, //off, in point for lower age boundary
			{2.5, 65, "8:45", false}, //on, in point for upper age boundary
			
			
			//Time tests 
			{0.0, 76, "9:01", false}, //on, in point for lower time boundary
			{2.5, 3, "9:00", false}, //off, out point for lower time boundary
			{0.0, 71, "6:59", false}, //on, in point for upper time boundary
			{2.5, 70, "7:00", false}, //off, out point for upper time boundary
			
			//Holiday tests
			{2.5, 6, "7:30", true},//is a holiday, on point for lower age boundary
			{0.0, 5, "8:00", true},//is a holiday, off point for lower age boundary
			{0.0, 65, "8:30", true},//is a holiday, on point for upper age boundary
			{2.5, 64, "7:15", true},//is a holiday, on point for upper age boundary
			
		});
	}
	
	@Test
	public void testCalculatedFare() {
		assertEquals(expected, FareCalculator.calculateFare(age, time, isHoliday), DELTA);
	}

}
