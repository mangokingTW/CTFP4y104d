// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package net.sqlcipher;

import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.CollationKey;
import java.text.Collator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import net.sqlcipher.database.SQLiteAbortException;
import net.sqlcipher.database.SQLiteConstraintException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseCorruptException;
import net.sqlcipher.database.SQLiteDiskIOException;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteFullException;
import net.sqlcipher.database.SQLiteProgram;
import net.sqlcipher.database.SQLiteStatement;
import org.apache.commons.codec.binary.Hex;

// Referenced classes of package net.sqlcipher:
//            AbstractWindowedCursor, SQLException

public class DatabaseUtils
{
    public static class InsertHelper
    {

        public static final int TABLE_INFO_PRAGMA_COLUMNNAME_INDEX = 1;
        public static final int TABLE_INFO_PRAGMA_DEFAULT_INDEX = 4;
        private HashMap mColumns;
        private final SQLiteDatabase mDb;
        private String mInsertSQL;
        private SQLiteStatement mInsertStatement;
        private SQLiteStatement mPreparedStatement;
        private SQLiteStatement mReplaceStatement;
        private final String mTableName;

        private void buildSQL()
            throws SQLException
        {
            net.sqlcipher.Cursor cursor;
            StringBuilder stringbuilder;
            StringBuilder stringbuilder1;
            int i;
            stringbuilder = new StringBuilder(128);
            stringbuilder.append("INSERT INTO ");
            stringbuilder.append(mTableName);
            stringbuilder.append(" (");
            stringbuilder1 = new StringBuilder(128);
            stringbuilder1.append("VALUES (");
            i = 1;
            cursor = null;
            net.sqlcipher.Cursor cursor1 = mDb.rawQuery((new StringBuilder()).append("PRAGMA table_info(").append(mTableName).append(")").toString(), null);
            cursor = cursor1;
            mColumns = new HashMap(cursor1.getCount());
_L6:
            cursor = cursor1;
            if (!cursor1.moveToNext())
            {
                break MISSING_BLOCK_LABEL_327;
            }
            cursor = cursor1;
            Object obj = cursor1.getString(1);
            cursor = cursor1;
            String s = cursor1.getString(4);
            cursor = cursor1;
            mColumns.put(obj, Integer.valueOf(i));
            cursor = cursor1;
            stringbuilder.append("'");
            cursor = cursor1;
            stringbuilder.append(((String) (obj)));
            cursor = cursor1;
            stringbuilder.append("'");
            if (s != null) goto _L2; else goto _L1
_L1:
            cursor = cursor1;
            stringbuilder1.append("?");
_L4:
            cursor = cursor1;
            if (i == cursor1.getCount())
            {
                obj = ") ";
            } else
            {
                obj = ", ";
            }
            cursor = cursor1;
            stringbuilder.append(((String) (obj)));
            cursor = cursor1;
            if (i == cursor1.getCount())
            {
                obj = ");";
            } else
            {
                obj = ", ";
            }
            cursor = cursor1;
            stringbuilder1.append(((String) (obj)));
            i++;
            continue; /* Loop/switch isn't completed */
_L2:
            cursor = cursor1;
            stringbuilder1.append("COALESCE(?, ");
            cursor = cursor1;
            stringbuilder1.append(s);
            cursor = cursor1;
            stringbuilder1.append(")");
            if (true) goto _L4; else goto _L3
_L3:
            obj;
            if (cursor != null)
            {
                cursor.close();
            }
            throw obj;
            if (cursor1 != null)
            {
                cursor1.close();
            }
            stringbuilder.append(stringbuilder1);
            mInsertSQL = stringbuilder.toString();
            return;
            if (true) goto _L6; else goto _L5
_L5:
        }

        private SQLiteStatement getStatement(boolean flag)
            throws SQLException
        {
            if (flag)
            {
                if (mReplaceStatement == null)
                {
                    if (mInsertSQL == null)
                    {
                        buildSQL();
                    }
                    String s = (new StringBuilder()).append("INSERT OR REPLACE").append(mInsertSQL.substring(6)).toString();
                    mReplaceStatement = mDb.compileStatement(s);
                }
                return mReplaceStatement;
            }
            if (mInsertStatement == null)
            {
                if (mInsertSQL == null)
                {
                    buildSQL();
                }
                mInsertStatement = mDb.compileStatement(mInsertSQL);
            }
            return mInsertStatement;
        }

