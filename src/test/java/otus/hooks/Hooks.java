package otus.hooks;

import com.google.inject.Inject;
import io.cucumber.java.After;
import otus.support.GuiceScoped;

public class Hooks {
  @Inject
  private GuiceScoped guiceScoped;

  @After
  public void afterScenario() {
    if (guiceScoped.getDriver() != null) {
      guiceScoped.getDriver().close();
      guiceScoped.getDriver().quit();
    }
  }
}