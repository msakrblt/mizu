package StepDefinitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver = null;
    public static Hashtable<String, String> my_dict = new Hashtable<String, String>();
    public static Hashtable<String, String> user_dict = new Hashtable<String, String>();
    public static String currentPage="";


    @Given("^I Open To (Chrome|Firefox) Browser$")
    public void open_chrome_browser(String browser) {
        String projectPath = System.getProperty("user.dir");
        System.out.println("Project Path is :" + projectPath);
        if (browser.equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", projectPath + "/src/main/resources/Drivers/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            //driver.manage().window().maximize();
            System.out.println("Chrome Browser Is Opened");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/src/main/resources/Drivers/geckodriver.exe");
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            //driver.manage().window().maximize();
            System.out.println("Firefox Browser Is Opened");
        }
    }


    @And("^I open to (\\w+(?: \\w+)*) application")
    public void openToApplication(String application) throws IOException, ParseException {
        Object document = null;
        JSONParser parser = new JSONParser();
        String projectPath = System.getProperty("user.dir");
        Object obj = parser.parse(new FileReader(projectPath + "\\src\\test\\java\\Applications\\" + application + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        document = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toJSONString());
        String application_url = JsonPath.read(document, "$.URL");
        try{
            driver.navigate().to(application_url);
        }catch (Exception ee){
            Assert.fail("COULD NOT OPEN APPLICATION, DIDN'T FIND URL or NOT URL");
        }
    }

    @After(value = "not @API", order = -1)
    public void close_browser() {
        driver.close();
        driver.quit();
    }


    @After(order = 1)
    public void takeScreenAfter(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] data =((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            String screenshotName = scenario.getName();
            scenario.attach(data,"image/png",screenshotName);

        }
    }
}
