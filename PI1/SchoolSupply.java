/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 41 "model.ump"
// line 133 "model.ump"
public abstract class SchoolSupply
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SchoolSupply Associations
  private CoolSupplies coolSupplies;
  private List<Order> orders;
  private List<PurchasedItem> purchasedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SchoolSupply(CoolSupplies aCoolSupplies)
  {
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create schoolSupply due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orders = new ArrayList<Order>();
    purchasedItems = new ArrayList<PurchasedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
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
      existingCoolSupplies.removeSchoolSupply(this);
    }
    coolSupplies.addSchoolSupply(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    orders.add(aOrder);
    if (aOrder.indexOfSupply(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOrder.addSupply(this);
      if (!wasAdded)
      {
        orders.remove(aOrder);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    if (!orders.contains(aOrder))
    {
      return wasRemoved;
    }

    int oldIndex = orders.indexOf(aOrder);
    orders.remove(oldIndex);
    if (aOrder.indexOfSupply(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOrder.removeSupply(this);
      if (!wasRemoved)
      {
        orders.add(oldIndex,aOrder);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPurchasedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PurchasedItem addPurchasedItem(int aQuantityItem, Order aOrder)
  {
    return new PurchasedItem(aQuantityItem, this, aOrder);
  }

  public boolean addPurchasedItem(PurchasedItem aPurchasedItem)
  {
    boolean wasAdded = false;
    if (purchasedItems.contains(aPurchasedItem)) { return false; }
    SchoolSupply existingSchoolSupply = aPurchasedItem.getSchoolSupply();
    boolean isNewSchoolSupply = existingSchoolSupply != null && !this.equals(existingSchoolSupply);
    if (isNewSchoolSupply)
    {
      aPurchasedItem.setSchoolSupply(this);
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
    //Unable to remove aPurchasedItem, as it must always have a schoolSupply
    if (!this.equals(aPurchasedItem.getSchoolSupply()))
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
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeSchoolSupply(this);
    }
    ArrayList<Order> copyOfOrders = new ArrayList<Order>(orders);
    orders.clear();
    for(Order aOrder : copyOfOrders)
    {
      aOrder.removeSupply(this);
    }
    for(int i=purchasedItems.size(); i > 0; i--)
    {
      PurchasedItem aPurchasedItem = purchasedItems.get(i - 1);
      aPurchasedItem.delete();
    }
  }

}