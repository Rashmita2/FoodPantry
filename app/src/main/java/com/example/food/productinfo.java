package com.example.food;


public class productinfo {

    String ProductName, ProductAmount, ProductDoe,Idnumber;
    String ProductImageUrl;

    public productinfo() {

    }

    public productinfo(String productName, String productAmount, String productDoe, String idnumber,String productimageurl) {
        ProductName = productName;
        ProductAmount = productAmount;
        ProductDoe = productDoe;
        Idnumber = idnumber;
        ProductImageUrl=productimageurl;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductAmount() {
        return ProductAmount;
    }

    public void setProductAmount(String productAmount) {
        ProductAmount = productAmount;
    }

    public String getProductDoe() {
        return ProductDoe;
    }

    public void setProductDoe(String productDoe) {
        ProductDoe = productDoe;
    }

    public String getIdnumber() {
        return Idnumber;
    }

    public void setIdnumber(String idnumber) {
        Idnumber = idnumber;
    }

    public String getProductImageUrl() {
        return ProductImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        ProductImageUrl = productImageUrl;
    }
}
