package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetParentStepDefinitions {
  @When("the school admin attempts to get from the system the parent with email {string} \\(p8)")
  public void the_school_admin_attempts_to_get_from_the_system_the_parent_with_email_p8(
      String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the school admin attempts to get from the system all the parents \\(p8)")
  public void the_school_admin_attempts_to_get_from_the_system_all_the_parents_p8() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following parent entities shall be presented \\(p8)")
  public void the_following_parent_entities_shall_be_presented_p8(
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

  @Then("no parent entities shall be presented \\(p8)")
  public void no_parent_entities_shall_be_presented_p8() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
