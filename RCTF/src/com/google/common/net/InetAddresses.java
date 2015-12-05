// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.google.common.net;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Ints;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class InetAddresses
{
    public static final class TeredoInfo
    {

        private final Inet4Address client;
        private final int flags;
        private final int port;
        private final Inet4Address server;

        public Inet4Address getClient()
        {
            return client;
        }

        public int getFlags()
        {
            return flags;
        }

        public int getPort()
        {
            return port;
        }

        public Inet4Address getServer()
        {
            return server;
        }

        public TeredoInfo(Inet4Address inet4address, Inet4Address inet4address1, int i, int j)
        {
            boolean flag;
            if (i >= 0 && i <= 65535)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            Preconditions.checkArgument(flag, "port '%d' is out of range (0 <= port <= 0xffff)", new Object[] {
                Integer.valueOf(i)
            });
            if (j >= 0 && j <= 65535)
            {
                flag = true;
            } else
            {
                flag = false;
            }
            Preconditions.checkArgument(flag, "flags '%d' is out of range (0 <= flags <= 0xffff)", new Object[] {
                Integer.valueOf(j)
            });
            if (inet4address != null)
            {
                server = inet4address;
            } else
            {
                server = InetAddresses.ANY4;
            }
            if (inet4address1 != null)
            {
                client = inet4address1;
            } else
            {
                client = InetAddresses.ANY4;
            }
            port = i;
            flags = j;
        }
    }


    private static final Inet4Address ANY4 = (Inet4Address)forString("0.0.0.0");
    private static final int IPV4_PART_COUNT = 4;
    private static final int IPV6_PART_COUNT = 8;
    private static final Inet4Address LOOPBACK4 = (Inet4Address)forString("127.0.0.1");

    private InetAddresses()
    {
    }

    public static int coerceToInteger(InetAddress inetaddress)
    {
        return ByteStreams.newDataInput(getCoercedIPv4Address(inetaddress).getAddress()).readInt();
    }

    private static String convertDottedQuadToHex(String s)
    {
        int i = s.lastIndexOf(':');
        String s1 = s.substring(0, i + 1);
        byte abyte0[] = textToNumericFormatV4(s.substring(i + 1));
        if (abyte0 == null)
        {
            return null;
        } else
        {
            s = Integer.toHexString((abyte0[0] & 0xff) << 8 | abyte0[1] & 0xff);
            String s2 = Integer.toHexString((abyte0[2] & 0xff) << 8 | abyte0[3] & 0xff);
            return (new StringBuilder()).append(s1).append(s).append(":").append(s2).toString();
        }
    }

    private static byte[] copyOfRange(byte abyte0[], int i, int j)
    {
        Preconditions.checkNotNull(abyte0);
        int k = Math.min(j, abyte0.length);
        byte abyte1[] = new byte[j - i];
        System.arraycopy(abyte0, i, abyte1, 0, k - i);
        return abyte1;
    }

    public static InetAddress forString(String s)
    {
        byte abyte1[] = textToNumericFormatV4(s);
        byte abyte0[] = abyte1;
        if (abyte1 == null)
        {
            abyte0 = textToNumericFormatV6(s);
        }
        if (abyte0 == null)
        {
            throw new IllegalArgumentException(String.format("'%s' is not an IP string literal.", new Object[] {
                s
            }));
        }
        InetAddress inetaddress;
        try
        {
            inetaddress = InetAddress.getByAddress(abyte0);
        }
        catch (UnknownHostException unknownhostexception)
        {
            throw new IllegalArgumentException(String.format("'%s' is extremely broken.", new Object[] {
                s
            }), unknownhostexception);
        }
        return inetaddress;
    }

    public static InetAddress forUriString(String s)
    {
        Preconditions.checkNotNull(s);
        InetAddress inetaddress;
        boolean flag;
        if (s.length() > 0)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag, "host string is empty");
        inetaddress = forString(s);
        flag = inetaddress instanceof Inet4Address;
        if (flag)
        {
            return inetaddress;
        }
        break MISSING_BLOCK_LABEL_42;
        IllegalArgumentException illegalargumentexception;
        illegalargumentexception;
        if (!s.startsWith("[") || !s.endsWith("]"))
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Not a valid address: \"").append(s).append('"').toString());
        }
        InetAddress inetaddress1 = forString(s.substring(1, s.length() - 1));
        if (inetaddress1 instanceof Inet6Address)
        {
            return inetaddress1;
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("Not a valid address: \"").append(s).append('"').toString());
        }
    }

    public static Inet4Address fromInteger(int i)
    {
        return getInet4Address(Ints.toByteArray(i));
    }

    public static InetAddress fromLittleEndianByteArray(byte abyte0[])
        throws UnknownHostException
    {
        byte abyte1[] = new byte[abyte0.length];
        for (int i = 0; i < abyte0.length; i++)
        {
            abyte1[i] = abyte0[abyte0.length - i - 1];
        }

        return InetAddress.getByAddress(abyte1);
    }

    public static Inet4Address get6to4IPv4Address(Inet6Address inet6address)
    {
        Preconditions.checkArgument(is6to4Address(inet6address), "Address '%s' is not a 6to4 address.", new Object[] {
            inet6address.getHostAddress()
        });
        return getInet4Address(copyOfRange(inet6address.getAddress(), 2, 6));
    }

    public static Inet4Address getCoercedIPv4Address(InetAddress inetaddress)
    {
        if (inetaddress instanceof Inet4Address)
        {
            return (Inet4Address)inetaddress;
        }
        byte abyte0[] = inetaddress.getAddress();
        boolean flag = true;
        int i = 0;
        int j;
label0:
        do
        {
label1:
            {
                j = ((flag) ? 1 : 0);
                if (i < 15)
                {
                    if (abyte0[i] == 0)
                    {
                        break label1;
                    }
                    j = 0;
                }
                if (j && abyte0[15] == 1)
                {
                    return LOOPBACK4;
                }
                break label0;
            }
            i++;
        } while (true);
        if (j && abyte0[15] == 0)
        {
            return ANY4;
        }
        inetaddress = (Inet6Address)inetaddress;
        long l;
        if (hasEmbeddedIPv4ClientAddress(inetaddress))
        {
            l = getEmbeddedIPv4ClientAddress(inetaddress).hashCode();
        } else
        {
            l = ByteBuffer.wrap(inetaddress.getAddress(), 0, 8).getLong();
        }
        j = hash64To32(l) | 0xe0000000;
        i = j;
        if (j == -1)
        {
            i = -2;
        }
        return getInet4Address(Ints.toByteArray(i));
    }

    public static Inet4Address getCompatIPv4Address(Inet6Address inet6address)
    {
        Preconditions.checkArgument(isCompatIPv4Address(inet6address), "Address '%s' is not IPv4-compatible.", new Object[] {
            inet6address.getHostAddress()
        });
        return getInet4Address(copyOfRange(inet6address.getAddress(), 12, 16));
    }

    public static Inet4Address getEmbeddedIPv4ClientAddress(Inet6Address inet6address)
    {
        if (isCompatIPv4Address(inet6address))
        {
            return getCompatIPv4Address(inet6address);
        }
        if (is6to4Address(inet6address))
        {
            return get6to4IPv4Address(inet6address);
        }
        if (isTeredoAddress(inet6address))
        {
            return getTeredoInfo(inet6address).getClient();
        } else
        {
            throw new IllegalArgumentException(String.format("'%s' has no embedded IPv4 address.", new Object[] {
                inet6address.getHostAddress()
            }));
        }
    }

    private static Inet4Address getInet4Address(byte abyte0[])
    {
        Object obj;
        boolean flag;
        if (abyte0.length == 4)
        {
            flag = true;
        } else
        {
            flag = false;
        }
        Preconditions.checkArgument(flag, "Byte array has invalid length for an IPv4 address: %s != 4.", new Object[] {
            Integer.valueOf(abyte0.length)
        });
        try
        {
            obj = InetAddress.getByAddress(abyte0);
            if (!(obj instanceof Inet4Address))
            {
                throw new UnknownHostException(String.format("'%s' is not an IPv4 address.", new Object[] {
                    ((InetAddress) (obj)).getHostAddress()
                }));
            }
        }
        // Misplaced declaration of an exception variable
        catch (Object obj)
        {
            throw new IllegalArgumentException(String.format("Host address '%s' is not a valid IPv4 address.", new Object[] {
                Arrays.toString(abyte0)
            }), ((Throwable) (obj)));
        }
        obj = (Inet4Address)obj;
        return ((Inet4Address) (obj));
    }

    public static Inet4Address getIsatapIPv4Address(Inet6Address inet6address)
    {
        Preconditions.checkArgument(isIsatapAddress(inet6address), "Address '%s' is not an ISATAP address.", new Object[] {
            inet6address.getHostAddress()
        });
        return getInet4Address(copyOfRange(inet6address.getAddress(), 12, 16));
    }

    public static TeredoInfo getTeredoInfo(Inet6Address inet6address)
    {
        Preconditions.checkArgument(isTeredoAddress(inet6address), "Address '%s' is not a Teredo address.", new Object[] {
            inet6address.getHostAddress()
        });
        byte abyte0[] = inet6address.getAddress();
        inet6address = getInet4Address(copyOfRange(abyte0, 4, 8));
        short word0 = ByteStreams.newDataInput(abyte0, 8).readShort();
        short word1 = ByteStreams.newDataInput(abyte0, 10).readShort();
        abyte0 = copyOfRange(abyte0, 12, 16);
        for (int i = 0; i < abyte0.length; i++)
        {
            abyte0[i] = (byte)(~abyte0[i]);
        }

        return new TeredoInfo(inet6address, getInet4Address(abyte0), ~word1 & 0xffff, word0 & 0xffff);
    }

    public static boolean hasEmbeddedIPv4ClientAddress(Inet6Address inet6address)
    {
        return isCompatIPv4Address(inet6address) || is6to4Address(inet6address) || isTeredoAddress(inet6address);
    }

    static int hash64To32(long l)
    {
        l = (-1L ^ l) + (l << 18);
        l = (l ^ l >>> 31) * 21L;
        l ^= l >>> 11;
        l += l << 6;
        return (int)(l ^ l >>> 22);
    }

    public static boolean is6to4Address(Inet6Address inet6address)
    {
        inet6address = inet6address.getAddress();
        return inet6address[0] == 32 && inet6address[1] == 2;
    }

    public static boolean isCompatIPv4Address(Inet6Address inet6address)
    {
        if (inet6address.isIPv4CompatibleAddress())
        {
            if ((inet6address = inet6address.getAddress())[12] != 0 || inet6address[13] != 0 || inet6address[14] != 0 || inet6address[15] != 0 && inet6address[15] != 1)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isInetAddress(String s)
    {
        try
        {
            forString(s);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return false;
        }
        return true;
    }

    public static boolean isIsatapAddress(Inet6Address inet6address)
    {
        if (!isTeredoAddress(inet6address))
        {
            if (((inet6address = inet6address.getAddress())[8] | 3) == 3 && inet6address[9] == 0 && inet6address[10] == 94 && inet6address[11] == -2)
            {
                return true;
            }
        }
        return false;
    }

    public static boolean isTeredoAddress(Inet6Address inet6address)
    {
        inet6address = inet6address.getAddress();
        return inet6address[0] == 32 && inet6address[1] == 1 && inet6address[2] == 0 && inet6address[3] == 0;
    }

    public static boolean isUriInetAddress(String s)
    {
        try
        {
            forUriString(s);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return false;
        }
        return true;
    }

    private static byte[] textToNumericFormatV4(String s)
    {
        if (!s.contains(":")) goto _L2; else goto _L1
_L1:
        s = null;
_L4:
        return s;
_L2:
        byte abyte0[];
        String as[];
        int i;
        as = s.split("\\.");
        if (as.length != 4)
        {
            return null;
        }
        abyte0 = new byte[4];
        i = 0;
_L5:
        s = abyte0;
        if (i >= abyte0.length) goto _L4; else goto _L3
_L3:
        int j = Integer.parseInt(as[i]);
        if (j < 0 || j > 255)
        {
            break MISSING_BLOCK_LABEL_107;
        }
        if (as[i].startsWith("0") && as[i].length() != 1)
        {
            return null;
        }
        abyte0[i] = (byte)j;
        i++;
          goto _L5
        s;
        return null;
        return null;
    }

    private static byte[] textToNumericFormatV6(String s)
    {
        String as[];
        int k;
        if (!s.contains(":"))
        {
            return null;
        }
        if (s.contains(":::"))
        {
            return null;
        }
        String s1 = s;
        if (s.contains("."))
        {
            s = convertDottedQuadToHex(s);
            s1 = s;
            if (s == null)
            {
                return null;
            }
        }
        s = ByteBuffer.allocate(16);
        k = 0;
        as = s1.split("::", 2);
        if (as[0].equals("")) goto _L2; else goto _L1
_L1:
        String as1[];
        int i;
        as1 = as[0].split(":", 8);
        i = 0;
        int j;
        while (i < as1.length) 
        {
            if (as1[i].equals(""))
            {
                return null;
            }
            try
            {
                s.putShort(i * 2, (short)Integer.parseInt(as1[i], 16));
            }
            // Misplaced declaration of an exception variable
            catch (String s)
            {
                return null;
            }
            i++;
        }
        j = as1.length;
_L10:
        i = k;
        if (as.length <= 1) goto _L4; else goto _L3
_L3:
        if (as[1].equals("")) goto _L6; else goto _L5
_L5:
        as1 = as[1].split(":", 8);
        i = 0;
_L7:
        if (i >= as1.length)
        {
            break MISSING_BLOCK_LABEL_232;
        }
        k = as1.length - i - 1;
        if (as1[k].equals(""))
        {
            return null;
        }
        try
        {
            s.putShort((8 - i - 1) * 2, (short)Integer.parseInt(as1[k], 16));
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            return null;
        }
        i++;
          goto _L7
        i = as1.length;
_L4:
        i = j + i;
        if (i > 8)
        {
            return null;
        }
        break; /* Loop/switch isn't completed */
_L6:
        i = 1;
        if (true) goto _L4; else goto _L8
_L8:
        if (as.length == 1 && i != 8)
        {
            return null;
        } else
        {
            return s.array();
        }
_L2:
        j = 1;
        if (true) goto _L10; else goto _L9
_L9:
    }

    public static String toUriString(InetAddress inetaddress)
    {
        if (inetaddress instanceof Inet6Address)
        {
            return (new StringBuilder()).append("[").append(inetaddress.getHostAddress()).append("]").toString();
        } else
        {
            return inetaddress.getHostAddress();
        }
    }


}
