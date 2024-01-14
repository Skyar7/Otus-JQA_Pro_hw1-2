package factories.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class OperaConfigure implements IBrowserSettings {
  private String browserVersion = System.getProperty("browser.version");

  @Override
  public WebDriver configure() {
    OperaOptions operaOptions = new OperaOptions();

    operaOptions.addArguments("--homepage=about:blank");
    operaOptions.addArguments("--ignore-certificate-errors");
    operaOptions.addArguments("--start-maximized");

    WebDriverManager.operadriver().browserVersion(this.browserVersion).setup();

    return new OperaDriver(operaOptions);
  }
}