package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;

public class DeleteParentStepDefinitions {

  String error;

  @Given("the following parent entities exists in the system \\(p8)")
  public void the_following_parent_entities_exists_in_the_system_p8(
          io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row: rows){
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      int phoneNumber = Integer.parseInt(row.get("phoneNumber"));
      CoolSuppliesFeatureSet1Controller.addParent(email, password, name, phoneNumber);
    }
  }

  @When("the school Administrator attempts to delete from the system the parent with email {string} \\(p8)")
  public void the_school_administrator_attempts_to_delete_from_the_system_the_parent_with_email_p8(
          String string) {
    // Write code here that turns the phrase above into concrete actions
    error = CoolSuppliesFeatureSet1Controller.deleteParent(string);
  }

//  @Then("the number of parent entities in the system shall be {string} \\(p8)")
//  public void the_number_of_parent_entities_in_the_system_shall_be_p8(String string) {
//    // Write code here that turns the phrase above into concrete actions
//    int numberOfParents = CoolSuppliesFeatureSet1Controller.getParents().size();
//    assertEquals(Integer.parseInt(string), numberOfParents);
//  }

  @Then("the following parent entities shall exist in the system \\(p8)")
  public void the_following_parent_entities_shall_exist_in_the_system_p8(
          io.cucumber.datatable.DataTable dataTable) {
    // Write code here that turns the phrase above into concrete actions
    // For automatic transformation, change DataTable to one of
    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
    // Double, Byte, Short, Long, BigInteger or BigDecimal.
    //
    // For other transformations you can register a DataTableType.
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row: rows){
      try{
        String email = row.get("email");
        TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(email);
        String password = row.get("password");
        assertEquals(password, parent.getPassword());
        String name = row.get("name");
        assertEquals(name, parent.getName());
        int phoneNumber = Integer.parseInt(row.get("phoneNumber"));
        assertEquals(phoneNumber, parent.getPhoneNumber());
      }
      catch(Exception e){
        the_error_shall_be_raised_p8("The parent does not exist.");
      }
    }
  }

  @Then("the error {string} shall be raised \\(p8)")
  public void the_error_shall_be_raised_p8(String string) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(string, error);
  }
}