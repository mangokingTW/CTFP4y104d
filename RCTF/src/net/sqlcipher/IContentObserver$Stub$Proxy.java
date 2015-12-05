// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

// Referenced classes of package net.sqlcipher:
//            IContentObserver

private static class mRemote
    implements IContentObserver
{

    private IBinder mRemote;

    public IBinder asBinder()
    {
        return mRemote;
    }

    public String getInterfaceDescriptor()
    {
        return "net.sqlcipher.IContentObserver";
    }

    public void onChange(boolean flag)
        throws RemoteException
    {
        Parcel parcel;
        int i;
        i = 1;
        parcel = Parcel.obtain();
        parcel.writeInterfaceToken("net.sqlcipher.IContentObserver");
        if (!flag)
        {
            i = 0;
        }
        parcel.writeInt(i);
        mRemote.transact(1, parcel, null, 1);
        parcel.recycle();
        return;
        Exception exception;
        exception;
        parcel.recycle();
        throw exception;
    }

    (IBinder ibinder)
    {
        mRemote = ibinder;
    }
}
