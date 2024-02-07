package otus.pageobject;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import otus.support.GuiceScoped;

import java.util.List;

public abstract class AbsElementActions {
  protected WebDriver driver;
  protected Actions actions;
  protected JavascriptExecutor js;
  protected GuiceScoped guiceScoped;

  @Inject
  public AbsElementActions(GuiceScoped guiceScoped) {
    this.guiceScoped = guiceScoped;
    this.driver = guiceScoped.getDriver();
    this.actions = new Actions(driver);
  }

  // checkstyle-plugin ругается на названия методов $ и $$.
  public WebElement fe(By by) {
    return driver.findElement(by);
  }

  public List<WebElement> fes(By by) {
    return driver.findElements(by);
  }

  public void moveToElement(WebElement element) {
    actions.moveToElement(element).perform();
  }

  public void moveAndClick(WebElement element) {
    actions.moveToElement(element);
    element.click();
  }
}