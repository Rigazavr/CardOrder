package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest{
    @Test
    void shouldSubmitRequest() {
        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79133056879");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSendRequestInvalidName() {

        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Ivanov Ivan");
        form.$("[data-test-id=phone] input").setValue("+79133056879");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSendRequestInvalidPhoneNumber() {

        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+7913305687");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSendRequestWithoutPhoneNumber() {

        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendRequestWithoutName() {

        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("");
        form.$("[data-test-id=phone] input").setValue("+79133056879");
        form.$("[data-test-id=agreement]").click();
        form.$("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSendRequestWithoutCheckbox() {

        open("http://localhost:9999/");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+79133056879");
        form.$("button.button").click();
        form.$("[data-test-id=agreement]").should(Condition.cssClass("input_invalid"));

    }
}