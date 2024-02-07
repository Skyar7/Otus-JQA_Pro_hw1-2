package otus.waiters;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiters {
  private WebDriver driver;
  private int timeoutSec = 5;

  public Waiters(WebDriver driver) {
    this.driver = driver;
  }

  public Waiters(WebDriver driver, int timeoutSec) {
    this.driver = driver;
    this.timeoutSec = timeoutSec;
  }

  public boolean waitForCondition(ExpectedCondition condition) {
    WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeoutSec);

    try {
      webDriverWait.until(condition);
      return true;
    } catch (TimeoutException ignored) {
      return false;
    }
  }

  public boolean waitForElementVisible(By by) {
    return waitForCondition(ExpectedConditions.visibilityOfElementLocated(by));
  }

  public boolean waitForElementVisible(WebElement element) {
    return waitForCondition(ExpectedConditions.visibilityOf(element));
  }

  public boolean waitForElementNotVisible(By by) {
    return waitForCondition(ExpectedConditions.invisibilityOfElementLocated(by));
  }
}