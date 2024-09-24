/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 54 "model.ump"
// line 143 "model.ump"
public class Item extends SchoolSupply
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum OrderType { Mandatory, MandatoryAndOptional, All }
  public enum Requirement { Mandatory, Optional, Recommended }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private String name;
  private Requirement requirement;
  private int price;

  //Item Associations
  private List<Bundle> bundles;
  private List<BundleDetail> bundleDetails;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(CoolSupplies aCoolSupplies, String aName, Requirement aRequirement, int aPrice)
  {
    super(aCoolSupplies);
    name = aName;
    requirement = aRequirement;
    price = aPrice;
    bundles = new ArrayList<Bundle>();
    bundleDetails = new ArrayList<BundleDetail>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequirement(Requirement aRequirement)
  {
    boolean wasSet = false;
    requirement = aRequirement;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Requirement getRequirement()
  {
    return requirement;
  }

  public int getPrice()
  {
    return price;
  }
  /* Code from template association_GetMany */
  public Bundle getBundle(int index)
  {
    Bundle aBundle = bundles.get(index);
    return aBundle;
  }

  public List<Bundle> getBundles()
  {
    List<Bundle> newBundles = Collections.unmodifiableList(bundles);
    return newBundles;
  }

  public int numberOfBundles()
  {
    int number = bundles.size();
    return number;
  }

  public boolean hasBundles()
  {
    boolean has = bundles.size() > 0;
    return has;
  }

  public int indexOfBundle(Bundle aBundle)
  {
    int index = bundles.indexOf(aBundle);
    return index;
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
  public static int minimumNumberOfBundles()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addBundle(Bundle aBundle)
  {
    boolean wasAdded = false;
    if (bundles.contains(aBundle)) { return false; }
    bundles.add(aBundle);
    if (aBundle.indexOfItem(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBundle.addItem(this);
      if (!wasAdded)
      {
        bundles.remove(aBundle);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeBundle(Bundle aBundle)
  {
    boolean wasRemoved = false;
    if (!bundles.contains(aBundle))
    {
      return wasRemoved;
    }

    int oldIndex = bundles.indexOf(aBundle);
    bundles.remove(oldIndex);
    if (aBundle.indexOfItem(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBundle.removeItem(this);
      if (!wasRemoved)
      {
        bundles.add(oldIndex,aBundle);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleAt(Bundle aBundle, int index)
  {  
    boolean wasAdded = false;
    if(addBundle(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleAt(Bundle aBundle, int index)
  {
    boolean wasAdded = false;
    if(bundles.contains(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleAt(aBundle, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleDetails()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleDetail addBundleDetail(int aSuppliesPerBundle, OrderType aOrderType, Bundle aBundle)
  {
    return new BundleDetail(aSuppliesPerBundle, aOrderType, aBundle, this);
  }

  public boolean addBundleDetail(BundleDetail aBundleDetail)
  {
    boolean wasAdded = false;
    if (bundleDetails.contains(aBundleDetail)) { return false; }
    Item existingItem = aBundleDetail.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aBundleDetail.setItem(this);
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
    //Unable to remove aBundleDetail, as it must always have a item
    if (!this.equals(aBundleDetail.getItem()))
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
    ArrayList<Bundle> copyOfBundles = new ArrayList<Bundle>(bundles);
    bundles.clear();
    for(Bundle aBundle : copyOfBundles)
    {
      aBundle.removeItem(this);
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
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "requirement" + "=" + (getRequirement() != null ? !getRequirement().equals(this)  ? getRequirement().toString().replaceAll("  ","    ") : "this" : "null");
  }
}