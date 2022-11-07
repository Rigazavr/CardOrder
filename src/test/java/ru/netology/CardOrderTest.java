package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardOrderTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");

    }

    @Test
    void shouldSuccessfully() {

        Configuration.holdBrowserOpen = false;

        $x("//*[@name=\"name\"]").setValue("Регина");
        $x("//*[@name=\"phone\"]").setValue("+79133056879");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@type=\"button\"]").click();
        $(withText("Ваша заявка успешно отправлена!")).shouldHave(visible);
    }

    @Test
    void shouldNotCorrectName() {

        Configuration.holdBrowserOpen = false;

        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@name=\"name\"]").setValue("Regina Vasileva");
        $x("//*[@name=\"phone\"]").setValue("+7133056879");
        $x("//*[@type=\"button\"]").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldEmptyNameField() {

        Configuration.holdBrowserOpen = false;

        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@name=\"name\"]").setValue("");
        $x("//*[@name=\"phone\"]").setValue("+79133056879");
        $x("//*[@type=\"button\"]").click();
        $(withText("Поле обязательно для заполнения")).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotCorrectNamePhone() {

        Configuration.holdBrowserOpen = false;

        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@name=\"name\"]").setValue("Регина");
        $x("//*[@name=\"phone\"]").setValue("Regina");
        $x("//*[@type=\"button\"]").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldEmptyPhoneField() {

        Configuration.holdBrowserOpen = false;

        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@name=\"name\"]").setValue("Антон");
        $x("//*[@name=\"phone\"]").setValue("");
        $x("//*[@type=\"button\"]").click();
        $(withText("Поле обязательно для заполнения"))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }
}