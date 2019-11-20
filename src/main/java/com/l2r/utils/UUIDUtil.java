package com.l2r.utils;

import java.util.UUID;

/**
 * @author sealyu on 4/9/16.
 */
public final class UUIDUtil
{
    private static String[] getUUID(Integer length, int bits)
    {
        String[] uuids = new String[length.intValue()];
        for (int i = 0; i < length.intValue(); i++) {
            if (bits == 32)
                uuids[i] = getUUID32Bits();
            else if (bits == 36) {
                uuids[i] = getUUID36Bits();
            }
        }
        return uuids;
    }

    public static String getUUID32Bits()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String[] getUUID32Bits(Integer count)
    {
        return getUUID(count, 32);
    }

    public static String getUUID36Bits()
    {
        return UUID.randomUUID().toString();
    }

    public static String[] getUUID36Bits(Integer count)
    {
        return getUUID(count, 36);
    }
}
