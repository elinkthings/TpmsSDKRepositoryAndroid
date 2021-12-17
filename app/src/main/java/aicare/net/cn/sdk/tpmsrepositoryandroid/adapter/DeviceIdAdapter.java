package aicare.net.cn.sdk.tpmsrepositoryandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import aicare.net.cn.sdk.tpmsrepositoryandroid.R;
import aicare.net.cn.sdk.tpmsrepositoryandroid.bean.DeviceId;

/**
 * Created by Suzy on 2016/8/30.
 */
public class DeviceIdAdapter extends BaseAdapter {

    private List<DeviceId> deviceIds;
    private Context context;

    public DeviceIdAdapter(Context context, List<DeviceId> deviceIds) {
        this.context = context;
        this.deviceIds = deviceIds;
    }

    @Override
    public int getCount() {
        return deviceIds.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.device_id_item, null);
            viewHolder.tv_device_id = (TextView) convertView.findViewById(R.id.tv_device_id);
            viewHolder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        setValues(viewHolder, position);
        return convertView;
    }

    private void setValues(ViewHolder viewHolder, int position) {
        viewHolder.tv_device_id.setText("设备ID：" + deviceIds.get(position).getDeviceId());
        viewHolder.tv_count.setText("次数：" + deviceIds.get(position).getCount());
    }

    private class ViewHolder {
        private TextView tv_device_id;
        private TextView tv_count;
    }
}
