import java.sql.*;
import java.util.Scanner;

public class MedicamentManager {
    private final String url = "jdbc:mysql://localhost:3306/pharmacie";
    private final String user = "root"; // ton utilisateur MySQL
    private final String password = "D@n!co"; // ton mot de passe MySQL

    // Ajouter un médicament
    public void ajouterMedicament(String nom, int quantite, double prix) {
        String sql = "INSERT INTO medicament (nom, quantite, prix) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);
            pstmt.setInt(2, quantite);
            pstmt.setDouble(3, prix);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Médicament ajouté avec succès !");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    // Afficher tous les médicaments
    public void afficherMedicaments() {
        String sql = "SELECT * FROM medicament";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Liste des Médicaments ---");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                int quantite = rs.getInt("quantite");
                double prix = rs.getDouble("prix");

                System.out.println("ID: " + id + ", Nom: " + nom + ", Quantité: " + quantite + ", Prix: " + prix + "€");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
    }

    // Modifier un médicament
    public void modifierMedicament(int id, String nouveauNom, int nouvelleQuantite, double nouveauPrix) {
        String sql = "UPDATE medicament SET nom = ?, quantite = ?, prix = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nouveauNom);
            pstmt.setInt(2, nouvelleQuantite);
            pstmt.setDouble(3, nouveauPrix);
            pstmt.setInt(4, id);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Médicament modifié avec succès !");
            } else {
                System.out.println("Aucun médicament trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer un médicament
    public void supprimerMedicament(int id) {
        String sql = "DELETE FROM medicament WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Médicament supprimé avec succès !");
            } else {
                System.out.println("Aucun médicament trouvé avec cet ID.");
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
