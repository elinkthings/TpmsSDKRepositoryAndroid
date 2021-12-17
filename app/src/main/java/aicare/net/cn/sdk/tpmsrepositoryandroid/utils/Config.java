package aicare.net.cn.sdk.tpmsrepositoryandroid.utils;

/**
 * Created by Suzy on 2016/2/22.
 */
public class Config {

    /**
     * 设备安装位置
     */
    public enum DevicePosition {
        /**
         * 左前
         */
        LEFT_FRONT,
        /**
         * 左后
         */
        LEFT_REAR,
        /**
         * 右前
         */
        RIGHT_FRONT,
        /**
         * 右后
         */
        RIGHT_REAR
    }

    /**
     * 设备状态
     */
    public enum DeviceState {
        /**
         * 正常
         */
        NORMAL,
        /**
         * 漏气
         */
        LEAK,
        /**
         * 充气
         */
        INFLATE,
        /**
         * 启动
         */
        START,
        /**
         * 上电
         */
        POWER_ON,
        /**
         * 唤醒
         */
        WEAK_UP,
        /**
         * 温度异常
         */
        TEMP_ERROR,
        /**
         * 电量异常
         */
        BATTERY_ERROR,
        /**
         * 未知
         */
        UNKNOWN
    }
}