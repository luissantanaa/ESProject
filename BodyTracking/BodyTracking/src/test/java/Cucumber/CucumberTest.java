/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cucumber;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 *
 * @author santananas
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/resources", glue={"StepDefinitions"})
public class CucumberTest {
   
}
