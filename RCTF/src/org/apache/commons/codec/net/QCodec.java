// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

// Referenced classes of package org.apache.commons.codec.net:
//            RFC1522Codec, QuotedPrintableCodec

public class QCodec extends RFC1522Codec
    implements StringEncoder, StringDecoder
{

    private static byte BLANK = 32;
    private static final BitSet PRINTABLE_CHARS;
    private static byte UNDERSCORE = 95;
    private String charset;
    private boolean encodeBlanks;

    public QCodec()
    {
        charset = "UTF-8";
        encodeBlanks = false;
    }

    public QCodec(String s)
    {
        charset = "UTF-8";
        encodeBlanks = false;
        charset = s;
    }

    public Object decode(Object obj)
        throws DecoderException
    {
        if (obj == null)
        {
            return null;
        }
        if (obj instanceof String)
        {
            return decode((String)obj);
        } else
        {
            throw new DecoderException("Objects of type " + obj.getClass().getName() + " cannot be decoded using Q codec");
        }
    }

    public String decode(String s)
        throws DecoderException
    {
        if (s == null)
        {
            return null;
        }
        try
        {
            s = decodeText(s);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            throw new DecoderException(s.getMessage());
        }
        return s;
    }

    protected byte[] doDecoding(byte abyte0[])
        throws DecoderException
    {
label0:
        {
            if (abyte0 == null)
            {
                return null;
            }
            boolean flag1 = false;
            int i = 0;
            byte abyte1[];
label1:
            do
            {
label2:
                {
                    boolean flag = flag1;
                    if (i < abyte0.length)
                    {
                        if (abyte0[i] != UNDERSCORE)
                        {
                            break label2;
                        }
                        flag = true;
                    }
                    if (!flag)
                    {
                        break label0;
                    }
                    abyte1 = new byte[abyte0.length];
                    i = 0;
                    while (i < abyte0.length) 
                    {
                        byte byte0 = abyte0[i];
                        if (byte0 != UNDERSCORE)
                        {
                            abyte1[i] = byte0;
                        } else
                        {
                            abyte1[i] = BLANK;
                        }
                        i++;
                    }
                    break label1;
                }
                i++;
            } while (true);
            return QuotedPrintableCodec.decodeQuotedPrintable(abyte1);
        }
        return QuotedPrintableCodec.decodeQuotedPrintable(abyte0);
    }

    protected byte[] doEncoding(byte abyte0[])
        throws EncoderException
    {
        if (abyte0 != null) goto _L2; else goto _L1
_L1:
        abyte0 = null;
_L4:
        return abyte0;
_L2:
        byte abyte1[] = QuotedPrintableCodec.encodeQuotedPrintable(PRINTABLE_CHARS, abyte0);
        abyte0 = abyte1;
        if (!encodeBlanks)
        {
            continue;
        }
        int i = 0;
        do
        {
            abyte0 = abyte1;
            if (i >= abyte1.length)
            {
                continue;
            }
            if (abyte1[i] == BLANK)
            {
                abyte1[i] = UNDERSCORE;
            }
            i++;
        } while (true);
        if (true) goto _L4; else goto _L3
_L3:
    }

    public Object encode(Object obj)
        throws EncoderException
    {
        if (obj == null)
        {
            return null;
        }
        if (obj instanceof String)
        {
            return encode((String)obj);
        } else
        {
            throw new EncoderException("Objects of type " + obj.getClass().getName() + " cannot be encoded using Q codec");
        }
    }

    public String encode(String s)
        throws EncoderException
    {
        if (s == null)
        {
            return null;
        } else
        {
            return encode(s, getDefaultCharset());
        }
    }

    public String encode(String s, String s1)
        throws EncoderException
    {
        if (s == null)
        {
            return null;
        }
        try
        {
            s = encodeText(s, s1);
        }
        // Misplaced declaration of an exception variable
        catch (String s)
        {
            throw new EncoderException(s.getMessage());
        }
        return s;
    }

    public String getDefaultCharset()
    {
        return charset;
    }

    protected String getEncoding()
    {
        return "Q";
    }

    public boolean isEncodeBlanks()
    {
        return encodeBlanks;
    }

    public void setEncodeBlanks(boolean flag)
    {
        encodeBlanks = flag;
    }

    static 
    {
        PRINTABLE_CHARS = new BitSet(256);
        PRINTABLE_CHARS.set(32);
        PRINTABLE_CHARS.set(33);
        PRINTABLE_CHARS.set(34);
        PRINTABLE_CHARS.set(35);
        PRINTABLE_CHARS.set(36);
        PRINTABLE_CHARS.set(37);
        PRINTABLE_CHARS.set(38);
        PRINTABLE_CHARS.set(39);
        PRINTABLE_CHARS.set(40);
        PRINTABLE_CHARS.set(41);
        PRINTABLE_CHARS.set(42);
        PRINTABLE_CHARS.set(43);
        PRINTABLE_CHARS.set(44);
        PRINTABLE_CHARS.set(45);
        PRINTABLE_CHARS.set(46);
        PRINTABLE_CHARS.set(47);
        for (int i = 48; i <= 57; i++)
        {
            PRINTABLE_CHARS.set(i);
        }

        PRINTABLE_CHARS.set(58);
        PRINTABLE_CHARS.set(59);
        PRINTABLE_CHARS.set(60);
        PRINTABLE_CHARS.set(62);
        PRINTABLE_CHARS.set(64);
        for (int j = 65; j <= 90; j++)
        {
            PRINTABLE_CHARS.set(j);
        }

        PRINTABLE_CHARS.set(91);
        PRINTABLE_CHARS.set(92);
        PRINTABLE_CHARS.set(93);
        PRINTABLE_CHARS.set(94);
        PRINTABLE_CHARS.set(96);
        for (int k = 97; k <= 122; k++)
        {
            PRINTABLE_CHARS.set(k);
        }

        PRINTABLE_CHARS.set(123);
        PRINTABLE_CHARS.set(124);
        PRINTABLE_CHARS.set(125);
        PRINTABLE_CHARS.set(126);
    }
}
