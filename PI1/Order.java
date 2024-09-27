/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 87 "model.ump"
// line 166 "model.ump"
public class Order
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, Order> ordersByOrderNumber = new HashMap<int, Order>();
  private static Map<int, Order> ordersByRegularAuthorizationCode = new HashMap<int, Order>();
  private static Map<int, Order> ordersByPenaltyAuthorizationCode = new HashMap<int, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private date dateCreated;
  private int orderNumber;
  private boolean isFinalized;
  private boolean isPaid;
  private boolean isReceived;
  private boolean isPickedUp;
  private int regularAuthorizationCode;
  private int penaltyAuthorizationCode;

  //Order Associations
  private ParentAccount payer;
  private List<SchoolSupply> supplies;
  private CoolSupplies coolSupplies;
  private List<PurchasedItem> purchasedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(date aDateCreated, int aOrderNumber, boolean aIsFinalized, boolean aIsPaid, boolean aIsReceived, boolean aIsPickedUp, int aRegularAuthorizationCode, int aPenaltyAuthorizationCode, ParentAccount aPayer, CoolSupplies aCoolSupplies)
  {
    dateCreated = aDateCreated;
    isFinalized = aIsFinalized;
    isPaid = aIsPaid;
    isReceived = aIsReceived;
    isPickedUp = aIsPickedUp;
    if (!setOrderNumber(aOrderNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate orderNumber. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setRegularAuthorizationCode(aRegularAuthorizationCode))
    {
      throw new RuntimeException("Cannot create due to duplicate regularAuthorizationCode. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setPenaltyAuthorizationCode(aPenaltyAuthorizationCode))
    {
      throw new RuntimeException("Cannot create due to duplicate penaltyAuthorizationCode. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddPayer = setPayer(aPayer);
    if (!didAddPayer)
    {
      throw new RuntimeException("Unable to create order due to payer. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    supplies = new ArrayList<SchoolSupply>();
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create order due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    purchasedItems = new ArrayList<PurchasedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDateCreated(date aDateCreated)
  {
    boolean wasSet = false;
    dateCreated = aDateCreated;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderNumber(int aOrderNumber)
  {
    boolean wasSet = false;
    int anOldOrderNumber = getOrderNumber();
    if (anOldOrderNumber != null && anOldOrderNumber.equals(aOrderNumber)) {
      return true;
    }
    if (hasWithOrderNumber(aOrderNumber)) {
      return wasSet;
    }
    orderNumber = aOrderNumber;
    wasSet = true;
    if (anOldOrderNumber != null) {
      ordersByOrderNumber.remove(anOldOrderNumber);
    }
    ordersByOrderNumber.put(aOrderNumber, this);
    return wasSet;
  }

  public boolean setIsFinalized(boolean aIsFinalized)
  {
    boolean wasSet = false;
    isFinalized = aIsFinalized;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPaid(boolean aIsPaid)
  {
    boolean wasSet = false;
    isPaid = aIsPaid;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsReceived(boolean aIsReceived)
  {
    boolean wasSet = false;
    isReceived = aIsReceived;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsPickedUp(boolean aIsPickedUp)
  {
    boolean wasSet = false;
    isPickedUp = aIsPickedUp;
    wasSet = true;
    return wasSet;
  }

  public boolean setRegularAuthorizationCode(int aRegularAuthorizationCode)
  {
    boolean wasSet = false;
    int anOldRegularAuthorizationCode = getRegularAuthorizationCode();
    if (anOldRegularAuthorizationCode != null && anOldRegularAuthorizationCode.equals(aRegularAuthorizationCode)) {
      return true;
    }
    if (hasWithRegularAuthorizationCode(aRegularAuthorizationCode)) {
      return wasSet;
    }
    regularAuthorizationCode = aRegularAuthorizationCode;
    wasSet = true;
    if (anOldRegularAuthorizationCode != null) {
      ordersByRegularAuthorizationCode.remove(anOldRegularAuthorizationCode);
    }
    ordersByRegularAuthorizationCode.put(aRegularAuthorizationCode, this);
    return wasSet;
  }

  public boolean setPenaltyAuthorizationCode(int aPenaltyAuthorizationCode)
  {
    boolean wasSet = false;
    int anOldPenaltyAuthorizationCode = getPenaltyAuthorizationCode();
    if (anOldPenaltyAuthorizationCode != null && anOldPenaltyAuthorizationCode.equals(aPenaltyAuthorizationCode)) {
      return true;
    }
    if (hasWithPenaltyAuthorizationCode(aPenaltyAuthorizationCode)) {
      return wasSet;
    }
    penaltyAuthorizationCode = aPenaltyAuthorizationCode;
    wasSet = true;
    if (anOldPenaltyAuthorizationCode != null) {
      ordersByPenaltyAuthorizationCode.remove(anOldPenaltyAuthorizationCode);
    }
    ordersByPenaltyAuthorizationCode.put(aPenaltyAuthorizationCode, this);
    return wasSet;
  }

  public date getDateCreated()
  {
    return dateCreated;
  }

  public int getOrderNumber()
  {
    return orderNumber;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithOrderNumber(int aOrderNumber)
  {
    return ordersByOrderNumber.get(aOrderNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithOrderNumber(int aOrderNumber)
  {
    return getWithOrderNumber(aOrderNumber) != null;
  }

  public boolean getIsFinalized()
  {
    return isFinalized;
  }

  public boolean getIsPaid()
  {
    return isPaid;
  }

  public boolean getIsReceived()
  {
    return isReceived;
  }

  public boolean getIsPickedUp()
  {
    return isPickedUp;
  }

  public int getRegularAuthorizationCode()
  {
    return regularAuthorizationCode;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithRegularAuthorizationCode(int aRegularAuthorizationCode)
  {
    return ordersByRegularAuthorizationCode.get(aRegularAuthorizationCode);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithRegularAuthorizationCode(int aRegularAuthorizationCode)
  {
    return getWithRegularAuthorizationCode(aRegularAuthorizationCode) != null;
  }

  public int getPenaltyAuthorizationCode()
  {
    return penaltyAuthorizationCode;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithPenaltyAuthorizationCode(int aPenaltyAuthorizationCode)
  {
    return ordersByPenaltyAuthorizationCode.get(aPenaltyAuthorizationCode);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithPenaltyAuthorizationCode(int aPenaltyAuthorizationCode)
  {
    return getWithPenaltyAuthorizationCode(aPenaltyAuthorizationCode) != null;
  }
  /* Code from template association_GetOne */
  public ParentAccount getPayer()
  {
    return payer;
  }
  /* Code from template association_GetMany */
  public SchoolSupply getSupply(int index)
  {
    SchoolSupply aSupply = supplies.get(index);
    return aSupply;
  }

  public List<SchoolSupply> getSupplies()
  {
    List<SchoolSupply> newSupplies = Collections.unmodifiableList(supplies);
    return newSupplies;
  }

  public int numberOfSupplies()
  {
    int number = supplies.size();
    return number;
  }

  public boolean hasSupplies()
  {
    boolean has = supplies.size() > 0;
    return has;
  }

  public int indexOfSupply(SchoolSupply aSupply)
  {
    int index = supplies.indexOf(aSupply);
    return index;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetMany */
  public PurchasedItem getPurchasedItem(int index)
  {
    PurchasedItem aPurchasedItem = purchasedItems.get(index);
    return aPurchasedItem;
  }

  public List<PurchasedItem> getPurchasedItems()
  {
    List<PurchasedItem> newPurchasedItems = Collections.unmodifiableList(purchasedItems);
    return newPurchasedItems;
  }

  public int numberOfPurchasedItems()
  {
    int number = purchasedItems.size();
    return number;
  }

  public boolean hasPurchasedItems()
  {
    boolean has = purchasedItems.size() > 0;
    return has;
  }

  public int indexOfPurchasedItem(PurchasedItem aPurchasedItem)
  {
    int index = purchasedItems.indexOf(aPurchasedItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPayer(ParentAccount aPayer)
  {
    boolean wasSet = false;
    if (aPayer == null)
    {
      return wasSet;
    }

    ParentAccount existingPayer = payer;
    payer = aPayer;
    if (existingPayer != null && !existingPayer.equals(aPayer))
    {
      existingPayer.removeOrder(this);
    }
    payer.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSupplies()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addSupply(SchoolSupply aSupply)
  {
    boolean wasAdded = false;
    if (supplies.contains(aSupply)) { return false; }
    supplies.add(aSupply);
    if (aSupply.indexOfOrder(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aSupply.addOrder(this);
      if (!wasAdded)
      {
        supplies.remove(aSupply);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeSupply(SchoolSupply aSupply)
  {
    boolean wasRemoved = false;
    if (!supplies.contains(aSupply))
    {
      return wasRemoved;
    }

    int oldIndex = supplies.indexOf(aSupply);
    supplies.remove(oldIndex);
    if (aSupply.indexOfOrder(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aSupply.removeOrder(this);
      if (!wasRemoved)
      {
        supplies.add(oldIndex,aSupply);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSupplyAt(SchoolSupply aSupply, int index)
  {  
    boolean wasAdded = false;
    if(addSupply(aSupply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSupplies()) { index = numberOfSupplies() - 1; }
      supplies.remove(aSupply);
      supplies.add(index, aSupply);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSupplyAt(SchoolSupply aSupply, int index)
  {
    boolean wasAdded = false;
    if(supplies.contains(aSupply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSupplies()) { index = numberOfSupplies() - 1; }
      supplies.remove(aSupply);
      supplies.add(index, aSupply);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSupplyAt(aSupply, index);
    }
    return wasAdded;
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
      existingCoolSupplies.removeOrder(this);
    }
    coolSupplies.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPurchasedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PurchasedItem addPurchasedItem(int aQuantityItem, SchoolSupply aSchoolSupply)
  {
    return new PurchasedItem(aQuantityItem, aSchoolSupply, this);
  }

  public boolean addPurchasedItem(PurchasedItem aPurchasedItem)
  {
    boolean wasAdded = false;
    if (purchasedItems.contains(aPurchasedItem)) { return false; }
    Order existingOrder = aPurchasedItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aPurchasedItem.setOrder(this);
    }
    else
    {
      purchasedItems.add(aPurchasedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePurchasedItem(PurchasedItem aPurchasedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aPurchasedItem, as it must always have a order
    if (!this.equals(aPurchasedItem.getOrder()))
    {
      purchasedItems.remove(aPurchasedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPurchasedItemAt(PurchasedItem aPurchasedItem, int index)
  {  
    boolean wasAdded = false;
    if(addPurchasedItem(aPurchasedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchasedItems()) { index = numberOfPurchasedItems() - 1; }
      purchasedItems.remove(aPurchasedItem);
      purchasedItems.add(index, aPurchasedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePurchasedItemAt(PurchasedItem aPurchasedItem, int index)
  {
    boolean wasAdded = false;
    if(purchasedItems.contains(aPurchasedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPurchasedItems()) { index = numberOfPurchasedItems() - 1; }
      purchasedItems.remove(aPurchasedItem);
      purchasedItems.add(index, aPurchasedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPurchasedItemAt(aPurchasedItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ordersByOrderNumber.remove(getOrderNumber());
    ordersByRegularAuthorizationCode.remove(getRegularAuthorizationCode());
    ordersByPenaltyAuthorizationCode.remove(getPenaltyAuthorizationCode());
    ParentAccount placeholderPayer = payer;
    this.payer = null;
    if(placeholderPayer != null)
    {
      placeholderPayer.removeOrder(this);
    }
    ArrayList<SchoolSupply> copyOfSupplies = new ArrayList<SchoolSupply>(supplies);
    supplies.clear();
    for(SchoolSupply aSupply : copyOfSupplies)
    {
      aSupply.removeOrder(this);
    }
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeOrder(this);
    }
    for(int i=purchasedItems.size(); i > 0; i--)
    {
      PurchasedItem aPurchasedItem = purchasedItems.get(i - 1);
      aPurchasedItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "orderNumber" + ":" + getOrderNumber()+ "," +
            "isFinalized" + ":" + getIsFinalized()+ "," +
            "isPaid" + ":" + getIsPaid()+ "," +
            "isReceived" + ":" + getIsReceived()+ "," +
            "isPickedUp" + ":" + getIsPickedUp()+ "," +
            "regularAuthorizationCode" + ":" + getRegularAuthorizationCode()+ "," +
            "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dateCreated" + "=" + (getDateCreated() != null ? !getDateCreated().equals(this)  ? getDateCreated().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "payer = "+(getPayer()!=null?Integer.toHexString(System.identityHashCode(getPayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}