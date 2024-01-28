package com.ji.jichat.common.util;

/**
 * @author jisl on 2024/1/28 15:09
 */
public class MessageIdUtil {

    public static String getChannelKey(long userId, long userId2) {
        if (userId > userId2) {
//            保证较小的id排前面
            long temp = userId;
            userId = userId2;
            userId2 = temp;
        }
        return Base62Util.encodeBase62(userId) + "_" + Base62Util.encodeBase62(userId2);
    }

    public static String getChannelKey(long groupId) {
        return Base62Util.encodeBase62(groupId);
    }
}
