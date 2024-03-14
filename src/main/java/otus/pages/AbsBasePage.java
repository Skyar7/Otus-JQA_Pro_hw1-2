package otus.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import otus.annotations.UrlPrefix;
import com.google.inject.Inject;
import otus.pageobject.AbsBaseUtils;
import otus.support.GuiceScoped;

public abstract class AbsBasePage<T extends AbsBasePage<T>> extends AbsBaseUtils {

  protected final static String BASE_URL = System.getProperty("base.url");
  protected String jivoChatIconLocator = "//jdiv[@class='iconWrap_f24a']";

  @Inject
  public AbsBasePage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public T openPage() {
    driver.get(BASE_URL + getUrlPrefix());
    return (T) this;
  }

  protected void closeCookiesMessage() {
    WebElement cookieMessageButton = fe(By.cssSelector(".sc-9a4spb-0.ckCZjI"));
    moveAndClick(cookieMessageButton);
  }

  private String getUrlPrefix() {
    UrlPrefix urlAnnotation = getClass().getAnnotation(UrlPrefix.class);
    return urlAnnotation.value().endsWith("/") ? urlAnnotation.value(): urlAnnotation.value() + "/";
  }
}