package truthdare.developer.com.app.beans;

public class HomeCardBean {
    String category,catId;
    int[] color;

    public HomeCardBean(String category,int[] color, String catID) {
        this.category = category;
        this.color = color;
        this.catId = catID;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    public String getCategory() {

        return category;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
