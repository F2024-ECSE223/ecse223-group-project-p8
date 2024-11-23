package ca.mcgill.ecse.coolsupplies.application;

import ca.mcgill.ecse.coolsupplies.model.CoolSupplies;
import ca.mcgill.ecse.coolsupplies.persistence.CoolSuppliesPersistence;
import ca.mcgill.ecse.coolsupplies.javafx.fxml.CoolSuppliesFxmlView;
import javafx.application.Application;



public class CoolSuppliesApplication {

  private static CoolSupplies coolSupplies;


  public static void main(String[] args) {
    // TODO Start the application user interface here
    Application.launch(CoolSuppliesFxmlView.class, args);
  }

  public static CoolSupplies getCoolSupplies() {
    if (coolSupplies == null) {
      // load model
      coolSupplies = CoolSuppliesPersistence.load();
    }
    return coolSupplies;
  }

}
