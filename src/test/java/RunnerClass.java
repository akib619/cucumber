import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.Test;

@CucumberOptions(features = {"src/test/resources/cucumber.feature"},
plugin = {
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
})
@Test
public class RunnerClass extends AbstractTestNGCucumberTests {
}
