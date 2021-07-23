package stepDefinitions;


import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.cucumber.java.After;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class steps {

    WebDriver driver;
    HomePage hp;
    LoginPage lp;
    AccountRegistrationPage regpage;
    public List<HashMap<String,String>>datamap;
    Logger logger;
    // in junit or cucumber we hooks its same like testNg Annotaiton but in cucumber junit we only have @Befare and @After
   ResourceBundle br;
   String rb;
    @Before
    public void setup()
    {
        logger= LogManager.getLogger(this.getClass());
      br=ResourceBundle.getBundle("config");
      rb=br.getString("browser");
    }
   /* @After
    public void teardown(Scenario scenario){
        System.out.println("Scernario status >>>"+scenario.getStatus());
        if(scenario.isFailed()){
            byte[] screenshot=((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());
        }


    }*/
    @Given("User Launch browser")
    public void user_launch_browser() {
        if(rb.equals("chrome"))
        {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }
        else if (rb.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if (rb.equals("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Given("opens URL {string}")
    public void opens_url(String url) {
        driver.get(url);
        driver.manage().window().maximize();
        logger.info("Browser Url Lunched");
    }

    @When("User navigate to MyAccount menu")
    public void user_navigate_to_my_account() {
        hp = new HomePage(driver);
        hp.clickMyAccount();
        logger.info("user click on MyAccount");
    }

    @When("click on Login")
    public void click_on_login() {
        hp.clickLogin();
        logger.info("user click on login");
    }

    @When("User enters Email as {string} and Password as {string}")
    public void user_enters_email_as_and_password_as(String email, String pwd) {
        lp = new LoginPage(driver);
        lp.setEmail(email);
        logger.info("user entered login email");
        lp.setPassword(pwd);

    }

    @When("Click on Login button")
    public void click_on_login_button() {
        lp.clickLogin();
        logger.info("user Clicked on login");

    }


    @Then("User navigates to MyAccount Page")
    public void user_navigates_to_my_account_page() {
        boolean targetpage = lp.isMyAccountPageExists();
        driver.close();
        if (targetpage) {

            Assert.assertTrue(true);
        } else {

            Assert.assertTrue(false);
        }

        // driver.close();
    }
    @Then("Check User navigates to MyAccount Page by passing Email and Password with excel row {string}")
    public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String rows) {
        datamap= DataReader.data(System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx", "Sheet1");

        int index=Integer.parseInt(rows)-1;
        String email= datamap.get(index).get("username");
        String pwd= datamap.get(index).get("password");
        String exp_res= datamap.get(index).get("res");

        lp=new LoginPage(driver);
        lp.setEmail(email);
        lp.setPassword(pwd);

        lp.clickLogin();
        try
        {
            boolean targetpage=lp.isMyAccountPageExists();

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
                    MyAccountPage myaccpage=new MyAccountPage(driver);
                    myaccpage.clickLogout();
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
    }
    //Account Registrations Steps
    @When("Click on Register")
    public void click_on_register() {
     hp.clickRegister();
    }
    @Then("User navigate to Register Account page")
    public void user_navigate_to_register_account_page() {
regpage=new AccountRegistrationPage(driver);
if(regpage.isRegisterAccountPageDiplayed())
{
    logger.info("Register Account Page Diplayed");
    Assert.assertTrue(true);
} else
{
    logger.info("Register Account Page Not Diplayed");
    Assert.assertTrue(false);
}

    }
    @When("user provide valid details")
    public void user_provide_valid_details() {
        regpage.setFirstName("John");
        logger.info("User Name Entered");
        regpage.setLastName("Canedy");
        logger.info("User Last Name is Entered");
        regpage.setEmail(RandomStringUtils.randomAlphabetic(5)+"@gmail.com");// Random Email
        regpage.setTelephone("65656565");
        logger.info("Telephone No is Entered");
        regpage.setPassword("abcxyz");
        logger.info("Password is Entered");
        regpage.setConfirmPassword("abcxyz");
        regpage.setPrivacyPolicy();
    }
    @When("Click on Continue")
    public void click_on_continue() {
regpage.clickContinue();
    }

    @Then("User should see {string} message")
    public void user_should_see_message(String expmsg) {
        String confmsg = regpage.getConfirmationMsg();
        driver.close();
        if (confmsg.equals(expmsg)){
            logger.info("Register Account created successful");
        Assert.assertTrue(true);
    }
    else{
            logger.info("Register Account not created");
            Assert.assertTrue(false);

    }
    }}


