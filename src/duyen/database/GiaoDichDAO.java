package duyen.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import duyen.model.Child;
import duyen.model.GiaoDich;
import duyen.model.Group;

public class GiaoDichDAO {
	public SQLiteDatabase db;
	public DbHelper myData;

	public GiaoDichDAO(Context context) {
		myData = new DbHelper(context);
		db = myData.getWritableDatabase();
	}

	public long insertGiaoDich(GiaoDich giaodich) {
		ContentValues values = new ContentValues();
		values.put("NGAY_GIAODICH", giaodich.ngaygiaodich);
		values.put("MOTA_GIAODICH", giaodich.mota);
		values.put("SOTIEN_GIAODICH", giaodich.sotien);
		values.put("ID_THUCHI", giaodich.id_thuchi);
		values.put("GHICHU_GIAODICH", giaodich.ghichu);
		return db.insert("GIAODICH", null, values);
	}

	public int updateGiaoDich(GiaoDich giaodich) {
		ContentValues values = new ContentValues();
		values.put("NGAY_GIAODICH", giaodich.ngaygiaodich);
		values.put("MOTA_GIAODICH", giaodich.mota);
		values.put("SOTIEN_GIAODICH", giaodich.sotien);
		values.put("ID_THUCHI", giaodich.id_thuchi);
		values.put("GHICHU_GIAODICH", giaodich.ghichu);
		return db.update("GIAODICH", values, "ID_GIAODICH=?", new String[] { String.valueOf(giaodich.id) });
	}

	public int deleteGiaoDich(String id) {
		return db.delete("GIAODICH", "ID_GIAODICH=?", new String[] { id });
	}

	public List<GiaoDich> getAll() {
		List<GiaoDich> List = new ArrayList<GiaoDich>();
		String sql = "SELECT * FROM GIAODICH";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			GiaoDich giaodich = new GiaoDich();
			giaodich.setId(c.getInt(0));
			giaodich.setNgaygiaodich(c.getString(1));
			giaodich.setMota(c.getString(2));
			giaodich.setSotien(c.getInt(3));
			giaodich.setGhichu(c.getString(5));
			List.add(giaodich);
		}
		return List;
	}

//	public List<Child> getChildThu() {
//		List<Child> List = new ArrayList<Child>();
//		String sql = "SELECT GIAODICH, SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản thu' GROUP BY GIAODICH";
//		Cursor c = db.rawQuery(sql, null);
//		while (c.moveToNext()) {
//			Child child = new Child();
//			child.setTen(c.getString(0));
//			child.setTien(String.valueOf(c.getDouble(1)));
//			List.add(child);
//		}
//		return List;
//	}
//	
//	public List<Child> getChildChi() {
//		List<Child> List = new ArrayList<Child>();
//		String sql = "SELECT GIAODICH, SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản chi' GROUP BY GIAODICH";
//		Cursor c = db.rawQuery(sql, null);
//		while (c.moveToNext()) {
//			Child child = new Child();
//			child.setTen(c.getString(0));
//			child.setTien(String.valueOf(c.getDouble(1)));
//			List.add(child);
//		}
//		return List;
//	}
	public List<Child> getChildChi2(int tuNgay, int denNgay) {
		List<Child> List = new ArrayList<Child>();
		String sql = "SELECT GIAODICH, SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản chi' AND (NGAY_GIAODICH BETWEEN date('now','"+tuNgay+" day') AND date('now','"+denNgay+" day')) GROUP BY GIAODICH";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Child child = new Child();
			child.setTen(c.getString(0));
			child.setTien(String.valueOf(c.getDouble(1)));
			List.add(child);
		}
		return List;
	}
	public List<Child> getChildThu2(int tuNgay, int denNgay) {
		List<Child> List = new ArrayList<Child>();
		String sql = "SELECT GIAODICH, SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản thu' AND (NGAY_GIAODICH BETWEEN date('now','"+tuNgay+" day') AND date('now','"+denNgay+" day')) GROUP BY GIAODICH";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Child child = new Child();
			child.setTen(c.getString(0));
			child.setTien(String.valueOf(c.getDouble(1)));
			List.add(child);
		}
		return List;
	}
	public double getTienChi() {
		double tien = 0;
		String sql = "SELECT SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản chi'";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
			do {
				tien = c.getDouble(0);
			} while (c.moveToNext());
		}
		return tien;
	}
	public double getTienThu() {
		double tien = 0;
		String sql = "SELECT SUM(SOTIEN_GIAODICH) FROM GIAODICH, THUCHI WHERE GIAODICH.ID_THUCHI = THUCHI.ID_THUCHI AND THUCHI.LOAIGIAODICH ='Khoản thu'";
		Cursor c = db.rawQuery(sql, null);
		if (c.moveToFirst()) {
			do {
				tien = c.getDouble(0);
			} while (c.moveToNext());
		}
		return tien;
	}
	public double TongTienGiaoDich() {
		double tien = getTienThu() - getTienChi();
		return tien;
	}
	
	public List<GiaoDich> getNgay(String tuNgay, String denNgay) {
		List<GiaoDich> List = new ArrayList<GiaoDich>();
		String sql = "SELECT * FROM GIAODICH WHERE NGAY_GIAODICH BETWEEN '"+tuNgay+"' AND '"+denNgay+"'";
		Cursor c = db.rawQuery(sql, null);
		while (c.moveToNext()) {
			GiaoDich giaodich = new GiaoDich();
			giaodich.setId(c.getInt(0));
			giaodich.setNgaygiaodich(c.getString(1));
			giaodich.setMota(c.getString(2));
			giaodich.setSotien(c.getInt(3));
			giaodich.setGhichu(c.getString(5));
			List.add(giaodich);
		}
		return List;
	}
}
