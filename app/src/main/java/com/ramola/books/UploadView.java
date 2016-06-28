package com.ramola.books;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class UploadView extends EditText implements ActivityUpload.onDrawListener{

    private Paint mPaint;
    private Rect mRect;
    private Bitmap bitmap;
    private  boolean isDrawImage;
    public UploadView(Context context) {
        super(context);

    }

    public UploadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRect = new Rect();
        ActivityUpload activityUpload= (ActivityUpload) context;
        activityUpload.setListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isDrawImage){
            drawPhoto(canvas,bitmap);
        }
    }

    public void drawPhoto(Canvas canvas, Bitmap bitmap) {
        Rect rect = mRect;
        Paint paint=mPaint;
        getLineBounds(getLineCount()-1,rect);
        canvas.drawBitmap(bitmap,((canvas.getWidth()/2)-(bitmap.getWidth()/2)),rect.bottom+1,paint);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    @Override
    public void deliverBitmap(Bitmap bitmap) {
        setBitmap(bitmap);
        setDrawImage(true);
        invalidate();
    }

    public boolean isDrawImage() {
        return isDrawImage;
    }

    public void setDrawImage(boolean drawImage) {
        isDrawImage = drawImage;
    }


}
