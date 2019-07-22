package cn.eoe.app.widget;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import cn.eoe.app.ui.WeatherActivity;
import cn.eoe.app.util.RefreshListener;
import cn.eoe.app.util.UIUtil;

import static cn.eoe.app.ui.WeatherActivity.mConnectivity;
import static cn.eoe.app.ui.WeatherActivity.refresh;

/**
 * 自定义ScrollView
 * Created by 徐启 on 2019/5/11.
 */

public class RefreshScrollView extends ScrollView {

    private int downY;                 //按下时候的y坐标
    private int scrollY;  //ScrollView的滑动距离
    private View headViewRefresh;  //头布局
    private RefreshListener listsner;  //刷新加载数据监听
    private boolean bDown; //是否可以刷新
    private int viewWidth;  //scrollView宽度
    private int headViewHeight;  //头布局刷新时的高度


    //设置重复下拉失效
//    private long lastClickTime = 0L;
//    private static final int FAST_CLICK_DELAY_TIME = 500;  // 快速点击间隔

    /**
         * 重写构造函数
         */
        public RefreshScrollView(Context context) {
            this(context,null);
        }

        public RefreshScrollView(Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }

        public RefreshScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            viewWidth = getWidth();     //获取ScrollView的宽度用来设置头布局的宽度
        }



        /**
         * 设置刷新头布局
         * @param view
         */
        public void setHeadView(View view){  //在引用此自定义ScrollView的activity中传入初始化完成的头布局文件
            this.headViewRefresh = view;
            this.headViewHeight = UIUtil.dip2px(getContext(),50); //dp转px,px转dp工具，下文给出
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headViewRefresh.getLayoutParams();
            params.width = viewWidth;
            params.height = 0;
            headViewRefresh.setLayoutParams(params); //将headView的高度重新设置为0,也就是不可见，
        }

        /**
         * 提供给调用scrollView的页面的刷新加载回调方法
         * @param listener
         */
        public void setListener(RefreshListener listener){               //回调接口下文给出
            this.listsner = listener;
        }

        /**
         * 刷新停止,给scrollView外部调用
         */
        public void stopRefresh() {
            listsner.hintChange(" ");                         //停止刷新之后，将提示文字设置成初始值，时刻准备着下次刷新
            headViewRefresh.setVisibility(View.GONE);                                                 //隐藏headView
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headViewRefresh.getLayoutParams();  //将headView的高度重新设置为1
            params.width = viewWidth;
            params.height = 0;
            headViewRefresh.setLayoutParams(params); //设置头布局的高度为0,也就是隐藏头布局
//            Toast.makeText(getContext(), "更新成功", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
            this.scrollY = scrollY;              //监听赋值，监听scrollView的滑动状态，当滑动到顶部的时候才可以下拉刷新
            if(scrollY == 0){

            }else if(scrollY+this.getMeasuredHeight() == this.getChildAt(0).getMeasuredHeight()){  //滑动距离+scrollView的高度如果等于scrollView的内部子view的高度则证明滑动到了底部，则自动加载更多数据
                listsner.loadMore();                                                                //加载更多
            }
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) { //重写dispatchTouchEvent

            if(event.getAction() == MotionEvent.ACTION_DOWN){   //获取手指初次触摸位置
                downY = (int) event.getY();  //记录下手指点下的纵坐标
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE){                                      //滑动事件
                if(scrollY == 0){                                                                  //如果scrollY == 0,在顶部，可以刷新
                    if(event.getY() - downY > 0){                                                  //手势判断：向下滑动,可以刷新
                        int downRange = (int) ((event.getY()- downY)*1/3);                         //给headView动态设置高度，动态高度是手指向下滑动距离的1/3
                        headViewRefresh.setVisibility(View.VISIBLE);                              //显示刷新的根视图  显示headView控件
                        bDown = false;                                                              //刚开始滑动，松手还不可以刷新
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headViewRefresh.getLayoutParams();  //将滑动距离转化后的值用来给headView动态设置高度
                        params.width = viewWidth;
                        params.height = downRange;
                        headViewRefresh.setLayoutParams(params);

                        if(downRange >= headViewHeight){                                          //当动态设置的高度大于初始高度的时候，变换hint，此时松手可刷新
                            listsner.hintChange("松开刷新");                                       //超过了设定的高度，可以刷新
                            bDown = true;                                                          //可以刷新，如果此时抬起手指就可以刷新了
                        }else{                                                                      //当动态设置的高度不大于初始高度的时候，变换hint，此时松手不可刷新
                            listsner.hintChange("下拉刷新");
                            bDown = false;                                                         //不可以刷新
                        }
//                        listsner.setWidthX((int)event.getX());                                    //设置触摸点的横坐标
                        return true;                                                               //拦截触摸事件，scrollView不可响应触摸事件，否则会造成松手滑动跳动错位
                    }else{                                                                         //手势判断：小于0则是上滑，此时按正常程序走
                        bDown = false;                                                             //不可以刷新
                        return super.dispatchTouchEvent(event);                                    //向上滑动，不拦截
                    }
                }else{                                                                              //scrollY不等于0则是上滑，此时按正常程序走
                    bDown = false;                                                                 //不可以刷新
                    return super.dispatchTouchEvent(event);                                        //scrollView不在顶部，不拦截
                }
            }
            if(event.getAction() == MotionEvent.ACTION_UP){                                        //抬起手指
                if(bDown) {                                                                         //如果可以刷新
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headViewRefresh.getLayoutParams();   //设置headView为原始高度
                    params.width = viewWidth;
                    params.height = headViewHeight;
                    headViewRefresh.setLayoutParams(params);
                    listsner.hintChange("正在刷新");
                    NetworkInfo info = mConnectivity.getActiveNetworkInfo();
                    if (info == null || !mConnectivity.getBackgroundDataSetting()) {
                        listsner.hintChange("刷新失败");
                    } else if (refresh) {                                                           //如果正在刷新,设置为不可刷新
                        stopRefresh();
                        System.out.println("正在刷新，不可刷新");
                        refresh = false;
                        System.out.println("不在刷新，---可以刷新");
                        listsner.startRefresh();
                        bDown = false;
//                        return super.dispatchTouchEvent(event);
                    } else {
                        listsner.startRefresh();
                        bDown = false;
                    }
                }else{                                                                              //如果不可以刷新，停止刷新
                    stopRefresh();
                }
            }
            return super.dispatchTouchEvent(event);
        }
    }

