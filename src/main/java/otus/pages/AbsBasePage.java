package otus.pages;

import otus.annotations.UrlPrefix;
import com.google.inject.Inject;
import otus.pageobject.AbsBaseUtils;
import otus.support.GuiceScoped;

public abstract class AbsBasePage<T extends AbsBasePage<T>> extends AbsBaseUtils {

  protected final static String BASE_URL = System.getProperty("base.url");

  @Inject
  public AbsBasePage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public T openPage() {
    driver.get(BASE_URL + getUrlPrefix());
    return (T) this;
  }

  private String getUrlPrefix() {
    UrlPrefix urlAnnotation = getClass().getAnnotation(UrlPrefix.class);
    return urlAnnotation.value().endsWith("/") ? urlAnnotation.value(): urlAnnotation.value() + "/";
  }
}