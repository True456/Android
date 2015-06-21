package com.example.fengzhengapp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private ArrayList fragments;
	
	private TextView view1, view2, view3;  
	
    private int currIndex;
    
    private ImageView image;
    private static int bmpW;//����ͼƬ���  
    private static int offset;//ͼƬ�ƶ���ƫ����  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViewPager();
		InitTextView();
		InitImage();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void InitTextView(){
		view1 = (TextView) findViewById(R.id.tv_hot);
		view2 = (TextView) findViewById(R.id.tv_news);
		view3 = (TextView) findViewById(R.id.tv_fav);
	
		
		view1.setOnClickListener(new txtListener(0));
		view2.setOnClickListener(new txtListener(1));
		view3.setOnClickListener(new txtListener(2));

	}
	
	//�ڲ��� ��дTextView����¼�
	public class txtListener implements View.OnClickListener{
		private int index = 0;
		
		public txtListener(int i){
			index = i;
		}
		
		@Override
		public void onClick(View v){
			mViewPager.setCurrentItem(index);
		}
	}
	
	/* 
     * ��ʼ��ͼƬ��λ������ 
     */  
    public void InitImage(){  
        image = (ImageView)findViewById(R.id.cursor);  
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.bgborder).getWidth();  
        DisplayMetrics dm = new DisplayMetrics();  
        getWindowManager().getDefaultDisplay().getMetrics(dm);  
        int screenW = dm.widthPixels;  
        offset = (screenW/3 - bmpW)/2;  
        Log.i("screenW",String.valueOf(screenW));
        Log.i("bmpW",String.valueOf(bmpW));
        Log.i("offset",String.valueOf(offset));
          
        //imgageview����ƽ�ƣ�ʹ�»���ƽ�Ƶ���ʼλ�ã�ƽ��һ��offset��  
        Matrix matrix = new Matrix();  
        matrix.postTranslate(offset, 0);  
        image.setImageMatrix(matrix);  
    }  
	
	
	
	private void initViewPager(){
		mViewPager = (ViewPager)findViewById(R.id.myViewPager);
		fragments = new ArrayList<Fragment>();
		Fragment fragmentHot = new FragmentHot();
		Fragment fragmentNews = new FragmentNews();
		Fragment fragmentFav = new FragmentFavorite();
		fragments.add(fragmentHot);
		fragments.add(fragmentNews);
		fragments.add(fragmentFav);
		mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), fragments));
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new myOnPageChangeListener());
	}
	
	public class myOnPageChangeListener implements OnPageChangeListener {
		private int one = offset*2 +bmpW;//��������ҳ���ƫ����   63*2+144=270
		
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}
		
		@Override  
        public void onPageSelected(int arg0) {  
            // TODO Auto-generated method stub  
			Log.i("aaaaaaaaaaaaa",String.valueOf(arg0));
			Log.i("one",String.valueOf(one));
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//ƽ�ƶ���  
            currIndex = arg0; 
            animation.setFillAfter(true);//������ֹʱͣ�������һ֡����Ȼ��ص�û��ִ��ǰ��״̬  
            animation.setDuration(200);//��������ʱ��0.2��  
            image.startAnimation(animation);//����ImageView����ʾ������  
            //int i = currIndex + 1;  
           // Toast.makeText(MainActivity.this, "��ѡ���˵�"+i+"��ҳ��", Toast.LENGTH_SHORT).show();  
        }  
    }  
}
