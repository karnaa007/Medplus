package test;

import org.testng.annotations.Test;

import page.MartLocationandLoginPage;

public class loacationAndLoginTest extends MartLocationandLoginPage {
  
  @Test
  public void locatinoTest() {
	  	try {
	  LocationPage();
	} catch (InterruptedException e) {
	  e.printStackTrace();
	}
  }	  
	  	@Test
	  public void loginTest() {
	  }
  }
