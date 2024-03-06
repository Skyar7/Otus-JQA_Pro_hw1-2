package ui;

import annotations.Driver;
import extensions.UIExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pageobject.pages.MainPage;
import pageobject.pages.PreparatoryCoursesPage;

@ExtendWith(UIExtensions.class)
public class CoursesPages_Test {

  @Driver
  private WebDriver driver;

  @Test
  public void choiceCourseByNameAndCheckItDataTest() {
    new MainPage(driver)
            .openPage()
            .coursesNamesFilter("Специализация С++")
            .checkFilteredCourseNameAndDescriptionData();
  }

  @Test
  public void choiceEarliestCourseAndCheckDateTest() {
    new MainPage(driver)
            .openPage()
            .choiceEarliestCourse()
            .checkEarliestCourseDateOnTileAndOnPage();
  }

  @Test
  public void choiceLatestCourseAndCheckDateTest() {
    new MainPage(driver)
            .openPage()
            .choiceLatestCourse()
            .checkLatestCourseDateOnTileAndOnPage();
  }

  @Test
  public void findMainPageRequiredOrLaterDateCourseTest() {
    new MainPage(driver)
            .openPage()
            .findRequiredOrLaterDateCourse("11.03.2024");
  }

  @Test
  public void findPreparatoryPageLowestAndHighestPriceCoursesTest() {
    new PreparatoryCoursesPage(driver)
            .openPage()
            .findLowestAndHighestPriceCourses();
  }
}