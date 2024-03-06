package otus.pages;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.ElementClickInterceptedException;
import otus.annotations.UrlPrefix;
import com.google.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import otus.support.GuiceScoped;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


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

      try {
        moveAndClick(chosenCourse);
      } catch (ElementClickInterceptedException e) {
        closeCookiesMessage();
        moveAndClick(chosenCourse);
      }
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

    try {
      moveAndClick(earlistCourseMap.keySet().iterator().next());
    } catch (ElementClickInterceptedException e) {
      closeCookiesMessage();
      moveAndClick(earlistCourseMap.keySet().iterator().next());
    }

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

    try {
      moveAndClick(latestCourseMap.keySet().iterator().next());
    } catch (ElementClickInterceptedException e) {
      closeCookiesMessage();
      moveAndClick(latestCourseMap.keySet().iterator().next());
    }

    return this;
  }

  public void checkEarliestCourseDateOnTileAndOnPage() {
    Assertions.assertEquals(this.earliestCourseTileDate, new AnyCourseCardPage(guiceScoped).getCourseDate());
  }

  public void checkLatestCourseDateOnTileAndOnPage() {
    Assertions.assertEquals(this.latestCourseTileDate, new AnyCourseCardPage(guiceScoped).getCourseDate());
  }

  public void findRequiredOrLaterDateCourse(String requiredCourseDateStr) {
    LocalDate requiredCourseDate = LocalDate.parse(requiredCourseDateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    Map<WebElement, LocalDate> originalMap = getTilesElementsWithLocalDate();

    Map<WebElement, LocalDate> filteredMap = originalMap.entrySet().stream()
            .filter(entry -> entry.getValue().isEqual(requiredCourseDate) || entry.getValue().isAfter(requiredCourseDate))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    log.info(String.format("Курсы, которые начинаются не раньше %s: ", requiredCourseDateStr));
    filteredMap.forEach((key, value) -> {

      // Поиск вверх по дереву атрибута, содержащего ссылку на страницу курса.
      WebElement parentElement = key;
      String hrefValue = null;

      while (parentElement != null) {
        hrefValue = parentElement.getAttribute("href");
        if (hrefValue != null) {
          break;
        }
        parentElement = parentElement.findElement(By.xpath(".."));
      }

      // Использование найденной ссылки для jsoup парсера и вывода названия курса
      try {
        Document doc = Jsoup.connect(hrefValue).get();
        Elements nameCourse = doc.select(".sc-1og4wiw-0.sc-s2pydo-1.ifZfhS.diGrSa");
        log.info("Название: " + nameCourse.get(0).text());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      log.info("Дата старта: " + value.toString());
    });
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