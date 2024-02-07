package otus.pageobject;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import otus.support.GuiceScoped;
import otus.waiters.Waiters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class AbsBaseUtils extends AbsElementActions {
  protected Waiters waiters;
  protected Logger log = LogManager.getLogger(AbsBaseUtils.class);

  @Inject
  public AbsBaseUtils(GuiceScoped guiceScoped) {
    super(guiceScoped);
    this.waiters = new Waiters(driver);
  }

  protected LocalDate dateParser(WebElement inputDate) {
    String dateStr = inputDate.getText();

    if (dateStr.startsWith("С ")) {
      dateStr =dateStr.substring(2);
    }

    if (dateStr.endsWith("месяцев")) {
      dateStr = dateStr.substring(0, dateStr.length() - 10).trim();
    } else if (dateStr.endsWith("месяца")) {
      dateStr = dateStr.substring(0, dateStr.length() - 9);
    } else if (dateStr.endsWith("месяц")) {
      dateStr = dateStr.substring(0, dateStr.length() - 8);
    }

    LocalDate formattedDate;

    if (dateStr.contains("года")) {
      formattedDate = LocalDate.parse(dateStr.substring(0, dateStr.length() - 5), DateTimeFormatter.ofPattern("d MMMM yyyy"));
    } else {
      formattedDate = LocalDate.parse(dateStr + " " + LocalDate.now().getYear(), DateTimeFormatter.ofPattern("d MMMM yyyy"));
    }

    return formattedDate;
  }
}