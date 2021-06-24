package exportkit.xd.model;

public class dog {
    private int id;
    private String title;
    private String mota;
    private String gia;
    private String diachi;
    private String loai;
    private String age;
    private float can_nang;
    private String gioitinh;
    private String trang_thai;
    private String url;

    public dog() {
    }

    public dog(int id, String title, String mota, String gia, String diachi, String loai, String age, float can_nang, String gioitinh, String trang_thai, String url) {
        this.id = id;
        this.title = title;
        this.mota = mota;
        this.gia = gia;
        this.diachi = diachi;
        this.loai = loai;
        this.age = age;
        this.can_nang = can_nang;
        this.gioitinh = gioitinh;
        this.trang_thai = trang_thai;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public float getCan_nang() {
        return can_nang;
    }

    public void setCan_nang(float can_nang) {
        this.can_nang = can_nang;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
