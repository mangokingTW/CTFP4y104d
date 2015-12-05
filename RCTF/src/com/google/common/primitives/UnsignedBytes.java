// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.primitives;

import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Comparator;
import sun.misc.Unsafe;

public final class UnsignedBytes
{
    static class LexicographicalComparatorHolder
    {

        static final Comparator BEST_COMPARATOR = getBestComparator();
        static final String UNSAFE_COMPARATOR_NAME = (new StringBuilder()).append(com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder.getName()).append("$UnsafeComparator").toString();

        static Comparator getBestComparator()
        {
            Comparator comparator;
            try
            {
                comparator = (Comparator)Class.forName(UNSAFE_COMPARATOR_NAME).getEnumConstants()[0];
            }
            catch (Throwable throwable)
            {
                return UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
            return comparator;
        }


        LexicographicalComparatorHolder()
        {
        }
    }

    static final class LexicographicalComparatorHolder.PureJavaComparator extends Enum
        implements Comparator
    {

        private static final LexicographicalComparatorHolder.PureJavaComparator $VALUES[];
        public static final LexicographicalComparatorHolder.PureJavaComparator INSTANCE;

        public static LexicographicalComparatorHolder.PureJavaComparator valueOf(String s)
        {
            return (LexicographicalComparatorHolder.PureJavaComparator)Enum.valueOf(com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$PureJavaComparator, s);
        }

        public static LexicographicalComparatorHolder.PureJavaComparator[] values()
        {
            return (LexicographicalComparatorHolder.PureJavaComparator[])$VALUES.clone();
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((byte[])obj, (byte[])obj1);
        }

        public int compare(byte abyte0[], byte abyte1[])
        {
            int j = Math.min(abyte0.length, abyte1.length);
            for (int i = 0; i < j; i++)
            {
                int k = UnsignedBytes.compare(abyte0[i], abyte1[i]);
                if (k != 0)
                {
                    return k;
                }
            }

            return abyte0.length - abyte1.length;
        }

        static 
        {
            INSTANCE = new LexicographicalComparatorHolder.PureJavaComparator("INSTANCE", 0);
            $VALUES = (new LexicographicalComparatorHolder.PureJavaComparator[] {
                INSTANCE
            });
        }

        private LexicographicalComparatorHolder.PureJavaComparator(String s, int i)
        {
            super(s, i);
        }
    }

