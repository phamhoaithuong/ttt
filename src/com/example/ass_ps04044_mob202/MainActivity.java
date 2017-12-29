package com.example.ass_ps04044_mob202;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import duyen.adapter.ExpandListAdapter;
import duyen.adapter.main_adapter;
import duyen.adapter.settings_adapter;
import duyen.database.GiaoDichDAO;
import duyen.database.ThuChiDAO;
import duyen.model.Child;
import duyen.model.GiaoDich;
import duyen.model.Group;
import duyen.model.ThuChi;

public class MainActivity extends Activity implements OnItemSelectedListener {
	TabHost tabs;
	Calendar calendar = Calendar.getInstance();
	String[] list = { "Khoản chi", "Khoản thu" };
	String[] thang = { "Tất cả","Hôm nay","Tuần này", "Tháng này" };
	ImageButton btnadd;
	EditText nhap, txtMota, txtSoTien, txtGhiChu;
	TextView chonNgay, txtSoTienDu, tuNgay, denNgay;
	Spinner spn, spnGD, spnThongKe;
	ListView listview, listview_main;
	List<ThuChi> listThuChi;
	List<GiaoDich> listGiaoDich;
	private ThuChi thuchi;
	ThuChiDAO thuChiDao;
	GiaoDichDAO giaoDichDAO;
	ArrayAdapter<ThuChi> adapter;
	settings_adapter adapter2;
	main_adapter adapterMain;
	private String tuNgayA, denNgayA, ngayDaChon;
	private double tienThu, tienChi, getGiaodich_TienThu;
	private ExpandListAdapter ExpAdapter;
	private ArrayList<Group> ExpListItems;
	private ExpandableListView ExpandList;
	private List<Child> listChildThu, listChildChi, listChildChi2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		/*
		 * Database
		 */
		thuChiDao = new ThuChiDAO(this);
		giaoDichDAO = new GiaoDichDAO(this);

		anhxa();
		capNhat();
		/*
		 * TabHost
		 */
		tabs = (TabHost) findViewById(R.id.tabhost);
		tabs.setup();

		TabSpec thuchitab = tabs.newTabSpec("ThuChiTab");
		thuchitab.setIndicator("Thu Chi");
		thuchitab.setContent(R.id.tab1);
		tabs.addTab(thuchitab);

		TabSpec danhsach = tabs.newTabSpec("ListTab");
		danhsach.setIndicator("Thống Kê");
		danhsach.setContent(R.id.tab2);
		tabs.addTab(danhsach);

		TabSpec setting = tabs.newTabSpec("SettingsTab");
		setting.setIndicator(" Cài Đặt");
		setting.setContent(R.id.tab3);
		tabs.addTab(setting);

		tabs.setCurrentTab(0);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		spn.setAdapter(adapter);

