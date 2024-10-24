package ca.mcgill.ecse.coolsupplies.features;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UpdateBundleItemInBundleItemsOfGradeBundleStepDefinitions {
  @When("the school admin attempts to update a bundle item {string} of grade bundle {string} with quantity {string} and level {string} \\(p12)")
  public void the_school_admin_attempts_to_update_a_bundle_item_of_grade_bundle_with_quantity_and_level_p12(
      String string, String string2, String string3, String string4) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the bundle item with quantity {string}, level {string}, and item name {string} shall not exist for grade bundle {string} in the system \\(p12)")
  public void the_bundle_item_with_quantity_level_and_item_name_shall_not_exist_for_grade_bundle_in_the_system_p12(
      String string, String string2, String string3, String string4) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the following bundle item entities shall exist in the system \\(p12)")
  public void the_following_bundle_item_entities_shall_exist_in_the_system_p12(
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
