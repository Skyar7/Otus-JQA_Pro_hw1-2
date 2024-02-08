package otus.pages;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import otus.annotations.UrlPrefix;
import otus.support.GuiceScoped;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UrlPrefix("/online/")
public class PreparatoryCoursesPage extends AbsBasePage<PreparatoryCoursesPage> {

  @Inject
  public PreparatoryCoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public void findCheapestAndMostExpensiveCourses() {
    Map<String, Integer> courseWithMinPrice = new HashMap<>();
    Map<String, Integer> courseWithMaxPrice = new HashMap<>();

    List<WebElement> allPreparatoryCoursesTiles = fes(By.cssSelector(".lessons__new-item-container"));

    allPreparatoryCoursesTiles.stream()
            .map(element -> element.findElement(By.cssSelector(".lessons__new-item-price")).getText())
            .map(Integer::parseInt)
            .filter(price -> price == allPreparatoryCoursesTiles.stream()
                    .map(element -> element.findElement(By.cssSelector(".lessons__new-item-price")).getText())
                    .map(Integer::parseInt)
                    .min(Comparator.naturalOrder()).orElse(Integer.MAX_VALUE))
            .forEach(price -> {
              allPreparatoryCoursesTiles.stream()
                      .map(element -> element.findElement(By.cssSelector(".lessons__new-item-title.lessons__new-item-title_with-tags.lessons__new-item-title_with-bg.js-ellipse")).getText())
                      .forEach(name -> courseWithMinPrice.put(name, price));
            });
    System.out.println("2222");

    courseWithMinPrice.forEach((key, value) -> {
      System.out.println("Ключ: " + key + ", Значение: " + value);
    });
  }


  //    .lessons__new-item-title.lessons__new-item-title_with-tags.lessons__new-item-title_with-bg.js-ellipse
  //    .lessons__new-item-price
}
