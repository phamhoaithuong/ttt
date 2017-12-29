package duyen.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.ass_ps04044_mob202.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import duyen.model.ThuChi;

public class settings_adapter extends ArrayAdapter<ThuChi> {
	Activity activity;
	List<ThuChi> list;

	public settings_adapter(Activity activity, List<ThuChi> list) {
		super(activity, R.layout.cai_dat_hien_thi, list);
		this.activity = activity;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View row = inflater.inflate(R.layout.cai_dat_hien_thi, null, true);

		TextView txtName = (TextView) row.findViewById(R.id.txtSettings_HienThi);
		TextView txtLoai = (TextView) row.findViewById(R.id.txtSettings_KhoanChi);

		ThuChi thuChi = list.get(position);

		txtName.setText(thuChi.giaodich);
		txtLoai.setText(thuChi.loaigiaodich);
		if (thuChi.loaigiaodich.equals("Khoản chi")) {
			txtLoai.setTextColor(Color.rgb(255, 153, 0));
		} else {
			txtLoai.setTextColor(Color.rgb(172, 230, 0));
		}

		return row;
	}

}
