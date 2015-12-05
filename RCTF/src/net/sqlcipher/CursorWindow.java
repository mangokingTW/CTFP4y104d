// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.database.CharArrayBuffer;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class CursorWindow extends android.database.CursorWindow
    implements Parcelable
{

    public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator() {

        public volatile Object createFromParcel(Parcel parcel)
        {
            return createFromParcel(parcel);
        }

        public CursorWindow createFromParcel(Parcel parcel)
        {
            return new CursorWindow(parcel, 0);
        }

        public volatile Object[] newArray(int i)
        {
            return newArray(i);
        }

        public CursorWindow[] newArray(int i)
        {
            return new CursorWindow[i];
        }

    };
    private int mStartPos;
    private int nWindow;

    public CursorWindow(Parcel parcel, int i)
    {
        super(true);
        IBinder ibinder = parcel.readStrongBinder();
        mStartPos = parcel.readInt();
        native_init(ibinder);
    }

    public CursorWindow(boolean flag)
    {
        super(flag);
        mStartPos = 0;
        native_init(flag);
    }

    private native boolean allocRow_native();

    private native void close_native();

    private native char[] copyStringToBuffer_native(int i, int j, int k, CharArrayBuffer chararraybuffer);

    private native void freeLastRow_native();

    private native byte[] getBlob_native(int i, int j);

    private native double getDouble_native(int i, int j);

    private native long getLong_native(int i, int j);

    private native int getNumRows_native();

    private native String getString_native(int i, int j);

    private native int getType_native(int i, int j);

    private native boolean isBlob_native(int i, int j);

    private native boolean isFloat_native(int i, int j);

    private native boolean isInteger_native(int i, int j);

    private native boolean isNull_native(int i, int j);

    private native boolean isString_native(int i, int j);

    private native void native_clear();

    private native IBinder native_getBinder();

    private native void native_init(IBinder ibinder);

    private native void native_init(boolean flag);

    public static CursorWindow newFromParcel(Parcel parcel)
    {
        return (CursorWindow)CREATOR.createFromParcel(parcel);
    }

    private native boolean putBlob_native(byte abyte0[], int i, int j);

    private native boolean putDouble_native(double d, int i, int j);

    private native boolean putLong_native(long l, int i, int j);

    private native boolean putNull_native(int i, int j);

    private native boolean putString_native(String s, int i, int j);

    private native boolean setNumColumns_native(int i);

    public boolean allocRow()
    {
        acquireReference();
        boolean flag = allocRow_native();
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void clear()
    {
        acquireReference();
        mStartPos = 0;
        native_clear();
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void close()
    {
        releaseReference();
    }

    public void copyStringToBuffer(int i, int j, CharArrayBuffer chararraybuffer)
    {
        if (chararraybuffer == null)
        {
            throw new IllegalArgumentException("CharArrayBuffer should not be null");
        }
        if (chararraybuffer.data == null)
        {
            chararraybuffer.data = new char[64];
        }
        acquireReference();
        char ac[] = copyStringToBuffer_native(i - mStartPos, j, chararraybuffer.data.length, chararraybuffer);
        if (ac == null)
        {
            break MISSING_BLOCK_LABEL_63;
        }
        chararraybuffer.data = ac;
        releaseReference();
        return;
        chararraybuffer;
        releaseReference();
        throw chararraybuffer;
    }

    public int describeContents()
    {
        return 0;
    }

    protected void finalize()
    {
        if (nWindow == 0)
        {
            return;
        } else
        {
            close_native();
            return;
        }
    }

    public void freeLastRow()
    {
        acquireReference();
        freeLastRow_native();
        releaseReference();
        return;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public byte[] getBlob(int i, int j)
    {
        acquireReference();
        byte abyte0[] = getBlob_native(i - mStartPos, j);
        releaseReference();
        return abyte0;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public double getDouble(int i, int j)
    {
        acquireReference();
        double d = getDouble_native(i - mStartPos, j);
        releaseReference();
        return d;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public float getFloat(int i, int j)
    {
        acquireReference();
        double d = getDouble_native(i - mStartPos, j);
        float f = (float)d;
        releaseReference();
        return f;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public int getInt(int i, int j)
    {
        acquireReference();
        long l = getLong_native(i - mStartPos, j);
        i = (int)l;
        releaseReference();
        return i;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public long getLong(int i, int j)
    {
        acquireReference();
        long l = getLong_native(i - mStartPos, j);
        releaseReference();
        return l;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public int getNumRows()
    {
        acquireReference();
        int i = getNumRows_native();
        releaseReference();
        return i;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public short getShort(int i, int j)
    {
        acquireReference();
        long l = getLong_native(i - mStartPos, j);
        short word0 = (short)(int)l;
        releaseReference();
        return word0;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public int getStartPosition()
    {
        return mStartPos;
    }

    public String getString(int i, int j)
    {
        acquireReference();
        String s = getString_native(i - mStartPos, j);
        releaseReference();
        return s;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public int getType(int i, int j)
    {
        acquireReference();
        i = getType_native(i - mStartPos, j);
        releaseReference();
        return i;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean isBlob(int i, int j)
    {
        acquireReference();
        boolean flag = isBlob_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean isFloat(int i, int j)
    {
        acquireReference();
        boolean flag = isFloat_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean isLong(int i, int j)
    {
        acquireReference();
        boolean flag = isInteger_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean isNull(int i, int j)
    {
        acquireReference();
        boolean flag = isNull_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean isString(int i, int j)
    {
        acquireReference();
        boolean flag = isString_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    protected void onAllReferencesReleased()
    {
        close_native();
        super.onAllReferencesReleased();
    }

    public boolean putBlob(byte abyte0[], int i, int j)
    {
        acquireReference();
        boolean flag = putBlob_native(abyte0, i - mStartPos, j);
        releaseReference();
        return flag;
        abyte0;
        releaseReference();
        throw abyte0;
    }

    public boolean putDouble(double d, int i, int j)
    {
        acquireReference();
        boolean flag = putDouble_native(d, i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean putLong(long l, int i, int j)
    {
        acquireReference();
        boolean flag = putLong_native(l, i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean putNull(int i, int j)
    {
        acquireReference();
        boolean flag = putNull_native(i - mStartPos, j);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public boolean putString(String s, int i, int j)
    {
        acquireReference();
        boolean flag = putString_native(s, i - mStartPos, j);
        releaseReference();
        return flag;
        s;
        releaseReference();
        throw s;
    }

    public boolean setNumColumns(int i)
    {
        acquireReference();
        boolean flag = setNumColumns_native(i);
        releaseReference();
        return flag;
        Exception exception;
        exception;
        releaseReference();
        throw exception;
    }

    public void setStartPosition(int i)
    {
        mStartPos = i;
    }

    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeStrongBinder(native_getBinder());
        parcel.writeInt(mStartPos);
    }

}
