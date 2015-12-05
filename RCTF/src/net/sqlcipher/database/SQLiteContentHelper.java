// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher.database;

import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

// Referenced classes of package net.sqlcipher.database:
//            SQLiteDatabase

public class SQLiteContentHelper
{

    public SQLiteContentHelper()
    {
    }

    public static AssetFileDescriptor getBlobColumnAsAssetFile(SQLiteDatabase sqlitedatabase, String s, String as[])
        throws FileNotFoundException
    {
        Object obj;
        obj = null;
        try
        {
            s = simpleQueryForBlobMemoryFile(sqlitedatabase, s, as);
        }
        // Misplaced declaration of an exception variable
        catch (SQLiteDatabase sqlitedatabase)
        {
            throw new FileNotFoundException(sqlitedatabase.toString());
        }
        if (s != null)
        {
            break MISSING_BLOCK_LABEL_36;
        }
        throw new FileNotFoundException("No results.");
        sqlitedatabase = s.getClass();
        sqlitedatabase = sqlitedatabase.getDeclaredMethod("getParcelFileDescriptor", new Class[0]);
        sqlitedatabase.setAccessible(true);
        sqlitedatabase = (ParcelFileDescriptor)sqlitedatabase.invoke(s, new Object[0]);
_L1:
        return new AssetFileDescriptor(sqlitedatabase, 0L, s.length());
        sqlitedatabase;
        Log.i("SQLiteContentHelper", (new StringBuilder()).append("SQLiteCursor.java: ").append(sqlitedatabase).toString());
        sqlitedatabase = obj;
          goto _L1
    }

    private static MemoryFile simpleQueryForBlobMemoryFile(SQLiteDatabase sqlitedatabase, String s, String as[])
        throws IOException
    {
        sqlitedatabase = sqlitedatabase.rawQuery(s, as);
        if (sqlitedatabase == null)
        {
            return null;
        }
        boolean flag = sqlitedatabase.moveToFirst();
        if (!flag)
        {
            sqlitedatabase.close();
            return null;
        }
        s = sqlitedatabase.getBlob(0);
        if (s == null)
        {
            sqlitedatabase.close();
            return null;
        }
        as = new MemoryFile(null, s.length);
        as.writeBytes(s, 0, 0, s.length);
        sqlitedatabase.close();
        return as;
        s;
        sqlitedatabase.close();
        throw s;
    }
}
