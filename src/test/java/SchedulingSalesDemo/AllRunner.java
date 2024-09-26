package SchedulingSalesDemo;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/Features/SalesDemoSchedulingRegression.feature" }, glue = {
		"SchedulingSalesDemo" }, monochrome = false, plugin = {})

@RunWith(Cucumber.class)

public class AllRunner {

}
