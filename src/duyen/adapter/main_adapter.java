package duyen.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.ass_ps04044_mob202.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import duyen.model.GiaoDich;

public class main_adapter extends ArrayAdapter<GiaoDich> {
	Activity activity;
	List<GiaoDich> list;
	public main_adapter(Activity activity, List<GiaoDich> list){
		super(activity, R.layout.listview_main_item, list);
		this.activity = activity;
		this.list = list;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.listview_main_item, null, true);
		
		TextView txtName = (TextView) view.findViewById(R.id.lblTenGD);
		TextView txtTien = (TextView) view.findViewById(R.id.lbl23123);
		TextView txtGhiChu = (TextView) view.findViewById(R.id.lblSuaSoTien);
		TextView txtNgay = (TextView) view.findViewById(R.id.lbl2222);
		
		GiaoDich giaoDich = list.get(position);

		txtName.setText(giaoDich.mota);
		txtTien.setText(String.valueOf(giaoDich.sotien));
		txtNgay.setText(giaoDich.ngaygiaodich);
		txtGhiChu.setText(giaoDich.ghichu);
		
		return view;
	}
}
