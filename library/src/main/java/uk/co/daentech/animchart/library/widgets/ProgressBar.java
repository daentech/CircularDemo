package uk.co.daentech.animchart.library.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by dan.gilbert on 06/08/14.
 */
public class ProgressBar extends View {

    private float progress = 0.4f;
    private float currentProgress;
    private float gap = 5;
    private int lineHeight = 30;
    private Paint progressPaint = new Paint();
    private Paint incompletePaint = new Paint();
    private RectF clipRect;
    private Path clipPath;
    private Paint bitmapPaint = new Paint();
    private Bitmap bitmap;
    private Canvas temp;

    public ProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        progressPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        progressPaint.setStrokeWidth(lineHeight);

        incompletePaint.setColor(getResources().getColor(android.R.color.darker_gray));
        incompletePaint.setStrokeWidth(lineHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        if (clipRect == null) {
            clipRect = new RectF(0, 0, canvas.getWidth(), lineHeight);
            clipPath = new Path();
            clipPath.addRoundRect(clipRect, lineHeight / 2, lineHeight / 2, Path.Direction.CW);

            bitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        }

        bitmap.eraseColor(getResources().getColor(android.R.color.transparent));
        temp = new Canvas(bitmap);

        if (currentProgress * canvas.getWidth() > lineHeight / 2) {
            temp.drawLine(
                    0,
                    lineHeight / 2,
                    canvas.getWidth() * currentProgress
                            - (currentProgress * canvas.getWidth() < canvas.getWidth() - lineHeight / 2 ?
                            gap : 0),
                    lineHeight / 2,
                    progressPaint);
        }
        if (currentProgress * canvas.getWidth() < canvas.getWidth() - lineHeight / 2) {
            temp.drawLine(
                    canvas.getWidth() * currentProgress
                            + (currentProgress * canvas.getWidth() > lineHeight / 2 ? gap : 0),
                    lineHeight / 2,
                    canvas.getWidth(),
                    lineHeight / 2,
                    incompletePaint);
        }


        temp.clipPath(clipPath, Region.Op.DIFFERENCE);
        temp.drawColor(0, PorterDuff.Mode.DST_ATOP);

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }

    public void setProgress(float progress) {
        if (progress > 1.0f || progress < 0) {
            throw new RuntimeException("Value must be between 0 and 1: " + progress);
        }
        this.progress = progress;

        this.invalidate();
    }

    public void startAnimation() {
        ValueAnimator anim = ValueAnimator.ofFloat(currentProgress, progress);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ProgressBar.this.currentProgress = (Float) valueAnimator.getAnimatedValue();
                ProgressBar.this.invalidate();
            }
        });
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(getMeasuredWidth(), lineHeight);
    }
}
