/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 25 "model.ump"
// line 127 "model.ump"
public class PurchasedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PurchasedItem Attributes
  private int quantityItem;

  //PurchasedItem Associations
  private SchoolSupply schoolSupply;
  private Order order;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetSchoolSupply;
  private boolean canSetOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PurchasedItem(int aQuantityItem, SchoolSupply aSchoolSupply, Order aOrder)
  {
    cachedHashCode = -1;
    canSetSchoolSupply = true;
    canSetOrder = true;
    quantityItem = aQuantityItem;
    boolean didAddSchoolSupply = setSchoolSupply(aSchoolSupply);
    if (!didAddSchoolSupply)
    {
      throw new RuntimeException("Unable to create purchasedItem due to schoolSupply. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddOrder = setOrder(aOrder);
    if (!didAddOrder)
    {
      throw new RuntimeException("Unable to create purchasedItem due to order. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantityItem(int aQuantityItem)
  {
    boolean wasSet = false;
    quantityItem = aQuantityItem;
    wasSet = true;
    return wasSet;
  }

  public int getQuantityItem()
  {
    return quantityItem;
  }
  /* Code from template association_GetOne */
  public SchoolSupply getSchoolSupply()
  {
    return schoolSupply;
  }
  /* Code from template association_GetOne */
  public Order getOrder()
  {
    return order;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setSchoolSupply(SchoolSupply aSchoolSupply)
  {
    boolean wasSet = false;
    if (!canSetSchoolSupply) { return false; }
    if (aSchoolSupply == null)
    {
      return wasSet;
    }

    SchoolSupply existingSchoolSupply = schoolSupply;
    schoolSupply = aSchoolSupply;
    if (existingSchoolSupply != null && !existingSchoolSupply.equals(aSchoolSupply))
    {
      existingSchoolSupply.removePurchasedItem(this);
    }
    if (!schoolSupply.addPurchasedItem(this))
    {
      schoolSupply = existingSchoolSupply;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setOrder(Order aOrder)
  {
    boolean wasSet = false;
    if (!canSetOrder) { return false; }
    if (aOrder == null)
    {
      return wasSet;
    }

    Order existingOrder = order;
    order = aOrder;
    if (existingOrder != null && !existingOrder.equals(aOrder))
    {
      existingOrder.removePurchasedItem(this);
    }
    if (!order.addPurchasedItem(this))
    {
      order = existingOrder;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    PurchasedItem compareTo = (PurchasedItem)obj;
  
    if (getSchoolSupply() == null && compareTo.getSchoolSupply() != null)
    {
      return false;
    }
    else if (getSchoolSupply() != null && !getSchoolSupply().equals(compareTo.getSchoolSupply()))
    {
      return false;
    }

    if (getOrder() == null && compareTo.getOrder() != null)
    {
      return false;
    }
    else if (getOrder() != null && !getOrder().equals(compareTo.getOrder()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getSchoolSupply() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getSchoolSupply().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getOrder() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getOrder().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetSchoolSupply = false;
    canSetOrder = false;
    return cachedHashCode;
  }

  public void delete()
  {
    SchoolSupply placeholderSchoolSupply = schoolSupply;
    this.schoolSupply = null;
    if(placeholderSchoolSupply != null)
    {
      placeholderSchoolSupply.removePurchasedItem(this);
    }
    Order placeholderOrder = order;
    this.order = null;
    if(placeholderOrder != null)
    {
      placeholderOrder.removePurchasedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantityItem" + ":" + getQuantityItem()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "schoolSupply = "+(getSchoolSupply()!=null?Integer.toHexString(System.identityHashCode(getSchoolSupply())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null");
  }
}