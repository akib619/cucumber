import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CucumberTest {
    String downloadFilePath = "C:\\Users\\a.mahmud\\Downloads";
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", Map.of(
                "safebrowsing.enabled", "true"
        ));

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", downloadFilePath);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Given("I am on the homepage")
    public void onHomePage() {
        driver.get("https://store.steampowered.com/");
    }

    @When("Navigate to the About Page")
    public void navigateToAboutPage() {

        navigate();
        navigateAndVerify();

    }

    @Step("Naviagte to the about")
    public void navigate() {
        WebElement aboutBtn = driver.findElement(By.xpath("//div[@aria-label='Global Menu']//a[contains(@class,'menuitem')][normalize-space()='About']"));
        aboutBtn.click();
    }

    @Step("Naviagte to the about and verify")
    public void navigateAndVerify() {
        WebElement aboutPage = driver.findElement(By.xpath("//div[contains(@class,'about_subtitle')]"));
        Assert.assertTrue(aboutPage.isDisplayed(), "About Page is not displayed");
    }

    @Then("Check file download and Verify")
    public void checkFileDownloadAndVerify() {
        WebElement installBtn = driver.findElement(By.xpath("//div[@id='about_greeting']//a[@class='about_install_steam_link'][normalize-space()='Install Steam']"));
        installBtn.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        boolean isDownloaded = wait.until(driver -> {
            File downloadedFile = new File(downloadFilePath + "/SteamSetup.exe");
            return downloadedFile.exists();
        });

        Assert.assertTrue(isDownloaded, "File download failed !");
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
