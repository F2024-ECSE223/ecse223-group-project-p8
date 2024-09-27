/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.34.0.7242.6b8819789 modeling language!*/


import java.util.*;

// line 72 "model.ump"
// line 155 "model.ump"
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
  private Grade grade;

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
  /* Code from template association_GetOne */
  public Grade getGrade()
  {
    return grade;
  }

  public boolean hasGrade()
  {
    boolean has = grade != null;
    return has;
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
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGrade(Grade aGrade)
  {
    boolean wasSet = false;
    Grade existingGrade = grade;
    grade = aGrade;
    if (existingGrade != null && !existingGrade.equals(aGrade))
    {
      existingGrade.removeStudent(this);
    }
    if (aGrade != null)
    {
      aGrade.addStudent(this);
    }
    wasSet = true;
    return wasSet;
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
    if (grade != null)
    {
      Grade placeholderGrade = grade;
      this.grade = null;
      placeholderGrade.removeStudent(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "studentID" + ":" + getStudentID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "coolSupplies = "+(getCoolSupplies()!=null?Integer.toHexString(System.identityHashCode(getCoolSupplies())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "parent = "+(getParent()!=null?Integer.toHexString(System.identityHashCode(getParent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "grade = "+(getGrade()!=null?Integer.toHexString(System.identityHashCode(getGrade())):"null");
  }
}