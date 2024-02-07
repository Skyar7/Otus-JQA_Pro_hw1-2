package otus.steps;

import com.google.inject.Inject;
import otus.factories.WebDriverFactory;
import io.cucumber.java.ru.Пусть;
import otus.listeners.WebDriverListener;
import otus.support.GuiceScoped;

public class CommonSteps {

  @Inject
  public GuiceScoped guiceScoped;

  @Inject
  private WebDriverFactory webDriverFactory;

  @Пусть("Используется браузер {string}")
  public void browserSelect(String browser) {
    webDriverFactory.setBrowserName(browser.toLowerCase());
    guiceScoped.setDriver(webDriverFactory.create().register(new WebDriverListener()));
  }
}