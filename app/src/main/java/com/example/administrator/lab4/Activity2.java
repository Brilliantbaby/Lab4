package com.example.administrator.lab4;

/**
 * Created by Administrator on 2017/10/26.
 */


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import static com.example.administrator.lab4.R.id.shop_car;

public class Activity2 extends AppCompatActivity {
    private ListView mListinfo;
    private String[] mListData;
    private ImageView imagView ;
    private TextView mytext;
    private TextView priceView;
    private TextView InfoView;
    private boolean temp = false;
    private String name;
    private String price;
    private String info;
    private int ImageId;
    private String firstletter;
    private int tag = 1;

    private DynamicReceiver dynamicReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        mListinfo= (ListView)findViewById(R.id.Info_list_total);
        mListData = new String[]{"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.list_info,mListData);
        mListinfo.setAdapter(arrayAdapter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("DYNAMICATION");
        dynamicReceiver = new DynamicReceiver();
        registerReceiver(dynamicReceiver, intentFilter);

        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("extra_name");
        price = bundle.getString("extra_price");
        info = bundle.getString("extra_information");
        ImageId = bundle.getInt("extra_imageId");
        firstletter = bundle.getString("extra_firstletter");

        imagView =  (ImageView)findViewById(R.id.Item_Image);
        mytext =  (TextView)findViewById(R.id.Item_name);
        priceView = (TextView)findViewById(R.id.Item_price);
        InfoView = (TextView)findViewById(R.id.Item_info);
        mytext.setText(name);
        imagView.setBackgroundResource(ImageId);
        priceView.setText(price);
        InfoView.setText(info);

        final Product shoppingItem = new Product(firstletter,name,price,info,ImageId);
        final ImageButton shoppingbutton =(ImageButton)findViewById(shop_car);


        shoppingbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(Activity2.this,"商品已添加到购物车", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent("DYNAMICATION");
                Bundle mBundle = new Bundle();
                mBundle.putInt("extra_imageId",ImageId);
                mBundle.putString("extra_firstletter",firstletter);
                mBundle.putString("extra_name",name);
                mBundle.putString("extra_price",price);
                mBundle.putString("extra_info",info);
                intent1.putExtras(mBundle);
                sendBroadcast(intent1);
                EventBus.getDefault().post(new MessageEvent(shoppingItem));

            }
        });

        final ImageButton starbutton =(ImageButton)findViewById(R.id.starButton);
        starbutton.setBackgroundResource(R.mipmap.empty_star);
        starbutton.setTag("0");
        starbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Object starTag=starbutton.getTag();
                if(starTag=="0"){
                    starbutton.setBackgroundResource(R.mipmap.full_star);
                    starbutton.setTag("1");
                }
                else{
                    starbutton.setBackgroundResource(R.mipmap.empty_star);
                    starbutton.setTag("0");
                }
            }
        });
        ImageButton backbutton =(ImageButton)findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent aintent = new Intent();
                Bundle abundle = new Bundle();
                abundle.putString("name_returned",name);
                abundle.putString("price_returned",price);
                abundle.putString("info_returned",info);
                abundle.putInt("image_returned",ImageId);
                abundle.putString("fletter_returned",firstletter);
                aintent.putExtras(abundle);
                if(temp){
                    setResult(RESULT_OK,aintent);
                }
                else {setResult(RESULT_CANCELED,aintent);}
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent bintent = new Intent();
        Bundle bbundle = new Bundle();
        bbundle.putString("name_returned",name);
        bbundle.putString("price_returned",price);
        bbundle.putString("info_returned",info);
        bbundle.putInt("image_returned",ImageId);
        bbundle.putString("fletter_returned",firstletter);
        bintent.putExtras(bbundle);
        if(temp){
            setResult(RESULT_OK,bintent);
        }
        else {setResult(RESULT_CANCELED,bintent);}
        finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
    }
}
