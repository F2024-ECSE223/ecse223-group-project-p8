/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 78 "model.ump"
// line 158 "model.ump"
public class Bundle extends SchoolSupply
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderType { Mandatory, MandatoryAndOptional, All }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bundle Attributes
  private int discount;

  //Bundle Associations
  private List<Item> items;
  private Grade grade;
  private List<BundleDetail> bundleDetails;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bundle(CoolSupplies aCoolSupplies, int aDiscount, Grade aGrade)
  {
    super(aCoolSupplies);
    discount = aDiscount;
    items = new ArrayList<Item>();
    boolean didAddGrade = setGrade(aGrade);
    if (!didAddGrade)
    {
      throw new RuntimeException("Unable to create bundle due to grade. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bundleDetails = new ArrayList<BundleDetail>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDiscount(int aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public int getDiscount()
  {
    return discount;
  }
  /* Code from template association_GetMany */
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetOne */
  public Grade getGrade()
  {
    return grade;
  }
  /* Code from template association_GetMany */
  public BundleDetail getBundleDetail(int index)
  {
    BundleDetail aBundleDetail = bundleDetails.get(index);
    return aBundleDetail;
  }

  public List<BundleDetail> getBundleDetails()
  {
    List<BundleDetail> newBundleDetails = Collections.unmodifiableList(bundleDetails);
    return newBundleDetails;
  }

  public int numberOfBundleDetails()
  {
    int number = bundleDetails.size();
    return number;
  }

  public boolean hasBundleDetails()
  {
    boolean has = bundleDetails.size() > 0;
    return has;
  }

  public int indexOfBundleDetail(BundleDetail aBundleDetail)
  {
    int index = bundleDetails.indexOf(aBundleDetail);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    items.add(aItem);
    if (aItem.indexOfBundle(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aItem.addBundle(this);
      if (!wasAdded)
      {
        items.remove(aItem);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    if (!items.contains(aItem))
    {
      return wasRemoved;
    }

    int oldIndex = items.indexOf(aItem);
    items.remove(oldIndex);
    if (aItem.indexOfBundle(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aItem.removeBundle(this);
      if (!wasRemoved)
      {
        items.add(oldIndex,aItem);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGrade(Grade aGrade)
  {
    boolean wasSet = false;
    if (aGrade == null)
    {
      return wasSet;
    }

    Grade existingGrade = grade;
    grade = aGrade;
    if (existingGrade != null && !existingGrade.equals(aGrade))
    {
      existingGrade.removeBundle(this);
    }
    grade.addBundle(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleDetails()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleDetail addBundleDetail(int aSuppliesPerBundle, OrderType aOrderType, Item aItem)
  {
    return new BundleDetail(aSuppliesPerBundle, aOrderType, this, aItem);
  }

  public boolean addBundleDetail(BundleDetail aBundleDetail)
  {
    boolean wasAdded = false;
    if (bundleDetails.contains(aBundleDetail)) { return false; }
    Bundle existingBundle = aBundleDetail.getBundle();
    boolean isNewBundle = existingBundle != null && !this.equals(existingBundle);
    if (isNewBundle)
    {
      aBundleDetail.setBundle(this);
    }
    else
    {
      bundleDetails.add(aBundleDetail);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundleDetail(BundleDetail aBundleDetail)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundleDetail, as it must always have a bundle
    if (!this.equals(aBundleDetail.getBundle()))
    {
      bundleDetails.remove(aBundleDetail);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleDetailAt(BundleDetail aBundleDetail, int index)
  {  
    boolean wasAdded = false;
    if(addBundleDetail(aBundleDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleDetails()) { index = numberOfBundleDetails() - 1; }
      bundleDetails.remove(aBundleDetail);
      bundleDetails.add(index, aBundleDetail);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleDetailAt(BundleDetail aBundleDetail, int index)
  {
    boolean wasAdded = false;
    if(bundleDetails.contains(aBundleDetail))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleDetails()) { index = numberOfBundleDetails() - 1; }
      bundleDetails.remove(aBundleDetail);
      bundleDetails.add(index, aBundleDetail);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleDetailAt(aBundleDetail, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Item> copyOfItems = new ArrayList<Item>(items);
    items.clear();
    for(Item aItem : copyOfItems)
    {
      aItem.removeBundle(this);
    }
    Grade placeholderGrade = grade;
    this.grade = null;
    if(placeholderGrade != null)
    {
      placeholderGrade.removeBundle(this);
    }
    for(int i=bundleDetails.size(); i > 0; i--)
    {
      BundleDetail aBundleDetail = bundleDetails.get(i - 1);
      aBundleDetail.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "discount" + ":" + getDiscount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "grade = "+(getGrade()!=null?Integer.toHexString(System.identityHashCode(getGrade())):"null");
  }
}