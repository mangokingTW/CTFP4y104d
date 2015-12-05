// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

// Referenced classes of package net.sqlcipher:
//            IBulkCursor, BulkCursorProxy, CursorWindow, DatabaseUtils

public abstract class BulkCursorNative extends Binder
    implements IBulkCursor
{

    public BulkCursorNative()
    {
        attachInterface(this, "android.content.IBulkCursor");
    }

    public static IBulkCursor asInterface(IBinder ibinder)
    {
        IBulkCursor ibulkcursor;
        if (ibinder == null)
        {
            ibulkcursor = null;
        } else
        {
            IBulkCursor ibulkcursor1 = (IBulkCursor)ibinder.queryLocalInterface("android.content.IBulkCursor");
            ibulkcursor = ibulkcursor1;
            if (ibulkcursor1 == null)
            {
                return new BulkCursorProxy(ibinder);
            }
        }
        return ibulkcursor;
    }

    public IBinder asBinder()
    {
        return this;
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel1, int j)
        throws RemoteException
    {
        i;
        JVM INSTR tableswitch 1 12: default 64
    //                   1 74
    //                   2 117
    //                   3 139
    //                   4 267
    //                   5 303
    //                   6 186
    //                   7 218
    //                   8 338
    //                   9 358
    //                   10 388
    //                   11 410
    //                   12 202;
           goto _L1 _L2 _L3 _L4 _L5 _L6 _L7 _L8 _L9 _L10 _L11 _L12 _L13
_L1:
        return super.onTransact(i, parcel, parcel1, j);
_L2:
        boolean flag;
        try
        {
            parcel.enforceInterface("android.content.IBulkCursor");
            parcel = getWindow(parcel.readInt());
        }
        // Misplaced declaration of an exception variable
        catch (Parcel parcel)
        {
            DatabaseUtils.writeExceptionToParcel(parcel1, parcel);
            return true;
        }
        if (parcel != null) goto _L15; else goto _L14
_L14:
        parcel1.writeInt(0);
        return true;
_L15:
        parcel1.writeNoException();
        parcel1.writeInt(1);
        parcel.writeToParcel(parcel1, 0);
        return true;
_L3:
        parcel.enforceInterface("android.content.IBulkCursor");
        i = count();
        parcel1.writeNoException();
        parcel1.writeInt(i);
        return true;
_L4:
        parcel.enforceInterface("android.content.IBulkCursor");
        parcel = getColumnNames();
        parcel1.writeNoException();
        parcel1.writeInt(parcel.length);
        j = parcel.length;
        i = 0;
_L17:
        if (i >= j)
        {
            break; /* Loop/switch isn't completed */
        }
        parcel1.writeString(parcel[i]);
        i++;
        if (true) goto _L17; else goto _L16
_L7:
        parcel.enforceInterface("android.content.IBulkCursor");
        deactivate();
        parcel1.writeNoException();
        return true;
_L13:
        parcel.enforceInterface("android.content.IBulkCursor");
        close();
        parcel1.writeNoException();
        return true;
_L8:
        parcel.enforceInterface("android.content.IBulkCursor");
        i = requery(IContentObserver.Stub.asInterface(parcel.readStrongBinder()), (CursorWindow)CursorWindow.CREATOR.createFromParcel(parcel));
        parcel1.writeNoException();
        parcel1.writeInt(i);
        parcel1.writeBundle(getExtras());
        return true;
_L5:
        parcel.enforceInterface("android.content.IBulkCursor");
        flag = updateRows(parcel.readHashMap(null));
        parcel1.writeNoException();
        if (flag)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel1.writeInt(i);
        return true;
_L6:
        parcel.enforceInterface("android.content.IBulkCursor");
        flag = deleteRow(parcel.readInt());
        parcel1.writeNoException();
        if (flag)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel1.writeInt(i);
        return true;
_L9:
        parcel.enforceInterface("android.content.IBulkCursor");
        onMove(parcel.readInt());
        parcel1.writeNoException();
        return true;
_L10:
        parcel.enforceInterface("android.content.IBulkCursor");
        flag = getWantsAllOnMoveCalls();
        parcel1.writeNoException();
        if (flag)
        {
            i = 1;
        } else
        {
            i = 0;
        }
        parcel1.writeInt(i);
        return true;
_L11:
        parcel.enforceInterface("android.content.IBulkCursor");
        parcel = getExtras();
        parcel1.writeNoException();
        parcel1.writeBundle(parcel);
        return true;
_L12:
        parcel.enforceInterface("android.content.IBulkCursor");
        parcel = respond(parcel.readBundle());
        parcel1.writeNoException();
        parcel1.writeBundle(parcel);
        return true;
_L16:
        return true;
    }
}
