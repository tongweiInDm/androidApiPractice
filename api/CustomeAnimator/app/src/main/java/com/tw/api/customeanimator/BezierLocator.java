package com.tw.api.customeanimator;

import android.graphics.PointF;

public class BezierLocator {
    PointF mPoint1;
    PointF mPoint2;
    PointF mPoint3;

    PointF mResult;

    public BezierLocator(PointF point1, PointF point2, PointF point3) {
        this.mPoint1 = point1;
        this.mPoint2 = point2;
        this.mPoint3 = point3;
        this.mResult = new PointF();
    }

    public PointF get(float t) {
        t = Math.max(Math.min(t, 1), 0);
        float tempPoint12X = mPoint1.x * (1 - t) + mPoint2.x * t;
        float tempPoint12Y = mPoint1.y * (1 - t) + mPoint2.y * t;

        float tempPoint23X = mPoint2.x * (1 - t) + mPoint3.x * t;
        float tempPoint23Y = mPoint2.y * (1 - t) + mPoint3.y * t;

        mResult.x = tempPoint12X * (1 - t) + tempPoint23X * t;
        mResult.y = tempPoint12Y * (1 - t) + tempPoint23Y * t;
        return mResult;
    }
}
