package pageobject.utils;

import org.openqa.selenium.WebDriver;
import waiters.Waiters;

public abstract class AbsBaseUtils extends ElementActions {
    protected Waiters waiters;

    public AbsBaseUtils(WebDriver driver) {
        super(driver);
        this.waiters = new Waiters(driver);
    }
}