
package StepDefinitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class WebSteps {

    public static WebDriver driver;
    public static Hashtable<String, String> my_dict;
    public static Hashtable<String, String> user_dict;
    public static String currentPage;
    Hooks hooks;
    BaseSteps baseSteps;


    private static final Logger LOGGER = LoggerFactory.getLogger(WebSteps.class);

    public WebSteps() {
        this.driver = Hooks.driver;
        this.my_dict = Hooks.my_dict;
        this.user_dict = Hooks.user_dict;
        this.currentPage = Hooks.currentPage;
    }

    @And("^I see (\\w+(?: \\w+)*) page")
    public void seePage(String page) throws IOException, ParseException {
        currentPage = page;
        Object document = null;
        JSONParser parser = new JSONParser();
        String projectPath = System.getProperty("user.dir");
        Object obj = parser.parse(new FileReader(projectPath + "\\src\\test\\java\\pages\\" + page + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        document = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toJSONString());
        String MustElementKey = JsonPath.read(document, "$.waitPageLoad.elementKey");
        if (MustElementKey.equals("")) {
            // DO NOTHING
        } else {
            seeElement(MustElementKey);
            LOGGER.info("SUCCESSFULLY SEE PAGE " + page);
        }
    }

    @And("^I see (\\w+(?: \\w+)*) element")
    public void seeElement(String elementKey) throws IOException, ParseException {
        By element = findSelector(elementKey);
        driver.findElement(element).isDisplayed();
    }

    @And("^I see (\\w+(?: \\w+)*) element in (\\d+) seconds")
    public void seeElementWithSecond(String elementKey, int seconds) throws IOException, ParseException {
        By element = findSelector(elementKey);
        WebElement web = new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(driver -> driver.findElement(element));
    }

    By findSelector(String element) throws IOException, ParseException {
        By EE = null;
        Object document = null;
        JSONParser parser = new JSONParser();
        Object page = null;
        if (currentPage.equals("")) {
            Assert.fail("BEFORE TEST STEPS HAVE TO SEE ANY PAGE 'I see *** page' ");
        } else {
            page = currentPage;
        }
        String projectPath = System.getProperty("user.dir");
        Object obj = parser.parse(new FileReader(projectPath + "\\src\\test\\java\\Pages\\" + page + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        document = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toJSONString());
        String elementKey = JsonPath.read(document, "$.elements.['" + element + "']");
        if (elementKey.charAt(0) == '/' || elementKey.charAt(0) == '(') {
            EE = By.xpath(elementKey);
            return EE;
        } else if (elementKey.charAt(0) == '#') {
            EE = By.id(elementKey.substring(1));
            return EE;
        } else {
            Assert.fail("COULD NOT FIND ELEMENT");
        }
        return EE;
    }


    @And("^I click (\\w+(?: \\w+)*) element")
    public void seeElementAndClick(String element) throws IOException, ParseException {
        By elementKey = findSelector(element);
        try {
            driver.findElement(elementKey).click();
            LOGGER.info("CLICKED " + element);
            System.out.println("CLICKED " + element);
        } catch (Exception ee) {
            Assert.fail("COULD NOT FIND ELEMENT : " + elementKey);
        }
    }

    @And("^I fill:")
    public void seeElementAndFill(Map<String, String> map) throws IOException, ParseException {
        for (String key : map.keySet()) {
            if (!map.get(key).equals("my username") && !map.get(key).equals("my password")) {
                By elementKey = findSelector(key);
                driver.findElement(elementKey).sendKeys(map.get(key));
                LOGGER.info("FILLED " + elementKey + " = " + map.get(key));
                System.out.println("FILLED " + elementKey + " = " + map.get(key));
            } else if (map.get(key).equals("my username")) {
                String username = user_dict.get("my username");
                By elementKey = findSelector(key);
                driver.findElement(elementKey).sendKeys(username);
                LOGGER.info("FILLED " + elementKey + " = " + username);
                System.out.println("FILLED " + elementKey + " = " + username);
            } else {
                String password = user_dict.get("my password");
                By elementKey = findSelector(key);
                driver.findElement(elementKey).sendKeys(password);
                LOGGER.info("FILLED " + elementKey + " = " + password);
                System.out.println("FILLED " + elementKey + " = " + password);
            }
        }
    }

}
