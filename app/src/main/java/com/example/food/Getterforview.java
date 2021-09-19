package com.example.food;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class Getterforview implements Comparable<Getterforview>{


    String productName,productDoe;
    Integer idnumber,productAmount;
    String productImageUrl;

    public Getterforview() {

    }

    public Getterforview(Integer idnumber,String productName, Integer productAmount, String productDoe,String productImageUrl) {
        this.idnumber=idnumber;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productDoe = productDoe;
        this.productImageUrl=productImageUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductDoe() {
        return productDoe;
    }

    public void setProductDoe(String productDoe) {
        this.productDoe = productDoe;
    }
    public Integer getId() {
        return idnumber;
    }

    public void setId(Integer idnumber) { this.idnumber = idnumber;
    }

    @Override
    public int compareTo(Getterforview o) {
        return this.idnumber- o.getId();
    }
 /*
    @Override
    public int compareTo(Getterforview o) {
        return Integer.valueOf(this.idnumber)- Integer.valueOf(o.getId());
    }*/
 public static Comparator<Getterforview> pid =new Comparator<Getterforview>() {
     @Override
     public int compare(Getterforview p1, Getterforview p2) {
         return (p2.getId()-p1.getId());
     }
 };

    public static Comparator<Getterforview> pname=new Comparator<Getterforview>() {
        @Override
        public int compare(Getterforview p1, Getterforview p2) {
            return p1.getProductName().toLowerCase().compareTo(p2.getProductName().toLowerCase());
        }
    };
    public static Comparator<Getterforview> pname2=new Comparator<Getterforview>() {
        @Override
        public int compare(Getterforview p1, Getterforview p2) {
            return p2.getProductName().toLowerCase().compareTo(p1.getProductName().toLowerCase());
        }
    };
    public static Comparator<Getterforview> pamount=new Comparator<Getterforview>() {
        @Override
        public int compare(Getterforview a1, Getterforview a2) {
            return Integer.valueOf(a1.getProductAmount())-(Integer.valueOf(a2.getProductAmount()));
        }
    };
    public static Comparator<Getterforview> pamount2=new Comparator<Getterforview>() {
        @Override
        public int compare(Getterforview a1, Getterforview a2) {
            return (Integer.valueOf(a2.getProductAmount())-Integer.valueOf(a1.getProductAmount()));
        }
    };
public static Comparator<Getterforview> pdoe=new Comparator<Getterforview>() {
    @Override
    public int compare(Getterforview c1, Getterforview c2) {
        int a1 = Integer.parseInt(c1.getProductDoe().replaceAll("[^0-9]", ""));
        int a2 = Integer.parseInt(c2.getProductDoe().replaceAll("[^0-9]", ""));
        return a1-a2;
      //  return c1.getProductName().compareTo(c2.getProductName());
    }
};
    public static Comparator<Getterforview> pdoe2=new Comparator<Getterforview>() {
        @Override
        public int compare(Getterforview c1, Getterforview c2) {
            int a1 = Integer.parseInt(c1.getProductDoe().replaceAll("[^0-9]", ""));
            int a2 = Integer.parseInt(c2.getProductDoe().replaceAll("[^0-9]", ""));
            return a2-a1;
            //  return c1.getProductName().compareTo(c2.getProductName());
        }
    };
}
