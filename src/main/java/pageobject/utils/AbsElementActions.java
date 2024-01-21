package pageobject.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public abstract class AbsElementActions {
  protected WebDriver driver;
  protected Actions actions;
  protected JavascriptExecutor js;

  public AbsElementActions(WebDriver driver) {
    this.driver = driver;
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