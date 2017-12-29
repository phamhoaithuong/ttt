package duyen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	Context context;
	public final static String DB_NAME = "dulieu.sqlite";
	public final static int DB_VERSION = 3;
	

	public DbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String thuchi = "CREATE TABLE THUCHI("
				+ "ID_THUCHI INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "GIAODICH TEXT NOT NULL, "
				+ "LOAIGIAODICH TEXT NOT NULL)";
		db.execSQL(thuchi);
		
		String giaodich = "CREATE TABLE GIAODICH("
				+ "ID_GIAODICH INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "NGAY_GIAODICH DATE NOT NULL, "
				+ "MOTA_GIAODICH TEXT NOT NULL, "
				+ "SOTIEN_GIAODICH INTEGER NOT NULL, "
				+ "ID_THUCHI REAL NOT NULL CONSTRAINT ID_THUCHI REFERENCES THUCHI(ID_THUCHI) ON DELETE CASCADE, "
				+ "GHICHU_GIAODICH TEXT)";
		db.execSQL(giaodich);
		
		String creatAdmin = "CREATE table TaiKhoan("
				+ "taikhoan text primary key, "
				+ "matkhau text)";
		db.execSQL(creatAdmin);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS THUCHI";
		String giaodich = "DROP TABLE IF EXISTS GIAODICH";
		String sdasd = "DROP TABLE IF EXISTS TaiKhoan";
		db.execSQL(sql);
		db.execSQL(giaodich);db.execSQL(sdasd);
		onCreate(db);
	}

}