        private long insertInternal(ContentValues contentvalues, boolean flag)
        {
            this;
            JVM INSTR monitorenter ;
            Object obj;
            obj = getStatement(flag);
            ((SQLiteStatement) (obj)).clearBindings();
            java.util.Map.Entry entry;
            for (Iterator iterator = contentvalues.valueSet().iterator(); iterator.hasNext(); DatabaseUtils.bindObjectToProgram(((SQLiteProgram) (obj)), getColumnIndex((String)entry.getKey()), entry.getValue()))
            {
                entry = (java.util.Map.Entry)iterator.next();
            }

              goto _L1
            obj;
            Log.e("DatabaseUtils", (new StringBuilder()).append("Error inserting ").append(contentvalues).append(" into table  ").append(mTableName).toString(), ((Throwable) (obj)));
            long l = -1L;
_L3:
            this;
            JVM INSTR monitorexit ;
            return l;
_L1:
            l = ((SQLiteStatement) (obj)).executeInsert();
            if (true) goto _L3; else goto _L2
_L2:
            contentvalues;
            throw contentvalues;
        }

        public void bind(int i, double d)
        {
            mPreparedStatement.bindDouble(i, d);
        }

        public void bind(int i, float f)
        {
            mPreparedStatement.bindDouble(i, f);
        }

        public void bind(int i, int j)
        {
            mPreparedStatement.bindLong(i, j);
        }

        public void bind(int i, long l)
        {
            mPreparedStatement.bindLong(i, l);
        }

        public void bind(int i, String s)
        {
            if (s == null)
            {
                mPreparedStatement.bindNull(i);
                return;
            } else
            {
                mPreparedStatement.bindString(i, s);
                return;
            }
        }

        public void bind(int i, boolean flag)
        {
            SQLiteStatement sqlitestatement = mPreparedStatement;
            long l;
            if (flag)
            {
                l = 1L;
            } else
            {
                l = 0L;
            }
            sqlitestatement.bindLong(i, l);
        }

        public void bind(int i, byte abyte0[])
        {
            if (abyte0 == null)
            {
                mPreparedStatement.bindNull(i);
                return;
            } else
            {
                mPreparedStatement.bindBlob(i, abyte0);
                return;
            }
        }

        public void bindNull(int i)
        {
            mPreparedStatement.bindNull(i);
        }

        public void close()
        {
            if (mInsertStatement != null)
            {
                mInsertStatement.close();
                mInsertStatement = null;
            }
            if (mReplaceStatement != null)
            {
                mReplaceStatement.close();
                mReplaceStatement = null;
            }
            mInsertSQL = null;
            mColumns = null;
        }

        public long execute()
        {
            if (mPreparedStatement == null)
            {
                throw new IllegalStateException("you must prepare this inserter before calling execute");
            }
            long l = mPreparedStatement.executeInsert();
            mPreparedStatement = null;
            return l;
            Object obj;
            obj;
            Log.e("DatabaseUtils", (new StringBuilder()).append("Error executing InsertHelper with table ").append(mTableName).toString(), ((Throwable) (obj)));
            mPreparedStatement = null;
            return -1L;
            obj;
            mPreparedStatement = null;
            throw obj;
        }

        public int getColumnIndex(String s)
        {
            getStatement(false);
            Integer integer = (Integer)mColumns.get(s);
            if (integer == null)
            {
                throw new IllegalArgumentException((new StringBuilder()).append("column '").append(s).append("' is invalid").toString());
            } else
            {
                return integer.intValue();
            }
        }

        public long insert(ContentValues contentvalues)
        {
            return insertInternal(contentvalues, false);
        }

        public void prepareForInsert()
        {
            mPreparedStatement = getStatement(false);
            mPreparedStatement.clearBindings();
        }

