package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateStudentStepDefinitions {

  @When("the school admin attempts to update student {string} in the system with name {string} and grade level {string} \\(p4)")
  public void the_school_admin_attempts_to_update_student_in_the_system_with_name_and_grade_level_p4(
      String string, String string2, String string3) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the student {string} with grade level {string} shall not exist in the system \\(p4)")
  public void the_student_with_grade_level_shall_not_exist_in_the_system_p4(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following student entities shall exist in the system \\(p4)")
  public void the_following_student_entities_shall_exist_in_the_system_p4(
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
