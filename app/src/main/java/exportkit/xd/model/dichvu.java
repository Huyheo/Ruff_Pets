package exportkit.xd.model;

public class dichvu {
    private String gia;
    private String title;
    private String mota;
    private String url;
    private int id;

    public dichvu(String gia, String title, String mota, String url, int id) {
        this.gia = gia;
        this.title = title;
        this.mota = mota;
        this.url = url;
        this.id = id;
    }

    public dichvu() {
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
