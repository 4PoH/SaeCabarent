package vue.consultation;


import java.awt.EventQueue;
import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.insertion.NouvelleFactureElectricite;
import vue.insertion.NouvelleFactureEntretien;
import vue.insertion.NouvelleFactureEau;
import vue.insertion.NouveauTravaux;
import vue.insertion.NouvelleChargeSupp;
import vue.insertion.NouvelleLocation;
import vue.insertion.ProtectionJuridique;
import vue.insertion.NouvelleTaxeFonciere;

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

import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JToolBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TravauxEnCours extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TravauxEnCours frame = new TravauxEnCours();
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
	public TravauxEnCours() {
		setTitle("Travaux en cours");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 480);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 64, 946, 269);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"Numero SIREN", "numero Facture", "numero Devis", "libelle", "date debut", "date fin", "detail", "montant payer", "montant non deductible", "reduction", "pdf"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel TitreTravauxC = new JLabel("Travaux en cours");
		TitreTravauxC.setFont(new Font("Tahoma", Font.BOLD, 20));
		TitreTravauxC.setBounds(10, 10, 195, 32);
		contentPane.add(TitreTravauxC);
		
		JButton btnCharger = new JButton("Charger");
		btnCharger.addActionListener(this);
		btnCharger.setBounds(87, 372, 85, 21);
		contentPane.add(btnCharger);
		
		JButton btnInserer = new JButton("Inserer");
		btnInserer.addActionListener(this);
		btnInserer.setBounds(271, 372, 85, 21);
		contentPane.add(btnInserer);
		
		JButton btnMiseJour = new JButton("Mise ?? jour");
		btnMiseJour.addActionListener(this);
		btnMiseJour.setBounds(459, 372, 85, 21);
		contentPane.add(btnMiseJour);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(this);
		btnSupprimer.setBounds(640, 372, 85, 21);
		contentPane.add(btnSupprimer);
		
		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(this);
		btnAnnuler.setBounds(813, 372, 85, 21);
		contentPane.add(btnAnnuler);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Charger" :
			break;
			
			case "Inserer" :
				this.dispose();
				new NouveauTravaux().setVisible(true);
				break;
			
			case "Mise ?? Jour":
				break;
			
			case "Supprimer":
				break;
			
			case "Annuler":
				this.dispose();
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
				new NouveauTravaux().setVisible(true);
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