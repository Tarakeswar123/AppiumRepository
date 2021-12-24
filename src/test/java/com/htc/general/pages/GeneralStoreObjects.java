package com.htc.general.pages;

import java.net.MalformedURLException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.htc.general.capabilities.GeneralStoreCapabilities;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;

public class GeneralStoreObjects extends GeneralStoreCapabilities
{
	AndroidDriver<AndroidElement> androidDriver;
	TouchAction touchAction;
	TapOptions tapOptions=new TapOptions();
	LongPressOptions longPressOptions=new LongPressOptions();
	ElementOption elementOption= new ElementOption();
	
	public void registrationPage() throws MalformedURLException 
	{
		androidDriver=setCapabilitesData("Android Emulator");
		String countryName="Brazil";
		String gender="Female";
		touchAction=new TouchAction<>(androidDriver);
		elementOption=new ElementOption();
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		AndroidElement dropDownElement=androidDriver.findElementById("android:id/text1");
		touchAction.tap(elementOption.element(dropDownElement)).perform();
		androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+countryName+"\"))");
		AndroidElement countryNameElement=androidDriver.findElementByXPath("//*[@text='"+countryName+"']");
		countryNameElement.click();
		//touchAction.tap(elementOption.element(countryNameElement)).perform();
		
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		AndroidElement nameElement=androidDriver.findElementByXPath("//*[contains(@text,'name')]");
		nameElement.sendKeys("Hello");
		androidDriver.hideKeyboard();
		
		AndroidElement genderElement=androidDriver.findElementByXPath("//*[contains(@text,'"+gender+"')]");
		touchAction.tap(elementOption.element(genderElement)).perform();
		
		AndroidElement buttonElement=androidDriver.findElementById("com.androidsample.generalstore:id/btnLetsShop");
		buttonElement.click();
		
		try
		{
			WebDriverWait webDriverWait= new WebDriverWait(androidDriver, 5);
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Products']")));
		}
		catch(NoSuchElementException noSuchElementException)
		{
			AndroidElement toastErrorElement=androidDriver.findElementByXPath("//android.widget.Toast[1]");
			String toastErrorMessage=toastErrorElement.getAttribute("name");
			Assert.assertEquals("Please enter your name", toastErrorMessage);
		}
	}
	
	public void selectTheProductAndAddToCart(List<String> productNames)
	{
		for(int productIndex=0;productIndex<productNames.size();productIndex++)
		{
			//Traversing to Display required product from Product List (List->One entire Product Element)
			androidDriver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\""+productNames.get(productIndex)+"\").instance(0))");
			androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<AndroidElement> addToCartList=androidDriver.findElementsById("com.androidsample.generalstore:id/productAddCart");
			for(int index=0;index<addToCartList.size();index++)
			{
				String data=androidDriver.findElementsById("com.androidsample.generalstore:id/productName").get(index).getText().trim();
				String productData=productNames.get(productIndex).trim();
				if(data.equalsIgnoreCase(productData))
				{
					System.out.println(data+" "+addToCartList.size());
					addToCartList.get(index).click();
				}
			}
			androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		//View Add to Cart Page
		AndroidElement viewCartElement=androidDriver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart");
		touchAction.tap(elementOption.element(viewCartElement)).perform();
	}
	
	public double getPriceInDouble(String priceData)
	{
		String data=priceData.substring(1);
		double price=Double.parseDouble(data);
		return price;
	}
	
	public void sumOfTotalInCart()
	{
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String total=androidDriver.findElementById("com.androidsample.generalstore:id/totalAmountLbl").getText();
		System.out.println(total);
		List<AndroidElement> productPricesElements=androidDriver.findElementsById("com.androidsample.generalstore:id/productPrice");
		int count=productPricesElements.size();
		System.out.println("Number Of Products in Cart:"+count);
		double sum=0;
		for(int index=0;index<count;index++)
		{
			String data=productPricesElements.get(index).getText();
			System.out.println(data);
			double productPrice=getPriceInDouble(data);
			sum=sum+productPrice;
		}
		double productPrice=getPriceInDouble(total);
		if(sum==productPrice)
		{
			System.out.println("Total Equal");
		}
	}
	
	public void checkOutPage()
	{
		AndroidElement checkBoxDataElement=androidDriver.findElementByClassName("android.widget.CheckBox");
		touchAction.tap(elementOption.element(checkBoxDataElement)).perform();
		
		AndroidElement termsAndConditionsDataElement=androidDriver.findElementByXPath("//*[contains(@text,'terms of conditions')]");
		touchAction.longPress(longPressOptions.withElement(elementOption.element(termsAndConditionsDataElement)).withDuration(Duration.ofSeconds(5))).release().perform();
		
		AndroidElement closeButtonOnTermsAndConditionsElement=androidDriver.findElementById("android:id/button1");
		closeButtonOnTermsAndConditionsElement.click();
		
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		AndroidElement proceedToCheckoutPageElement=androidDriver.findElementById("com.androidsample.generalstore:id/btnProceed");
		proceedToCheckoutPageElement.click();
	}
	
	public void navigateToWebBrowserView() throws InterruptedException
	{
		Thread.sleep(10000);
		Set<String> contextNames=androidDriver.getContextHandles();
		for(String contextName: contextNames)
		{
			System.out.println(contextName);
		}
		androidDriver.context((String) contextNames.toArray()[1]);
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		androidDriver.findElement(By.xpath("//input[contains(@type,'search')]")).sendKeys("Hello");
		androidDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		androidDriver.findElement(By.xpath("//input[contains(@type,'search')]")).sendKeys(Keys.ENTER);
		Thread.sleep(10000);
		//Coming Back From web view to our Native application, hitting the back button in mobile
		androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
	}
	
}
