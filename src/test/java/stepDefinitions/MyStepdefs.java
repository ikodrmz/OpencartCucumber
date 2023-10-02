package stepDefinitions;

import io.cucumber.java.Scenario;
import io.cucumber.java.an.E;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import pageObjects.HomePage;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;


public class MyStepdefs {
    WebDriver driver=new EdgeDriver();

    HomePage hp;
    LoginPage lp;
    MyAccountPage macc;

    List<HashMap<String, String>> datamap; //Data driven

    Logger logger; //for logging
    ResourceBundle rb; // for reading properties file
    String br; //to store browser name





    @Before
    public void setup()    //Junit hook - executes once before starting
    {
        //for logging
        logger= LogManager.getLogger(this.getClass());
        //Reading config.properties (for browser)
        rb=ResourceBundle.getBundle("config");
        br=rb.getString("browser");

    }

   @After
    public void tearDown(Scenario scenario) {
        System.out.println("Scenario status ======>"+scenario.getStatus());
        if(scenario.isFailed()) {

            TakesScreenshot ts=(TakesScreenshot) driver;
            byte[] screenshot=ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png",scenario.getName());

        }
        driver.quit();

    }

    @Given("user launch browser")
    public void userLaunchBrowser() {
        if (br.equals("edge")){
            driver=new EdgeDriver();
        }
    }

    @And("opens URL {string}")
    public void opensURL(String url) {
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("user navigate to MyAccount menu")
    public void userNavigateToMyAccountMenu() {
        hp=new HomePage(driver);
        hp.clickMyAccount();
        logger.info("Clicked on MyAccount");
    }

    @And("click on Login")
    public void clickOnLogin() {
        hp.clickLogin();
        logger.info("Clicked on Login");
    }

    @And("User enters email as {string} and Password as {string}")
    public void userEntersEmailAsAndPasswordAs(String email, String pwd) {
        lp=new LoginPage(driver);

        lp.setEmail(email);
        logger.info("Email provided");
        lp.setPassword(pwd);
        logger.info("Password provided");
    }

    @And("click on Login button")
    public void clickOnLoginButton() {
        lp.clickLogin();
    }

    @Then("User navigates to MyAccount Page")
    public void userNavigatesToMyAccountPage() {
        macc=new MyAccountPage(driver);
        boolean targetpage=macc.isMyAccountPageExists();
        if (targetpage){
            logger.info("Login Success");
            Assert.assertTrue(true);
        }else
        {
            logger.error("Login Failed");
            Assert.assertTrue(false);
        }
    }

//Data driven with provider
    @Then("check User navigates to MyAccount Page by passing Email and Password with excel row {string}")
    public void checkUserNavigatesToMyAccountPageByPassingEmailAndPasswordWithExcelRow(String rows) {
        datamap=DataReader.data(System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx", "Sheet1");

        int index=Integer.parseInt(rows)-1;
        String email= datamap.get(index).get("username");
        String pwd= datamap.get(index).get("password");
        String exp_res= datamap.get(index).get("res");

        lp=new LoginPage(driver);
        lp.setEmail(email);
        lp.setPassword(pwd);
        macc=new MyAccountPage(driver);

        lp.clickLogin();
        try
        {
            boolean targetpage=macc.isMyAccountPageExists();

            if(exp_res.equals("Valid"))
            {
                if(targetpage==true)
                {
                    MyAccountPage myaccpage=new MyAccountPage(driver);
                    myaccpage.clickLogout();
                    Assert.assertTrue(true);
                }
                else
                {
                    Assert.assertTrue(false);
                }
            }

            if(exp_res.equals("Invalid"))
            {
                if(targetpage==true)
                {
                    macc.clickLogout();
                    Assert.assertTrue(false);
                }
                else
                {
                    Assert.assertTrue(true);
                }
            }


        }
        catch(Exception e)
        {

            Assert.assertTrue(false);
        }
        driver.close();
    }
}
