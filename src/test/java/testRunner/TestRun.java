package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions
        (        // features = "@target/rerun.txt",
                features = {".//features/Login.feature", "./features/AccountRegistration.feature"},
                // features= {".//features/Login.feature"},
                //,"./features/AccountRegistration.feature"},

                //features = {"./features/loginDDT.feature"},
                //features = {"./features/AccountRegistration.feature"},
                glue = "stepDefinitions",
                dryRun = false,
               // tags = "@sanaty"
                //tags="@sanaty and @regression"
                //tags="@sanaty or @regression"
                //  tags="@sanaty and not @regression"

                plugin={"pretty","html:reports/myreport.html",
               "rerun:target/rerun.txt",// mandatory required to capture the failures
        }
        )
public class TestRun {


}
