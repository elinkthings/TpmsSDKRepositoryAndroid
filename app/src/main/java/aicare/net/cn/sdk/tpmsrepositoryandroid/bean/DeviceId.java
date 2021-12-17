package aicare.net.cn.sdk.tpmsrepositoryandroid.bean;

/**
 * Created by Suzy on 2016/8/30.
 */
public class DeviceId {
    private String deviceId;
    private int count;

    public DeviceId(String deviceId, int count) {
        this.deviceId = deviceId;
        this.count = count;
    }

    public DeviceId() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DeviceId{" +
                "deviceId='" + deviceId + '\'' +
                ", count=" + count +
                '}';
    }
}
