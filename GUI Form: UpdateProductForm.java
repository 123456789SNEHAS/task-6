package yourpackage;

import java.sql.*;
import javax.swing.JOptionPane;

public class UpdateProductForm extends javax.swing.JFrame {

    public UpdateProductForm() {
        initComponents();
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String id = productIdField.getText();
        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "SELECT * FROM products WHERE id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nameField.setText(rs.getString("name"));
                categoryField.setText(rs.getString("category"));
                priceField.setText(String.valueOf(rs.getDouble("price")));
                quantityField.setText(String.valueOf(rs.getInt("quantity")));
                descriptionArea.setText(rs.getString("description"));
            } else {
                JOptionPane.showMessageDialog(this, "Product Not Found!");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String id = productIdField.getText();
        String name = nameField.getText();
        String category = categoryField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();
        String description = descriptionArea.getText();

        if (name.isEmpty() || category.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.");
            return;
        }

        Connection conn = DatabaseConnection.getConnection();
        try {
            String query = "UPDATE products SET name=?, category=?, price=?, quantity=?, description=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.setDouble(3, Double.parseDouble(price));
            pst.setInt(4, Integer.parseInt(quantity));
            pst.setString(5, description);
            pst.setString(6, id);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Product Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update Failed!");
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
