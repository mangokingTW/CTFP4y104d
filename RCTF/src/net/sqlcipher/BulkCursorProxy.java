// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

// Referenced classes of package net.sqlcipher:
//            IBulkCursor, DatabaseUtils, CursorWindow, IContentObserver

final class BulkCursorProxy
    implements IBulkCursor
{

    private Bundle mExtras;
    private IBinder mRemote;

    public BulkCursorProxy(IBinder ibinder)
    {
        mRemote = ibinder;
        mExtras = null;
    }

    public IBinder asBinder()
    {
        return mRemote;
    }

    public void close()
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        mRemote.transact(12, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        parcel.recycle();
        parcel1.recycle();
    }

    public int count()
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        boolean flag = mRemote.transact(2, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        int i;
        if (!flag)
        {
            i = -1;
        } else
        {
            i = parcel1.readInt();
        }
        parcel.recycle();
        parcel1.recycle();
        return i;
    }

    public void deactivate()
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        mRemote.transact(6, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        parcel.recycle();
        parcel1.recycle();
    }

    public boolean deleteRow(int i)
        throws RemoteException
    {
        boolean flag = true;
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeInt(i);
        mRemote.transact(5, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        if (parcel1.readInt() != 1)
        {
            flag = false;
        }
        parcel.recycle();
        parcel1.recycle();
        return flag;
    }

    public String[] getColumnNames()
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        mRemote.transact(3, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        int j = parcel1.readInt();
        String as[] = new String[j];
        for (int i = 0; i < j; i++)
        {
            as[i] = parcel1.readString();
        }

        parcel.recycle();
        parcel1.recycle();
        return as;
    }

    public Bundle getExtras()
        throws RemoteException
    {
        if (mExtras == null)
        {
            Parcel parcel = Parcel.obtain();
            Parcel parcel1 = Parcel.obtain();
            parcel.writeInterfaceToken("android.content.IBulkCursor");
            mRemote.transact(10, parcel, parcel1, 0);
            DatabaseUtils.readExceptionFromParcel(parcel1);
            mExtras = parcel1.readBundle();
            parcel.recycle();
            parcel1.recycle();
        }
        return mExtras;
    }

    public boolean getWantsAllOnMoveCalls()
        throws RemoteException
    {
        boolean flag = false;
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        mRemote.transact(9, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        int i = parcel1.readInt();
        parcel.recycle();
        parcel1.recycle();
        if (i != 0)
        {
            flag = true;
        }
        return flag;
    }

    public CursorWindow getWindow(int i)
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeInt(i);
        mRemote.transact(1, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        CursorWindow cursorwindow = null;
        if (parcel1.readInt() == 1)
        {
            cursorwindow = CursorWindow.newFromParcel(parcel1);
        }
        parcel.recycle();
        parcel1.recycle();
        return cursorwindow;
    }

    public void onMove(int i)
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeInt(i);
        mRemote.transact(8, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        parcel.recycle();
        parcel1.recycle();
    }

    public int requery(IContentObserver icontentobserver, CursorWindow cursorwindow)
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeStrongInterface(icontentobserver);
        cursorwindow.writeToParcel(parcel, 0);
        boolean flag = mRemote.transact(7, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        int i;
        if (!flag)
        {
            i = -1;
        } else
        {
            i = parcel1.readInt();
            mExtras = parcel1.readBundle();
        }
        parcel.recycle();
        parcel1.recycle();
        return i;
    }

    public Bundle respond(Bundle bundle)
        throws RemoteException
    {
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeBundle(bundle);
        mRemote.transact(11, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        bundle = parcel1.readBundle();
        parcel.recycle();
        parcel1.recycle();
        return bundle;
    }

    public boolean updateRows(Map map)
        throws RemoteException
    {
        boolean flag = true;
        Parcel parcel = Parcel.obtain();
        Parcel parcel1 = Parcel.obtain();
        parcel.writeInterfaceToken("android.content.IBulkCursor");
        parcel.writeMap(map);
        mRemote.transact(4, parcel, parcel1, 0);
        DatabaseUtils.readExceptionFromParcel(parcel1);
        if (parcel1.readInt() != 1)
        {
            flag = false;
        }
        parcel.recycle();
        parcel1.recycle();
        return flag;
    }
}
