// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.database.CharArrayBuffer;
import android.database.CursorWindow;

// Referenced classes of package net.sqlcipher:
//            AbstractCursor, StaleDataException, CursorWindow

public abstract class AbstractWindowedCursor extends AbstractCursor
{

    protected net.sqlcipher.CursorWindow mWindow;

    public AbstractWindowedCursor()
    {
    }

    protected void checkPosition()
    {
        super.checkPosition();
        if (mWindow == null)
        {
            throw new StaleDataException("Access closed cursor");
        } else
        {
            return;
        }
    }

    public void copyStringToBuffer(int i, CharArrayBuffer chararraybuffer)
    {
        checkPosition();
        synchronized (mUpdatedRows)
        {
            if (isFieldUpdated(i))
            {
                super.copyStringToBuffer(i, chararraybuffer);
            }
        }
        mWindow.copyStringToBuffer(mPos, i, chararraybuffer);
        return;
        chararraybuffer;
        hashmap;
        JVM INSTR monitorexit ;
        throw chararraybuffer;
    }

    public byte[] getBlob(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        byte abyte0[];
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_35;
        }
        abyte0 = (byte[])(byte[])getUpdatedField(i);
        return abyte0;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getBlob(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public double getDouble(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        double d;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_38;
        }
        d = ((Number)getUpdatedField(i)).doubleValue();
        return d;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getDouble(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public float getFloat(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        float f;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_35;
        }
        f = ((Number)getUpdatedField(i)).floatValue();
        return f;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getFloat(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int getInt(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_35;
        }
        i = ((Number)getUpdatedField(i)).intValue();
        return i;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getInt(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public long getLong(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        long l;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_37;
        }
        l = ((Number)getUpdatedField(i)).longValue();
        return l;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getLong(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public short getShort(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        short word0;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_35;
        }
        word0 = ((Number)getUpdatedField(i)).shortValue();
        return word0;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getShort(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public String getString(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        String s;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_32;
        }
        s = (String)getUpdatedField(i);
        return s;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.getString(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public int getType(int i)
    {
        checkPosition();
        return mWindow.getType(mPos, i);
    }

    public volatile CursorWindow getWindow()
    {
        return getWindow();
    }

    public net.sqlcipher.CursorWindow getWindow()
    {
        return mWindow;
    }

    public boolean hasWindow()
    {
        return mWindow != null;
    }

    public boolean isBlob(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i)) goto _L2; else goto _L1
_L1:
        Object obj = getUpdatedField(i);
        if (obj == null) goto _L4; else goto _L3
_L3:
        if (!(obj instanceof byte[])) goto _L5; else goto _L4
_L7:
        hashmap;
        JVM INSTR monitorexit ;
        boolean flag;
        return flag;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.isBlob(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        flag = false;
        if (true) goto _L7; else goto _L6
_L6:
    }

    public boolean isFloat(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i)) goto _L2; else goto _L1
_L1:
        Object obj = getUpdatedField(i);
        if (obj == null) goto _L4; else goto _L3
_L3:
        Exception exception;
        boolean flag;
        if ((obj instanceof Float) || (obj instanceof Double))
        {
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
          goto _L4
_L6:
        hashmap;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.isFloat(mPos, i);
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        flag = false;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public boolean isLong(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i)) goto _L2; else goto _L1
_L1:
        Object obj = getUpdatedField(i);
        if (obj == null) goto _L4; else goto _L3
_L3:
        Exception exception;
        boolean flag;
        if ((obj instanceof Integer) || (obj instanceof Long))
        {
            flag = true;
            continue; /* Loop/switch isn't completed */
        }
          goto _L4
_L6:
        hashmap;
        JVM INSTR monitorexit ;
        return flag;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.isLong(mPos, i);
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        flag = false;
        if (true) goto _L6; else goto _L5
_L5:
    }

    public boolean isNull(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i))
        {
            break MISSING_BLOCK_LABEL_35;
        }
        Exception exception;
        boolean flag;
        if (getUpdatedField(i) == null)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        return flag;
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.isNull(mPos, i);
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
    }

    public boolean isString(int i)
    {
        checkPosition();
        java.util.HashMap hashmap = mUpdatedRows;
        hashmap;
        JVM INSTR monitorenter ;
        if (!isFieldUpdated(i)) goto _L2; else goto _L1
_L1:
        Object obj = getUpdatedField(i);
        if (obj == null) goto _L4; else goto _L3
_L3:
        if (!(obj instanceof String)) goto _L5; else goto _L4
_L7:
        hashmap;
        JVM INSTR monitorexit ;
        boolean flag;
        return flag;
_L2:
        hashmap;
        JVM INSTR monitorexit ;
        return mWindow.isString(mPos, i);
        Exception exception;
        exception;
        hashmap;
        JVM INSTR monitorexit ;
        throw exception;
_L4:
        flag = true;
        continue; /* Loop/switch isn't completed */
_L5:
        flag = false;
        if (true) goto _L7; else goto _L6
_L6:
    }

    public void setWindow(net.sqlcipher.CursorWindow cursorwindow)
    {
        if (mWindow != null)
        {
            mWindow.close();
        }
        mWindow = cursorwindow;
    }
}
