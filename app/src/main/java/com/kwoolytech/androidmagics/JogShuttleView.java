package com.kwoolytech.androidmagics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class JogShuttleView extends View {

    private float _radius;
    private float _centerX;
    private float _centerY;
    private float _borderWidth;

    private float _angle;

    private Paint _paint = new Paint();
    private RectF _progressOval = new RectF();

    public JogShuttleView(Context context) {
        super(context);
    }

    public JogShuttleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public JogShuttleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        _paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        _radius      = MeasureSpec.getSize(widthMeasureSpec) / 3;
        _centerX     = MeasureSpec.getSize(widthMeasureSpec) / 2;
        _centerY     = MeasureSpec.getSize(heightMeasureSpec) / 2;
        _borderWidth = 5;

        _progressOval.set(_centerX - _radius, _centerY - _radius, _centerX + _radius, _centerY + _radius);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        _paint.setColor(Color.CYAN);
        canvas.drawCircle(_centerX, _centerY, _radius, _paint);

        _paint.setColor(Color.WHITE);
        canvas.drawCircle(_centerX, _centerY, _radius - _borderWidth, _paint);

        _paint.setColor(Color.RED);
        _paint.setStrokeWidth(5);
        _paint.setStyle(Paint.Style.STROKE);

        canvas.drawArc(_progressOval, -90, _angle, false, _paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch(motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                double dx = motionEvent.getX() - _centerX;
                double dy = motionEvent.getY() - _centerY;
                _angle = (float)Math.toDegrees(Math.atan2(dy, dx)) + 90;
                if(_angle < 0) _angle += 360;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        invalidate();
        return true;
    }
}
