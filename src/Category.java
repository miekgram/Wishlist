import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;




public class Category {
    private Statement stmt;

    public void houseHold(Connection conn, TextUI textUI) {
        String sql = "SELECT * FROM Wishes WHERE categories = 'Household'";
        // String sql = "SELECT * FROM Wishes";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    textUI.displayMsg(rs.getString(i)); //Printer alle kolonner
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }




    public void personalCare(Connection conn, TextUI textUI) {
        String sql = "SELECT * FROM Wishes WHERE categories = 'Personalcare'";
        // String sql = "SELECT * FROM Wishes";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    textUI.displayMsg(rs.getString(i)); //Printer alle kolonner
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }



    public void fashion(Connection conn, TextUI textUI) {
        String sql = "SELECT * FROM Wishes WHERE categories = 'Fashion'";
        // String sql = "SELECT * FROM Wishes";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    textUI.displayMsg(rs.getString(i)); //Printer alle kolonner
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }



    public void children(Connection conn, TextUI textUI) {
        String sql = "SELECT * FROM Wishes WHERE categories = 'Children'";
        // String sql = "SELECT * FROM Wishes";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    textUI.displayMsg(rs.getString(i)); //Printer alle kolonner
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }



    public void hobby(Connection conn, TextUI textUI) {
        String sql = "SELECT * FROM Wishes WHERE categories = 'Hobby'";
        // String sql = "SELECT * FROM Wishes";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    textUI.displayMsg(rs.getString(i)); //Printer alle kolonner
                }
            }

        } catch (SQLException e) {
            e.getMessage();
        }

    }
}





