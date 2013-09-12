package com.github.hippoom.ramblings.credit.features;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

/**
 * </pre>
 * 
 * This test runs specific acceptance test by name given. This test is not
 * supposed to run in any test suite, it's just a convenient class for
 * developing or debugging acceptance tests.
 * 
 * </pre>
 * 
 */
@RunWith(Cucumber.class)
@Cucumber.Options(features = { "classpath:." }, name = "客户按机场三字码搜索春航国内单程航班", format = { "html:target/acceptance-cucumber-html-report" })
public class SpecificAcceptanceTests {

}
