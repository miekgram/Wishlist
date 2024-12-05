public class Wish {
    String nameOfItem;
    String description;
    float price;
    String category;
    String URL;

    public Wish(String nameOfItem, String description, float price, String category, String URL) {
        this.nameOfItem = nameOfItem;
        this.description = description;
        this.price = price;
        this.category = category;
        this.URL = URL;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getURL() {
        return URL;
    }
}



