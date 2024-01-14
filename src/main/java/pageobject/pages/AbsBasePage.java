package pageobject.pages;

import annotations.UrlPrefix;
import org.openqa.selenium.WebDriver;
import pageobject.utils.AbsBaseUtils;

public abstract class AbsBasePage<T extends AbsBasePage<T>> extends AbsBaseUtils {

  protected final static String BASE_URL = System.getProperty("base.url");

  public AbsBasePage(WebDriver driver) {
    super(driver);
  }

  public T openPage() {
    driver.get(BASE_URL + getUrlPrefix());
    return (T) this;
  }

  private String getUrlPrefix() {
    UrlPrefix urlAnnotation = getClass().getAnnotation(UrlPrefix.class);
    return urlAnnotation.value();
  }
}