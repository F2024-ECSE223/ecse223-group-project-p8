/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 34 "model.ump"
// line 128 "model.ump"
public abstract class UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, UserAccount> useraccountsByAccountName = new HashMap<String, UserAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String accountName;
  private String password;

  //UserAccount Associations
  private CoolSupplies coolSupplies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aAccountName, String aPassword, CoolSupplies aCoolSupplies)
  {
    password = aPassword;
    if (!setAccountName(aAccountName))
    {
      throw new RuntimeException("Cannot create due to duplicate accountName. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create userAccount due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAccountName(String aAccountName)
  {
    boolean wasSet = false;
    String anOldAccountName = getAccountName();
    if (anOldAccountName != null && anOldAccountName.equals(aAccountName)) {
      return true;
    }
    if (hasWithAccountName(aAccountName)) {
      return wasSet;
    }
    accountName = aAccountName;
    wasSet = true;
    if (anOldAccountName != null) {
      useraccountsByAccountName.remove(anOldAccountName);
    }
    useraccountsByAccountName.put(aAccountName, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getAccountName()
  {
    return accountName;
  }
  /* Code from template attribute_GetUnique */
  public static UserAccount getWithAccountName(String aAccountName)
  {
    return useraccountsByAccountName.get(aAccountName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithAccountName(String aAccountName)
  {
    return getWithAccountName(aAccountName) != null;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCoolSupplies(CoolSupplies aCoolSupplies)
  {
    boolean wasSet = false;
    if (aCoolSupplies == null)
    {
      return wasSet;
    }

    CoolSupplies existingCoolSupplies = coolSupplies;
    coolSupplies = aCoolSupplies;
    if (existingCoolSupplies != null && !existingCoolSupplies.equals(aCoolSupplies))
    {
      existingCoolSupplies.removeUserAccount(this);
    }
    coolSupplies.addUserAccount(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    useraccountsByAccountName.remove(getAccountName());
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeUserAccount(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "accountName" + ":" + getAccountName()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}