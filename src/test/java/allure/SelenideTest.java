package allure;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

    @Test
    public void testIssueSearch(){
        open("https://github.com/");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys("eroshenkoam/allure-example");
        $(".header-search-input").submit();

        $(By.linkText("eroshenkoam/allure-example")).click();
        $(By.partialLinkText("Issues")).click();
        $(withText("68")).should(Condition.exist);


    }
}
