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
import javax.swing.JTextPane;

public class NouvelleFactureEau extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNumeroFacture;
	private JTextField textFieldNumDevis;
	private JTextField textFieldLibelle;
	private JTextField textFieldDateDebut;
	private JTextField textFieldMontant;
	private JTextField textFieldMontantNonDeductible;
	private JTextField textFieldReduction;
	private JTextField textFieldLienPDF;
	private JTextField textFieldDateFin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouvelleFactureEau frame = new NouvelleFactureEau();
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
	public NouvelleFactureEau() {
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
		
		textFieldNumeroFacture = new JTextField();
		textFieldNumeroFacture.setBounds(165, 65, 132, 20);
		contentPane.add(textFieldNumeroFacture);
		textFieldNumeroFacture.setColumns(10);
		
		textFieldNumDevis = new JTextField();
		textFieldNumDevis.setColumns(10);
		textFieldNumDevis.setBounds(165, 96, 132, 20);
		contentPane.add(textFieldNumDevis);
		
		JLabel lblLabelNumeroFacture = new JLabel("* Numéro de facture :");
		lblLabelNumeroFacture.setBounds(37, 65, 132, 14);
		contentPane.add(lblLabelNumeroFacture);
		
		JLabel lblLabelNumeroDevis = new JLabel("Numéro de devis :");
		lblLabelNumeroDevis.setBounds(37, 96, 132, 14);
		contentPane.add(lblLabelNumeroDevis);
		
		textFieldLibelle = new JTextField();
		textFieldLibelle.setColumns(10);
		textFieldLibelle.setBounds(165, 127, 132, 20);
		contentPane.add(textFieldLibelle);
		
		JLabel lblLabelLibelle = new JLabel("Libellé  :");
		lblLabelLibelle.setBounds(37, 127, 132, 14);
		contentPane.add(lblLabelLibelle);
		
		textFieldDateDebut = new JTextField();
		textFieldDateDebut.setColumns(10);
		textFieldDateDebut.setBounds(165, 158, 68, 20);
		contentPane.add(textFieldDateDebut);
		
		JLabel lblLabelDateDebut = new JLabel("Date de début  :");
		lblLabelDateDebut.setBounds(37, 158, 132, 14);
		contentPane.add(lblLabelDateDebut);
		
		JLabel lblLabelMontant = new JLabel("Montant  :");
		lblLabelMontant.setBounds(37, 261, 132, 14);
		contentPane.add(lblLabelMontant);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(165, 261, 132, 20);
		contentPane.add(textFieldMontant);
		
		JLabel lblLabelMontantNonDeductible = new JLabel("Montant  non déductible :");
		lblLabelMontantNonDeductible.setBounds(37, 292, 132, 14);
		contentPane.add(lblLabelMontantNonDeductible);
		
		textFieldMontantNonDeductible = new JTextField();
		textFieldMontantNonDeductible.setColumns(10);
		textFieldMontantNonDeductible.setBounds(165, 292, 132, 20);
		contentPane.add(textFieldMontantNonDeductible);
		
		JLabel lblLabelReduction = new JLabel("Réduction :");
		lblLabelReduction.setBounds(37, 322, 132, 14);
		contentPane.add(lblLabelReduction);
		
		textFieldReduction = new JTextField();
		textFieldReduction.setColumns(10);
		textFieldReduction.setBounds(165, 322, 132, 20);
		contentPane.add(textFieldReduction);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setBounds(307, 384, 132, 23);
		btnAjouter.addActionListener(this);
		contentPane.add(btnAjouter);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(49, 384, 132, 23);
		btnAnnuler.addActionListener(this);
		contentPane.add(btnAnnuler);
		
		JLabel lblFactureDEau = new JLabel("Nouvelle facture d'eau");
		lblFactureDEau.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFactureDEau.setBounds(37, 0, 260, 41);
		contentPane.add(lblFactureDEau);
		
		textFieldLienPDF = new JTextField();
		textFieldLienPDF.setColumns(10);
		textFieldLienPDF.setBounds(165, 350, 235, 20);
		contentPane.add(textFieldLienPDF);
		
		JLabel lblLabelLienPDF = new JLabel("Lien pdf  :");
		lblLabelLienPDF.setBounds(37, 350, 132, 14);
		contentPane.add(lblLabelLienPDF);
		
		JLabel lblLabelDateFin = new JLabel("Date de fin :");
		lblLabelDateFin.setBounds(255, 161, 93, 14);
		contentPane.add(lblLabelDateFin);
		
		textFieldDateFin = new JTextField();
		textFieldDateFin.setColumns(10);
		textFieldDateFin.setBounds(332, 158, 68, 20);
		contentPane.add(textFieldDateFin);
		
		JLabel lblLabelDescription = new JLabel("Description  :");
		lblLabelDescription.setBounds(37, 191, 132, 14);
		contentPane.add(lblLabelDescription);
		
		JTextPane textPaneDescription = new JTextPane();
		textPaneDescription.setBounds(165, 189, 235, 60);
		contentPane.add(textPaneDescription);
		
		JLabel lblLabelEntreprise = new JLabel("* Entreprise :");
		lblLabelEntreprise.setBounds(37, 40, 132, 14);
		contentPane.add(lblLabelEntreprise);
		
		JComboBox comboBoxEntreprise = new JComboBox();
		comboBoxEntreprise.setBounds(165, 36, 132, 22);
		contentPane.add(comboBoxEntreprise);
		
		JButton btnNouvelleEntreprise = new JButton("Nouvelle entreprise");
		btnNouvelleEntreprise.setBounds(307, 36, 132, 23);
		btnNouvelleEntreprise.addActionListener(this);
		contentPane.add(btnNouvelleEntreprise);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Nouvelle entreprise":
				this.dispose();
				new NouvelleEntreprise().setVisible(true);
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
				new NouvelleFactureEau().setVisible(true);
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