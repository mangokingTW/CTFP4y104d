// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.io;

import java.io.IOException;
import java.io.InputStream;

// Referenced classes of package com.google.common.io:
//            InputSupplier, FileBackedOutputStream

class this._cls0
    implements InputSupplier
{

    final FileBackedOutputStream this$0;

    protected void finalize()
    {
        try
        {
            reset();
            return;
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace(System.err);
        }
    }

    public InputStream getInput()
        throws IOException
    {
        return FileBackedOutputStream.access$100(FileBackedOutputStream.this);
    }

    public volatile Object getInput()
        throws IOException
    {
        return getInput();
    }

    ()
    {
        this$0 = FileBackedOutputStream.this;
        super();
    }
}
