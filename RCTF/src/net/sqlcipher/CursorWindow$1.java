// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.os.Parcel;

// Referenced classes of package net.sqlcipher:
//            CursorWindow

static final class 
    implements android.os.tor
{

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

    ()
    {
    }
}
