/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/


import ca.mcgill.ecse.coolsupplies.model.BundleItem.PurchaseLevel;
import java.util.*;
import java.sql.Date;

// line 39 "model.ump"
// line 85 "model.ump"
// line 308 "model.ump"
public class Order
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Order> ordersByNumber = new HashMap<Integer, Order>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private int number;
  private Date date;
  private PurchaseLevel level;
  private String authorizationCode;
  private String penaltyAuthorizationCode;
  private String inventoryItems;

  //Order State Machines
  public enum Status { Started, Paid, Penalized, Prepared, Canceled, Final, PickedUp }
  private Status status;

  //Order Associations
  private Parent parent;
  private Student student;
  private CoolSupplies coolSupplies;
  private List<OrderItem> orderItems;

  //Helper Variables
  private boolean canSetInventoryItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(int aNumber, Date aDate, PurchaseLevel aLevel, Parent aParent, Student aStudent, CoolSupplies aCoolSupplies)
  {
    date = aDate;
    level = aLevel;
    authorizationCode = null;
    penaltyAuthorizationCode = null;
    canSetInventoryItems = true;
    if (!setNumber(aNumber))
    {
      throw new RuntimeException("Cannot create due to duplicate number. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddParent = setParent(aParent);
    if (!didAddParent)
    {
      throw new RuntimeException("Unable to create order due to parent. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddStudent = setStudent(aStudent);
    if (!didAddStudent)
    {
      throw new RuntimeException("Unable to create order due to student. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create order due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    orderItems = new ArrayList<OrderItem>();
    setStatus(Status.Started);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNumber(int aNumber)
  {
    boolean wasSet = false;
    Integer anOldNumber = getNumber();
    if (anOldNumber != null && anOldNumber.equals(aNumber)) {
      return true;
    }
    if (hasWithNumber(aNumber)) {
      return wasSet;
    }
    number = aNumber;
    wasSet = true;
    if (anOldNumber != null) {
      ordersByNumber.remove(anOldNumber);
    }
    ordersByNumber.put(aNumber, this);
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setLevel(PurchaseLevel aLevel)
  {
    boolean wasSet = false;
    level = aLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setAuthorizationCode(String aAuthorizationCode)
  {
    boolean wasSet = false;
    authorizationCode = aAuthorizationCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPenaltyAuthorizationCode(String aPenaltyAuthorizationCode)
  {
    boolean wasSet = false;
    penaltyAuthorizationCode = aPenaltyAuthorizationCode;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetImmutable */
  public boolean setInventoryItems(String aInventoryItems)
  {
    boolean wasSet = false;
    if (!canSetInventoryItems) { return false; }
    canSetInventoryItems = false;
    inventoryItems = aInventoryItems;
    wasSet = true;
    return wasSet;
  }

  public int getNumber()
  {
    return number;
  }
  /* Code from template attribute_GetUnique */
  public static Order getWithNumber(int aNumber)
  {
    return ordersByNumber.get(aNumber);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithNumber(int aNumber)
  {
    return getWithNumber(aNumber) != null;
  }

  public Date getDate()
  {
    return date;
  }

  public PurchaseLevel getLevel()
  {
    return level;
  }

  public String getAuthorizationCode()
  {
    return authorizationCode;
  }

  public String getPenaltyAuthorizationCode()
  {
    return penaltyAuthorizationCode;
  }

  public String getInventoryItems()
  {
    return inventoryItems;
  }

  public String getStatusFullName()
  {
    String answer = status.toString();
    return answer;
  }

  public Status getStatus()
  {
    return status;
  }

  private void cancelOrder()
  {
    switch(status)
    {
      case Started:
        this.delete();
        return;
      case Paid:
        this.delete();
        return;
      default:
        return;
    }
  }

  private void updateOrder(PurchaseLevel purchaseLevel, Student student)
  {
    switch(status)
    {
      case Started:
        this.setLevel(purchaseLevel);
        this.setStudent(student);
        return;
      default:
        return;
    }
  }

  private void addItemToOrder(InventoryItem item)
  {
    switch(status)
    {
      case Started:
        this.addItemToOrder(item);
        return;
      default:
        return;
    }
  }

  private void updateQuantity(int newQuantity, OrderItem item)
  {
    switch(status)
    {
      case Started:
        item.setQuantity(newQuantity)
        return;
      default:
        return;
    }
  }

  private void deleteOrderItem(OrderItem item)
  {
    switch(status)
    {
      case Started:
        item.delete();
        return;
      default:
        return;
    }
  }

  private void rejectUpdate()
  {
    switch(status)
    {
      case Paid:
        throw new RuntimeException("Cannot update a paid order");
        return;
      case Penalized:
        throw new RuntimeException("Cannot update a penalized order");
        return;
      case Prepared:
        throw new RuntimeException("Cannot update a prepared order");
        return;
      case PickedUp:
        throw new RuntimeException("Cannot update a picked up order");
        return;
      default:
        return;
    }
  }

  private void rejectAdd()
  {
    switch(status)
    {
      case Paid:
        throw new RuntimeException("Cannot add items to a paid order");
        return;
      case Penalized:
        throw new RuntimeException("Cannot add items to a penalized order");
        return;
      case Prepared:
        throw new RuntimeException("Cannot add items to a prepared order");
        return;
      case PickedUp:
        throw new RuntimeException("Cannot add items to a picked up order");
        return;
      default:
        return;
    }
  }

  private void rejectUpdateQ()
  {
    switch(status)
    {
      case Paid:
        throw new RuntimeException("Cannot update items in a paid order");
        return;
      case Penalized:
        throw new RuntimeException("Cannot update items in a penalized order");
        return;
      case Prepared:
        throw new RuntimeException("Cannot update items in a prepared order");
        return;
      case PickedUp:
        throw new RuntimeException("Cannot update items in a picked up order");
        return;
      default:
        return;
    }
  }

  private void rejectDelete()
  {
    switch(status)
    {
      case Paid:
        throw new RuntimeException("Cannot delete items from a paid order");
        return;
      case Penalized:
        throw new RuntimeException("Cannot delete items from a penalized order");
        return;
      case Prepared:
        throw new RuntimeException("Cannot delete items from a prepared order");
        return;
      case PickedUp:
        throw new RuntimeException("Cannot delete items from a picked up order");
        return;
      default:
        return;
    }
  }

  private void rejectStartYear()
  {
    switch(status)
    {
      case Penalized:
        throw new RuntimeException("The school year has already been started");
        return;
      case Prepared:
        throw new RuntimeException("The school year has already been started");
        return;
      case PickedUp:
        throw new RuntimeException("The school year has already been started");
        return;
      default:
        return;
    }
  }

  private void rejectCancelOrder()
  {
    switch(status)
    {
      case Penalized:
        throw new RuntimeException("Cannot cancel a penalized order");
        return;
      case Prepared:
        throw new RuntimeException("Cannot cancel a prepared order");
        return;
      case PickedUp:
        throw new RuntimeException("Cannot cancel a picked up order");
        return;
      default:
        return;
    }
  }

  public boolean addUniqueItemToOrder(OrderItem aItem,String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (aItem.getQuantity()==0)
        {
          // line 89 "model.ump"
          addItemToOrder(aItem, orderNumber);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addNonUniqueItemToOrder(int newQuantity,OrderItem aItem,String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (aItem.getQuantity()!=0)
        {
          // line 92 "model.ump"
          updateQuantity(newQuantity, aItem, orderNumber);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeUniqueItemOrder(int newQuantity,OrderItem aItem,String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isOrderExist(orderNumber)&&aItem.getQuantity()==1)
        {
          // line 95 "model.ump"
          deleteOrderItem(aItem, orderNumber);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeNonUniqueItemOrder(int newQuantity,OrderItem aItem,String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        if (isOrderExist(orderNumber)&&aItem.getQuantity()>1)
        {
          // line 97 "model.ump"
          updateQuantity(newQuantity, aItem, orderNumber);
          setStatus(Status.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateOrder(PurchaseLevel aPurchaseLevel,String studentName)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 99 "model.ump"
        updateOrder(aPurchaseLevel, studentName);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean orderHasBeenPaid(String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 101 "model.ump"
        payOrder(orderNumber);
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean startSchoolYear()
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 105 "model.ump"

        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 143 "model.ump"

        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 183 "model.ump"
        rejectStartYear();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 223 "model.ump"
        rejectStartYear();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 265 "model.ump"
        rejectStartYear();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 107 "model.ump"
        cancelOrder();
        setStatus(Status.Canceled);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 145 "model.ump"
        cancelOrder();
        setStatus(Status.Canceled);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 189 "model.ump"
        rejectCancelOrder();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 229 "model.ump"
        rejectCancelOrder();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 271 "model.ump"
        rejectCancelOrder();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateOrderEvent(PurchaseLevel purchaseLevel,Student student)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 113 "model.ump"
        updateOrder (purchaseLevel, student);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 151 "model.ump"
        rejectUpdate();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 195 "model.ump"
        rejectUpdate();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 235 "model.ump"
        rejectUpdate();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 277 "model.ump"
        rejectUpdate();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean add(InventoryItem item)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 121 "model.ump"
        addItemToOrder (item);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 157 "model.ump"
        rejectAdd();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 201 "model.ump"
        rejectAdd();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 241 "model.ump"
        rejectAdd();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 283 "model.ump"
        rejectAdd();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean updateQuantityEvent(int newQuantity,OrderItem item)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 128 "model.ump"
        updateQuantity(newQuantity, item);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 163 "model.ump"
        rejectUpdateQ();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 207 "model.ump"
        rejectUpdateQ();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 247 "model.ump"
        rejectUpdateQ();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 289 "model.ump"
        rejectUpdateQ();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean delete(OrderItem item)
  {
    ordersByNumber.remove(getNumber());
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Started:
        // line 134 "model.ump"
        deleteOrderItem(item);
        setStatus(Status.Started);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 169 "model.ump"
        rejectDelete();
        setStatus(Status.Paid);
        wasEventProcessed = true;
        break;
      case Penalized:
        // line 213 "model.ump"
        rejectDelete();
        setStatus(Status.Penalized);
        wasEventProcessed = true;
        break;
      case Prepared:
        // line 253 "model.ump"
        rejectDelete();
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      case PickedUp:
        // line 295 "model.ump"
        rejectDelete();
        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean orderHasBeenPrepared(String orderNumber)
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Penalized:
        // line 179 "model.ump"
        payOrder(orderNumber);
        setStatus(Status.Prepared);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean orderHasBeenPickedUp()
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Prepared:
        // line 221 "model.ump"

        setStatus(Status.PickedUp);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean goToFinal()
  {
    boolean wasEventProcessed = false;

    Status aStatus = status;
    switch (aStatus)
    {
      case Canceled:
        // line 261 "model.ump"

        setStatus(Status.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setStatus(Status aStatus)
  {
    status = aStatus;

    // entry actions and do activities
    switch(status)
    {
      case Final:
        delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Parent getParent()
  {
    return parent;
  }
  /* Code from template association_GetOne */
  public Student getStudent()
  {
    return student;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetMany */
  public OrderItem getOrderItem(int index)
  {
    OrderItem aOrderItem = orderItems.get(index);
    return aOrderItem;
  }

  public List<OrderItem> getOrderItems()
  {
    List<OrderItem> newOrderItems = Collections.unmodifiableList(orderItems);
    return newOrderItems;
  }

  public int numberOfOrderItems()
  {
    int number = orderItems.size();
    return number;
  }

  public boolean hasOrderItems()
  {
    boolean has = orderItems.size() > 0;
    return has;
  }

  public int indexOfOrderItem(OrderItem aOrderItem)
  {
    int index = orderItems.indexOf(aOrderItem);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setParent(Parent aParent)
  {
    boolean wasSet = false;
    if (aParent == null)
    {
      return wasSet;
    }

    Parent existingParent = parent;
    parent = aParent;
    if (existingParent != null && !existingParent.equals(aParent))
    {
      existingParent.removeOrder(this);
    }
    parent.addOrder(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setStudent(Student aStudent)
  {
    boolean wasSet = false;
    if (aStudent == null)
    {
      return wasSet;
    }

    Student existingStudent = student;
    student = aStudent;
    if (existingStudent != null && !existingStudent.equals(aStudent))
    {
      existingStudent.removeOrder(this);
    }
    student.addOrder(this);
    wasSet = true;
    return wasSet;
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
  public static int minimumNumberOfOrderItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public OrderItem addOrderItem(int aQuantity, CoolSupplies aCoolSupplies, InventoryItem aItem)
  {
    return new OrderItem(aQuantity, aCoolSupplies, this, aItem);
  }

  public boolean addOrderItem(OrderItem aOrderItem)
  {
    boolean wasAdded = false;
    if (orderItems.contains(aOrderItem)) { return false; }
    Order existingOrder = aOrderItem.getOrder();
    boolean isNewOrder = existingOrder != null && !this.equals(existingOrder);
    if (isNewOrder)
    {
      aOrderItem.setOrder(this);
    }
    else
    {
      orderItems.add(aOrderItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrderItem(OrderItem aOrderItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrderItem, as it must always have a order
    if (!this.equals(aOrderItem.getOrder()))
    {
      orderItems.remove(aOrderItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(addOrderItem(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderItemAt(OrderItem aOrderItem, int index)
  {
    boolean wasAdded = false;
    if(orderItems.contains(aOrderItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrderItems()) { index = numberOfOrderItems() - 1; }
      orderItems.remove(aOrderItem);
      orderItems.add(index, aOrderItem);
      wasAdded = true;
    }
    else
    {
      wasAdded = addOrderItemAt(aOrderItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ordersByNumber.remove(getNumber());
    Parent placeholderParent = parent;
    this.parent = null;
    if(placeholderParent != null)
    {
      placeholderParent.removeOrder(this);
    }
    Student placeholderStudent = student;
    this.student = null;
    if(placeholderStudent != null)
    {
      placeholderStudent.removeOrder(this);
    }
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeOrder(this);
    }
    for(int i=orderItems.size(); i > 0; i--)
    {
      OrderItem aOrderItem = orderItems.get(i - 1);
      aOrderItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "authorizationCode" + ":" + getAuthorizationCode()+ "," +
            "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "," +
            "inventoryItems" + ":" + getInventoryItems()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "level" + "=" + (getLevel() != null ? !getLevel().equals(this)  ? getLevel().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "student = "+(getStudent()!=null?Integer.toHexString(System.identityHashCode(getStudent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null");
  }
}