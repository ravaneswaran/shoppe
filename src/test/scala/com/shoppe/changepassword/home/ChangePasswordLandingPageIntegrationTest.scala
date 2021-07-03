package com.shoppe.changepassword.home

import io.cucumber.junit.{Cucumber, CucumberOptions}
import io.cucumber.scala.{EN, ScalaDsl}
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.runner.RunWith
import org.openqa.selenium.{By, WebDriver}
import org.openqa.selenium.firefox.FirefoxDriver
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("classpath:features/change-password/change-password-landing-page.feature"),
  glue = Array("com.shoppe.changepassword.home"))
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@CucumberContextConfiguration
class ChangePasswordLandingPageIntegrationTest extends ScalaDsl with EN {

  var webDriver: WebDriver = null

  Given("""the user has logged into the system""") { () =>
    System.setProperty("webdriver.gecko.driver","src/test/resources/geckodriver")
    this.webDriver = new FirefoxDriver()
    this.webDriver.get("http://localhost:8080");
    this.webDriver.findElement(By.id("emailId")).sendKeys("almighty@test.com");
    this.webDriver.findElement(By.id("password")).sendKeys("welcome");
    this.webDriver.findElement(By.id("login")).click();
  }

  When("""the user hits the url change password url on the address bar""") { () =>
    this.webDriver.get("http://localhost:8080/change-password")
  }

  Then("""the user is expected to see the change password page""") { () =>
    val title = this.webDriver.getTitle
    assert("Shoppe : Change Password".equals(title))
    this.webDriver.close()
  }

}