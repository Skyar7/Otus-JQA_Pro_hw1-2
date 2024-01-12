package factories;

import exceptions.BrowserNotSupportedException;
import factories.impl.ChromeConfigure;
import factories.impl.FirefoxConfigure;
import factories.impl.OperaConfigure;
import org.openqa.selenium.support.events.EventFiringWebDriver;


public class WebDriverFactory implements IFactory<EventFiringWebDriver> {

    private String browserName = System.getProperty("browser.name").toLowerCase();

    @Override
    public EventFiringWebDriver create() {
        switch (this.browserName) {
            case "chrome": {
                return new EventFiringWebDriver(new ChromeConfigure().configure());
            }
            case "opera": {
                return new EventFiringWebDriver(new OperaConfigure().configure());
            }
            case "firefox": {
                return new EventFiringWebDriver(new FirefoxConfigure().configure());
            }
            default: {
                throw new BrowserNotSupportedException(this.browserName);
            }
        }
    }
}