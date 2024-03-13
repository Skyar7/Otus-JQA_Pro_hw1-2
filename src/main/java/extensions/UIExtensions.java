package extensions;

import annotations.Driver;
import factories.WebDriverFactory;
import listeners.WebDriverListener;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback {
  private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
  private String driverType = System.getProperty("driver.type");

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws IllegalAccessException, MalformedURLException {
    if (Objects.equals(this.driverType, "remote")) {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      String browserName = System.getProperty("browser.name").toLowerCase();
      String browserVersion = System.getProperty("browser.version");

      capabilities.setCapability(CapabilityType.BROWSER_NAME, browserName);
      capabilities.setCapability(CapabilityType.BROWSER_VERSION, browserVersion);
      capabilities.setCapability("enableVNC", true);

      if ("chrome".equals(browserName)) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", new HashMap<String, Object>() {{
            put("profile.managed_default_content_settings.javascript", 2); // Блокировка JavaScript
          }});
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
      }

      WebDriver remoteDriver = new RemoteWebDriver(URI.create(System.getProperty("remote.url")).toURL(), capabilities);
      driver.set(new EventFiringWebDriver(remoteDriver).register(new WebDriverListener()));
    } else {
      driver.set(new WebDriverFactory().create().register(new WebDriverListener()));
    }

    List<Field> fields = this.getAnnotatedFields(Driver.class, extensionContext);

    for (Field field : fields) {
      if (field.getType().getName().equals(WebDriver.class.getName())) {
        field.setAccessible(true);
        field.set(extensionContext.getTestInstance().get(), driver.get());
      }
    }
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    if (driver.get() != null) {
      driver.get().close();
      driver.get().quit();
    }
    driver.remove();
  }

  private List<Field> getAnnotatedFields(Class<? extends Annotation> annotation, ExtensionContext extensionContext) {
    Class<?> testClass = extensionContext.getTestClass().get();
    return Arrays.stream(testClass.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(annotation))
            .collect(Collectors.toList());
  }
}