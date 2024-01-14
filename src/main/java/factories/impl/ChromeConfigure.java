package factories.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeConfigure implements IBrowserSettings {
  private String browserVersion = System.getProperty("browser.version");

  @Override
  public WebDriver configure() {
    ChromeOptions chromeOptions = new ChromeOptions();

    chromeOptions.addArguments("--homepage=about:blank");
    chromeOptions.addArguments("--ignore-certificate-errors");
    chromeOptions.addArguments("--start-maximized");

    WebDriverManager.chromedriver().browserVersion(this.browserVersion).setup();

    return new ChromeDriver(chromeOptions);
  }
}