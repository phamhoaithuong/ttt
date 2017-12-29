package duyen.database;

import com.example.ass_ps04044_mob202.MainActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import duyen.model.admin;

public class AdminDAO {
	private SQLiteDatabase db;
	private DbHelper myData;
	Context context;
	public AdminDAO(Context context) {
		this.context = context;
		myData = new DbHelper(context);
		db = myData.getWritableDatabase();
	}
	public long insert(admin admin){
		ContentValues contentValues = new ContentValues();
		contentValues.put("taikhoan", admin.id);
		contentValues.put("matkhau", admin.pass);
		return db.insert("TaiKhoan", null, contentValues);
	}
	public int update(admin admin){
		ContentValues values = new ContentValues();
		values.put("matkhau", admin.pass);
		return db.update("TaiKhoan", values, "makhoa=?", new String[]{String.valueOf(admin.id)});
	}
	public int dangNhap(String user, String pass){
		String sql = "select * from TaiKhoan WHERE taikhoan='" + user + "' AND matkhau='" + pass + "'";
		Cursor c = db.rawQuery(sql, null);
		if(c.getCount()<=0){
			Toast.makeText(context, "Tài khoản hoặc mật khẩu không đúng!!", Toast.LENGTH_LONG).show();
			return 0;
		}
		Toast.makeText(context, "Đăng nhập thành công!!", Toast.LENGTH_LONG).show();
		return 1;
	}
	public String quenMatKhau(String user){
		String mk = "";
		String sql = "select * from TaiKhoan WHERE taikhoan='"+user+"'";
		Cursor c = db.rawQuery(sql, null);
		if(c.getCount()<=0){
			Toast.makeText(context, "Tài khoản không đúng!!", Toast.LENGTH_LONG).show();
		}else{
			if (c.moveToFirst()) {
				do {
					mk = c.getString(1);

				} while (c.moveToNext());
			}
		}
		return mk;
	}
}
