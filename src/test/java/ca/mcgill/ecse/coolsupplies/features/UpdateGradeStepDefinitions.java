package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateGradeStepDefinitions {
  @When("the school admin attempts to update grade {string} in the system with level {string} \\(p13)")
  public void the_school_admin_attempts_to_update_grade_in_the_system_with_level_p13(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the grade {string} shall not exist in the system \\(p13)")
  public void the_grade_shall_not_exist_in_the_system_p13(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following grade entities shall exist in the system \\(p13)")
  public void the_following_grade_entities_shall_exist_in_the_system_p13(
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

}
