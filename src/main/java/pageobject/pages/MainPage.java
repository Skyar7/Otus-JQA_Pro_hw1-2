package pageobject.pages;

import annotations.UrlPrefix;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UrlPrefix("/")
public class MainPage extends AbsBasePage<MainPage> {

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public MainPage coursesNamesFilter(String requiredCourseName) {
    String requiredCourseNameSelector = "img[alt*='%s'][class*='sc-1ftuaec-4']";
    List<WebElement> result = fes(By.cssSelector(String.format(requiredCourseNameSelector, requiredCourseName)));
    log.info(String.format("Найдено курсов %d, по запросу %s", result.size(), requiredCourseName));

    return this;
  }

  public MainPage choiceEarliestCourse() {

    // Плитки на главной странице двух типов, с разными локаторами, поэтому будет два списка, из которых будет выбран курс с ближайшей датой.

    List<WebElement> firstTypeTiles = fes(By.xpath("//span[@class='sc-1pljn7g-3 cdTYKW'][position() mod 2 = 1]"));
    List<WebElement> secondTypeTiles = fes(By.cssSelector(".sc-12yergf-7.dPBnbE"));
    List<WebElement> selectedTilesFromBothTypes = new ArrayList<>(Arrays.asList(this.findEarliestCourse(firstTypeTiles), this.findEarliestCourse(secondTypeTiles)));

    moveAndClick(this.findEarliestCourse(selectedTilesFromBothTypes));

    return this;
  }

  private WebElement findEarliestCourse(List<WebElement> list) {
    LocalDate date;
    LocalDate earliestDate = LocalDate.MAX;
    WebElement earliestTile = null;

    for (WebElement element : list) {
      String dateStr = element.getText();

      if (dateStr.endsWith("месяцев")) {
        dateStr = dateStr.substring(0, dateStr.length() - 10).trim();
      } else if (dateStr.endsWith("месяца")) {
        dateStr = dateStr.substring(0, dateStr.length() - 9);
      } else if (dateStr.endsWith("месяц")) {
        dateStr = dateStr.substring(0, dateStr.length() - 8);
      }

      // На некоторых плитках есть надпись просто "В декабре" и точной даты нет, поэтому их пропускаем.
      if (dateStr.contains("В")) {
        continue;
      } else if (dateStr.contains("года")) {
        date = LocalDate.parse(dateStr.substring(2, dateStr.length() - 5), DateTimeFormatter.ofPattern("d MMMM yyyy"));
      } else {
        date = LocalDate.parse(dateStr.substring(2) + " " + LocalDate.now().getYear(), DateTimeFormatter.ofPattern("d MMMM yyyy"));
      }

      if (date.isBefore(earliestDate)) {
        earliestDate = date;
        earliestTile = element;
      }
    }

    if (list.size() == 2) {
      log.info(String.format("Cамый ранний курс начинается %s", earliestDate));
    }

    return earliestTile;
  }
}