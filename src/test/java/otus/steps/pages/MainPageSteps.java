package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.*;
import otus.pages.MainPage;

public class MainPageSteps {

  @Inject
  public MainPage mainPage;

  @Если("Открыта главная страница")
  public void openMainPage() {
    mainPage.openPage();
  }

  @И("Найден и открыт курс с названием {string}")
  public void findAndOpenCourse(String requiredCourseName) {
    mainPage.coursesNamesFilter(requiredCourseName)
            .chooseFilteredByNameCourse();
  }

  @Тогда("Открыть самый ранний курс и проверить дату")
  public void earliestCourseCheck() {
    mainPage.choiceEarliestCourse()
            .checkEarliestCourseDateOnTileAndOnPage();
  }

  @Тогда("Открыть самый поздний курс и проверить дату")
  public void latestCourseCheck() {
    mainPage.choiceEarliestCourse()
            .checkEarliestCourseDateOnTileAndOnPage();
  }

}
