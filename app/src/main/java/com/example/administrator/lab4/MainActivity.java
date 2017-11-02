package com.example.administrator.lab4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;


public class MainActivity extends AppCompatActivity {
    public  List<Product> productList;				//商品列表
    private List<Product> shoppinglist;				//购物车的商品列表

    private RecyclerView mRecyclerView;				//商品列表的RecyclerView
    private ListView mListView;						//购物车的ListView

    private LinearLayoutManager mlayoutManager;
    private FloatingActionButton mFAB;				//用于切换商品列表页面和购物车页面的悬浮按钮
    private boolean flag = false;					//用于控制商品页面切换的布尔变量
    private CommonAdapter mCommonAdapter;			//商品列表的适配器
    private MyAdapter mListAdapter;					//购物车列表的适配器

    private Context mcontext = null;				//当前活动
    
    private Bundle mBundle;
    private Intent intentBroadcast;

    //----------------------------------lab4---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);					//继承自onCreate
        productList = new ArrayList<>();					//创建Product的列表
        LoadProducts();										//装载数据

        setContentView(R.layout.activity_main);				//设置当前主活动的布局
        mlayoutManager = new LinearLayoutManager(this);		//设置线性布局的layoutmanager

        mRecyclerView =(RecyclerView)findViewById(R.id.recycler_view);//设置RecyclerView的布局
        mRecyclerView.setLayoutManager(mlayoutManager);					//设置RecyclerView的layoutmanager
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(110));	//设置动画和装饰
        mCommonAdapter = new CommonAdapter(productList);			//为商品列表设定配置器
        ScaleInAnimationAdapter mAnimAdapter = new ScaleInAnimationAdapter(mCommonAdapter);//动画插值
        mAnimAdapter.setDuration(1100);								//动画的时间
        mRecyclerView.setAdapter(mAnimAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());//移除动画
        mRecyclerView.getItemAnimator().setRemoveDuration(240);

       
        Random mRandom = new Random();
        int n = mRandom.nextInt(productList.size());
        Product temp=productList.get(n);
        intentBroadcast = new Intent("STATICATION");//通知
        mBundle=new Bundle();
        mBundle.putString("extra_firstletter",temp.get_firstletter());
        mBundle.putString("extra_name",temp.get_name());
        mBundle.putString("extra_price",temp.get_price());
        mBundle.putString("extra_information",temp.get_Information());
        mBundle.putInt("extra_imageId",temp.get_ImageId());
        intentBroadcast.putExtras(mBundle);
        sendBroadcast(intentBroadcast);

        //注册EventBus
        EventBus.getDefault().register(this);
       

        mCommonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            public void onLongClick(int position) {
                mCommonAdapter.remove(position);
                Toast.makeText(MainActivity.this,"移除第"+(position+1)+"个商品",Toast.LENGTH_SHORT).show();
            }

            public void onClick(int position) {
                //设定一个切换的mIntent
                Intent mIntent = new Intent(MainActivity.this, com.example.administrator.lab4.Activity2.class);
                mIntent.setClass(MainActivity.this, com.example.administrator.lab4.Activity2.class);
                //用bundle来传递数据
                Bundle bundle = new Bundle();
                Product temp = mCommonAdapter.getmProductList().get(position);

                bundle.putString("extra_firstletter",temp.get_firstletter());
                bundle.putString("extra_name",temp.get_name());
                bundle.putString("extra_price",temp.get_price());
                bundle.putString("extra_information",temp.get_Information());
                bundle.putInt("extra_imageId",temp.get_ImageId());
                mIntent.putExtras(bundle);//传递数据
                startActivityForResult(mIntent,1);
            }

        });



        mListView= (ListView)findViewById(R.id.list_view);
        mcontext = MainActivity.this;
        shoppinglist = new ArrayList<>();
        Product p =new Product("*","购物车","价格");
        mListAdapter = new MyAdapter(shoppinglist, mcontext);
        mListView.setAdapter(mListAdapter);
        mListAdapter.add(p);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                if(!(mListAdapter.get_mProduct().get(position).get_name().equals("购物车"))) {
                    Intent mIntent = new Intent();
                    mIntent.setClass(MainActivity.this, com.example.administrator.lab4.Activity2.class);
                    Bundle bundle = new Bundle();
                    Product temp =  mListAdapter.get_mProduct().get(position);
                    bundle.putString("extra_name",temp.get_name());
                    bundle.putString("extra_price",temp.get_price());
                    bundle.putString("extra_information",temp.get_Information());
                    bundle.putInt("extra_imageId",temp.get_ImageId());
                    bundle.putString("extra_firstletter",temp.get_firstletter());
                    mIntent.putExtras(bundle);
                    startActivityForResult(mIntent,1);
                }
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if(!(mListAdapter.get_mProduct().get(position).get_name().equals("购物车"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("从购物车移除" + mListAdapter.get_mProduct().get(position).get_name());
                    builder.setTitle("移除商品");

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mListAdapter.remove(position);
                        }
                    });

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.create().show();
                }
                return true;
            }



        });

        
        mListView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mFAB =(FloatingActionButton)findViewById(R.id.fab);
       
        mFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mFAB.setImageResource(flag ? R.mipmap.shoplist:R.mipmap.mainpage );
                mListView.setVisibility(flag ? View.GONE: View.VISIBLE );
                mRecyclerView.setVisibility(flag ? View.VISIBLE : View.GONE);
                flag=!flag;
            }
        });

    }

    private void LoadProducts(){
        productList.add(new Product("E","Enchated Forest","¥ 5.00","作者  Johanna Basford",R.mipmap.enchatedforest));
        productList.add(new Product("A","Arla Milk","¥ 59.00","产地  德国",R.mipmap.arla));
        productList.add(new Product("D","Devondale Milk","¥ 79.00","产地  澳大利亚",R.mipmap.devondale));
        productList.add(new Product("K","Kindle Oasis","¥ 2399.00","版本  8GB",R.mipmap.kindle));
        productList.add(new Product("w","waitrose 早餐麦片","¥ 179.00","重量  2Kg",R.mipmap.waitrose));
        productList.add(new Product("M","Mcvitie's 饼干","¥ 14.90","产地  英国",R.mipmap.mcvitie));
        productList.add(new Product("E","Ferrero Rocher","¥ 132.59","重量  300g",R.mipmap.ferrero));
        productList.add(new Product("M","Maltesers","¥ 141.43","重量  118g",R.mipmap.maltesers));
        productList.add(new Product("L","Lindt","¥ 139.43","重量  249g",R.mipmap.lindt));
        productList.add(new Product("B","Borggreve","¥ 28.90","重量 640g",R.mipmap.borggreve));
    }



    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( resultCode ) {
            case RESULT_OK :
                Bundle bundle = data.getExtras();
                String returnedName = bundle.getString("name_returned");
                String returnPrice = bundle.getString("price_returned");
                String returnInfo = bundle.getString("info_returned");
                int returnImage = bundle.getInt("image_returned");
                String returnFetter = bundle.getString("fletter_returned");
                Product p = new Product(returnFetter,returnedName,returnPrice,returnInfo,returnImage);
                mListAdapter.add(p);
                break;
            case RESULT_CANCELED:
                break;
            default :
                break;
        }

    }

    //Event事件的处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnEvent(MessageEvent event){
        Product mproduct = event.getShoppingItem();
        shoppinglist.add(mproduct);
        mListAdapter.notifyDataSetChanged();
    }

    //取消事件订阅
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
       
        mRecyclerView.setVisibility(View.INVISIBLE);
        mListView.setVisibility(View.VISIBLE);
        mFAB.setImageResource(R.mipmap.shoplist);
    }
}

