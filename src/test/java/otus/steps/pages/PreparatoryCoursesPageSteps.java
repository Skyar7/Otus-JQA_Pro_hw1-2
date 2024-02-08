package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Тогда;
import otus.pages.PreparatoryCoursesPage;

public class PreparatoryCoursesPageSteps {

  @Inject
  public PreparatoryCoursesPage preparatoryCoursesPage;

  @Если("Открыта страница подготовительных курсов")
  public void openPreparatoryPage() {
    preparatoryCoursesPage.openPage();
  }

  @Тогда("Найти самый дешёвый и дорогой курсы")
  public void findLowestAndHighestPriceCourses() {
    preparatoryCoursesPage.findLowestAndHighestPriceCourses();
  }
}