        public void prepareForReplace()
        {
            mPreparedStatement = getStatement(true);
            mPreparedStatement.clearBindings();
        }

        public long replace(ContentValues contentvalues)
        {
            return insertInternal(contentvalues, true);
        }

        public InsertHelper(SQLiteDatabase sqlitedatabase, String s)
        {
            mInsertSQL = null;
            mInsertStatement = null;
            mReplaceStatement = null;
            mPreparedStatement = null;
            mDb = sqlitedatabase;
            mTableName = s;
        }
    }


    private static final boolean DEBUG = false;
    private static final boolean LOCAL_LOGV = false;
    private static final String TAG = "DatabaseUtils";
    private static final String countProjection[] = {
        "count(*)"
    };
    private static Collator mColl = null;

    public DatabaseUtils()
    {
    }

    public static void appendEscapedSQLString(StringBuilder stringbuilder, String s)
    {
        stringbuilder.append('\'');
        if (s.indexOf('\'') != -1)
        {
            int j = s.length();
            for (int i = 0; i < j; i++)
            {
                char c = s.charAt(i);
                if (c == '\'')
                {
                    stringbuilder.append('\'');
                }
                stringbuilder.append(c);
            }

        } else
        {
            stringbuilder.append(s);
        }
        stringbuilder.append('\'');
    }

    public static final void appendValueToSql(StringBuilder stringbuilder, Object obj)
    {
        if (obj == null)
        {
            stringbuilder.append("NULL");
            return;
        }
        if (obj instanceof Boolean)
        {
            if (((Boolean)obj).booleanValue())
            {
                stringbuilder.append('1');
                return;
            } else
            {
                stringbuilder.append('0');
                return;
            }
        } else
        {
            appendEscapedSQLString(stringbuilder, obj.toString());
            return;
        }
    }

    public static void bindObjectToProgram(SQLiteProgram sqliteprogram, int i, Object obj)
    {
        if (obj == null)
        {
            sqliteprogram.bindNull(i);
            return;
        }
        if ((obj instanceof Double) || (obj instanceof Float))
        {
            sqliteprogram.bindDouble(i, ((Number)obj).doubleValue());
            return;
        }
        if (obj instanceof Number)
        {
            sqliteprogram.bindLong(i, ((Number)obj).longValue());
            return;
        }
        if (obj instanceof Boolean)
        {
            if (((Boolean)obj).booleanValue())
            {
                sqliteprogram.bindLong(i, 1L);
                return;
            } else
            {
                sqliteprogram.bindLong(i, 0L);
                return;
            }
        }
        if (obj instanceof byte[])
        {
            sqliteprogram.bindBlob(i, (byte[])(byte[])obj);
            return;
        } else
        {
            sqliteprogram.bindString(i, obj.toString());
            return;
        }
    }

    public static String concatenateWhere(String s, String s1)
    {
        if (TextUtils.isEmpty(s))
        {
            return s1;
        }
        if (TextUtils.isEmpty(s1))
        {
            return s;
        } else
        {
            return (new StringBuilder()).append("(").append(s).append(") AND (").append(s1).append(")").toString();
        }
    }

