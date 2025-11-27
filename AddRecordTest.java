package Odevler;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class AddRecordTest {

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
    public void addAndEditRecordTest() {
        driver.get("https://demoqa.com/webtables");
        bekle(1);


        WebElement addButton = driver.findElement(By.cssSelector("button#addNewRecordButton"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);
        bekle(1);
        addButton.click();
        bekle(1);


        String email = "deniz@test.com";

        driver.findElement(By.cssSelector("input#firstName")).sendKeys("Deniz");
        driver.findElement(By.cssSelector("input#lastName")).sendKeys("Yazici");
        driver.findElement(By.cssSelector("input#userEmail")).sendKeys(email);
        driver.findElement(By.cssSelector("input#age")).sendKeys("35");
        driver.findElement(By.cssSelector("input#salary")).sendKeys("50000");
        driver.findElement(By.cssSelector("input#department")).sendKeys("QA");

        bekle(1);


        driver.findElement(By.cssSelector("button#submit")).click();
        bekle(1);


        List<WebElement> rows = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group"));

        WebElement targetRow = null;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector(".rt-td"));
            if (cells.size() > 3) {
                String rowEmail = cells.get(3).getText(); // 4. sütun email
                if (email.equals(rowEmail)) {
                    targetRow = row;
                    break;
                }
            }
        }


        Assert.assertNotNull(targetRow, "Eklenen kayıt tabloda bulunamadı!");


        WebElement editButtonInRow = targetRow.findElement(By.cssSelector("span[title='Edit']"));


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButtonInRow);
        bekle(1);

        editButtonInRow.click();
        bekle(1);


        WebElement ageInput = driver.findElement(By.cssSelector("input#age"));
        ageInput.clear();
        ageInput.sendKeys("36");
        bekle(1);

        driver.findElement(By.cssSelector("button#submit")).click();
        bekle(1);


        rows = driver.findElements(By.cssSelector(".rt-tbody .rt-tr-group"));
        String updatedAge = null;

        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.cssSelector(".rt-td"));
            if (cells.size() > 3) {
                String rowEmail = cells.get(3).getText();
                if (email.equals(rowEmail)) {
                    updatedAge = cells.get(2).getText(); // 3. sütun: Age
                    break;
                }
            }
        }


        Assert.assertEquals(
                updatedAge,
                "36",
                "Kaydın yaşı düzenlendikten sonra beklenen değere güncellenmemiş!"
        );
    }

    private void bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000L);
        } catch (InterruptedException e) {

        }
    }
}
