package otus.pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import otus.annotations.UrlPrefix;
import otus.support.GuiceScoped;

import java.util.*;

@UrlPrefix("/online/")
public class PreparatoryCoursesPage extends AbsBasePage<PreparatoryCoursesPage> {

  @Inject
  public PreparatoryCoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public void findLowestAndHighestPriceCourses() {
    Map<String, Integer> coursesWithNamesAndPrices = new HashMap<>();
    List<WebElement> coursesTiles = fes(By.cssSelector(".lessons__new-item-container"));

    for (WebElement element : coursesTiles) {
      String name = element
              .findElement(By.cssSelector(".lessons__new-item-title.lessons__new-item-title_with-tags.lessons__new-item-title_with-bg.js-ellipse"))
              .getText();

      Integer price = Integer.parseInt(element
              .findElement(By.cssSelector(".lessons__new-item-price"))
              .getText()
              .replaceAll("[^\\d]", ""));

      coursesWithNamesAndPrices.put(name, price);
    }

    // Вывод курса(ов) с самой низкой ценой
    Optional<Map.Entry<String, Integer>> minEntry = coursesWithNamesAndPrices.entrySet().stream()
            .min(Map.Entry.comparingByValue());
    int lowestPrice = minEntry.get().getValue();
    log.info("КУРС(Ы) С САМОЙ НИЗКОЙ ЦЕНОЙ:");
    coursesWithNamesAndPrices.entrySet().stream()
            .filter(entry -> entry.getValue() == lowestPrice)
            .forEach(entry ->  log.info(entry.getKey() + " - " + entry.getValue() + " руб."));

    // Вывод курса(ов) с самой высокой ценой
    Optional<Map.Entry<String, Integer>> maxEntry = coursesWithNamesAndPrices.entrySet().stream()
            .max(Map.Entry.comparingByValue());
    int highestPrice = maxEntry.get().getValue();
    log.info("КУРС(Ы) С САМОЙ ВЫСОКОЙ ЦЕНОЙ:");
    coursesWithNamesAndPrices.entrySet().stream()
            .filter(entry -> entry.getValue() == highestPrice)
            .forEach(entry ->  log.info(entry.getKey() + " - " + entry.getValue() + " руб."));
  }
}