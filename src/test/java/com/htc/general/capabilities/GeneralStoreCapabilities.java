package com.htc.general.capabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class GeneralStoreCapabilities 
{
	public static AndroidDriver<AndroidElement> setCapabilitesData(String emulatorType) throws MalformedURLException 
	{
		File appFolder=new File("src/test/resources");
		File appLocation=new File(appFolder,"General-Store.apk");
		DesiredCapabilities desiredCapabilities=new DesiredCapabilities();
		
		switch (emulatorType) {
		case "Android Emulator":
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel4XL");
			break;
		case "Real Emulator":
			desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Driver");
			break;
		default:
			System.out.println("Inavlid Selection of the Android Driver");
		}
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		//Used to set the time out for Android Driver
		desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
		desiredCapabilities.setCapability(MobileCapabilityType.APP, appLocation.getAbsolutePath());
		
		AndroidDriver<AndroidElement> androidDriver=new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
		return androidDriver;
	}
}
