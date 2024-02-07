package otus.pages;

import otus.annotations.UrlPrefix;
import com.google.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import otus.support.GuiceScoped;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.*;


@UrlPrefix("/")
public class MainPage extends AbsBasePage<MainPage> {
  public List<WebElement> filteredByNameCourses;
  private LocalDate earliestCourseTileDate;
  private LocalDate latestCourseTileDate;

  @Inject
  public MainPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public MainPage coursesNamesFilter(String requiredCourseName) {
    String requiredCourseNameLocator = "//h5[contains(text(),'%s')]";
    this.filteredByNameCourses = fes(By.xpath(String.format(requiredCourseNameLocator, requiredCourseName)));
    log.info(String.format("Найдено курсов, по запросу '%s': %d.", requiredCourseName, filteredByNameCourses.size()));
    return this;
  }

  public void chooseFilteredByNameCourse() {

    if (this.filteredByNameCourses.isEmpty()) {
      String noCoursesForCheckingFailMessage = "Нет курсов для проверки!";
      log.info(noCoursesForCheckingFailMessage);
      Assertions.fail(noCoursesForCheckingFailMessage);
    } else {
      Random random = new Random();
      WebElement chosenCourse = filteredByNameCourses.get(random.nextInt(filteredByNameCourses.size()));
      if (filteredByNameCourses.size() > 1) {
        log.info(String.format("Из найденных курсов, для проверки случайно был выбран '%s'.", chosenCourse.getText()));
      }
      moveAndClick(chosenCourse);
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

    this.earliestCourseTileDate = earlistCourseMap.values().iterator().next();
    log.info(String.format("Cамый ранний курс начинается %s", this.earliestCourseTileDate));
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

    this.latestCourseTileDate = latestCourseMap.values().iterator().next();
    log.info(String.format("Cамый поздний курс начинается %s", this.latestCourseTileDate));
    moveAndClick(latestCourseMap.keySet().iterator().next());

    return this;
  }

  public void checkEarliestCourseDateOnTileAndOnPage() {
    Assertions.assertEquals(this.earliestCourseTileDate, new AnyCourseCardPage(guiceScoped).getCourseDate());
  }

  public void checkLatestCourseDateOnTileAndOnPage() {
    Assertions.assertEquals(this.latestCourseTileDate, new AnyCourseCardPage(guiceScoped).getCourseDate());
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