// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.util.concurrent;

import java.util.concurrent.Future;

// Referenced classes of package com.google.common.util.concurrent:
//            Futures, ExecutionList

class this._cls0
    implements Runnable
{

    final this._cls0 this$0;

    public void run()
    {
        try
        {
            cess._mth200(this._cls0.this).get();
        }
        catch (Error error)
        {
            throw error;
        }
        catch (InterruptedException interruptedexception)
        {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Adapter thread interrupted!", interruptedexception);
        }
        catch (Throwable throwable) { }
        cess._mth300(this._cls0.this).run();
    }

    I()
    {
        this$0 = this._cls0.this;
        super();
    }
}
