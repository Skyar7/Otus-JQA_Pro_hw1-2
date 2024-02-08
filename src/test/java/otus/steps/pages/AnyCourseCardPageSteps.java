package otus.steps.pages;

import com.google.inject.Inject;
import io.cucumber.java.ru.Тогда;
import otus.pages.AnyCourseCardPage;

public class AnyCourseCardPageSteps {

  @Inject
  public AnyCourseCardPage anyCourseCardPage;

  @Тогда("На странице курса будет его имя и описание")
  public void checkCourseNameAndDescriptionData() {
    anyCourseCardPage.checkCourseNameAndDescriptionData();
  }
}