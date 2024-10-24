package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetStudentStepDefinitions {
  @When("the school admin attempts to get from the system the student with name {string} \\(p5)")
  public void the_school_admin_attempts_to_get_from_the_system_the_student_with_name_p5(
      String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the school admin attempts to get from the system all the students \\(p5)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_students_p5() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following student entities shall be presented \\(p5)")
  public void the_following_student_entities_shall_be_presented_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    throw new io.cucumber.java.PendingException();
  }


  @Then("no student entities shall be presented \\(p5)")
  public void no_student_entities_shall_be_presented_p5() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

}
