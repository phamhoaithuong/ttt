package duyen.model;

public class ThuChi {
	public int id;
	public String giaodich;
	public String loaigiaodich;
	public int idten;
	
	public ThuChi( String giaodich, int idten) {
		this.giaodich = giaodich;
		this.idten = idten;
	}
	public int getIdten() {
		return idten;
	}
	public void setIdten(int idten) {
		this.idten = idten;
	}
	public ThuChi() {
	}
	public ThuChi(int id, String giaodich, String loaigiaodich) {
		super();
		this.id = id;
		this.giaodich = giaodich;
		this.loaigiaodich = loaigiaodich;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGiaodich() {
		return giaodich;
	}
	public void setGiaodich(String giaodich) {
		this.giaodich = giaodich;
	}
	public String getLoaigiaodich() {
		return loaigiaodich;
	}
	public void setLoaigiaodich(String loaigiaodich) {
		this.loaigiaodich = loaigiaodich;
	}
	@Override
	public String toString() {
		return giaodich;
	}
}
