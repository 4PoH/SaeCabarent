package vue.insertion;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;

import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.consultation.AncienneLocation;
import vue.consultation.AnciensTravaux;
import vue.consultation.Impositions;
import vue.consultation.LocationEnCours;
import vue.consultation.TravauxEnCours;

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;

public class NouvelleLocation extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldDateFac;
	private JTextField textFieldMontant;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouvelleLocation frame = new NouvelleLocation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NouvelleLocation() {
		setTitle("Nouveaux travaux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 480);
		
		JMenuBar menuBarTop = new JMenuBar();
		menuBarTop.setMargin(new Insets(0, 5, 0, 5));
		setJMenuBar(menuBarTop);
		
		JButton ButtonAccueil = new JButton("Accueil");
		ButtonAccueil.addActionListener(this);
		menuBarTop.add(ButtonAccueil);
		
		JMenu MenuLocations = new JMenu("Locations");
		menuBarTop.add(MenuLocations);
		
		JMenuItem MenuItemNouvelleLocation = new JMenuItem("Nouvelle location");
		MenuItemNouvelleLocation.addActionListener(this);
		MenuLocations.add(MenuItemNouvelleLocation);
		
		JMenuItem MenuItemLocationEnCour = new JMenuItem("Locations en cours");
		MenuItemLocationEnCour.addActionListener(this);
		MenuLocations.add(MenuItemLocationEnCour);
		
		JMenuItem MenuItemAncienneLocation = new JMenuItem("Anciennes location");
		MenuItemAncienneLocation.addActionListener(this);
		MenuLocations.add(MenuItemAncienneLocation);
		
		JMenu MenuCharges = new JMenu("Charges");
		menuBarTop.add(MenuCharges);
		
		JMenuItem MenuItemFactureEau = new JMenuItem("Facture d'eau");
		MenuItemFactureEau.addActionListener(this);
		MenuCharges.add(MenuItemFactureEau);
		
		JMenuItem MenuItemEntretien = new JMenuItem("Entretien");
		MenuItemEntretien.addActionListener(this);
		MenuCharges.add(MenuItemEntretien);
		
		JMenuItem MenuItemTaxeFonciere = new JMenuItem("Taxe fonciere");
		MenuItemTaxeFonciere.addActionListener(this);
		MenuCharges.add(MenuItemTaxeFonciere);
		
		JMenuItem MenuItemElectricite = new JMenuItem("Electricite");
		MenuItemElectricite.addActionListener(this);
		MenuCharges.add(MenuItemElectricite);
		
		JMenuItem MenuItemProtectionJuridique = new JMenuItem("Protection juridique");
		MenuItemProtectionJuridique.addActionListener(this);
		MenuCharges.add(MenuItemProtectionJuridique);
		
		JMenuItem MenuItemAutre = new JMenuItem("Autre");
		MenuItemAutre.addActionListener(this);
		MenuCharges.add(MenuItemAutre);
		
		JMenu MenuTravaux = new JMenu("Travaux");
		menuBarTop.add(MenuTravaux);
		
		JMenuItem MenuItemNouveauTravaux = new JMenuItem("Nouveaux travaux");
		MenuItemNouveauTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemNouveauTravaux);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Travaux en cours");
		mntmNewMenuItem.addActionListener(this);
		MenuTravaux.add(mntmNewMenuItem);
		
		JMenuItem MenuItemAncienTravaux = new JMenuItem("Anciens travaux");
		MenuItemAncienTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemAncienTravaux);
		
		JMenu MenuParametres = new JMenu("Parametres");
		menuBarTop.add(MenuParametres);
		
		JMenuItem MenuItemInfosBailleur = new JMenuItem("Informations bailleur");
		MenuItemInfosBailleur.addActionListener(this);
		MenuParametres.add(MenuItemInfosBailleur);
		
		JMenuItem MenuItemIRL = new JMenuItem("IRL");
		MenuItemIRL.addActionListener(this);
		MenuParametres.add(MenuItemIRL);
		
		JMenu MenuGenerer = new JMenu("Generer documents");
		menuBarTop.add(MenuGenerer);
		
		JMenuItem MenuItemQuittance = new JMenuItem("Quittances");
		MenuItemQuittance.addActionListener(this);
		MenuGenerer.add(MenuItemQuittance);
		
		JMenuItem MenuItemImpositions = new JMenuItem("Impositions");
		MenuItemImpositions.addActionListener(this);
		MenuGenerer.add(MenuItemImpositions);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldDateFac = new JTextField();
		textFieldDateFac.setColumns(10);
		textFieldDateFac.setBounds(166, 91, 26, 20);
		contentPane.add(textFieldDateFac);
		
		JLabel lblLabelContrat = new JLabel("* Contrat :");
		lblLabelContrat.setBounds(24, 63, 132, 14);
		contentPane.add(lblLabelContrat);
		
		JLabel lblLabelDateFac = new JLabel("Nombre de locataire :");
		lblLabelDateFac.setBounds(24, 94, 132, 14);
		contentPane.add(lblLabelDateFac);
		
		JLabel lblLabelLibelle = new JLabel("* Locataire(s)  :");
		lblLabelLibelle.setBounds(24, 125, 132, 14);
		contentPane.add(lblLabelLibelle);
		
		JLabel lblLabelLienPDF = new JLabel("* Logement  :");
		lblLabelLienPDF.setBounds(24, 156, 132, 14);
		contentPane.add(lblLabelLienPDF);
		
		JLabel lblLabelMontant = new JLabel("Montant du loyer  :");
		lblLabelMontant.setBounds(24, 184, 132, 14);
		contentPane.add(lblLabelMontant);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(166, 181, 132, 20);
		contentPane.add(textFieldMontant);
		
		JLabel lblLabelMontantNonDeductible = new JLabel("Montant des charges :");
		lblLabelMontantNonDeductible.setBounds(24, 215, 132, 14);
		contentPane.add(lblLabelMontantNonDeductible);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(166, 212, 132, 20);
		contentPane.add(textField);
		
		JLabel lblLabelReduction = new JLabel("Participation entretien :");
		lblLabelReduction.setBounds(24, 245, 132, 14);
		contentPane.add(lblLabelReduction);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(166, 242, 132, 20);
		contentPane.add(textField_1);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setBounds(307, 384, 132, 23);
		btnAjouter.addActionListener(this);
		contentPane.add(btnAjouter);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(49, 384, 132, 23);
		btnAnnuler.addActionListener(this);
		contentPane.add(btnAnnuler);
		
		JLabel lblNouvelleLocation = new JLabel("Nouvelle Location");
		lblNouvelleLocation.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNouvelleLocation.setBounds(24, 0, 307, 41);
		contentPane.add(lblNouvelleLocation);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(166, 122, 132, 22);
		contentPane.add(comboBox_1);
		
		JButton btnNouveauLocataire = new JButton("Nouveau Locataire");
		btnNouveauLocataire.setBounds(308, 121, 132, 23);
		btnNouveauLocataire.addActionListener(this);
		contentPane.add(btnNouveauLocataire);
		
		JButton btnNouveauContrat = new JButton("Nouveau Contrat");
		btnNouveauContrat.setBounds(308, 59, 132, 23);
		btnNouveauContrat.addActionListener(this);
		contentPane.add(btnNouveauContrat);
		
		JComboBox comboBox_1_1 = new JComboBox();
		comboBox_1_1.setBounds(166, 59, 132, 22);
		contentPane.add(comboBox_1_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(166, 150, 132, 22);
		contentPane.add(comboBox_2);
		
		JButton btnNouveauLogement = new JButton("Nouveau Logement");
		btnNouveauLogement.setBounds(308, 150, 132, 23);
		btnNouveauLogement.addActionListener(this);
		contentPane.add(btnNouveauLogement);
		
		JLabel lblParticipationlctricitCommune = new JLabel("Participation éléctricité :");
		lblParticipationlctricitCommune.setBounds(24, 273, 132, 14);
		contentPane.add(lblParticipationlctricitCommune);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(166, 270, 132, 20);
		contentPane.add(textField_2);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case"Nouveau Locataire":
				this.dispose();
				new NouveauLocataire().setVisible(true);
				break;
			case"Nouveau Contrat":
				this.dispose();
				new NouveauContrat().setVisible(true);
				break;
			case"Nouveau Logement":
				this.dispose();
				new NouveauLogement().setVisible(true);
				break;
			case "Annuler":
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Ajouter":
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Accueil":
				this.dispose();
				new Accueil().setVisible(true);
				break;
				
			case "Anciennes location":
				this.dispose();
				new AncienneLocation().setVisible(true);
				break;
			
			case "Anciens travaux":
				this.dispose();
				new AnciensTravaux().setVisible(true);
				break;
				
			case "Electricite":
				this.dispose();
				new NouvelleFactureElectricite().setVisible(true);
				break;
				
			case "Entretien":
				this.dispose();
				new NouvelleFactureEntretien().setVisible(true);
				break;
				
			case "Facture d'eau":
				this.dispose();
				new NouvelleFactureEau().setVisible(true);
				break;
			
			case "Impositions":
				this.dispose();
				new Impositions().setVisible(true);
				break;
				
			case "Informations bailleur":
				this.dispose();
				new InformationsBailleur().setVisible(true);
				break;
				
			case "IRL":
				this.dispose();
				new IRL().setVisible(true);
				break;
				
			case "Locations en cours":
				this.dispose();
				new LocationEnCours().setVisible(true);
				break;
			
			case "Nouveaux travaux":
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
				
			case "Nouvelle location":
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
				
			case "Protection juridique":
				this.dispose();
				new ProtectionJuridique().setVisible(true);
				break;
				
			case "Quittances":
				this.dispose();
				new Quittances().setVisible(true);
				break;
			
			case "Taxe fonciere":
				this.dispose();
				new NouvelleTaxeFonciere().setVisible(true);
				break;
			case "Autre":
				this.dispose();
				new NouvelleChargeSupp().setVisible(true);
				break;
			case "Travaux en cours":
				this.dispose();
				new TravauxEnCours().setVisible(true);
				break;
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}