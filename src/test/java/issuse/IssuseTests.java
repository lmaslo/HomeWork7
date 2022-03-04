package issuse;//commit

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.addAttachment;
import static io.qameta.allure.Allure.step;

public class IssuseTests {

    private static final String Repository = "lmaslo/HomeWork7";
    private static final String IssuesText = "Allure Report";

    @Test
    @Feature("Задачи в репозитории")
    @Story("Поиск Issue Listener")
    public void checkIssueText() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(Repository);
        $(".header-search-input").submit();

        $(By.linkText(Repository)).click();
        $(By.partialLinkText("Issues")).click();
        $(withText(IssuesText)).should(Condition.visible);
    }


    @Test
    @Owner("Lena")
    @Severity(SeverityLevel.NORMAL)
    @Feature("Задачи в репозитории")
    @Story("Поиск Issue, с использованием step.")
    @DisplayName("Поиск заданого Issue")
    @Description("Этот тест проверят существование Issue, с текстом " + IssuesText)
    @Link(value = "Testing", url = "https://github.com")
    public void checkIssueTextWithStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("открываем главную страницу", () -> {
            open("https://github.com/");
        });

        step("Ищем репозиторий " + Repository, () -> {
            $(".header-search-input").click();
            $(".header-search-input").sendKeys(Repository);
            $(".header-search-input").submit();
        });

        step("Открываем репозиторий " + Repository, () -> {
            $(By.linkText(Repository)).click();
        });

        step("Переходим в Tab Issue", () -> {
            $(By.partialLinkText("Issues")).click();
            //добавить атачмент html
            addAttachment("Page Source", "text/html", WebDriverRunner.source(), "html");
        });

        step("Проверяем, что существует " + IssuesText, () -> {
            $(withText(IssuesText)).should(Condition.visible);
        });

    }


    @Test
    public void checkIssueAnnotatedSteps() {
        Allure.label("owner", "Lena");
        Allure.label("severity", SeverityLevel.NORMAL.value());
        Allure.feature("Задачи в репозитории");
        Allure.story("Поиск Issue, с использованием анотации step");
        Allure.getLifecycle().updateTestCase(testCase -> {
            testCase.setName("Поиск заданого Issue");
        });
        Allure.description("Этот тест проверят существование Issue, с текстом" + IssuesText);
        Allure.link("Testing", "https://github.com");

        Allure.parameter("Наименование Issues", "Allure Report");

        Steps steps = new Steps();
        steps.openMainPage();
        steps.searchForRepository(Repository);
        steps.openRepository(Repository);
        steps.openIssuesTab();
        steps.checkText(IssuesText);

        steps.takeScreenshot();

    }


}
