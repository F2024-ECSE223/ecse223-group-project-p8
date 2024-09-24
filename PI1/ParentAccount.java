/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 46 "model.ump"
// line 138 "model.ump"
public class ParentAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ParentAccount Attributes
  private int phoneNumber;
  private String name;

  //ParentAccount Associations
  private List<Student> Children;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ParentAccount(String aAccountName, String aPassword, CoolSupplies aCoolSupplies, String aName)
  {
    super(aAccountName, aPassword, aCoolSupplies);
    name = aName;
    Children = new ArrayList<Student>();
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPhoneNumber(int aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public int getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public Student getChildren(int index)
  {
    Student aChildren = Children.get(index);
    return aChildren;
  }

  public List<Student> getChildren()
  {
    List<Student> newChildren = Collections.unmodifiableList(Children);
    return newChildren;
  }

  public int numberOfChildren()
  {
    int number = Children.size();
    return number;
  }

  public boolean hasChildren()
  {
    boolean has = Children.size() > 0;
    return has;
  }

  public int indexOfChildren(Student aChildren)
  {
    int index = Children.indexOf(aChildren);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfChildren()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Student addChildren(String aName, int aStudentID, CoolSupplies aCoolSupplies)
  {
    return new Student(aName, aStudentID, aCoolSupplies, this);
  }

  public boolean addChildren(Student aChildren)
  {
    boolean wasAdded = false;
    if (Children.contains(aChildren)) { return false; }
    ParentAccount existingParent = aChildren.getParent();
    boolean isNewParent = existingParent != null && !this.equals(existingParent);
    if (isNewParent)
    {
      aChildren.setParent(this);
    }
    else
    {
      Children.add(aChildren);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeChildren(Student aChildren)
  {
    boolean wasRemoved = false;
    //Unable to remove aChildren, as it must always have a parent
    if (!this.equals(aChildren.getParent()))
    {
      Children.remove(aChildren);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addChildrenAt(Student aChildren, int index)
  {  
    boolean wasAdded = false;
    if(addChildren(aChildren))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChildren()) { index = numberOfChildren() - 1; }
      Children.remove(aChildren);
      Children.add(index, aChildren);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveChildrenAt(Student aChildren, int index)
  {
    boolean wasAdded = false;
    if(Children.contains(aChildren))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfChildren()) { index = numberOfChildren() - 1; }
      Children.remove(aChildren);
      Children.add(index, aChildren);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addChildrenAt(aChildren, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(date aDateCreated, int aOrderNumber, boolean aIsFinalized, boolean aIsPaid, boolean aIsReceived, boolean aIsPickedUp, int aRegularAuthorizationCode, int aPenaltyAuthorizationCode, CoolSupplies aCoolSupplies)
  {
    return new Order(aDateCreated, aOrderNumber, aIsFinalized, aIsPaid, aIsReceived, aIsPickedUp, aRegularAuthorizationCode, aPenaltyAuthorizationCode, this, aCoolSupplies);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    ParentAccount existingPayer = aOrder.getPayer();
    boolean isNewPayer = existingPayer != null && !this.equals(existingPayer);
    if (isNewPayer)
    {
      aOrder.setPayer(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a payer
    if (!this.equals(aOrder.getPayer()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
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

  public void delete()
  {
    for(int i=Children.size(); i > 0; i--)
    {
      Student aChildren = Children.get(i - 1);
      aChildren.delete();
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "name" + ":" + getName()+ "]";
  }
}