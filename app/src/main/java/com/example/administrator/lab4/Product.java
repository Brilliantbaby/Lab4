package com.example.administrator.lab4;

/**
 * Created by Administrator on 2017/11/1.
 */

public class Product{
    private String firstletter;
    private String name;
    private String price;
    private String information;
    private int imageId;

    public Product(String firstletter,String name,String price){
        this.firstletter=firstletter;
        this.name=name;
        this.price = price;
   }

    public String get_firstletter(){return firstletter;}
    public String get_name(){return name;}
    public String get_price(){return price;}
    public String get_Information(){return information;}
    public int get_ImageId(){return imageId;}
    public Product(String firstletter,String name,String price,String information,int imageId){
        this.firstletter = firstletter;
        this.name = name;
        this.price = price;
        this.information = information;
        this.imageId = imageId;
    }


}