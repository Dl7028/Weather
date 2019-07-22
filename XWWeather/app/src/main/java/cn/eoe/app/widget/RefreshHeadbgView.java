package cn.eoe.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import cn.eoe.app.util.UIUtil;

/**
 *下拉的头布局
 * Created by 徐启 on 2019/5/11.
 */

public class RefreshHeadbgView extends View {
    private int startdp;
    private int width,height,length;
    private Paint paint;
    private Paint p;
    private int widthX;
    private boolean useX;

    public RefreshHeadbgView(Context context) {
        this(context,null);
    }

    public RefreshHeadbgView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshHeadbgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        paint.setAntiAlias(true);
        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        startdp = UIUtil.dip2px(getContext(),50);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        height = getHeight();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Path mPath = new Path();
//        canvas.drawColor(0xff00ecec);

        if(height>startdp){                            //高度达到一顶高度才显示弧线
            mPath.moveTo(0, startdp);
            if(useX){                                  //判断有没有设置X,也就是setWidth(x)方法
                mPath.rQuadTo(widthX, (height-startdp)*2, width, 0);
            }else{
                mPath.rQuadTo(width/2, (height-startdp)*2, width, 0);
            }
            canvas.drawPath(mPath, paint);
            canvas.drawRect(0,0,width,startdp,paint);  //填充空白
        }else{
            canvas.drawRect(0,0,width,height,paint);
        }

    }


    public void setWidthX(int x){
        useX = true;
        widthX = x;
    }
}