    static final class LexicographicalComparatorHolder.UnsafeComparator extends Enum
        implements Comparator
    {

        private static final LexicographicalComparatorHolder.UnsafeComparator $VALUES[];
        static final int BYTE_ARRAY_BASE_OFFSET;
        public static final LexicographicalComparatorHolder.UnsafeComparator INSTANCE;
        static final boolean littleEndian;
        static final Unsafe theUnsafe;

        static boolean lessThanUnsigned(long l, long l1)
        {
            return l - 0x8000000000000000L < 0x8000000000000000L + l1;
        }

        public static LexicographicalComparatorHolder.UnsafeComparator valueOf(String s)
        {
            return (LexicographicalComparatorHolder.UnsafeComparator)Enum.valueOf(com/google/common/primitives/UnsignedBytes$LexicographicalComparatorHolder$UnsafeComparator, s);
        }

        public static LexicographicalComparatorHolder.UnsafeComparator[] values()
        {
            return (LexicographicalComparatorHolder.UnsafeComparator[])$VALUES.clone();
        }

        public volatile int compare(Object obj, Object obj1)
        {
            return compare((byte[])obj, (byte[])obj1);
        }

        public int compare(byte abyte0[], byte abyte1[])
        {
            int k = Math.min(abyte0.length, abyte1.length);
            int l = k / 8;
            for (int i = 0; i < l * 8; i += 8)
            {
                long l1 = theUnsafe.getLong(abyte0, (long)BYTE_ARRAY_BASE_OFFSET + (long)i);
                long l2 = theUnsafe.getLong(abyte1, (long)BYTE_ARRAY_BASE_OFFSET + (long)i);
                long l3 = l1 ^ l2;
                if (l3 != 0L)
                {
                    if (!littleEndian)
                    {
                        return !lessThanUnsigned(l1, l2) ? 1 : -1;
                    }
                    k = 0;
                    l = (int)l3;
                    i = l;
                    if (l == 0)
                    {
                        i = (int)(l3 >>> 32);
                        k = 32;
                    }
                    l = i << 16;
                    if (l == 0)
                    {
                        k += 16;
                    } else
                    {
                        i = l;
                    }
                    l = k;
                    if (i << 8 == 0)
                    {
                        l = k + 8;
                    }
                    return (int)((l1 >>> l & 255L) - (l2 >>> l & 255L));
                }
            }

            for (int j = l * 8; j < k; j++)
            {
                int i1 = UnsignedBytes.compare(abyte0[j], abyte1[j]);
                if (i1 != 0)
                {
                    return i1;
                }
            }

            return abyte0.length - abyte1.length;
        }

        static 
        {
            INSTANCE = new LexicographicalComparatorHolder.UnsafeComparator("INSTANCE", 0);
            $VALUES = (new LexicographicalComparatorHolder.UnsafeComparator[] {
                INSTANCE
            });
            littleEndian = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
            theUnsafe = (Unsafe)AccessController.doPrivileged(new PrivilegedAction() {

                public Object run()
                {
                    Object obj;
                    try
                    {
                        obj = sun/misc/Unsafe.getDeclaredField("theUnsafe");
                        ((Field) (obj)).setAccessible(true);
                        obj = ((Field) (obj)).get(null);
                    }
                    catch (NoSuchFieldException nosuchfieldexception)
                    {
                        throw new Error();
                    }
                    catch (IllegalAccessException illegalaccessexception)
                    {
                        throw new Error();
                    }
                    return obj;
                }

            });
            BYTE_ARRAY_BASE_OFFSET = theUnsafe.arrayBaseOffset([B);
            if (theUnsafe.arrayIndexScale([B) != 1)
            {
                throw new AssertionError();
            }
        }

        private LexicographicalComparatorHolder.UnsafeComparator(String s, int i)
        {
            super(s, i);
        }
    }


    private UnsignedBytes()
    {
    }

    public static byte checkedCast(long l)
    {
        boolean flag;
        if (l >> 8 == 0L)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag, "out of range: %s", new Object[] {
            Long.valueOf(l)
        });
        return (byte)(int)l;
    }

    public static int compare(byte byte0, byte byte1)
    {
        return toInt(byte0) - toInt(byte1);
    }

    public static transient String join(String s, byte abyte0[])
    {
        Preconditions.checkNotNull(s);
        if (abyte0.length == 0)
        {
            return "";
        }
        StringBuilder stringbuilder = new StringBuilder(abyte0.length * 5);
        stringbuilder.append(toInt(abyte0[0]));
        for (int i = 1; i < abyte0.length; i++)
        {
            stringbuilder.append(s).append(toInt(abyte0[i]));
        }

        return stringbuilder.toString();
    }

    public static Comparator lexicographicalComparator()
    {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }

    static Comparator lexicographicalComparatorJavaImpl()
    {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }

    public static transient byte max(byte abyte0[])
    {
        int j;
        boolean flag;
        if (abyte0.length > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag);
        j = toInt(abyte0[0]);
        for (int i = 1; i < abyte0.length;)
        {
            int l = toInt(abyte0[i]);
            int k = j;
            if (l > j)
            {
                k = l;
            }
            i++;
            j = k;
        }

        return (byte)j;
    }

    public static transient byte min(byte abyte0[])
    {
        int j;
        boolean flag;
        if (abyte0.length > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag);
        j = toInt(abyte0[0]);
        for (int i = 1; i < abyte0.length;)
        {
            int l = toInt(abyte0[i]);
            int k = j;
            if (l < j)
            {
                k = l;
            }
            i++;
            j = k;
        }

        return (byte)j;
    }

    public static byte saturatedCast(long l)
    {
        if (l > 255L)
        {
            return -1;
        }
        if (l < 0L)
        {
            return 0;
        } else
        {
            return (byte)(int)l;
        }
    }

    public static int toInt(byte byte0)
    {
        return byte0 & 0xff;
    }
}
