package extensions;

import annotations.Driver;
import factories.WebDriverFactory;
import listeners.WebDriverListener;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback {

    private WebDriver driver = null;
    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if(driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Class clazz = extensionContext.getTestInstance().getClass();
        List<Field> annotatedFields = getFields(Driver.class, clazz);

        EventFiringWebDriver eventFiringWebDriver = new WebDriverFactory().create();
        eventFiringWebDriver.register(new WebDriverListener());

        for(Field field: annotatedFields) {
            field.setAccessible(true);
            field.set(extensionContext.getTestInstance().get(), eventFiringWebDriver);
        }
    }

    private List<Field> getFields(Class<? extends Annotation> annotation, Class clazz) {
        return Arrays
                .stream(clazz.getFields())
                .filter((Field field) -> field.isAnnotationPresent(annotation)
                        && field.getType().getName().equals(WebDriver.class.getName()))
                .collect(Collectors.toList());
    }
}