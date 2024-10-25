package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RemoveBundleItemFromBundleItemsOfGradeBundleStepDefinitions {

  @When("the school admin attempts to remove a bundle item with name {string} from a grade bundle with name {string} \\(p11)")
  public void the_school_admin_attempts_to_remove_a_bundle_item_with_name_from_a_grade_bundle_with_name_p11(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }


  @Then("the following bundle item entities shall exist in the system \\(p11)")
  public void the_following_bundle_item_entities_shall_exist_in_the_system_p11(
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

  @Then("the error {string} shall be raised \\(p11)")
  public void the_error_shall_be_raised_p11(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
