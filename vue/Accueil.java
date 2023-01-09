package vue;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import vue.consultation.AncienneLocation;
import vue.consultation.AnciensTravaux;
import vue.consultation.Impositions;
import vue.consultation.LocationEnCours;
import vue.consultation.TravauxEnCours;
import vue.insertion.NouvelleFactureElectricite;
import vue.insertion.NouvelleFactureEntretien;
import vue.insertion.NouvelleFactureEau;
import vue.insertion.NouveauTravaux;
import vue.insertion.NouvelleChargeSupp;
import vue.insertion.NouvelleLocation;
import vue.insertion.ProtectionJuridique;
import vue.insertion.NouvelleTaxeFonciere;

import javax.swing.ListSelectionModel;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

import Requetes.Requete;

public class Accueil extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JTable tableAcceuilBati;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accueil frame = new Accueil();
					frame.setVisible(true);
					} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	
	private ResultSet RequeteTableauBati() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select distinct lieuxdelocations.adresse, lieuxdelocations.codepostal, lieuxdelocations.libelle, locataire.nom, locataire.prenom\r\n"
							+ "from loue, lieuxdelocations, contrat, relie, locataire\r\n"
							+ "where loue.idlogement = lieuxdelocations.idlogement\r\n"
							+ "and loue.idcontrat = contrat.idcontrat\r\n"
							+ "and contrat.idcontrat = relie.idcontrat\r\n"
							+ "and relie.idlocataire = locataire.idlocataire\r\n"
							+ "and to_date(ADD_MONTHS(SYSDATE, -2), 'mm-yyyy') < to_date(loue.datelocation, 'mm-yyyy')";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	public Accueil() {
		setTitle("Accueil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 480);
		
		JMenuBar menuBarTop = new JMenuBar();
		menuBarTop.setMargin(new Insets(5, 5, 5, 5));
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
		scrollPane.setBounds(10, 10, 680, 300);
		contentPane.add(scrollPane);
		
		// Header de JTable 
	    final String[] columns = {"Adresse", "Argent", ""};
		// Créer le modèle de table
	    final DefaultTableModel model = new DefaultTableModel(columns, 0);
		tableAcceuilBati = new JTable(model);
		tableAcceuilBati.addMouseListener(this);
		tableAcceuilBati.setRowSelectionAllowed(false);
		tableAcceuilBati.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAcceuilBati.setSurrendersFocusOnKeystroke(true);
				
		try {
			ResultSet rsEnsBati = RequeteTableauBati();
			int i = 0;
			rsEnsBati.next();
			while ( i < rsEnsBati.getRow()) {
				String adresse = rsEnsBati.getString("ADRESSE");
				adresse += ", ";
				adresse += rsEnsBati.getString("CODEPOSTAL");
				adresse += ", ";
				adresse += rsEnsBati.getString("LIBELLE");
				adresse += ", ";
				adresse += rsEnsBati.getString("NOM");
				adresse += ", ";
				adresse += rsEnsBati.getString("PRENOM");
				adresse += ", ";
				model.addRow(new String[]{adresse,"",""});
				rsEnsBati.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		tableAcceuilBati.getColumnModel().getColumn(0).setPreferredWidth(560);
		tableAcceuilBati.getColumnModel().getColumn(1).setPreferredWidth(120);
		tableAcceuilBati.getColumnModel().getColumn(2).setPreferredWidth(40);
		scrollPane.setViewportView(tableAcceuilBati);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(10, 380, 680, 30);
		contentPane.add(menuBar);
		
		JButton ButtonRegimeFoncier = new JButton("Regime foncier");
		menuBar.add(ButtonRegimeFoncier);
		
		JLabel LabelTotal = new JLabel("Total : ");
		LabelTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		menuBar.add(LabelTotal);
		
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
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
	public void mouseClicked(MouseEvent e) {
		this.dispose();
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	
}