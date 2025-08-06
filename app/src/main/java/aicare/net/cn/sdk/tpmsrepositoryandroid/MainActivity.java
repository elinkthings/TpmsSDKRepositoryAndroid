package aicare.net.cn.sdk.tpmsrepositoryandroid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pingwang.tpmslibrary.TpmsScan;
import com.pingwang.tpmslibrary.TpmsScanListener;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;

import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.Config;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.Configs;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.L;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.ParseData;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.SPUtils;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.T;
import aicare.net.cn.sdk.tpmsrepositoryandroid.views.SetIdDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TpmsScanListener, OnIdSetListener {

    private Button btn_get_info, btn_clear_log, btn_stop_scan;
    private TextView tv_left_front, tv_right_front, tv_left_rear, tv_right_rear;
    private ListView lv_show_log;
    private LinearLayout ll_set_id;
    private String leftFront, rightFront, leftRear, rightRear;

    private List<String> infoList;
    private ArrayAdapter listAdapter;
    private TpmsScan mTpmsScan;
    private EnumMap<Config.DevicePosition, String> deviceIdMap;
    private String[] mDeviceIdS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        L.isDebug = true;
        initData();
        initViews();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTpmsScan != null) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                showBLEDialog();
            }
        }
    }


    private void initData() {
        infoList = new ArrayList<>();
        deviceIdMap = new EnumMap<>(Config.DevicePosition.class);
        setDeviceIdMap();

        mTpmsScan = new TpmsScan(MainActivity.this);
    }

    private void setDeviceIdMap() {
        leftFront = (String) SPUtils.get(this, Configs.LEFT_FRONT, "");
        leftRear = (String) SPUtils.get(this, Configs.LEFT_REAR, "");
        rightFront = (String) SPUtils.get(this, Configs.RIGHT_FRONT, "");
        rightRear = (String) SPUtils.get(this, Configs.RIGHT_REAR, "");
        deviceIdMap.put(Config.DevicePosition.LEFT_FRONT, leftFront);
        deviceIdMap.put(Config.DevicePosition.LEFT_REAR, leftRear);
        deviceIdMap.put(Config.DevicePosition.RIGHT_FRONT, rightFront);
        deviceIdMap.put(Config.DevicePosition.RIGHT_REAR, rightRear);
    }

    private void initViews() {
        btn_stop_scan = (Button) findViewById(R.id.btn_stop_scan);
        btn_get_info = (Button) findViewById(R.id.btn_get_info);
        btn_clear_log = (Button) findViewById(R.id.btn_clear_log);

        tv_left_front = (TextView) findViewById(R.id.tv_left_front);
        tv_right_front = (TextView) findViewById(R.id.tv_right_front);
        tv_left_rear = (TextView) findViewById(R.id.tv_left_rear);
        tv_right_rear = (TextView) findViewById(R.id.tv_right_rear);
        setTyreId();

        ll_set_id = (LinearLayout) findViewById(R.id.ll_set_id);

        lv_show_log = (ListView) findViewById(R.id.lv_show_log);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, infoList);
        lv_show_log.setAdapter(listAdapter);
    }

    private void setTyreId() {
        if (TextUtils.isEmpty(leftFront)) {
            tv_left_front.setText(R.string.please_set_id);
        } else {
            tv_left_front.setText(String.format(getResources().getString(R.string.left_front_id), leftFront));
        }
        if (TextUtils.isEmpty(rightFront)) {
            tv_right_front.setText(R.string.please_set_id);
        } else {
            tv_right_front.setText(String.format(getResources().getString(R.string.right_front_id), rightFront));
        }
        if (TextUtils.isEmpty(leftRear)) {
            tv_left_rear.setText(R.string.please_set_id);
        } else {
            tv_left_rear.setText(String.format(getResources().getString(R.string.left_rear_id), leftRear));
        }
        if (TextUtils.isEmpty(rightRear)) {
            tv_right_rear.setText(R.string.please_set_id);
        } else {
            tv_right_rear.setText(String.format(getResources().getString(R.string.right_rear_id), rightRear));
        }
    }

    private void initEvents() {
        btn_stop_scan.setOnClickListener(this);
        btn_get_info.setOnClickListener(this);
        btn_clear_log.setOnClickListener(this);
        ll_set_id.setOnClickListener(this);
    }


    private static final int REQUEST_ENABLE_BT = 2;

    @SuppressLint("MissingPermission")
    private void showBLEDialog() {
        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTpmsScan != null) {
            mTpmsScan.stopScan();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stop_scan:
                if (mTpmsScan != null) {
                    mTpmsScan.stopScan();
                }
                break;
            case R.id.btn_get_info:
                if (deviceIdMap.isEmpty() || noId()) {
                    T.showLong(this, R.string.please_set_id);
                } else {
                    if (mTpmsScan != null && deviceIdMap != null) {
                        mDeviceIdS = null;
                        if (getUuidStrings()) {
                            mTpmsScan.startScan(mDeviceIdS);
                        }
                    }
                }
                break;

            case R.id.btn_clear_log:
                infoList.clear();
                listAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_set_id:
                new SetIdDialog(this, deviceIdMap, this).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onGetData(byte[] bytes,String mac, String deviceId, int rssi, float pressure, int pressureUnit, float battery, int temp,int tempUnit, int status, float mcuVersion, int year, int month, int day,
                          float bleVersion,String deviceName) {
        if (deviceIdMap != null) {
            for (Config.DevicePosition devicePosition : deviceIdMap.keySet()) {
                if (TextUtils.equals(deviceId, deviceIdMap.get(devicePosition))) {
                    Config.DeviceState deviceState = Config.DeviceState.NORMAL;
                    switch (status) {
                        case 1:
                            deviceState = Config.DeviceState.LEAK;
                            break;
                        case 2:
                            deviceState = Config.DeviceState.INFLATE;
                            break;
                        case 3:
                            deviceState = Config.DeviceState.START;
                            break;
                        case 4:
                            deviceState = Config.DeviceState.POWER_ON;
                            break;
                        case 5:
                            deviceState = Config.DeviceState.WEAK_UP;
                            break;
                        default:
                            break;
                    }

                    StringBuffer buffer = new StringBuffer();
                    buffer.append("设备位置：");
                    buffer.append(getPosition(devicePosition));
                    buffer.append(", 设备id：");
                    buffer.append(deviceId);
                    buffer.append(", 电压：");
                    buffer.append(battery);
                    buffer.append(", 气压：");
                    buffer.append(pressure);
                    buffer.append(", 温度：");
                    buffer.append(temp);
                    buffer.append(", 状态：");
                    buffer.append(getState(deviceState));
                    buffer.append(", 时间：");
                    buffer.append(ParseData.getTime());
                    sendMsg(buffer.toString());
                    return;
                }
            }
        }
    }

    public void onStopScan() {
        sendMsg("停止扫描");
    }

    public void onBluetoothStateOn() {
        T.showLong(this, "蓝牙已开启！");
    }

    public void onBluetoothStateOff() {
        T.showLong(this, "蓝牙已关闭！");
    }

    public void onBluetoothTurningOn() {
        T.showLong(this, "蓝牙开启中！");
    }

    public void onBluetoothTurningOff() {
        T.showLong(this, "蓝牙关闭中！");
    }

    private String getPosition(Config.DevicePosition devicePosition) {
        switch (devicePosition) {
            case LEFT_FRONT:
                return "左前";
            case RIGHT_FRONT:
                return "右前";
            case LEFT_REAR:
                return "左后";
            case RIGHT_REAR:
                return "右后";
            default:
                return "";
        }
    }


    private String getState(Config.DeviceState deviceState) {
        switch (deviceState) {
            case NORMAL:
                return "正常";
            case LEAK:
                return "漏气";
            case INFLATE:
                return "充气";
            case START:
                return "启动";
            case POWER_ON:
                return "上电";
            case WEAK_UP:
                return "唤醒";
            case TEMP_ERROR:
                return "温度异常";
            case BATTERY_ERROR:
                return "电量异常";
            case UNKNOWN:
                return "未知";
            default:
                return "";
        }
    }

    @Override
    public void onIdSet() {
        setDeviceIdMap();
        if (noId()) {
            if (mTpmsScan != null) {
                mTpmsScan.stopScan();
            }
        }
        setTyreId();
    }

    @Override
    public void cancel() {
        if (mTpmsScan != null) {
            mTpmsScan.stopScan();
        }
    }

    /**
     * 没有设置ID重新扫描
     */
    private boolean noId() {
        if (deviceIdMap != null && !deviceIdMap.isEmpty()) {
            if (TextUtils.isEmpty(deviceIdMap.get(Config.DevicePosition.LEFT_FRONT)) && TextUtils.isEmpty(deviceIdMap.get(Config.DevicePosition.LEFT_REAR)) && TextUtils
                    .isEmpty(deviceIdMap.get(Config.DevicePosition.RIGHT_FRONT)) && TextUtils.isEmpty(deviceIdMap.get(Config.DevicePosition.RIGHT_REAR))) {//当所有ID都为空时，停止扫描
                return true;
            }
        }
        return false;
    }

    private void showInfo(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(infoList.size() + 1);
        stringBuilder.append(".\n");
        stringBuilder.append(str);
        infoList.add(stringBuilder.toString());
        listAdapter.notifyDataSetChanged();
        lv_show_log.setSelection(infoList.size() - 1);
    }

    private Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            String result = msg.getData().getString(RESULT);
            if (!TextUtils.isEmpty(result)) {
                showInfo(result);
            }
        }
    };

    private final static String RESULT = "RESULT";

    private void sendMsg(String str) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(RESULT, str);
        msg.setData(bundle);
        handler.sendMessage(msg);
    }


    private boolean getUuidStrings() {
        if (deviceIdMap != null) {
            Set<Config.DevicePosition> devicePositions = deviceIdMap.keySet();
            mDeviceIdS = new String[devicePositions.size()];
            int i = 0;
            for (Config.DevicePosition devicePosition : devicePositions) {
                mDeviceIdS[i] = deviceIdMap.get(devicePosition);
                i++;
            }
            return true;
        }
        return false;
    }


}
