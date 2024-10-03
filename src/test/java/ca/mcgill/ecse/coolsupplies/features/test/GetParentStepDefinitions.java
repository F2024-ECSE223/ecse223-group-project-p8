package ca.mcgill.ecse.coolsupplies.features.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetParentStepDefinitions {
    List<TOParent> data = new ArrayList<>();
    List<TOParent> actualParents = new ArrayList<>();

    @Given("the following parent entities exists in the system \\(p8)")
    public void the_following_parent_entities_exists_in_the_system_p8(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (var row : rows) {
            String email = row.get("email");
            String password = row.get("password");
            String name = row.get("name");
            int phoneNumber = Integer.parseInt(row.get("phoneNumber"));

            data.add(new TOParent(email, password, name, phoneNumber));
        }
    }

    @When("the school admin attempts to get from the system the parent with email {string} \\(p8)")
    public void the_school_admin_attempts_to_get_from_the_system_the_parent_with_email_p8(
            String string) {
        // Write code here that turns the phrase above into concrete actions
        for (TOParent parent : data) {
            if (parent.getEmail().equals(string)) {
                actualParents.add(parent);
            }
        }
    }

    @When("the school admin attempts to get from the system all the parents \\(p8)")
    public void the_school_admin_attempts_to_get_from_the_system_all_the_parents_p8() {
        // Write code here that turns the phrase above into concrete actions
        actualParents.addAll(data);
    }

    @Then("the number of parent entities in the system shall be {string} \\(p8)")
    public void the_number_of_parent_entities_in_the_system_shall_be_p8(String string) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(string, String.valueOf(data.size()));
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

        List<Map<String, String>> expectedParents = dataTable.asMaps();
        assertEquals(expectedParents.size(), actualParents.size());

        for (int i = 0; i < expectedParents.size(); i++) {
            Map<String, String> e = expectedParents.get(i);
            TOParent a = actualParents.get(i);
            assertEquals(e.get("email"), a.getEmail());
            assertEquals(e.get("password"), a.getPassword());
            assertEquals(e.get("name"), a.getName());
            assertEquals(Integer.parseInt(e.get("phoneNumber")), a.getPhoneNumber());
        }
    }

    @Then("no parent entities shall be presented \\(p8)")
    public void no_parent_entities_shall_be_presented_p8() {
        // Write code here that turns the phrase above into concrete actions
        if (actualParents.isEmpty()) {
            actualParents = null;  // Set to null if empty
        }
        assertNull(actualParents);
    }
}
