package otus.factories;

import com.google.inject.Inject;
import otus.exceptions.BrowserNotSupportedException;
import otus.factories.impl.ChromeConfigure;
import otus.factories.impl.FirefoxConfigure;
import otus.factories.impl.OperaConfigure;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class WebDriverFactory implements IFactory<EventFiringWebDriver> {

  private String browserName = System.getProperty("browser.name").toLowerCase();

  @Inject
  public WebDriverFactory() {
  }

  public void setBrowserName(String browserName) {
    this.browserName = browserName;
  }

  public String getBrowserName() {
    return browserName;
  }

  @Override
  public EventFiringWebDriver create() {
    switch (this.browserName) {
      case "chrome": {
        return new EventFiringWebDriver(new ChromeConfigure().configure());
      }
      case "opera": {
        return new EventFiringWebDriver(new OperaConfigure().configure());
      }
      case "firefox": {
        return new EventFiringWebDriver(new FirefoxConfigure().configure());
      }
      default: {
        throw new BrowserNotSupportedException(this.browserName);
      }
    }
  }
}