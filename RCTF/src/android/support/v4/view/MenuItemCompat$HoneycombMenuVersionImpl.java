// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package android.support.v4.view;

import android.view.MenuItem;
import android.view.View;

// Referenced classes of package android.support.v4.view:
//            MenuItemCompat, MenuItemCompatHoneycomb

static class 
    implements 
{

    public MenuItem setActionView(MenuItem menuitem, View view)
    {
        return MenuItemCompatHoneycomb.setActionView(menuitem, view);
    }

    public boolean setShowAsAction(MenuItem menuitem, int i)
    {
        MenuItemCompatHoneycomb.setShowAsAction(menuitem, i);
        return true;
    }

    ()
    {
    }
}