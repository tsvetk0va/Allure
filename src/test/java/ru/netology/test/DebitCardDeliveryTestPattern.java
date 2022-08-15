package ru.netology.test;


import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class DebitCardDeliveryTestPattern {

    @BeforeEach
    public void openChrome() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTestPassedWithoutReplanDate() {
        var plusDaysForFirstDate = 4;
        var firstDate = DataGenerator.generateDate(plusDaysForFirstDate);
        var validUser = DataGenerator.Registration.generateByCard("ru");

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(validUser.getName());
        form.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
    }

    @Test
    void shouldTestPassedWithReplanDate() {
        var plusDaysForFirstMeeting = 5;
        var firstDate = DataGenerator.generateDate(plusDaysForFirstMeeting);
        var plusDaysForSecondDate = 10;
        var secondDate = DataGenerator.generateDate(plusDaysForSecondDate);
        var validUser = DataGenerator.Registration.generateByCard("ru");

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(firstDate);
        form.$("[data-test-id=name] input").setValue(validUser.getName());
        form.$("[data-test-id=phone] input").setValue(validUser.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$$("[role=button]").find(exactText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));

        $("[data-test-id=date]").$("[class='input__control']").click();
        $("[data-test-id=date]").$("[class='input__control']").sendKeys(Keys.chord(Keys.CONTROL + "A", Keys.DELETE));
        form.$("[data-test-id=date] input").setValue(secondDate);
        form.$$("[role=button]").find(exactText("Запланировать")).click();
        $("[data-test-id=replan-notification] .notification__content").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?\n" +
                        "\n" +
                        "Перепланировать"));
        $("[data-test-id=replan-notification] .button__text").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate));
    }
}


