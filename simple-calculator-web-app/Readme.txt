Running Selenium way:
@Test
	public void itShouldReturnHomePageSeleniumWay() throws InterruptedException {
		File pathToBinary = new File("C:\\Utils\\Mozilla Firefox\\firefox.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		FirefoxDriver driver = new FirefoxDriver(ffBinary,firefoxProfile);
		//WebDriver driver = new FirefoxDriver();
		driver.get(getDefaultBaseUrl());
		WebElement element = driver.findElement(id("home-page-heder"));
		Thread.sleep(20000);
		assertEquals("Hello World", element.getText());
	}
	
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>2.52.0</version>
		</dependency>
