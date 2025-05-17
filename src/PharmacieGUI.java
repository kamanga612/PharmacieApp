import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class PharmacieGUI extends JFrame {
    private final MedicamentManager manager = new MedicamentManager();
    private final JTable table;
    private final DefaultTableModel tableModel;

    public PharmacieGUI() {
        setTitle("Gestion de Pharmacie");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Boutons
        JButton btnAjouter = new JButton("Ajouter");
        JButton btnAfficher = new JButton("Afficher");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nom", "Quantité", "Prix"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel de boutons
        JPanel panel = new JPanel();
        panel.add(btnAjouter);
        panel.add(btnAfficher);
        panel.add(btnModifier);
        panel.add(btnSupprimer);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Événements
        btnAjouter.addActionListener(e -> ajouterMedicament());
        btnAfficher.addActionListener(e -> afficherMedicaments());
        btnModifier.addActionListener(e -> modifierMedicament());
        btnSupprimer.addActionListener(e -> supprimerMedicament());
    }

    private void ajouterMedicament() {
        JTextField nomField = new JTextField();
        JTextField quantiteField = new JTextField();
        JTextField prixField = new JTextField();

        Object[] message = {
                "Nom:", nomField,
                "Quantité:", quantiteField,
                "Prix:", prixField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Ajouter Médicament", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nom = nomField.getText();
            int quantite = Integer.parseInt(quantiteField.getText());
            double prix = Double.parseDouble(prixField.getText());
            manager.ajouterMedicament(nom, quantite, prix);
            afficherMedicaments();
        }
    }

    private void afficherMedicaments() {
        tableModel.setRowCount(0); // Réinitialiser les lignes
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacie", "root", "D@n!co");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM medicament")) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("nom"));
                row.add(rs.getInt("quantite"));
                row.add(rs.getDouble("prix"));
                tableModel.addRow(row);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur d'affichage : " + ex.getMessage());
        }
    }

    private void modifierMedicament() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            String nom = (String) tableModel.getValueAt(row, 1);
            int quantite = (int) tableModel.getValueAt(row, 2);
            double prix = (double) tableModel.getValueAt(row, 3);

            JTextField nomField = new JTextField(nom);
            JTextField quantiteField = new JTextField(String.valueOf(quantite));
            JTextField prixField = new JTextField(String.valueOf(prix));

            Object[] message = {
                    "Nom:", nomField,
                    "Quantité:", quantiteField,
                    "Prix:", prixField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Modifier Médicament", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nouveauNom = nomField.getText();
                int nouvelleQuantite = Integer.parseInt(quantiteField.getText());
                double nouveauPrix = Double.parseDouble(prixField.getText());

                manager.modifierMedicament(id, nouveauNom, nouvelleQuantite, nouveauPrix);
                afficherMedicaments();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un médicament à modifier.");
        }
    }

    private void supprimerMedicament() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Supprimer", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.supprimerMedicament(id);
                afficherMedicaments();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un médicament à supprimer.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PharmacieGUI().setVisible(true));
    }
}
