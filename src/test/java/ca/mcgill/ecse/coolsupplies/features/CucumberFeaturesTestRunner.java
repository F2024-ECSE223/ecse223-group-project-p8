package ca.mcgill.ecse.coolsupplies.features;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/resources/UpdateStudent.feature",
    glue = "ca.mcgill.ecse.coolsupplies.features.test")
public class CucumberFeaturesTestRunner {

}
