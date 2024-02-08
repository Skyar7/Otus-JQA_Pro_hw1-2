package otus.pages;

import com.google.inject.Inject;
import otus.annotations.UrlPrefix;
import otus.support.GuiceScoped;

@UrlPrefix("/online/")
public class PreparatoryCoursesPage extends AbsBasePage<PreparatoryCoursesPage> {

  @Inject
  public PreparatoryCoursesPage(GuiceScoped guiceScoped) {
    super(guiceScoped);
  }

  public void findCheapestAndMostExpensiveCourses() {

  }
}
