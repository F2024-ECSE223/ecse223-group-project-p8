package ca.mcgill.ecse.coolsupplies.features;

import ca.mcgill.ecse.coolsupplies.controller.CoolSuppliesFeatureSet1Controller;
import ca.mcgill.ecse.coolsupplies.controller.TOParent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Zhengxuan Zhao
 * @author Shengyi Zhong
 * @author Snigdha Sen
 * @author Jiatian Liu
 */

public class GetParentStepDefinitions {

    private List<TOParent> actualParentList = new ArrayList<>();

    /**
     * Step definition for attempting to get a parent by their email from the system.
     * If the parent is found, it is added to the actualParentList.
     *
     * @param string the email of the parent to retrieve
     */
    @When("the school admin attempts to get from the system the parent with email {string} \\(p8)")
    public void the_school_admin_attempts_to_get_from_the_system_the_parent_with_email_p8(
            String string) {
        TOParent parent = CoolSuppliesFeatureSet1Controller.getParent(string);

        // Check if the parent is not found, return without adding to the list
        if(parent == null) return;
        actualParentList.add(parent);
    }

    /**
     * Step definition for retrieving all parents from the system.
     * The retrieved parents are added to the actualParentList.
     */
    @When("the school admin attempts to get from the system all the parents \\(p8)")
    public void the_school_admin_attempts_to_get_from_the_system_all_the_parents_p8() {
        // Retrieve all parents and add them to the actual parent list
        actualParentList.addAll(CoolSuppliesFeatureSet1Controller.getParents());
    }

    /**
     * Step definition for validating that the expected parents are presented.
     * The expected parent data is passed in a Cucumber DataTable, and this method
     * asserts that the actual parents match the expected data.
     *
     * @param dataTable a DataTable containing the expected parent data
     */
    @Then("the following parent entities shall be presented \\(p8)")
    public void the_following_parent_entities_shall_be_presented_p8(
            io.cucumber.datatable.DataTable dataTable) {
        // Convert the expected parent data from the DataTable into a list of maps
        List<Map<String, String>> expectedParentList = dataTable.asMaps();

        // Assert that the size of the expected list matches the actual list
        assertEquals(expectedParentList.size(), actualParentList.size());

        // Loop through each expected parent and verify it exists in the actual list
        for (Map<String, String> expectedParent : expectedParentList) {
            boolean matchFound = false;

            for (TOParent actualParent : actualParentList) {
                if (expectedParent.get("email").equals(actualParent.getEmail()) &&
                        expectedParent.get("password").equals(actualParent.getPassword()) &&
                        expectedParent.get("name").equals(actualParent.getName()) &&
                        Integer.parseInt(expectedParent.get("phoneNumber")) == actualParent.getPhoneNumber()) {
                    matchFound = true;
                    break;
                }
            }

            assertTrue(matchFound, "Expected parent not found: " + expectedParent);
        }
    }

    /**
     * Step definition for validating that no parent entities are presented.
     * This method asserts that the actualParentList is empty.
     */
    @Then("no parent entities shall be presented \\(p8)")
    public void no_parent_entities_shall_be_presented_p8() {
        assertTrue(actualParentList.isEmpty(), "Expected no parents, but found some.");
    }
}
