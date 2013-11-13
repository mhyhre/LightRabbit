package mhyhre.lightrabbit.game;

import mhyhre.lightrabbit.MainActivity;
import android.util.Log;

public class LevelItem {

    float mX, mY;
    float mWidth, mHeight;
    boolean mLocked;

    public String filename;
    public String label;

    public LevelItem() {

    }

    public float getLeftX() {
        return mX - mWidth / 2;
    }

    public float getDownY() {
        return mY - mHeight / 2;
    }

    public float getX() {
        return mX;
    }

    public void setX(float pX) {
        this.mX = pX;
    }

    public float getY() {
        return mY;
    }

    public void setY(float pY) {
        this.mY = pY;
    }

    public float getWidth() {
        return mWidth;
    }

    public void setWidth(float pWidth) {
        this.mWidth = pWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public void setHeight(float pHeight) {
        this.mHeight = pHeight;
    }

    public boolean isLocked() {
        return mLocked;
    }

    public void setLocked(boolean locked) {
        this.mLocked = locked;
    }

    public boolean isCollided(float x, float y) {

        float dx = mX - mWidth / 2;
        float dy = mY - mHeight / 2;

        if (x >= dx && x <= dx + mWidth) {
            if (y >= dy && y <= dy + mHeight) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        Log.i(MainActivity.DEBUG_ID, "Level:" + label + "filename:" + filename);
    }
}
