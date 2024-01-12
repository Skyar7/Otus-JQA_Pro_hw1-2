package pageobject.pages;

import org.openqa.selenium.WebDriver;
import pageobject.utils.AbsBaseUtils;

public abstract class AbsBasePage<T> extends AbsBaseUtils {

    protected final static String BASE_URL = System.getProperty("base.url");

    public AbsBasePage(WebDriver driver) {
        super(driver);
    }

    public T openPage(String path) {
        driver.get(BASE_URL + path);

        return (T)this;
    }
}