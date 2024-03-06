package otus.steps;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import otus.factories.WebDriverFactory;
import io.cucumber.java.ru.Пусть;
import otus.listeners.WebDriverListener;
import otus.support.GuiceScoped;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Objects;

public class CommonSteps {

  private String driverType = System.getProperty("driver.type");
  private EventFiringWebDriver driver = null;

  @Inject
  public GuiceScoped guiceScoped;

  @Inject
  private WebDriverFactory webDriverFactory;

  @Пусть("Используется браузер {string}")
  public void browserSelect(String browser) throws MalformedURLException {
    webDriverFactory.setBrowserName(browser.toLowerCase());

    if (Objects.equals(this.driverType, "remote")) {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.BROWSER_NAME, webDriverFactory.getBrowserName());
      capabilities.setCapability(CapabilityType.BROWSER_VERSION, System.getProperty("browser.version"));
      capabilities.setCapability("enableVNC", true);
      WebDriver remoteDriver = new RemoteWebDriver(URI.create(System.getProperty("remote.url")).toURL(), capabilities);
      this.driver = new EventFiringWebDriver(remoteDriver).register(new WebDriverListener());
    } else {
      this.driver = new WebDriverFactory().create().register(new WebDriverListener());
    }
    guiceScoped.setDriver(this.driver);
  }
}