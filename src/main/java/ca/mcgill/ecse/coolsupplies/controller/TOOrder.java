package ca.mcgill.ecse.coolsupplies.controller;

import java.sql.Date;
import java.util.List;

// line 2 "model.ump"
// line 16 "model.ump"
public class TOOrder
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //TOOrder Attributes
    private String parentEmail;
    private String studentName;
    private String status;
    private int number;
    private Date date;
    private String level;
    private String authorizationCode;
    private String penaltyAuthorizationCode;
    private int totalPrice;
    private List<TOOrderItem> items;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public TOOrder(String aParentEmail, String aStudentName, String aStatus, int aNumber, Date aDate, String aLevel, String aAuthorizationCode, String aPenaltyAuthorizationCode, int aTotalPrice)
    {
        parentEmail = aParentEmail;
        studentName = aStudentName;
        status = aStatus;
        number = aNumber;
        date = aDate;
        level = aLevel;
        authorizationCode = aAuthorizationCode;
        penaltyAuthorizationCode = aPenaltyAuthorizationCode;
        totalPrice = aTotalPrice;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setParentEmail(String aParentEmail)
    {
        boolean wasSet = false;
        parentEmail = aParentEmail;
        wasSet = true;
        return wasSet;
    }

    public boolean setStudentName(String aStudentName)
    {
        boolean wasSet = false;
        studentName = aStudentName;
        wasSet = true;
        return wasSet;
    }

    public boolean setStatus(String aStatus)
    {
        boolean wasSet = false;
        status = aStatus;
        wasSet = true;
        return wasSet;
    }

    public boolean setNumber(int aNumber)
    {
        boolean wasSet = false;
        number = aNumber;
        wasSet = true;
        return wasSet;
    }

    public boolean setDate(Date aDate)
    {
        boolean wasSet = false;
        date = aDate;
        wasSet = true;
        return wasSet;
    }

    public boolean setLevel(String aLevel)
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

    public boolean setTotalPrice(int aTotalPrice)
    {
        boolean wasSet = false;
        totalPrice = aTotalPrice;
        wasSet = true;
        return wasSet;
    }

    public void setItems(List<TOOrderItem> items) {
        this.items = items;
    }

    public String getParentEmail()
    {
        return parentEmail;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public String getStatus()
    {
        return status;
    }

    public int getNumber()
    {
        return number;
    }

    public Date getDate()
    {
        return date;
    }

    public String getLevel()
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

    public int getTotalPrice()
    {
        return totalPrice;
    }

    public void delete()
    {}


    public String toString()
    {
        return super.toString() + "["+
                "parentEmail" + ":" + getParentEmail()+ "," +
                "studentName" + ":" + getStudentName()+ "," +
                "status" + ":" + getStatus()+ "," +
                "number" + ":" + getNumber()+ "," +
                "level" + ":" + getLevel()+ "," +
                "authorizationCode" + ":" + getAuthorizationCode()+ "," +
                "penaltyAuthorizationCode" + ":" + getPenaltyAuthorizationCode()+ "," +
                "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null");
    }
}

