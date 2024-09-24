/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 71 "model.ump"
// line 153 "model.ump"
public class Student
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<int, Student> studentsByStudentID = new HashMap<int, Student>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Student Attributes
  private String name;
  private int studentID;

  //Student Associations
  private CoolSupplies coolSupplies;
  private ParentAccount parent;
  private List<Grade> grades;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Student(String aName, int aStudentID, CoolSupplies aCoolSupplies, ParentAccount aParent)
  {
    name = aName;
    if (!setStudentID(aStudentID))
    {
      throw new RuntimeException("Cannot create due to duplicate studentID. See https://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCoolSupplies = setCoolSupplies(aCoolSupplies);
    if (!didAddCoolSupplies)
    {
      throw new RuntimeException("Unable to create student due to coolSupplies. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddParent = setParent(aParent);
    if (!didAddParent)
    {
      throw new RuntimeException("Unable to create Children due to parent. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    grades = new ArrayList<Grade>();
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

  public boolean setStudentID(int aStudentID)
  {
    boolean wasSet = false;
    int anOldStudentID = getStudentID();
    if (anOldStudentID != null && anOldStudentID.equals(aStudentID)) {
      return true;
    }
    if (hasWithStudentID(aStudentID)) {
      return wasSet;
    }
    studentID = aStudentID;
    wasSet = true;
    if (anOldStudentID != null) {
      studentsByStudentID.remove(anOldStudentID);
    }
    studentsByStudentID.put(aStudentID, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getStudentID()
  {
    return studentID;
  }
  /* Code from template attribute_GetUnique */
  public static Student getWithStudentID(int aStudentID)
  {
    return studentsByStudentID.get(aStudentID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithStudentID(int aStudentID)
  {
    return getWithStudentID(aStudentID) != null;
  }
  /* Code from template association_GetOne */
  public CoolSupplies getCoolSupplies()
  {
    return coolSupplies;
  }
  /* Code from template association_GetOne */
  public ParentAccount getParent()
  {
    return parent;
  }
  /* Code from template association_GetMany */
  public Grade getGrade(int index)
  {
    Grade aGrade = grades.get(index);
    return aGrade;
  }

  public List<Grade> getGrades()
  {
    List<Grade> newGrades = Collections.unmodifiableList(grades);
    return newGrades;
  }

  public int numberOfGrades()
  {
    int number = grades.size();
    return number;
  }

  public boolean hasGrades()
  {
    boolean has = grades.size() > 0;
    return has;
  }

  public int indexOfGrade(Grade aGrade)
  {
    int index = grades.indexOf(aGrade);
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
      existingCoolSupplies.removeStudent(this);
    }
    coolSupplies.addStudent(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setParent(ParentAccount aParent)
  {
    boolean wasSet = false;
    if (aParent == null)
    {
      return wasSet;
    }

    ParentAccount existingParent = parent;
    parent = aParent;
    if (existingParent != null && !existingParent.equals(aParent))
    {
      existingParent.removeChildren(this);
    }
    parent.addChildren(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGrades()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addGrade(Grade aGrade)
  {
    boolean wasAdded = false;
    if (grades.contains(aGrade)) { return false; }
    grades.add(aGrade);
    if (aGrade.indexOfStudent(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aGrade.addStudent(this);
      if (!wasAdded)
      {
        grades.remove(aGrade);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeGrade(Grade aGrade)
  {
    boolean wasRemoved = false;
    if (!grades.contains(aGrade))
    {
      return wasRemoved;
    }

    int oldIndex = grades.indexOf(aGrade);
    grades.remove(oldIndex);
    if (aGrade.indexOfStudent(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aGrade.removeStudent(this);
      if (!wasRemoved)
      {
        grades.add(oldIndex,aGrade);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGradeAt(Grade aGrade, int index)
  {  
    boolean wasAdded = false;
    if(addGrade(aGrade))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrades()) { index = numberOfGrades() - 1; }
      grades.remove(aGrade);
      grades.add(index, aGrade);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGradeAt(Grade aGrade, int index)
  {
    boolean wasAdded = false;
    if(grades.contains(aGrade))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGrades()) { index = numberOfGrades() - 1; }
      grades.remove(aGrade);
      grades.add(index, aGrade);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGradeAt(aGrade, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    studentsByStudentID.remove(getStudentID());
    CoolSupplies placeholderCoolSupplies = coolSupplies;
    this.coolSupplies = null;
    if(placeholderCoolSupplies != null)
    {
      placeholderCoolSupplies.removeStudent(this);
    }
    ParentAccount placeholderParent = parent;
    this.parent = null;
    if(placeholderParent != null)
    {
      placeholderParent.removeChildren(this);
    }
    ArrayList<Grade> copyOfGrades = new ArrayList<Grade>(grades);
    grades.clear();
    for(Grade aGrade : copyOfGrades)
    {
      aGrade.removeStudent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "studentID" + ":" + getStudentID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null");
  }
}