		/*
		 * ListView
		 */
		listView();

	}

	public ArrayList<Group> SetStandardGroups() {
		tienThu = giaoDichDAO.getTienThu();
		tienChi = giaoDichDAO.getTienChi();
//		listChildThu = giaoDichDAO.getChildThu();
//		listChildChi = giaoDichDAO.getChildChi2(-9999 , 9999);
//		spnThongKe.setOnItemSelectedListener(this);
		String group_names[] = { "TỔNG THU", "TỔNG CHI" };
		ArrayList<Group> list = new ArrayList<Group>();
		ArrayList<Child> ch_list;
		
		

		for (int i = 0; i < group_names.length; i++) {
			Group gru = new Group();
			gru.setName(group_names[i]);

			if (i == 0) {
				// ExpandListView Child Tong Thu
				gru.setTien("" + tienThu);
				ch_list = new ArrayList<Child>();
				for (int k = 0; k < listChildThu.size(); k++) {
					Child ch = new Child();
					ch.setTen(listChildThu.get(k).getTen());
					ch.setTien(listChildThu.get(k).getTien());
					ch_list.add(ch);
				}
				
				gru.setItems(ch_list);
			} else {
				// ExpandListView Child Tong Chi
				gru.setTien("" + tienChi);
				ch_list = new ArrayList<Child>();
				for (int k = 0; k < listChildChi.size(); k++) {
					Child ch = new Child();
					ch.setTen(listChildChi.get(k).getTen());
					ch.setTien(listChildChi.get(k).getTien());
					ch_list.add(ch);
				}
				
				gru.setItems(ch_list);
			}
			list.add(gru);
		}
		return list;
	}

	public void anhxa() {
		
		btnadd = (ImageButton) findViewById(R.id.btnAddSettings);
		nhap = (EditText) findViewById(R.id.txtNhapSettings);
		spn = (Spinner) findViewById(R.id.spnSettings);
		listview = (ListView) findViewById(R.id.listView_Settings);
		listview_main = (ListView) findViewById(R.id.listView_main);
		txtSoTienDu = (TextView) findViewById(R.id.txtSoTienDu);
		tuNgay = (TextView) findViewById(R.id.txtTuNgay);
		denNgay = (TextView) findViewById(R.id.txtDenNgay);
		spnThongKe = (Spinner) findViewById(R.id.spnThongKe2);
		ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
		
		ArrayAdapter<String> adapterThongKe = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, thang);
		spnThongKe.setAdapter(adapterThongKe);
		
		listChildThu = giaoDichDAO.getChildThu2(-9999, 9999);
		listChildChi = giaoDichDAO.getChildChi2(-9999 , 9999);
		spnThongKe.setOnItemSelectedListener(this);
		
		DatePickerDialog();
	}

	public void ThemVao(View view) {
		String ten = nhap.getText().toString();
		if (ten.isEmpty()) {
			Toast.makeText(getApplicationContext(), "Nhập tên cài đặt", Toast.LENGTH_SHORT).show();
		} else {
			thuchi = new ThuChi();

			nhap.setText("");

			thuchi.giaodich = ten;
			thuchi.loaigiaodich = spn.getSelectedItem().toString();

			thuChiDao.insertThuChi(thuchi);
			capNhat();
		}
	}

	public void capNhat() {
		
		
		listThuChi = thuChiDao.getAll();
		adapter2 = new settings_adapter(MainActivity.this, listThuChi);
		listview.setAdapter(adapter2);

		listGiaoDich = giaoDichDAO.getAll();
		adapterMain = new main_adapter(MainActivity.this, listGiaoDich);
		listview_main.setAdapter(adapterMain);

		txtSoTienDu.setText("" + giaoDichDAO.TongTienGiaoDich()); // So Tien Du
		
		ExpListItems = SetStandardGroups();
		ExpAdapter = new ExpandListAdapter(MainActivity.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		
		
	}
public void capnhat2(){
	ExpListItems = SetStandardGroups();
	ExpAdapter = new ExpandListAdapter(MainActivity.this, ExpListItems);
	ExpandList.setAdapter(ExpAdapter);
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add) {
			addGiaoDich();

			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public void addGiaoDich() {
		final Dialog dialog3 = new Dialog(this);
		dialog3.setTitle("Thêm Giao Dịch");
		dialog3.setContentView(R.layout.them_giao_dich);
		dialog3.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog3.show();

		spnGD = (Spinner) dialog3.findViewById(R.id.spnMa_ThuChi);
		txtMota = (EditText) dialog3.findViewById(R.id.editMoTa_GiaoDich);
		txtSoTien = (EditText) dialog3.findViewById(R.id.editSoTien_GiaoDich);
		txtGhiChu = (EditText) dialog3.findViewById(R.id.editGhiChu_GiaoDich);
		Button luuGD = (Button) dialog3.findViewById(R.id.btnLuuGD);
		Button ResetGD = (Button) dialog3.findViewById(R.id.btnResetGD);
		chonNgay = (TextView) dialog3.findViewById(R.id.txtNgay_GiaoDich);

		List<ThuChi> listTen = thuChiDao.getTen();
		adapter = new ArrayAdapter<ThuChi>(this, android.R.layout.simple_list_item_1, listTen);
		spnGD.setAdapter(adapter);
		chonNgay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
						new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar date = calendar.getInstance();
						date.set(Calendar.YEAR, year);
						date.set(Calendar.MONTH, monthOfYear);
						date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

						
						String aaaa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate;
						try {
					        startDate = df.parse(aaaa);
					        ngayDaChon = df.format(startDate);
					    } catch (ParseException e) {
					        e.printStackTrace();
					    }
						
						chonNgay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();
			}
		});
		ResetGD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtMota.setText("");
				txtGhiChu.setText("");
				txtMota.setText("");
				txtSoTien.setText("");
				spnGD.setSelection(0);
				chonNgay.setText("Chọn ngày");
			}
		});
		luuGD.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (chonNgay.getText().toString().equals("Chọn ngày")) {
					Toast.makeText(getApplicationContext(), "Chọn ngày !!", Toast.LENGTH_LONG).show();
					return;
				}
				if (txtMota.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Nhập mô tả !!", Toast.LENGTH_LONG).show();
					txtMota.requestFocus();
					return;
				} 
				if(spnGD.getSelectedItemPosition()==-1){
					Toast.makeText(getApplicationContext(), "Chưa nhập Cài Đặt", Toast.LENGTH_LONG).show();
					return;
				}
				if (txtSoTien.getText().toString().isEmpty()) {
					Toast.makeText(getApplicationContext(), "Nhập tiền !!", Toast.LENGTH_LONG).show();
					txtSoTien.requestFocus();
					
				} else {
					GiaoDich giaodich = new GiaoDich();
					giaodich.ngaygiaodich = ngayDaChon;
					giaodich.mota = txtMota.getText().toString();
					giaodich.sotien = Integer.parseInt(txtSoTien.getText().toString());
					giaodich.ghichu = txtGhiChu.getText().toString();
					giaodich.id_thuchi = listThuChi.get(spnGD.getSelectedItemPosition()).id;

					giaoDichDAO.insertGiaoDich(giaodich);
					Toast.makeText(getApplicationContext(), "OK !!", Toast.LENGTH_LONG).show();

					capNhat();
					
				}
			}
		});

	}

	public void listView() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setMessage("Bạn có muốn xóa không?");
				builder.setTitle("Xóa");
				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						thuChiDao.deleteThuChi(listThuChi.get(arg2).getId());
						listThuChi.remove(arg2);
						adapter2.notifyDataSetChanged();
						
					}
				});

				builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.create().show();
			}
		});

		listview_main.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final int pos = position;
				AlertDialog.Builder buider = new AlertDialog.Builder(MainActivity.this);
				buider.setTitle("Thông tin Giao Dịch !");
				buider.setMessage("Bạn có muốn xóa không?");
				buider.setNeutralButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						giaoDichDAO.deleteGiaoDich(String.valueOf(listGiaoDich.get(pos).id));
						capNhat();
					}
				});
				buider.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				buider.create().show();
			}
		});
	}

	public void DatePickerDialog() {
		tuNgay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
						new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar date = calendar.getInstance();
						date.set(Calendar.YEAR, year);
						date.set(Calendar.MONTH, monthOfYear);
						date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						
						
						String aaaa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate;
						try {
					        startDate = df.parse(aaaa);
					        tuNgayA = df.format(startDate);
					    } catch (ParseException e) {
					        e.printStackTrace();
					    }
						
						tuNgay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//						tuNgayA = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();
			}
		});

		denNgay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
						new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						
						Calendar date = calendar.getInstance();
						date.set(Calendar.YEAR, year);
						date.set(Calendar.MONTH, monthOfYear);
						date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
						
						String aaaa = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						Date startDate;
						try {
					        startDate = df.parse(aaaa);
					        denNgayA = df.format(startDate);
					    } catch (ParseException e) {
					        e.printStackTrace();
					    }
						denNgay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//						denNgayA = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.show();
			}

		});

	}

	public void truyVanNgay(View view) {

		if (tuNgay.getText().toString().equals("Chọn ngày") || denNgay.getText().toString().equals("Chọn ngày")) {
			Toast.makeText(getApplicationContext(), "Chưa chọn ngày", Toast.LENGTH_LONG).show();
		} else {
			List<GiaoDich> list = giaoDichDAO.getNgay(tuNgayA, denNgayA);
			adapterMain = new main_adapter(MainActivity.this, list);
			listview_main.setAdapter(adapterMain);
		}
	}

	public void resetNgay(View view) {
		tuNgay.setText("Chọn ngày");
		denNgay.setText("Chọn ngày");
		capNhat();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		switch (position){
		case 0:
			listChildThu = giaoDichDAO.getChildThu2(-9999, 9999);
			listChildChi = giaoDichDAO.getChildChi2(-9999 , 9999);
			capnhat2();
			break;
		case 1:
			listChildThu = giaoDichDAO.getChildThu2(0, 0);
			listChildChi = giaoDichDAO.getChildChi2(0 , 0);
			capnhat2();
			break;
		case 2:
			listChildThu = giaoDichDAO.getChildThu2(-3, 3);
			listChildChi = giaoDichDAO.getChildChi2(-3 , 3);
			capnhat2();
			break;
		case 3:
			listChildThu = giaoDichDAO.getChildThu2(-15, 15);
			listChildChi = giaoDichDAO.getChildChi2(-15 , 15);
			capnhat2();
			break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
