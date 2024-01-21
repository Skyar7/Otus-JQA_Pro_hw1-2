package pageobject.pages;

import annotations.UrlPrefix;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@UrlPrefix("/")
public class MainPage extends AbsBasePage<MainPage> {
  private List<WebElement> filteredByNameCourses;
  private LocalDate chosenCourseTileDate;
  private ChosenCourseCardPage chosenCourseCardPage;

  public MainPage(WebDriver driver) {
    super(driver);
  }

  public MainPage coursesNamesFilter(String requiredCourseName) {
    String requiredCourseNameLocator = "//h5[contains(text(),'%s')]";
    filteredByNameCourses = fes(By.xpath(String.format(requiredCourseNameLocator, requiredCourseName)));
    log.info(String.format("Найдено курсов, по запросу '%s': %d.", requiredCourseName, filteredByNameCourses.size()));
    return this;
  }

  public void checkFilteredCourseNameAndDescriptionData() {

    if (filteredByNameCourses.isEmpty()) {
      String noCoursesForCheckingFailMessage = "Нет курсов для проверки!";
      log.info(noCoursesForCheckingFailMessage);
      Assertions.fail(noCoursesForCheckingFailMessage);
    } else {
      WebElement chosenCourse = filteredByNameCourses.get(0);

      if (filteredByNameCourses.size() > 1) {
        log.info(String.format("Из найденных курсов, для проверки выбран '%s'.", chosenCourse.getText()));
      }
      chosenCourse.click();
      chosenCourseCardPage = new ChosenCourseCardPage(driver);
      chosenCourseCardPage.checkCourseNameAndDescriptionData();

    }
  }

  public MainPage choiceEarliestCourse() {
    Map<WebElement, LocalDate> tilesDateMap = this.getTilesElementsWithLocalDate();

    Optional<Map.Entry<WebElement, LocalDate>> earliestEntry = tilesDateMap.entrySet().stream()
            .reduce((entry1, entry2) -> entry1.getValue().isBefore(entry2.getValue()) ? entry1 : entry2);

    Map<WebElement, LocalDate> earlistCourseMap = earliestEntry.map(entry -> {
      Map<WebElement, LocalDate> mapWithLatestDate = new HashMap<>();
      mapWithLatestDate.put(entry.getKey(), entry.getValue());
      return mapWithLatestDate;
    }).orElseGet(HashMap::new);

    this.chosenCourseTileDate = earlistCourseMap.values().iterator().next();
    log.info(String.format("Cамый ранний курс начинается %s", this.chosenCourseTileDate));
    moveAndClick(earlistCourseMap.keySet().iterator().next());

    return this;
  }

  public MainPage choiceLatestCourse() {
    Map<WebElement, LocalDate> tilesDateMap = this.getTilesElementsWithLocalDate();

    Optional<Map.Entry<WebElement, LocalDate>> latestEntry = tilesDateMap.entrySet().stream()
            .reduce((entry1, entry2) -> entry1.getValue().isAfter(entry2.getValue()) ? entry1 : entry2);

    Map<WebElement, LocalDate> latestCourseMap = latestEntry.map(entry -> {
      Map<WebElement, LocalDate> mapWithLatestDate = new HashMap<>();
      mapWithLatestDate.put(entry.getKey(), entry.getValue());
      return mapWithLatestDate;
    }).orElseGet(HashMap::new);

    this.chosenCourseTileDate = latestCourseMap.values().iterator().next();
    log.info(String.format("Cамый поздний курс начинается %s", this.chosenCourseTileDate));
    moveAndClick(latestCourseMap.keySet().iterator().next());

    return this;
  }

  public void checkChosenCourseDate() {
    chosenCourseCardPage = new ChosenCourseCardPage(driver);
    Assertions.assertEquals(this.chosenCourseTileDate, chosenCourseCardPage.getCourseDate());
  }

  private Map<WebElement, LocalDate> getTilesElementsWithLocalDate() {

    // Плитки на главной странице двух типов, с разными локаторами.
    String firstTypeTilesLocator = "//span[@class='sc-1pljn7g-3 cdTYKW' and contains(text(), 'С ')]";
    String secondTypeTilesSelector = ".sc-12yergf-7.dPBnbE";

    List<WebElement> tilesList = fes(By.xpath(firstTypeTilesLocator));
    tilesList.addAll(fes(By.cssSelector(secondTypeTilesSelector)));

    Map<WebElement, LocalDate> tilesDateMap = new HashMap<>();

    for (WebElement element : tilesList) {
      LocalDate date = dateParser(element);
      tilesDateMap.put(element, date);
    }

    return tilesDateMap;
  }
}