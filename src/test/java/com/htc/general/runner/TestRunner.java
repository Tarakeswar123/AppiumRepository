package com.htc.general.runner;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.htc.general.pages.GeneralStoreObjects;

public class TestRunner 
{
	@Test
	public void runTest() throws MalformedURLException, InterruptedException 
	{
		GeneralStoreObjects generalStoreObjects=new GeneralStoreObjects();
		generalStoreObjects.registrationPage();
		List<String> productNames=new ArrayList<String>();
		productNames.add("Jordan Lift Off");
		productNames.add("LeBron Soldier 12 ");
		generalStoreObjects.selectTheProductAndAddToCart(productNames);
		generalStoreObjects.sumOfTotalInCart();
		generalStoreObjects.checkOutPage();
		generalStoreObjects.navigateToWebBrowserView();
	}
}

