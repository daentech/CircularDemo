package uk.co.daentech.circulardemo.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import uk.co.daentech.circulardemo.R;

/**
 * Created by dangilbert on 05/08/2014.
 */
public class ProgressCircle extends View {

    private final RectF mOval = new RectF();
    private float mSweepAngle = 0;
    private int startAngle = 90;
    private int angleGap = 2;


    float mEndAngle = 1.0f;

    Paint progressPaint = new Paint();
    Paint textPaint = new Paint();
    Paint incompletePaint = new Paint();

    private float strokeWidth = 30.0f;

    public ProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProgressCircle,
                0, 0);

        int textColor;
        float textSize;

        int progressColor;
        int incompleteColor;

        try {
            textColor = a.getColor(R.styleable.ProgressCircle_android_textColor, android.R.color.holo_red_dark);
            textSize = a.getDimension(R.styleable.ProgressCircle_android_textSize, 100);

            strokeWidth = a.getDimension(R.styleable.ProgressCircle_strokeWidth, 30.0f);

            progressColor = a.getColor(R.styleable.ProgressCircle_progressColor, android.R.color.holo_blue_bright);
            incompleteColor = a.getColor(R.styleable.ProgressCircle_incompleteProgressColor, android.R.color.darker_gray);
        } finally {
            a.recycle();
        }

        progressPaint.setColor(progressColor);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);

        incompletePaint.setColor(incompleteColor);
        incompletePaint.setStrokeWidth(strokeWidth);
        incompletePaint.setStyle(Paint.Style.STROKE);
        incompletePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mOval.set(strokeWidth / 2, strokeWidth / 2, getWidth() - (strokeWidth / 2), getHeight() - (strokeWidth / 2));
        canvas.drawArc(mOval, -startAngle + angleGap, (mSweepAngle * 360) - angleGap, false,
                progressPaint);

        mOval.set(strokeWidth / 2, strokeWidth / 2, getWidth() - (strokeWidth / 2), getHeight() - (strokeWidth / 2));
        canvas.drawArc(mOval, mSweepAngle * 360- startAngle + angleGap, 360 - (mSweepAngle * 360) - angleGap, false,
                incompletePaint);

        drawText(canvas, textPaint, (int) (mSweepAngle * 100) + "%");
    }

    private void drawText(Canvas canvas, Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        int y = (canvas.getHeight() / 2) + (bounds.height() / 2);
        canvas.drawText(text, x, y, paint);
    }

    public void setTextColor(int color) {
        textPaint.setColor(color);
    }

    public void setProgressColor(int color) {
        progressPaint.setColor(color);
    }

    public void setIncompleteColor(int color) {
        incompletePaint.setColor(color);
    }

    public void setValue(float value) {
        if (value > 1.0f || value < 0) {
            throw new RuntimeException("Value must be between 0 and 1");
        }

        mEndAngle = value;
    }

    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, mEndAngle);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ProgressCircle.this.mSweepAngle = (Float) valueAnimator.getAnimatedValue();
                ProgressCircle.this.invalidate();
            }
        });
        anim.setDuration(500);
        anim.setInterpolator(new OvershootInterpolator());
        anim.start();

    }

}
