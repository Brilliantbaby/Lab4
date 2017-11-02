package com.example.administrator.lab4;
/**
 * Created by Administrator on 2017/11/1.
 */


public class MessageEvent {
    private Product shoppingItem;
    public MessageEvent(Product shoppingItem){this.shoppingItem = shoppingItem;
    }
    public Product getShoppingItem(){
        return shoppingItem;
    }
}
