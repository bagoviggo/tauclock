package com.gorillasapiens.sunclock1
import android.graphics.*
import android.graphics.Paint.Style
import android.graphics.drawable.Drawable
import android.location.Location
import java.nio.IntBuffer


class SunclockDrawable(width: Int, height: Int) : Drawable() {
    private val mPaint: Paint
    private val mWidth: Int
    private val mHeight: Int
    private var mThing: IntArray

    //private native void do_all(double, double,double);

    fun setThing(something: IntArray) {
        mThing = something;
    }

    override fun draw(canvas: Canvas) {
        synchronized(mThing) {
            if (mThing.size > 0) {
                val w = mThing[0];
                val h = mThing[1];

                val x = (mWidth / 2.0f) - (w / 2.0f)
                val y = (mHeight / 2.0f) - (h / 2.0f)

                val bitmap = Bitmap.createBitmap(mThing.sliceArray(IntRange(2,mThing.size-1)),
                                                 w.toInt(),
                                                 h.toInt(),
                                                 Bitmap.Config.ARGB_8888);
                canvas.drawBitmap(bitmap,
                              null,
                                  Rect(x.toInt(),
                                       y.toInt(),
                                      x.toInt()+w.toInt(),
                                      y.toInt()+h.toInt()),
                            null)

                //canvas.drawBitmap(mThing, 2, w.toInt(), x.toInt(), y.toInt(), w.toInt(), h.toInt(), true, null)

                mPaint.setARGB(255, 0, 0, 0)
                mPaint.setStrokeWidth(2.0f)
                mPaint.setStyle(Style.FILL)

                var r : Rect;
                // above
                r = Rect(0,0,mWidth.toInt(),y.toInt());
                canvas.drawRect(r, mPaint);
                // below
                r = Rect(0,(y+h).toInt(),mWidth.toInt(), mHeight.toInt());
                canvas.drawRect(r, mPaint);
                // left
                r = Rect(0,y.toInt(),x.toInt(), (y+h).toInt());
                canvas.drawRect(r, mPaint);
                // right
                r = Rect((x+w).toInt(),y.toInt(),mWidth.toInt(), (y+h).toInt());
                canvas.drawRect(r, mPaint);
            } else {
                mPaint.setARGB(255, 0, 0, 0)
                mPaint.setStrokeWidth(2.0f)
                mPaint.setStyle(Style.FILL)

                var r : Rect;
                // above
                r = Rect(0,0,mWidth.toInt(),mHeight.toInt());
                canvas.drawRect(r, mPaint);
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setAlpha(arg0: Int) {}
    override fun setColorFilter(arg0: ColorFilter?) {}

    init {
        mPaint = Paint()
        mWidth = width
        mHeight = height
        mThing = IntArray(0)
    }
}