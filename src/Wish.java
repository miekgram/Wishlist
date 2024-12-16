public class Wish {
    String nameOfItem;
    String description;
    float price;
    String category;
    String URL;
    boolean isReservated;


    public Wish(String nameOfItem, String description, float price, String category, String URL, boolean isReservated) {
        this.nameOfItem = nameOfItem;
        this.description = description;
        this.price = price;
        this.category = category;
        this.URL = URL;
        this.isReservated = isReservated;
    }

    @Override
    public String toString() {
        return nameOfItem +", "+ price+",- , "+ description+", "+ URL;

    }

    public boolean getIsReservated(){
        return isReservated;
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



