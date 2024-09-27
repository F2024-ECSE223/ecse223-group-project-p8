/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 65 "model.ump"
// line 150 "model.ump"
public class AdminAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AdminAccount Attributes
  private String accountName;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AdminAccount(String aAccountName, String aPassword, CoolSupplies aCoolSupplies)
  {
    super(aAccountName, aPassword, aCoolSupplies);
    resetAccountName();
    resetPassword();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template attribute_SetDefaulted */
  public boolean setAccountName(String aAccountName)
  {
    boolean wasSet = false;
    accountName = aAccountName;
    wasSet = true;
    return wasSet;
  }

  public boolean resetAccountName()
  {
    boolean wasReset = false;
    accountName = getDefaultAccountName();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean resetPassword()
  {
    boolean wasReset = false;
    password = getDefaultPassword();
    wasReset = true;
    return wasReset;
  }

  public String getAccountName()
  {
    return accountName;
  }
  /* Code from template attribute_GetDefaulted */
  public String getDefaultAccountName()
  {
    return "admin@cool.ca";
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template attribute_GetDefaulted */
  public String getDefaultPassword()
  {
    return "admin";
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "accountName" + ":" + getAccountName()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}