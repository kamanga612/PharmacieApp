import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("PharmacieApp - Interface");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Barre de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGestion = new JMenu("Gestion");
        JMenuItem itemMedicaments = new JMenuItem("Médicaments");
        JMenuItem itemPrescriptions = new JMenuItem("Prescriptions");

        // Ajout des menus
        menuGestion.add(itemMedicaments);
        menuGestion.add(itemPrescriptions);
        menuBar.add(menuGestion);
        setJMenuBar(menuBar);

        // Contenu principal
        JLabel label = new JLabel("Bienvenue dans l'application de gestion de pharmacie");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Actions
        itemMedicaments.addActionListener(e -> JOptionPane.showMessageDialog(this, "Gestion des médicaments à implémenter."));
        itemPrescriptions.addActionListener(e -> JOptionPane.showMessageDialog(this, "Gestion des prescriptions à implémenter."));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}
