import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Wishlist {
    User user;
    TextUI textUI;
    Statement stmt;
    Connection conn;


    public void editWishlist(User user) {
        // måske HashMap<String, ArrayList<String>> wishlists = user.getWishlists();
        // //(database select * where wishlistid = (brugerindput))
        //gerne komme ud som punktform i terminal
    }


    public void addWish(User user, int wishlistID) {
       /* if(!user.wishlist.contains(wish.getnameOfItem())){
            user.wishlist.add(wish.getnameOfItem());
            textUI.displayMsg(wish.getnameOfItem() + " has been added to your list.\n");

        }else{
            textUI.displayMsg(wish.getnameOfItem() + " is already in your list.\n");

    }*/
        String nameOfProduct = textUI.promptText("Type name of product: ");
        int price = Integer.parseInt(textUI.promptText("Type the price of the product:"));
        String store = textUI.promptText("In what store can u buy this product? ");
        String describtion = textUI.promptText("Describtion (size, color, etc.?): ");
        String category = textUI.promptText("What category matches this product? Hobby, Children, Clothes, Home, Beauty? ");
        String link = textUI.promptText("A link for a website: ");

        //Tilføjer info til wishes
        String sql = "INSERT INTO wishes (nameOfProducts, prices, store, descriptions, links, Categories, WishlistID)";
        sql += "VALUES ("+nameOfProduct+", "+price+", "+store+", "+describtion+", "+ link+", "+category +", "+ wishlistID+");";
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);


        } catch (SQLException e) {
            e.getSQLState();
        }
    }

    public void removeWish(User user) {
        /*if(user.wishlist.contains(wish.getnameOfItem())){
            user.wishlist.remove(wish.getnameOfItem());
            textUI.displayMsg(wish.getnameOfItem() + " has been removed from your wishlist list.\n");

        }else{
            textUI.displayMsg(wish.getnameOfItem() + " is not on your list.\n");
        }*/
        String deleteWish = textUI.promptText("Write the exact name of the product you want to delete from your wishlist: ");
        String sql = "DELETE FROM wishes WHERE nameOfProducts = " + deleteWish + ");";
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }



    public void editOrder(User user) {

    }
}
