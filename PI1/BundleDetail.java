/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/



// line 13 "model.ump"
// line 119 "model.ump"
public class BundleDetail
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderType { Mandatory, MandatoryAndOptional, All }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BundleDetail Attributes
  private int suppliesPerBundle;
  private OrderType orderType;

  //BundleDetail Associations
  private Bundle bundle;
  private Item item;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetBundle;
  private boolean canSetItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BundleDetail(int aSuppliesPerBundle, OrderType aOrderType, Bundle aBundle, Item aItem)
  {
    cachedHashCode = -1;
    canSetBundle = true;
    canSetItem = true;
    suppliesPerBundle = aSuppliesPerBundle;
    orderType = aOrderType;
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create bundleDetail due to bundle. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create bundleDetail due to item. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSuppliesPerBundle(int aSuppliesPerBundle)
  {
    boolean wasSet = false;
    suppliesPerBundle = aSuppliesPerBundle;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderType(OrderType aOrderType)
  {
    boolean wasSet = false;
    orderType = aOrderType;
    wasSet = true;
    return wasSet;
  }

  public int getSuppliesPerBundle()
  {
    return suppliesPerBundle;
  }

  public OrderType getOrderType()
  {
    return orderType;
  }
  /* Code from template association_GetOne */
  public Bundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_GetOne */
  public Item getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setBundle(Bundle aBundle)
  {
    boolean wasSet = false;
    if (!canSetBundle) { return false; }
    if (aBundle == null)
    {
      return wasSet;
    }

    Bundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      existingBundle.removeBundleDetail(this);
    }
    if (!bundle.addBundleDetail(this))
    {
      bundle = existingBundle;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setItem(Item aItem)
  {
    boolean wasSet = false;
    if (!canSetItem) { return false; }
    if (aItem == null)
    {
      return wasSet;
    }

    Item existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeBundleDetail(this);
    }
    if (!item.addBundleDetail(this))
    {
      item = existingItem;
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

    BundleDetail compareTo = (BundleDetail)obj;
  
    if (getBundle() == null && compareTo.getBundle() != null)
    {
      return false;
    }
    else if (getBundle() != null && !getBundle().equals(compareTo.getBundle()))
    {
      return false;
    }

    if (getItem() == null && compareTo.getItem() != null)
    {
      return false;
    }
    else if (getItem() != null && !getItem().equals(compareTo.getItem()))
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
    if (getBundle() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getBundle().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getItem() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getItem().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetBundle = false;
    canSetItem = false;
    return cachedHashCode;
  }

  public void delete()
  {
    Bundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeBundleDetail(this);
    }
    Item placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeBundleDetail(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "suppliesPerBundle" + ":" + getSuppliesPerBundle()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "orderType" + "=" + (getOrderType() != null ? !getOrderType().equals(this)  ? getOrderType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "bundle = "+(getBundle()!=null?Integer.toHexString(System.identityHashCode(getBundle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}