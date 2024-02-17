package extensions;

import annotations.Driver;
import factories.WebDriverFactory;
import listeners.WebDriverListener;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback {
  private String driverType = System.getProperty("driver.type");
  private EventFiringWebDriver driver = null;

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws IllegalAccessException, MalformedURLException {

    if (Objects.equals(this.driverType, "remote")) {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability(CapabilityType.BROWSER_NAME, System.getProperty("browser.name").toLowerCase());
      capabilities.setCapability(CapabilityType.BROWSER_VERSION, System.getProperty("browser.version"));
      capabilities.setCapability("enableVNC", true);
      WebDriver remoteDriver = new RemoteWebDriver(URI.create(System.getProperty("remote.url")).toURL(), capabilities);
      this.driver = new EventFiringWebDriver(remoteDriver).register(new WebDriverListener());
    } else {
      this.driver = new WebDriverFactory().create().register(new WebDriverListener());
    }

    List<Field> fields = this.getAnnotatedFields(Driver.class, extensionContext);

    for (Field field : fields) {
      if (field.getType().getName().equals(WebDriver.class.getName())) {
        field.setAccessible(true);
        field.set(extensionContext.getTestInstance().get(), driver);
      }
    }
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }

  private List<Field> getAnnotatedFields(Class<? extends Annotation> annotation, ExtensionContext extensionContext) {
    Class<?> testClass = extensionContext.getTestClass().get();
    return Arrays.stream(testClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(annotation))
            .collect(Collectors.toList());
  }
}