package testRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

		//features= {".//Features//"},
		//features= {".//Features//Login.feature"},
		//features= {".//Features//LoginDDT.feature"},
		features= {".//Features//LoginDDTExcel.feature"},
        glue="stepDefinitions",
        plugin= {
        		"pretty", "html:reports/myreport.html", 
        		"json:reports/myreport.json",
        		"rerun:target/rerun.txt"},    //Mandatory to capture failures
        dryRun=false,
        monochrome=true
        //tags = "@sanity"	//Scenarios tagged with @sanity,
      )
public class TestRunner 
{

}

