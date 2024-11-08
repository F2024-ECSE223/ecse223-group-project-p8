/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.35.0.7523.c616a4dce modeling language!*/

package ca.mcgill.ecse.coolsupplies.controller;

// line 2 "model.ump"
// line 12 "model.ump"
public class TOOrderItem
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //TOOrderItem Attributes
    private int quantity;
    private String itemName;
    private String gradeBundleName;
    private int price;
    private String discount;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public TOOrderItem(int aQuantity, String aItemName, String aGradeBundleName, int aPrice, String aDiscount)
    {
        quantity = aQuantity;
        itemName = aItemName;
        gradeBundleName = aGradeBundleName;
        price = aPrice;
        discount = aDiscount;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setQuantity(int aQuantity)
    {
        boolean wasSet = false;
        quantity = aQuantity;
        wasSet = true;
        return wasSet;
    }

    public boolean setItemName(String aItemName)
    {
        boolean wasSet = false;
        itemName = aItemName;
        wasSet = true;
        return wasSet;
    }

    public boolean setGradeBundleName(String aGradeBundleName)
    {
        boolean wasSet = false;
        gradeBundleName = aGradeBundleName;
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

    public boolean setDiscount(String aDiscount)
    {
        boolean wasSet = false;
        discount = aDiscount;
        wasSet = true;
        return wasSet;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public String getItemName()
    {
        return itemName;
    }

    public String getGradeBundleName()
    {
        return gradeBundleName;
    }

    public int getPrice()
    {
        return price;
    }

    public String getDiscount()
    {
        return discount;
    }

    public void delete()
    {}


    public String toString()
    {
        return super.toString() + "["+
                "quantity" + ":" + getQuantity()+ "," +
                "itemName" + ":" + getItemName()+ "," +
                "gradeBundleName" + ":" + getGradeBundleName()+ "," +
                "price" + ":" + getPrice()+ "," +
                "discount" + ":" + getDiscount()+ "]";
    }
}