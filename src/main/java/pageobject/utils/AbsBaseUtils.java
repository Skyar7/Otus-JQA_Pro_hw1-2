package pageobject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import waiters.Waiters;

public abstract class AbsBaseUtils extends ElementActions {
  protected Waiters waiters;
  protected Logger log = LogManager.getLogger(AbsBaseUtils.class);

  public AbsBaseUtils(WebDriver driver) {
    super(driver);
    this.waiters = new Waiters(driver);
  }
}