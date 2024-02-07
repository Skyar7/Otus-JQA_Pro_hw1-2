package otus.factories.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxConfigure implements IBrowserSettings {
  private String browserVersion = System.getProperty("browser.version");

  @Override
  public WebDriver configure() {
    FirefoxOptions firefoxOptions = new FirefoxOptions();

    firefoxOptions.addArguments("--disable-notifications");
    firefoxOptions.addArguments("--start-maximized");

    WebDriverManager.firefoxdriver().browserVersion(this.browserVersion).setup();

    return new FirefoxDriver(firefoxOptions);
  }
}