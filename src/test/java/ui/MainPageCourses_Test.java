package ui;

import annotations.Driver;
import extensions.UIExtensions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(UIExtensions.class)
public class MainPageCourses_Test {

    @Driver
    private WebDriver driver;

}