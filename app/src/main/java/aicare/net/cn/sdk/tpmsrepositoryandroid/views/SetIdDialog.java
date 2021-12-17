package aicare.net.cn.sdk.tpmsrepositoryandroid.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.EnumMap;
import java.util.Map;

import aicare.net.cn.sdk.tpmsrepositoryandroid.OnIdSetListener;
import aicare.net.cn.sdk.tpmsrepositoryandroid.R;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.Config;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.Configs;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.ParseData;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.SPUtils;
import aicare.net.cn.sdk.tpmsrepositoryandroid.utils.T;


/**
 * Created by Suzy on 2016/2/22.
 */
public class SetIdDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private EnumMap<Config.DevicePosition, String> deviceIdMap;

    private EditText et_left_front, et_left_rear, et_right_front, et_right_rear;
    private Button btn_query;

    private OnIdSetListener onIdSetListener;

    public SetIdDialog(Context context, EnumMap<Config.DevicePosition, String> deviceIdMap, OnIdSetListener onIdSetListener) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.deviceIdMap = deviceIdMap;
        this.onIdSetListener = onIdSetListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initEvents();
        setInfo();
    }

    private void setInfo() {
        if (deviceIdMap.size() != 0) {
            for (Map.Entry<Config.DevicePosition, String> entry : deviceIdMap.entrySet()) {
                switch (entry.getKey()) {
                    case LEFT_FRONT:
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            et_left_front.setText(entry.getValue());
                        }
                        et_left_front.setSelection(entry.getValue().length());
                        break;
                    case LEFT_REAR:
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            et_left_rear.setText(entry.getValue());
                        }
                        et_left_rear.setSelection(entry.getValue().length());
                        break;
                    case RIGHT_FRONT:
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            et_right_front.setText(entry.getValue());
                        }
                        et_right_front.setSelection(entry.getValue().length());
                        break;
                    case RIGHT_REAR:
                        if (!TextUtils.isEmpty(entry.getValue())) {
                            et_right_rear.setText(entry.getValue());
                        }
                        et_right_rear.setSelection(entry.getValue().length());
                        break;
                }
            }
        }
    }

    private void initViews() {
        setContentView(R.layout.dialog_set_id);
        btn_query = (Button) findViewById(R.id.btn_query);
        et_left_front = (EditText) findViewById(R.id.et_left_front);
        et_left_rear = (EditText) findViewById(R.id.et_left_rear);
        et_right_front = (EditText) findViewById(R.id.et_right_front);
        et_right_rear = (EditText) findViewById(R.id.et_right_rear);
    }

    private void initEvents() {
        btn_query.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_query:
                if (onIdSetListener != null) {
                    String leftFront = et_left_front.getText().toString().trim().toUpperCase();
                    String leftRear = et_left_rear.getText().toString().trim().toUpperCase();
                    String rightFront = et_right_front.getText().toString().trim().toUpperCase();
                    String rightRear = et_right_rear.getText().toString().trim().toUpperCase();
                    boolean isLfIdOk = setDeviceIdMap(Config.DevicePosition.LEFT_FRONT, leftFront);
                    boolean isLrIdOk = setDeviceIdMap(Config.DevicePosition.LEFT_REAR, leftRear);
                    boolean isRfIdOk = setDeviceIdMap(Config.DevicePosition.RIGHT_FRONT, rightFront);
                    boolean isRrIdOk = setDeviceIdMap(Config.DevicePosition.RIGHT_REAR, rightRear);
                    if (showToast(isLfIdOk, isLrIdOk, isRfIdOk, isRrIdOk)) {
                        onIdSetListener.onIdSet();
                        dismiss();
                    }
                }
                break;
        }
    }

    private boolean setDeviceIdMap(Config.DevicePosition devicePosition, String id) {
        if (!TextUtils.isEmpty(id)) {
            if (!ParseData.isDeviceId(id)) {
                return false;
            }
        }
        switch (devicePosition) {
            case LEFT_FRONT:
                SPUtils.put(context, Configs.LEFT_FRONT, id);
                break;
            case LEFT_REAR:
                SPUtils.put(context, Configs.LEFT_REAR, id);
                break;
            case RIGHT_FRONT:
                SPUtils.put(context, Configs.RIGHT_FRONT, id);
                break;
            case RIGHT_REAR:
                SPUtils.put(context, Configs.RIGHT_REAR, id);
                break;
        }
        return true;
    }

    private boolean showToast(boolean isLfIdOk, boolean isLrIdOk, boolean isRfIdOk, boolean isRrIdOk) {
        if (!isLfIdOk) {
            T.showShort(context, R.string.left_front_error);
        } else if (!isLrIdOk) {
            T.showShort(context, R.string.left_rear_error);
        } else if (!isRfIdOk) {
            T.showShort(context, R.string.right_front_error);
        } else if (!isRrIdOk) {
            T.showShort(context, R.string.right_rear_error);
        } else {
            T.showShort(context, R.string.set_id_success);
            return true;
        }
        return false;
    }
}
