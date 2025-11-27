package Odevler;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ClickButtonTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod
    public void teardown() {

        bekle(2);
        driver.quit();
    }

    @Test
    public void clickButtonTest() {

        driver.get("https://demoqa.com/elements");
        bekle(1);


        WebElement buttonsMenu = driver.findElement(By.cssSelector("li#item-4"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buttonsMenu);
        bekle(1);
        buttonsMenu.click();
        bekle(1);


        List<WebElement> primaryButtons =
                driver.findElements(By.cssSelector("div.mt-4 button.btn.btn-primary"));
        WebElement clickMeButton = primaryButtons.get(primaryButtons.size() - 1);


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", clickMeButton);
        bekle(1);


        clickMeButton.click();
        bekle(1);


        WebElement message = driver.findElement(By.cssSelector("#dynamicClickMessage"));
        String actualMessage = message.getText();

        Assert.assertEquals(
                actualMessage,
                "You have done a dynamic click",
                "Click Me mesajı beklenen gibi değil!"
        );
    }


    private void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000L);
        } catch (InterruptedException e) {

        }
    }
}