    public static void cursorDoubleToContentValues(Cursor cursor, String s, ContentValues contentvalues, String s1)
    {
        int i = cursor.getColumnIndex(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s1, Double.valueOf(cursor.getDouble(i)));
            return;
        } else
        {
            contentvalues.put(s1, (Double)null);
            return;
        }
    }

    public static void cursorDoubleToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, Double.valueOf(cursor.getDouble(i)));
        }
    }

    public static void cursorDoubleToCursorValues(Cursor cursor, String s, ContentValues contentvalues)
    {
        cursorDoubleToContentValues(cursor, s, contentvalues, s);
    }

    public static void cursorFloatToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, Float.valueOf(cursor.getFloat(i)));
        }
    }

    public static void cursorIntToContentValues(Cursor cursor, String s, ContentValues contentvalues)
    {
        cursorIntToContentValues(cursor, s, contentvalues, s);
    }

    public static void cursorIntToContentValues(Cursor cursor, String s, ContentValues contentvalues, String s1)
    {
        int i = cursor.getColumnIndex(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s1, Integer.valueOf(cursor.getInt(i)));
            return;
        } else
        {
            contentvalues.put(s1, (Integer)null);
            return;
        }
    }

    public static void cursorIntToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, Integer.valueOf(cursor.getInt(i)));
        }
    }

    public static void cursorLongToContentValues(Cursor cursor, String s, ContentValues contentvalues)
    {
        cursorLongToContentValues(cursor, s, contentvalues, s);
    }

    public static void cursorLongToContentValues(Cursor cursor, String s, ContentValues contentvalues, String s1)
    {
        int i = cursor.getColumnIndex(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s1, Long.valueOf(cursor.getLong(i)));
            return;
        } else
        {
            contentvalues.put(s1, (Long)null);
            return;
        }
    }

    public static void cursorLongToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, Long.valueOf(cursor.getLong(i)));
        }
    }

    public static void cursorRowToContentValues(Cursor cursor, ContentValues contentvalues)
    {
        AbstractWindowedCursor abstractwindowedcursor;
        String as[];
        int i;
        int j;
        if (cursor instanceof AbstractWindowedCursor)
        {
            abstractwindowedcursor = (AbstractWindowedCursor)cursor;
        } else
        {
            abstractwindowedcursor = null;
        }
        as = cursor.getColumnNames();
        j = as.length;
        i = 0;
        while (i < j) 
        {
            if (abstractwindowedcursor != null && abstractwindowedcursor.isBlob(i))
            {
                contentvalues.put(as[i], cursor.getBlob(i));
            } else
            {
                contentvalues.put(as[i], cursor.getString(i));
            }
            i++;
        }
    }

    public static void cursorShortToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, Short.valueOf(cursor.getShort(i)));
        }
    }

    public static void cursorStringToContentValues(Cursor cursor, String s, ContentValues contentvalues)
    {
        cursorStringToContentValues(cursor, s, contentvalues, s);
    }

    public static void cursorStringToContentValues(Cursor cursor, String s, ContentValues contentvalues, String s1)
    {
        contentvalues.put(s1, cursor.getString(cursor.getColumnIndexOrThrow(s)));
    }

    public static void cursorStringToContentValuesIfPresent(Cursor cursor, ContentValues contentvalues, String s)
    {
        int i = cursor.getColumnIndexOrThrow(s);
        if (!cursor.isNull(i))
        {
            contentvalues.put(s, cursor.getString(i));
        }
    }

    public static void cursorStringToInsertHelper(Cursor cursor, String s, InsertHelper inserthelper, int i)
    {
        inserthelper.bind(i, cursor.getString(cursor.getColumnIndexOrThrow(s)));
    }

    public static void dumpCurrentRow(Cursor cursor)
    {
        dumpCurrentRow(cursor, System.out);
    }

    public static void dumpCurrentRow(Cursor cursor, PrintStream printstream)
    {
        String as[] = cursor.getColumnNames();
        printstream.println((new StringBuilder()).append("").append(cursor.getPosition()).append(" {").toString());
        int j = as.length;
        int i = 0;
        while (i < j) 
        {
            String s;
            try
            {
                s = cursor.getString(i);
            }
            catch (SQLiteException sqliteexception)
            {
                sqliteexception = "<unprintable>";
            }
            printstream.println((new StringBuilder()).append("   ").append(as[i]).append('=').append(s).toString());
            i++;
        }
        printstream.println("}");
    }

    public static void dumpCurrentRow(Cursor cursor, StringBuilder stringbuilder)
    {
        String as[] = cursor.getColumnNames();
        stringbuilder.append((new StringBuilder()).append("").append(cursor.getPosition()).append(" {\n").toString());
        int j = as.length;
        int i = 0;
        while (i < j) 
        {
            String s;
            try
            {
                s = cursor.getString(i);
            }
            catch (SQLiteException sqliteexception)
            {
                sqliteexception = "<unprintable>";
            }
            stringbuilder.append((new StringBuilder()).append("   ").append(as[i]).append('=').append(s).append("\n").toString());
            i++;
        }
        stringbuilder.append("}\n");
    }

    public static String dumpCurrentRowToString(Cursor cursor)
    {
        StringBuilder stringbuilder = new StringBuilder();
        dumpCurrentRow(cursor, stringbuilder);
        return stringbuilder.toString();
    }

    public static void dumpCursor(Cursor cursor)
    {
        dumpCursor(cursor, System.out);
    }

    public static void dumpCursor(Cursor cursor, PrintStream printstream)
    {
        printstream.println((new StringBuilder()).append(">>>>> Dumping cursor ").append(cursor).toString());
        if (cursor != null)
        {
            int i = cursor.getPosition();
            cursor.moveToPosition(-1);
            for (; cursor.moveToNext(); dumpCurrentRow(cursor, printstream)) { }
            cursor.moveToPosition(i);
        }
        printstream.println("<<<<<");
    }

    public static void dumpCursor(Cursor cursor, StringBuilder stringbuilder)
    {
        stringbuilder.append((new StringBuilder()).append(">>>>> Dumping cursor ").append(cursor).append("\n").toString());
        if (cursor != null)
        {
            int i = cursor.getPosition();
            cursor.moveToPosition(-1);
            for (; cursor.moveToNext(); dumpCurrentRow(cursor, stringbuilder)) { }
            cursor.moveToPosition(i);
        }
        stringbuilder.append("<<<<<\n");
    }

    public static String dumpCursorToString(Cursor cursor)
    {
        StringBuilder stringbuilder = new StringBuilder();
        dumpCursor(cursor, stringbuilder);
        return stringbuilder.toString();
    }

    public static String getCollationKey(String s)
    {
        s = getCollationKeyInBytes(s);
        try
        {
            s = new String(s, 0, getKeyLen(s), "ISO8859_1");
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return "";
        }
        return s;
    }

    private static byte[] getCollationKeyInBytes(String s)
    {
        if (mColl == null)
        {
            mColl = Collator.getInstance();
            mColl.setStrength(0);
        }
        return mColl.getCollationKey(s).toByteArray();
    }

    public static String getHexCollationKey(String s)
    {
        s = getCollationKeyInBytes(s);
        return new String(Hex.encodeHex(s), 0, getKeyLen(s) * 2);
    }

    private static int getKeyLen(byte abyte0[])
    {
        if (abyte0[abyte0.length - 1] != 0)
        {
            return abyte0.length;
        } else
        {
            return abyte0.length - 1;
        }
    }

    public static int getTypeOfObject(Object obj)
    {
        if (obj == null)
        {
            return 0;
        }
        if (obj instanceof byte[])
        {
            return 4;
        }
        if ((obj instanceof Float) || (obj instanceof Double))
        {
            return 2;
        }
        return !(obj instanceof Long) && !(obj instanceof Integer) ? 3 : 1;
    }

    public static long longForQuery(SQLiteDatabase sqlitedatabase, String s, String as[])
    {
        sqlitedatabase = sqlitedatabase.compileStatement(s);
        long l = longForQuery(((SQLiteStatement) (sqlitedatabase)), as);
        sqlitedatabase.close();
        return l;
        s;
        sqlitedatabase.close();
        throw s;
    }

    public static long longForQuery(SQLiteStatement sqlitestatement, String as[])
    {
        if (as != null)
        {
            int j = as.length;
            for (int i = 0; i < j; i++)
            {
                bindObjectToProgram(sqlitestatement, i + 1, as[i]);
            }

        }
        return sqlitestatement.simpleQueryForLong();
    }

    public static long queryNumEntries(SQLiteDatabase sqlitedatabase, String s)
    {
        sqlitedatabase = sqlitedatabase.query(s, countProjection, null, null, null, null, null);
        long l;
        sqlitedatabase.moveToFirst();
        l = sqlitedatabase.getLong(0);
        sqlitedatabase.close();
        return l;
        s;
        sqlitedatabase.close();
        throw s;
    }

    public static final void readExceptionFromParcel(Parcel parcel)
    {
        int i = parcel.readInt();
        if (i == 0)
        {
            return;
        } else
        {
            readExceptionFromParcel(parcel, parcel.readString(), i);
            return;
        }
    }

    private static final void readExceptionFromParcel(Parcel parcel, String s, int i)
    {
        switch (i)
        {
        default:
            parcel.readException(i, s);
            return;

        case 2: // '\002'
            throw new IllegalArgumentException(s);

        case 3: // '\003'
            throw new UnsupportedOperationException(s);

        case 4: // '\004'
            throw new SQLiteAbortException(s);

        case 5: // '\005'
            throw new SQLiteConstraintException(s);

        case 6: // '\006'
            throw new SQLiteDatabaseCorruptException(s);

        case 7: // '\007'
            throw new SQLiteFullException(s);

        case 8: // '\b'
            throw new SQLiteDiskIOException(s);

        case 9: // '\t'
            throw new SQLiteException(s);
        }
    }

    public static void readExceptionWithFileNotFoundExceptionFromParcel(Parcel parcel)
        throws FileNotFoundException
    {
        int i = parcel.readInt();
        if (i == 0)
        {
            return;
        }
        String s = parcel.readString();
        if (i == 1)
        {
            throw new FileNotFoundException(s);
        } else
        {
            readExceptionFromParcel(parcel, s, i);
            return;
        }
    }

    public static void readExceptionWithOperationApplicationExceptionFromParcel(Parcel parcel)
        throws OperationApplicationException
    {
        int i = parcel.readInt();
        if (i == 0)
        {
            return;
        }
        String s = parcel.readString();
        if (i == 10)
        {
            throw new OperationApplicationException(s);
        } else
        {
            readExceptionFromParcel(parcel, s, i);
            return;
        }
    }

    public static String sqlEscapeString(String s)
    {
        StringBuilder stringbuilder = new StringBuilder();
        appendEscapedSQLString(stringbuilder, s);
        return stringbuilder.toString();
    }

    public static String stringForQuery(SQLiteDatabase sqlitedatabase, String s, String as[])
    {
        sqlitedatabase = sqlitedatabase.compileStatement(s);
        s = stringForQuery(((SQLiteStatement) (sqlitedatabase)), as);
        sqlitedatabase.close();
        return s;
        s;
        sqlitedatabase.close();
        throw s;
    }

    public static String stringForQuery(SQLiteStatement sqlitestatement, String as[])
    {
        if (as != null)
        {
            int j = as.length;
            for (int i = 0; i < j; i++)
            {
                bindObjectToProgram(sqlitestatement, i + 1, as[i]);
            }

        }
        return sqlitestatement.simpleQueryForString();
    }

    public static final void writeExceptionToParcel(Parcel parcel, Exception exception)
    {
        boolean flag = true;
        int i;
        if (exception instanceof FileNotFoundException)
        {
            i = 1;
            flag = false;
        } else
        if (exception instanceof IllegalArgumentException)
        {
            i = 2;
        } else
        if (exception instanceof UnsupportedOperationException)
        {
            i = 3;
        } else
        if (exception instanceof SQLiteAbortException)
        {
            i = 4;
        } else
        if (exception instanceof SQLiteConstraintException)
        {
            i = 5;
        } else
        if (exception instanceof SQLiteDatabaseCorruptException)
        {
            i = 6;
        } else
        if (exception instanceof SQLiteFullException)
        {
            i = 7;
        } else
        if (exception instanceof SQLiteDiskIOException)
        {
            i = 8;
        } else
        if (exception instanceof SQLiteException)
        {
            i = 9;
        } else
        if (exception instanceof OperationApplicationException)
        {
            i = 10;
        } else
        {
            parcel.writeException(exception);
            Log.e("DatabaseUtils", "Writing exception to parcel", exception);
            return;
        }
        parcel.writeInt(i);
        parcel.writeString(exception.getMessage());
        if (flag)
        {
            Log.e("DatabaseUtils", "Writing exception to parcel", exception);
        }
    }

}
