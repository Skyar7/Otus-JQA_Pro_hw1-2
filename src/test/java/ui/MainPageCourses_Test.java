package ui;

import annotations.Driver;
import extensions.UIExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pageobject.pages.MainPage;

@ExtendWith(UIExtensions.class)
public class MainPageCourses_Test {

  @Driver
  private WebDriver driver;

  @Test
  public void choiceCourseByNameAndCheckItDataTest() {
    new MainPage(driver)
            .openPage()
            .coursesNamesFilter("Специализация")
            .checkFilteredCourseNameAndDescriptionData();
  }

  @Test
  public void choiceEarliestCourseAndCheckDateTest() {
    new MainPage(driver)
            .openPage()
            .choiceEarliestCourse()
            .checkChosenCourseDate();
  }

  @Test
  public void choiceLatestCourseAndCheckDateTest() {
    new MainPage(driver)
            .openPage()
            .choiceLatestCourse()
            .checkChosenCourseDate();
  }
}