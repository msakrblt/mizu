package StepDefinitions;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class BaseSteps {

    public static WebDriver driver;
    public static Hashtable<String, String> my_dict;
    public static Hashtable<String, String> user_dict;
    public static String currentPage;
    Hooks hooks;
    WebSteps webSteps;

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSteps.class);

    public BaseSteps() {
        this.driver = Hooks.driver;
        this.my_dict = Hooks.my_dict;
        this.user_dict = Hooks.user_dict;
        this.currentPage = Hooks.currentPage;
    }

    @And("^screen to maximize$")
    public void maximize() {
        driver.manage().window().maximize();
    }

    @And("^switch to new window$")
    public void switchTab() {
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
    }

    @And("^I wait to (\\d+) seconds$")
    public void Sleep(int second) throws InterruptedException {
        long time = second * 1000;
        Thread.sleep(time);

    }


    @And("^I am registered with (\\w+(?: \\w+)*)")
    public void registeredInformation(String user) throws IOException, ParseException {
        findAndSaveUser(user);
    }

    public void findAndSaveUser(String userJson) throws IOException, ParseException {
        Object document = null;
        JSONParser parser = new JSONParser();
        String projectPath = System.getProperty("user.dir");
        Object obj = parser.parse(new FileReader(projectPath + "\\src\\test\\java\\Users\\" + userJson + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        document = Configuration.defaultConfiguration().jsonProvider().parse(jsonObject.toJSONString());
        String userName = JsonPath.read(document, "$.username");
        String password = JsonPath.read(document, "$.password");
        user_dict.put("my username", userName);
        user_dict.put("my password", password);
    }

}
