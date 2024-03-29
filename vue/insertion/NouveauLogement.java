package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Requetes.RequeteInsertion;
import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.consultation.EntretiensAnciens;
import vue.consultation.EntretiensEnCours;
import vue.consultation.FacturesEauAnciennes;
import vue.consultation.FacturesEauEnCours;
import vue.consultation.FacturesElectriciteAnciennes;
import vue.consultation.FacturesElectriciteEnCours;
import vue.consultation.Impositions;
import vue.consultation.LocatairesAnciens;
import vue.consultation.LocatairesEnCours;
import vue.consultation.LocationsAnciennes;
import vue.consultation.LocationsEnCours;
import vue.consultation.ProtectionJuridique;
import vue.consultation.TaxeFonciere;
import vue.consultation.TravauxAnciens;
import vue.consultation.TravauxEnCours;

public class NouveauLogement extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNbPiece;
	private JTextField textFieldLibelle;
	private JTextField textFieldSurface;
	private JComboBox<String> comboBoxBatiment;
	private String SelectedComboBatimentCPAdresse;
	private JComboBox<String> comboBoxTypeReseau;
	private String SelectedComboTypeReseau;
	private JComboBox<String> comboBoxModaliteElectricite;
	private String SelectedComboModElec;
	private JComboBox<String> comboBoxModaliteEauChaude;
	private String SelectedComboModEau;
	private NouveauLogement frame;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauLogement frame = new NouveauLogement();
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
	public NouveauLogement() {
		setTitle("Nouveaux travaux");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 480);
		
		JMenuBar menuBarTop = new JMenuBar();
		menuBarTop.setMargin(new Insets(5, 5, 5, 5));
		setJMenuBar(menuBarTop);
		
		JButton ButtonAccueil = new JButton("Accueil");
		ButtonAccueil.addActionListener(this);
		menuBarTop.add(ButtonAccueil);
		
		JMenu MenuLocations = new JMenu("Locations");
		MenuLocations.addActionListener(this);
		menuBarTop.add(MenuLocations);
		
		JMenuItem MenuItemAncienneLocation = new JMenuItem("Anciennes locations");
		MenuItemAncienneLocation.addActionListener(this);
		MenuLocations.add(MenuItemAncienneLocation);
		
		JMenuItem MenuItemLocationEnCour = new JMenuItem("Locations en cours");
		MenuItemLocationEnCour.addActionListener(this);
		MenuLocations.add(MenuItemLocationEnCour);
		
		JMenuItem MenuItemNouvelleLocation = new JMenuItem("Nouvelles locations");
		MenuItemNouvelleLocation.addActionListener(this);
		MenuLocations.add(MenuItemNouvelleLocation);
		
		JMenuItem MenuItemAnciensLocataires = new JMenuItem("Anciens locataires");
		MenuLocations.add(MenuItemAnciensLocataires);
		
		JMenuItem MenuItemLocatairesEnCours = new JMenuItem("Locataires en cours");
		MenuLocations.add(MenuItemLocatairesEnCours);
		
		JMenu MenuCharges = new JMenu("Charges");
		MenuCharges.addActionListener(this);
		menuBarTop.add(MenuCharges);
		
		JMenu MenuEntretiens = new JMenu("Entretiens");
		MenuEntretiens.addActionListener(this);
		MenuCharges.add(MenuEntretiens);
		
		JMenuItem MenuItemAnciensEntretiens = new JMenuItem("Anciens entretiens");
		MenuItemAnciensEntretiens.addActionListener(this);
		MenuItemAnciensEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiens);
		
		JMenuItem mntmEntretiensEnCours = new JMenuItem("Entretiens en cours");
		mntmEntretiensEnCours.addActionListener(this);
		mntmEntretiensEnCours.setSelected(true);
		MenuEntretiens.add(mntmEntretiensEnCours);
		
		JMenuItem MenuItemNouveauxEntretiens = new JMenuItem("Nouveaux entretiens");
		MenuItemNouveauxEntretiens.addActionListener(this);
		
		JMenuItem MenuItemAnciensEntretiensPartiesCommunes = new JMenuItem("Anciens entretiens parties communes");
		MenuItemAnciensEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiensPartiesCommunes);
		
		JMenuItem MenuItemEntretiensPartiesCommunes = new JMenuItem("Entretiens parties communes en cours");
		MenuItemEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemEntretiensPartiesCommunes);
		MenuItemNouveauxEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemNouveauxEntretiens);
		
		JMenu MenuFacturesEau = new JMenu("Factures d'eau");
		MenuFacturesEau.addActionListener(this);
		MenuCharges.add(MenuFacturesEau);
		
		JMenuItem MenuItemAnciennesFacturesEau = new JMenuItem("Anciennes factures d'eau");
		MenuItemAnciennesFacturesEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemAnciennesFacturesEau);
		
		JMenuItem MenuItemFacturesEauEnCours = new JMenuItem("Factures d'eau en cours");
		MenuItemFacturesEauEnCours.addActionListener(this);
		MenuFacturesEau.add(MenuItemFacturesEauEnCours);
		
		JMenuItem MenuItemNouvellesFactureEau = new JMenuItem("Nouvelles factures d'eau");
		MenuItemNouvellesFactureEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemNouvellesFactureEau);
		
		JMenu MenuElectricite = new JMenu("Electricité");
		MenuElectricite.addActionListener(this);
		MenuCharges.add(MenuElectricite);
		
		JMenuItem MenuItemAnciennesFacturesElectricite = new JMenuItem("Anciennes factures d'électricité");
		MenuItemAnciennesFacturesElectricite.addActionListener(this);
		MenuElectricite.add(MenuItemAnciennesFacturesElectricite);
		
		JMenuItem mntmFacturesDlectricitEn = new JMenuItem("Factures d'électricité en cours");
		mntmFacturesDlectricitEn.addActionListener(this);
		MenuElectricite.add(mntmFacturesDlectricitEn);
		
		JMenuItem MenuItemNouvellesFacturesElectricite = new JMenuItem("Nouvelles factures d'électricité");
		MenuElectricite.add(MenuItemNouvellesFacturesElectricite);
		MenuItemNouvellesFacturesElectricite.addActionListener(this);
		
		JMenu MenuTaxesFoncieres = new JMenu("Taxes foncières");
		MenuTaxesFoncieres.addActionListener(this);
		MenuCharges.add(MenuTaxesFoncieres);
		
		JMenuItem MenuItemConsultationTaxesFonciere = new JMenuItem("Consultation taxes foncières");
		MenuItemConsultationTaxesFonciere.addActionListener(this);
		MenuTaxesFoncieres.add(MenuItemConsultationTaxesFonciere);
		
		JMenuItem MenuItemNouvellesTaxesFonciere = new JMenuItem("Nouvelles taxes foncières");
		MenuItemNouvellesTaxesFonciere.addActionListener(this);
		MenuTaxesFoncieres.add(MenuItemNouvellesTaxesFonciere);
		
		JMenu MenuProtectionJuridique = new JMenu("Protection juridique");
		MenuProtectionJuridique.addActionListener(this);
		MenuCharges.add(MenuProtectionJuridique);
		
		JMenuItem MenuItemConsultationProtectionsJuridiques = new JMenuItem("Consultation protection juridique");
		MenuProtectionJuridique.add(MenuItemConsultationProtectionsJuridiques);
		MenuItemConsultationProtectionsJuridiques.addActionListener(this);
		
		JMenuItem MenuItemNouvelleProtectionJuridique = new JMenuItem("Nouvelle protection juridique");
		MenuItemNouvelleProtectionJuridique.addActionListener(this);
		MenuProtectionJuridique.add(MenuItemNouvelleProtectionJuridique);
		
		JMenu MenuChargesSupplementaires = new JMenu("Charges supplémentaires");
		MenuChargesSupplementaires.addActionListener(this);
		MenuCharges.add(MenuChargesSupplementaires);
		
		JMenuItem MenuItemConsultationChargesSupplmentaires = new JMenuItem("Consultation charges supplémentaires");
		MenuItemConsultationChargesSupplmentaires.addActionListener(this);
		MenuChargesSupplementaires.add(MenuItemConsultationChargesSupplmentaires);
		
		JMenuItem MenuItemNouvelleChargesSupplmentaires = new JMenuItem("Nouvelle charges supplémentaires");
		MenuItemNouvelleChargesSupplmentaires.addActionListener(this);
		MenuChargesSupplementaires.add(MenuItemNouvelleChargesSupplmentaires);
		
		JMenu MenuTravaux = new JMenu("Travaux");
		menuBarTop.add(MenuTravaux);
		
		JMenuItem MenuItemAncienTravaux = new JMenuItem("Anciens travaux");
		MenuItemAncienTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemAncienTravaux);
		
		JMenuItem MenuItemTravauxEnCours = new JMenuItem("Travaux en cours");
		MenuItemTravauxEnCours.addActionListener(this);
		MenuTravaux.add(MenuItemTravauxEnCours);
		
		JMenuItem MenuItemNouveauTravaux = new JMenuItem("Nouveaux travaux");
		MenuItemNouveauTravaux.addActionListener(this);
		MenuTravaux.add(MenuItemNouveauTravaux);
		
		JMenu MenuParametres = new JMenu("Paramètres");
		menuBarTop.add(MenuParametres);
		
		JMenuItem MenuItemInfosBailleur = new JMenuItem("Informations bailleur");
		MenuItemInfosBailleur.addActionListener(this);
		MenuParametres.add(MenuItemInfosBailleur);
		
		JMenuItem MenuItemIRL = new JMenuItem("IRL");
		MenuItemIRL.addActionListener(this);
		MenuParametres.add(MenuItemIRL);
		
		JMenu MenuGenerer = new JMenu("Générer les documents");
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
		
		textFieldNbPiece = new JTextField();
		textFieldNbPiece.setColumns(10);
		textFieldNbPiece.setBounds(165, 179, 23, 20);
		contentPane.add(textFieldNbPiece);
		
		JLabel LabelSurface = new JLabel("Surface :");
		LabelSurface.setBounds(23, 151, 132, 14);
		contentPane.add(LabelSurface);
		
		JLabel LabelNbPieces = new JLabel("Nombre de pièces :");
		LabelNbPieces.setBounds(23, 182, 132, 14);
		contentPane.add(LabelNbPieces);
		
		JLabel LabelBatiment = new JLabel("* Bâtiment  :");
		LabelBatiment.setBounds(23, 92, 132, 14);
		contentPane.add(LabelBatiment);
		
		JLabel LabelTypeReseau = new JLabel("Type d'accés réseau :");
		LabelTypeReseau.setBounds(23, 214, 132, 14);
		contentPane.add(LabelTypeReseau);
		
		JLabel LabelLibelle = new JLabel("Libellé :");
		LabelLibelle.setBounds(23, 125, 132, 14);
		contentPane.add(LabelLibelle);
		
		textFieldLibelle = new JTextField();
		textFieldLibelle.setColumns(10);
		textFieldLibelle.setBounds(165, 122, 132, 20);
		contentPane.add(textFieldLibelle);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelNouvelleLocation = new JLabel("Nouveau logement");
		LabelNouvelleLocation.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelNouvelleLocation.setBounds(24, 0, 307, 41);
		contentPane.add(LabelNouvelleLocation);
		
		JComboBox comboBoxBatiment = new JComboBox();
		comboBoxBatiment.setBounds(165, 89, 132, 22);
		contentPane.add(comboBoxBatiment);
		comboBoxBatiment.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 10));
		try {
			ResultSet rsBatiCPAdress = RequeteInsertion.RequeteAfficheComboBatiment();
			int i = 0;
			rsBatiCPAdress.next();
			while ( i < rsBatiCPAdress.getRow()) {
				String cp = rsBatiCPAdress.getString("CODEPOSTAL");
				String adresse =  rsBatiCPAdress.getString("ADRESSE");
				comboBoxBatiment.addItem( cp + " - " + adresse );
				rsBatiCPAdress.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		// GET COMBO SELECTED VALUE
		comboBoxBatiment.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        SelectedComboBatimentCPAdresse = (String) jcmbType.getSelectedItem();
		      }
		    });
		
		JButton ButtonNouveauatiment = new JButton("Nouveau Bâtiment");
		ButtonNouveauatiment.setBounds(307, 88, 132, 23);
		ButtonNouveauatiment.addActionListener(this);
		contentPane.add(ButtonNouveauatiment);
		
		textFieldSurface = new JTextField();
		textFieldSurface.setColumns(10);
		textFieldSurface.setBounds(165, 148, 132, 20);
		contentPane.add(textFieldSurface);
		
		JComboBox comboBoxTypeReseau = new JComboBox();
		comboBoxTypeReseau.setBounds(165, 210, 132, 22);
		contentPane.add(comboBoxTypeReseau);
		ArrayList<String> typeReseau = new ArrayList<String>();
		typeReseau.add("Fibre");
		typeReseau.add("ADSL");
		typeReseau.add("Telephone");
		typeReseau.add("Autres...");
		int indexTypeReseau = 0;
		while ( indexTypeReseau < typeReseau.size()) {
			comboBoxTypeReseau.addItem(typeReseau.get(indexTypeReseau));
			indexTypeReseau++;
		}
		comboBoxTypeReseau.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        SelectedComboTypeReseau = (String) jcmbType.getSelectedItem();
		        System.out.println(SelectedComboTypeReseau);
		      }
		    });
		
		JLabel LaelModaliteElectricite = new JLabel("Modalité de l'élétricité :");
		LaelModaliteElectricite.setBounds(23, 248, 132, 14);
		contentPane.add(LaelModaliteElectricite);
		
		JComboBox comboBoxModaliteElectricite = new JComboBox();
		comboBoxModaliteElectricite.setBounds(165, 248, 132, 22);
		contentPane.add(comboBoxModaliteElectricite);
		ArrayList<String> typeModElec = new ArrayList<String>();
		typeModElec.add("Electricité");
		typeModElec.add("Energie 'verte'");
		typeModElec.add("Gaz");
		typeModElec.add("Charbon");
		typeModElec.add("Bois");
		typeModElec.add("Autres");
		int indexTypeModElec = 0;
		while ( indexTypeModElec < typeModElec.size()) {
			comboBoxModaliteElectricite.addItem(typeModElec.get(indexTypeModElec));
			indexTypeModElec++;
		}
		comboBoxModaliteElectricite.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        SelectedComboModElec = (String) jcmbType.getSelectedItem();
		        System.out.println(SelectedComboModElec);
		      }
		    });
		
		JLabel LaelModaliteEauChaude = new JLabel("Modalité de l'eau chaude :");
		LaelModaliteEauChaude.setBounds(23, 285, 132, 14);
		contentPane.add(LaelModaliteEauChaude);
		
		JComboBox comboBoxModaliteEauChaude = new JComboBox();
		comboBoxModaliteEauChaude.setBounds(165, 285, 132, 22);
		contentPane.add(comboBoxModaliteEauChaude);
		ArrayList<String> typeModEau = new ArrayList<String>();
		typeModEau.add("Electricité");
		typeModEau.add("Energie 'verte'");
		typeModEau.add("Gaz");
		typeModEau.add("Charbon");
		typeModEau.add("Bois");
		typeModEau.add("Autres");
		int indexTypeModEau = 0;
		while ( indexTypeModEau < typeModEau.size()) {
			comboBoxModaliteEauChaude.addItem(typeModEau.get(indexTypeModEau));
			indexTypeModEau++;
		}
		comboBoxModaliteEauChaude.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        SelectedComboModEau = (String) jcmbType.getSelectedItem();
		        System.out.println(SelectedComboModEau);
		      }
		    });
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		case "Nouveau Bâtiment":
			new NouveauBati().setVisible(true);
			break;
			case "Ajouter":
				float surfaceLieux = Float.parseFloat(textFieldSurface.getText());
				int nbPiece = Integer.parseInt(textFieldNbPiece.getText());
				String typeReseaux = SelectedComboTypeReseau;
				String libelle = textFieldLibelle.getText();
				String modChauffage = SelectedComboModElec;
				String modProdEau = SelectedComboModEau;
				String adresseLieux = SelectedComboBatimentCPAdresse.substring(8);
				int codepostalLieux = Integer.parseInt(SelectedComboBatimentCPAdresse.substring(0,5));				
				System.out.println(" /" + codepostalLieux + "/ " + adresseLieux + " " );
				try {						
					RequeteInsertion.RequeteInsertLieuLocation(surfaceLieux,nbPiece, typeReseaux, libelle, modChauffage, modProdEau, adresseLieux, codepostalLieux);
					JOptionPane.showMessageDialog(frame, "Logement " + libelle + " au " + adresseLieux + " inséré.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}				
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Accueil":
				this.dispose();
				new Accueil().setVisible(true);
				break;
				
			case "Anciennes locations":
				this.dispose();
				new LocationsAnciennes().setVisible(true);
				break;
				
			case "Locations en cours":
				this.dispose();
				new LocationsEnCours().setVisible(true);
				break;
				
			case "Nouvelles locations":
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
			
			case "Anciens locataires":
				this.dispose();
				new LocatairesAnciens().setVisible(true);
				break;
				
			case "Locataires en cours":
				this.dispose();
				new LocatairesEnCours().setVisible(true);
				break;
			
			case "Anciens entretiens":
				this.dispose();
				new EntretiensAnciens().setVisible(true);
				break;
				
			case "Entretiens en cours":
				this.dispose();
				new EntretiensEnCours().setVisible(true);
				break;
				
			case "Nouveaux entretiens":
				this.dispose();
				new NouveauEntretien().setVisible(true);
				break;
				
			case "Anciennes factures d'eau":
				this.dispose();
				new FacturesEauAnciennes().setVisible(true);
				break;
				
			case "Factures d'eau en cours":
				this.dispose();
				new FacturesEauEnCours().setVisible(true);
				break;
				
			case "Nouvelles factures d'eau":
				this.dispose();
				new NouvelleFactureEau().setVisible(true);
				break;
				
			case "Anciennes factures d'électricité":
				this.dispose();
				new FacturesElectriciteAnciennes().setVisible(true);
				break;
				
			case "Factures d'électricité en cours":
				this.dispose();
				new FacturesElectriciteEnCours().setVisible(true);
				break;
				
			case "Nouvelles factures d'électricité":
				this.dispose();
				new NouvelleFactureElectricite().setVisible(true);
				break;
				
			case "Consultation taxes foncières":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
			
			case "Nouvelles taxes foncières":
				this.dispose();
				new NouvelleTaxeFonciere().setVisible(true);
				break;
				
			case "Consultation protection juridique":
				this.dispose();
				new ProtectionJuridique().setVisible(true);
				break;
			
			case "Nouvelles protection juridique":
				this.dispose();
				new NouvelleProtectionJuridique().setVisible(true);
				break;
				
			case "Consultation charges supplémentaires":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
			
			case "Nouvelle charges supplémentaires":
				this.dispose();
				new NouvelleTaxeFonciere().setVisible(true);
				break;
			
			case "Anciens travaux":
				this.dispose();
				new TravauxAnciens().setVisible(true);
				break;
				
			case "Travaux en cours":
				this.dispose();
				new TravauxEnCours().setVisible(true);
				break;
			
			case "Nouveaux travaux":
				this.dispose();
				new NouveauTravaux().setVisible(true);
				break;
				
			case "Informations bailleur":
				this.dispose();
				new InformationsBailleur().setVisible(true);
				break;
				
			case "IRL":
				this.dispose();
				new IRL().setVisible(true);
				break;
				
			case "Quittances":
				this.dispose();
				new Quittances().setVisible(true);
				break;
				
			case "Impositions":
				this.dispose();
				new Impositions().setVisible(true);
				break;

			case "Annuler":
				this.dispose();
				new LocationsEnCours().setVisible(true);
				break;
	
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}