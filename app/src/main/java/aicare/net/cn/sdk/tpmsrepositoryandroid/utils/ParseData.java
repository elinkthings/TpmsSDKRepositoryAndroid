package aicare.net.cn.sdk.tpmsrepositoryandroid.utils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseData {

    private final static String TAG = ParseData.class.getSimpleName();

    /**
     * byte转16进制
     *
     * @param b
     * @return
     */
    public static String binaryToHex(byte b) {
        String str = byteToBit(b);
        String hexStr = Integer.toHexString(Integer.parseInt(str, 2));
        return hexStr.toUpperCase();
    }

    /**
     * Byte转Bit
     */
    public static String byteToBit(byte b) {
        return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }

    /**
     * byte转十进制
     *
     * @param b
     * @return
     */
    public static int binaryToDecimal(byte b) {
        String str = byteToBit(b);
        return Integer.parseInt(str, 2);
    }

    /**
     * 字符串长度等于1的话，补0
     *
     * @param str
     * @return
     */
    public static String addZero(String str) {
        StringBuffer sBuffer = new StringBuffer();
        if (str.length() == 1) {
            sBuffer.append("0");
            sBuffer.append(str);
            return sBuffer.toString();
        } else {
            return str;
        }
    }

    /**
     * Bit转Byte
     */
    public static byte bitToByte(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {//4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }

    // 获取系统时间
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }

    /**
     * 解析数据
     *
     * @param b1
     * @param b2
     * @return
     */
    public static int getData(byte b1, byte b2) {
        int data = ((b1 & 0xFF)) + (b2 & 0xFF);
        return data;
    }

    /**
     * 保留小数点后count位
     * @param d
     * @param count
     * @return
     */
    public static double keepDecimal(double d, int count) {
        BigDecimal decimal = new BigDecimal(d);
        return decimal.setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断是不是正确的设备ID
     * @param str
     * @return
     */
    public static boolean isDeviceId(String str) {
        String upperCaseStr = str.toUpperCase();
        switch (upperCaseStr.length()) {
            case 5:
                if (is0or1(upperCaseStr.substring(0, 1))) {
                    if (isHex(upperCaseStr.substring(1, upperCaseStr.length()))) {
                        return true;
                    }
                }
                break;
            case 6:
                if (isHex(upperCaseStr)) {
                    return true;
                }
        }
        return false;
    }

    /**
     * 验证是否为16进制数
     *
     * @param hex
     * @return
     */
    private static boolean isHex(String hex) {
        String str = "^[A-Fa-f0-9]+$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(hex);
        return m.matches();
    }

    /**
     * 验证是否是0或1
     *
     * @param numStr
     * @return
     */
    private static boolean is0or1(String numStr) {
        String str = "^[0-1]*$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(numStr);
        return m.matches();
    }

    /**
     * byte数组转string
     *
     * @param b
     * @return
     */
    public static String arr2Str(byte[] b) {
        if (b == null) {
            return null;
        }
        if (b.length == 0) {
            return "[]";
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        for (int i = 0; i < b.length; i++) {
            buffer.append(binaryToHex(b[i]));
            if (i != b.length - 1) {
                buffer.append(", ");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
