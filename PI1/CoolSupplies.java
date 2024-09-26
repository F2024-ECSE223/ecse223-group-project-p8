/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.sql.Date;
import java.util.*;

// line 4 "model.ump"
// line 114 "model.ump"
public class CoolSupplies
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CoolSupplies Attributes
  private Date currYear;

  //CoolSupplies Associations
  private List<UserAccount> userAccounts;
  private List<Student> students;
  private List<SchoolSupply> schoolSupplies;
  private List<Order> orders;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CoolSupplies(Date aCurrYear)
  {
    currYear = aCurrYear;
    userAccounts = new ArrayList<UserAccount>();
    students = new ArrayList<Student>();
    schoolSupplies = new ArrayList<SchoolSupply>();
    orders = new ArrayList<Order>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCurrYear(Date aCurrYear)
  {
    boolean wasSet = false;
    currYear = aCurrYear;
    wasSet = true;
    return wasSet;
  }

  public Date getCurrYear()
  {
    return currYear;
  }
  /* Code from template association_GetMany */
  public UserAccount getUserAccount(int index)
  {
    UserAccount aUserAccount = userAccounts.get(index);
    return aUserAccount;
  }

  public List<UserAccount> getUserAccounts()
  {
    List<UserAccount> newUserAccounts = Collections.unmodifiableList(userAccounts);
    return newUserAccounts;
  }

  public int numberOfUserAccounts()
  {
    int number = userAccounts.size();
    return number;
  }

  public boolean hasUserAccounts()
  {
    boolean has = userAccounts.size() > 0;
    return has;
  }

  public int indexOfUserAccount(UserAccount aUserAccount)
  {
    int index = userAccounts.indexOf(aUserAccount);
    return index;
  }
  /* Code from template association_GetMany */
  public Student getStudent(int index)
  {
    Student aStudent = students.get(index);
    return aStudent;
  }

  public List<Student> getStudents()
  {
    List<Student> newStudents = Collections.unmodifiableList(students);
    return newStudents;
  }

  public int numberOfStudents()
  {
    int number = students.size();
    return number;
  }

  public boolean hasStudents()
  {
    boolean has = students.size() > 0;
    return has;
  }

  public int indexOfStudent(Student aStudent)
  {
    int index = students.indexOf(aStudent);
    return index;
  }
  /* Code from template association_GetMany */
  public SchoolSupply getSchoolSupply(int index)
  {
    SchoolSupply aSchoolSupply = schoolSupplies.get(index);
    return aSchoolSupply;
  }

  public List<SchoolSupply> getSchoolSupplies()
  {
    List<SchoolSupply> newSchoolSupplies = Collections.unmodifiableList(schoolSupplies);
    return newSchoolSupplies;
  }

  public int numberOfSchoolSupplies()
  {
    int number = schoolSupplies.size();
    return number;
  }

  public boolean hasSchoolSupplies()
  {
    boolean has = schoolSupplies.size() > 0;
    return has;
  }

  public int indexOfSchoolSupply(SchoolSupply aSchoolSupply)
  {
    int index = schoolSupplies.indexOf(aSchoolSupply);
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
  public static int minimumNumberOfUserAccounts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUserAccount(UserAccount aUserAccount)
  {
    boolean wasAdded = false;
    if (userAccounts.contains(aUserAccount)) { return false; }
    CoolSupplies existingCoolSupplies = aUserAccount.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aUserAccount.setCoolSupplies(this);
    }
    else
    {
      userAccounts.add(aUserAccount);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUserAccount(UserAccount aUserAccount)
  {
    boolean wasRemoved = false;
    //Unable to remove aUserAccount, as it must always have a coolSupplies
    if (!this.equals(aUserAccount.getCoolSupplies()))
    {
      userAccounts.remove(aUserAccount);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAccountAt(UserAccount aUserAccount, int index)
  {  
    boolean wasAdded = false;
    if(addUserAccount(aUserAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserAccounts()) { index = numberOfUserAccounts() - 1; }
      userAccounts.remove(aUserAccount);
      userAccounts.add(index, aUserAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAccountAt(UserAccount aUserAccount, int index)
  {
    boolean wasAdded = false;
    if(userAccounts.contains(aUserAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserAccounts()) { index = numberOfUserAccounts() - 1; }
      userAccounts.remove(aUserAccount);
      userAccounts.add(index, aUserAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAccountAt(aUserAccount, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfStudents()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Student addStudent(String aName, int aStudentID, ParentAccount aParent, Grade aGrade)
  {
    return new Student(aName, aStudentID, this, aParent, aGrade);
  }

  public boolean addStudent(Student aStudent)
  {
    boolean wasAdded = false;
    if (students.contains(aStudent)) { return false; }
    CoolSupplies existingCoolSupplies = aStudent.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aStudent.setCoolSupplies(this);
    }
    else
    {
      students.add(aStudent);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeStudent(Student aStudent)
  {
    boolean wasRemoved = false;
    //Unable to remove aStudent, as it must always have a coolSupplies
    if (!this.equals(aStudent.getCoolSupplies()))
    {
      students.remove(aStudent);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addStudentAt(Student aStudent, int index)
  {  
    boolean wasAdded = false;
    if(addStudent(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveStudentAt(Student aStudent, int index)
  {
    boolean wasAdded = false;
    if(students.contains(aStudent))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfStudents()) { index = numberOfStudents() - 1; }
      students.remove(aStudent);
      students.add(index, aStudent);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addStudentAt(aStudent, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchoolSupplies()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addSchoolSupply(SchoolSupply aSchoolSupply)
  {
    boolean wasAdded = false;
    if (schoolSupplies.contains(aSchoolSupply)) { return false; }
    CoolSupplies existingCoolSupplies = aSchoolSupply.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aSchoolSupply.setCoolSupplies(this);
    }
    else
    {
      schoolSupplies.add(aSchoolSupply);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSchoolSupply(SchoolSupply aSchoolSupply)
  {
    boolean wasRemoved = false;
    //Unable to remove aSchoolSupply, as it must always have a coolSupplies
    if (!this.equals(aSchoolSupply.getCoolSupplies()))
    {
      schoolSupplies.remove(aSchoolSupply);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSchoolSupplyAt(SchoolSupply aSchoolSupply, int index)
  {  
    boolean wasAdded = false;
    if(addSchoolSupply(aSchoolSupply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchoolSupplies()) { index = numberOfSchoolSupplies() - 1; }
      schoolSupplies.remove(aSchoolSupply);
      schoolSupplies.add(index, aSchoolSupply);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSchoolSupplyAt(SchoolSupply aSchoolSupply, int index)
  {
    boolean wasAdded = false;
    if(schoolSupplies.contains(aSchoolSupply))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchoolSupplies()) { index = numberOfSchoolSupplies() - 1; }
      schoolSupplies.remove(aSchoolSupply);
      schoolSupplies.add(index, aSchoolSupply);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSchoolSupplyAt(aSchoolSupply, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(date aDateCreated, int aOrderNumber, boolean aIsFinalized, boolean aIsPaid, boolean aIsReceived, boolean aIsPickedUp, int aRegularAuthorizationCode, int aPenaltyAuthorizationCode, ParentAccount aPayer)
  {
    return new Order(aDateCreated, aOrderNumber, aIsFinalized, aIsPaid, aIsReceived, aIsPickedUp, aRegularAuthorizationCode, aPenaltyAuthorizationCode, aPayer, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    CoolSupplies existingCoolSupplies = aOrder.getCoolSupplies();
    boolean isNewCoolSupplies = existingCoolSupplies != null && !this.equals(existingCoolSupplies);
    if (isNewCoolSupplies)
    {
      aOrder.setCoolSupplies(this);
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
    //Unable to remove aOrder, as it must always have a coolSupplies
    if (!this.equals(aOrder.getCoolSupplies()))
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
    for(int i=userAccounts.size(); i > 0; i--)
    {
      UserAccount aUserAccount = userAccounts.get(i - 1);
      aUserAccount.delete();
    }
    for(int i=students.size(); i > 0; i--)
    {
      Student aStudent = students.get(i - 1);
      aStudent.delete();
    }
    for(int i=schoolSupplies.size(); i > 0; i--)
    {
      SchoolSupply aSchoolSupply = schoolSupplies.get(i - 1);
      aSchoolSupply.delete();
    }
    for(int i=orders.size(); i > 0; i--)
    {
      Order aOrder = orders.get(i - 1);
      aOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currYear" + "=" + (getCurrYear() != null ? !getCurrYear().equals(this)  ? getCurrYear().toString().replaceAll("  ","    ") : "this" : "null");
  }
}