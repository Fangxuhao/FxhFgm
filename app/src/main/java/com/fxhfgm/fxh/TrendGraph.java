package com.fxhfgm.fxh;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.support.constraint.Constraints.TAG;

/**
 * 方徐浩
 */
public class TrendGraph extends View {//自定义折线控件
    public TrendGraph(Context context) {//在Java代码中创建调用
        super(context);
    }

    public TrendGraph(Context context, AttributeSet attrs) {//在xml中创建调用
        super(context, attrs);
    }

    //@Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//       if (mElements == null || mElements.size() == 0) {
//                      return;
//                   }
//                double max_up = getMaxUp();
//                double min_down = getMinDown();
//                canvas.setDrawFilter(mDrawFilter);
//                 mPaint.setStrokeWidth(lineWeith);
//                 float width = getWidth();
//                 float grap = width / mElements.size();
//                 float textSize = mTextPaint.getTextSize();
//                 int textMargin = circleRadius * 2;
//                 float margin_top = textSize + 2 * textMargin;
//                 Log.d(TAG, "onDraw: " + margin_top + "|" + textSize);
//                 float height = getHeight() - 2 * margin_top;
//
//                 for (int i = 0; i < mElements.size() - 1; i++) {
//                         float startX = i * grap + grap / 2;
//                         float stopX = (i + 1) * grap + grap / 2;
//                        float startY = (float) (max_up - mElements.get(i).getUp()) / (float) (max_up -
//                                         min_down) * height + margin_top;
//                         float stopY = (float) (max_up - mElements.get(i + 1).getUp()) / (float) (max_up -
//                                         min_down) * height + margin_top;
//
//                         canvas.drawText((int) mElements.get(i).getUp() + "℃", startX - textSize, startY -
//                                         textMargin, mTextPaint);
//                         canvas.drawCircle(startX, startY, circleRadius, mPaint);
//                         canvas.drawLine(startX, startY, stopX, stopY, mPaint);
//                         if (i == mElements.size() - 2) {
//                                 canvas.drawText((int) mElements.get(i + 1).getUp() + "℃", stopX - textSize, stopY
//                                                 - textMargin, mTextPaint);
//                                canvas.drawCircle(stopX, stopY, circleRadius, mPaint);
//                             }
//
//                         startY = (float) (max_up - mElements.get(i).getDown()) / (float) (max_up - min_down) *
//                                         height + margin_top;
//                         stopY = (float) (max_up - mElements.get(i + 1).getDown()) / (float) (max_up -
//                                         min_down) * height + margin_top;
//                         canvas.drawText((int) mElements.get(i).getDown() + "℃", startX - textSize, startY +
//                                        textSize + textMargin, mTextPaint);
//                         canvas.drawCircle(startX, startY, circleRadius, mPaint);
//                         canvas.drawLine(startX, startY, stopX, stopY, mPaint);
//                         if (i == mElements.size() - 2) {
//                                 canvas.drawText((int) mElements.get(i + 1).getDown() + "℃", stopX - textSize,
//                                                 stopY + textSize + textMargin, mTextPaint);
//                                 canvas.drawCircle(stopX, stopY, circleRadius, mPaint);
//                            }
//                    }
//    }
}
