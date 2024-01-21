package pageobject.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.time.LocalDate;

public class ChosenCourseCardPage extends AbsBasePage<ChosenCourseCardPage> {

  public ChosenCourseCardPage(WebDriver driver) {
    super(driver);
  }

  public void checkCourseNameAndDescriptionData() {
    String name = "";

    try {
      name = fe(By.cssSelector(".sc-1og4wiw-0.sc-s2pydo-1.ifZfhS.diGrSa")).getText();
    } catch (NoSuchElementException e) {
      String nameFailMessage = "Имя карточки курса не найдено!";
      log.info(nameFailMessage);
      Assertions.fail(nameFailMessage);
    }

    try {
      fe(By.cssSelector(".sc-1og4wiw-0.sc-s2pydo-3.gaEufI.dZDxRw")).getText();
    } catch (NoSuchElementException e) {
      String descriptionFailMessage = "Описание курса для карточки '%s' не найдено!";
      log.info(String.format(descriptionFailMessage, name));
      Assertions.fail(String.format(descriptionFailMessage, name));
    }
  }

  public LocalDate getCourseDate() {
    return dateParser(fe(By.xpath("//div[@class='sc-3cb1l3-4 kGoYMV'][1]")));
  }
}