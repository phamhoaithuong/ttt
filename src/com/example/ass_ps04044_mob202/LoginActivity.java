package com.example.ass_ps04044_mob202;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import duyen.database.AdminDAO;
import duyen.model.admin;

public class LoginActivity extends Activity {
	private EditText user, pass;
	private Button btnDN, btnRS;
	private TextView txtQMK, txtDangKy;
	AdminDAO adminDAO;
	admin admin;
	String file = "mydata";
	CheckBox ck;
	int batki;
	Random ramdom = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);

		adminDAO = new AdminDAO(this);

		user = (EditText) findViewById(R.id.edIDDangNhap);
		pass = (EditText) findViewById(R.id.edPass);
		btnDN = (Button) findViewById(R.id.btnDangNhap);
		btnRS = (Button) findViewById(R.id.btnResetDangNhap);
		txtQMK = (TextView) findViewById(R.id.txtQuenMatKhau);
		txtDangKy = (TextView) findViewById(R.id.txtDangKy);
		ck = (CheckBox) findViewById(R.id.checkSave);
		admin = new admin();

		
	}


	public void DangNhap(View view) {
		String us = user.getText().toString();
		String ps = pass.getText().toString();
		if (us.isEmpty() || ps.isEmpty()) {
			Toast.makeText(LoginActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
		} else {
			if (adminDAO.dangNhap(us, ps) == 1) {
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}
	}

	public void DangKy(View view) {
		Dialog dialog = new Dialog(LoginActivity.this);
		dialog.setTitle("Đăng Ký");
		dialog.setContentView(R.layout.item_dangky);
		Button dangky = (Button) dialog.findViewById(R.id.btnDangKyOK);
		Button nhaplai = (Button) dialog.findViewById(R.id.btnNhapLaiDK);
		final EditText id = (EditText) dialog.findViewById(R.id.edIDDangKy);
		final EditText pass = (EditText) dialog.findViewById(R.id.edMKDangKy);
		dangky.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ten = id.getText().toString();
				String ps = pass.getText().toString();

				if (ten.isEmpty() || ps.isEmpty()) {
					Toast.makeText(getApplicationContext(), "Không được bỏ trống", Toast.LENGTH_SHORT).show();
				} else {
					admin.id = ten;
					admin.pass = ps;
					adminDAO.insert(admin);

					Toast.makeText(getApplicationContext(), "Thành công", Toast.LENGTH_SHORT).show();
				}
			}
		});
		nhaplai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				id.setText("");
				pass.setText("");
			}
		});

		dialog.show();
	}

	public void QuenMatKhau(View view) {

		batki = ramdom.nextInt(99999);
		Dialog dialog = new Dialog(LoginActivity.this);
		dialog.setTitle("Quên Mật Khẩu");
		dialog.setContentView(R.layout.item_quenmatkhau);
		Button quenmatkhau = (Button) dialog.findViewById(R.id.btnQuenMatKhauOK);
		Button nhaplai = (Button) dialog.findViewById(R.id.btnNhapLaiQMK);
		final TextView txtMaKiemTra = (TextView) dialog.findViewById(R.id.maKiemTraOK);
		final EditText id = (EditText) dialog.findViewById(R.id.edIDQuenMatKhau);
		final EditText pass = (EditText) dialog.findViewById(R.id.edMKQuenMatKhau);
		final EditText makiemtra = (EditText) dialog.findViewById(R.id.edMaKiemTra);

		txtMaKiemTra.setText("" + batki);

		quenmatkhau.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int kiemtra = Integer.parseInt(makiemtra.getText().toString());
				int sosanh = Integer.parseInt(txtMaKiemTra.getText().toString());

				if (id.getText().toString().isEmpty()) {
					Toast.makeText(LoginActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
					return;
				}
				if (kiemtra != sosanh) {
					Toast.makeText(LoginActivity.this, "Mã kiểm tra không đúng!", Toast.LENGTH_SHORT).show();
					pass.setText("");
				} else {
					pass.setText("" + adminDAO.quenMatKhau(id.getText().toString()));
					Toast.makeText(getApplicationContext(), "Thành công!!", Toast.LENGTH_LONG).show();
				}
			}
		});
		nhaplai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				id.setText("");
				pass.setText("");
				makiemtra.setText("");

			}
		});
		txtMaKiemTra.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				batki = ramdom.nextInt(99999);
				txtMaKiemTra.setText("" + batki);
				if (batki < 10) {
					txtMaKiemTra.setText("0000" + batki);
				} else if (batki < 100) {
					txtMaKiemTra.setText("000" + batki);
				} else if (batki < 1000) {
					txtMaKiemTra.setText("00" + batki);
				} else if (batki < 10000) {
					txtMaKiemTra.setText("0" + batki);
				}
			}
		});
		dialog.show();
	}

	public void NhapLai(View view) {
		user.setText("");
		pass.setText("");
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		save();
	}

	private void save() {
		SharedPreferences pref = getSharedPreferences(file, MODE_PRIVATE);
		SharedPreferences.Editor editer = pref.edit();
		String userA = user.getText().toString();
		String passA = pass.getText().toString();
		boolean check = ck.isChecked();
		if (!check) {
			editer.clear();
		} else {
			editer.putString("username", userA);
			editer.putString("pass", passA);
			editer.putBoolean("savestatus", check);
		}
		editer.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		restoring();
	}

	public void restoring() {
		SharedPreferences pref = getSharedPreferences(file, MODE_PRIVATE);
		boolean chk = pref.getBoolean("savestatus", false);
		if (chk) {
			String userA = pref.getString("username", "");
			String passA = pref.getString("pass", "");
			user.setText(userA);
			pass.setText(passA);
		}
		ck.setChecked(chk);
	}
}
