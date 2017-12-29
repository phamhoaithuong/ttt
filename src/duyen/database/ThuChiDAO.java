package duyen.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import duyen.model.ThuChi;

public class ThuChiDAO {
	private DbHelper myData;
	private SQLiteDatabase db;

	public ThuChiDAO(Context context) {
		myData = new DbHelper(context);
		db = myData.getWritableDatabase();
	}

	public long insertThuChi(ThuChi thuchi) {
		ContentValues values = new ContentValues();
		values.put("GIAODICH", thuchi.giaodich);
		values.put("LOAIGIAODICH", thuchi.loaigiaodich);

		return db.insert("THUCHI", null, values);

	}
	public int updateThuChi(ThuChi thuchi){
		ContentValues values = new ContentValues();
		values.put("GIAODICH", thuchi.giaodich);
		values.put("LOAIGIAODICH", thuchi.loaigiaodich);
		return db.update("THUCHI", values, "ID_THUCHI=?", new String[]{String.valueOf(thuchi.id)});
	}
	public int deleteThuChi(int id){
		return db.delete("THUCHI", "ID_THUCHI=?", new String[]{String.valueOf(id)} );
	}
	public List<ThuChi> getAll(){
		List<ThuChi> List = new ArrayList<ThuChi>();
		String truyvan = "SELECT * FROM THUCHI";
		Cursor c = db.rawQuery(truyvan, null);
		if(c.moveToFirst()){
			do {
				ThuChi thuChi = new ThuChi();
				thuChi.setId(c.getInt(0));
				thuChi.setGiaodich(c.getString(1));
				thuChi.setLoaigiaodich(c.getString(2));
				List.add(thuChi);
			} while (c.moveToNext());
		}
		return List;
	}
	public List<ThuChi> getTen(){
		List<ThuChi> List = new ArrayList<ThuChi>();
		String truyvan = "SELECT * FROM THUCHI";
		Cursor c = db.rawQuery(truyvan, null);
		if(c.moveToFirst()){
			do {
				ThuChi thuChi = new ThuChi();
				thuChi.setGiaodich(c.getString(1));
				thuChi.setLoaigiaodich(c.getString(2));
				List.add(thuChi);
			} while (c.moveToNext());
		}
		return List;
	}

}
