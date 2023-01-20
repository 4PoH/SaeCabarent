package vue.insertion;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JTextPane;
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

public class NouvelleLocation extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldMontant;
	private JTextField textFieldMontantCharges;
	private JTextField textFieldParticipationEntretien;
	private JTextField textFieldParticipationElectricite;
	private JTextField textFieldDateCourante;
	private JTextField textFieldMontantRegle;
	private JTextField textFieldMontantCompteur;
	private JComboBox<String> comboBoxContrats;
	private String selectedComboContrat;
	private String selectedComboIDContrat;
	private JComboBox<String> comboBoxLogements;
	private String selectedComboLogement;
	private String selectedComboIDLogement;
	private JComboBox<String> comboBoxMoyenDePaiement;
	private String selectedMoyenDePaiement;
	private NouvelleLocation frame;
	
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
		
		JLabel LabelContrat = new JLabel("* Contrat :");
		LabelContrat.setBounds(24, 77, 132, 14);
		contentPane.add(LabelContrat);
		
		JLabel LabelLogements = new JLabel("* Logement  :");
		LabelLogements.setBounds(25, 113, 132, 14);
		contentPane.add(LabelLogements);
		
		JLabel LabelMontant = new JLabel("!! Montant du loyer  :");
		LabelMontant.setBounds(24, 148, 132, 14);
		contentPane.add(LabelMontant);
		
		textFieldMontant = new JTextField();
		textFieldMontant.setColumns(10);
		textFieldMontant.setBounds(166, 145, 132, 20);
		contentPane.add(textFieldMontant);
		
		JLabel LabelMontantCharges = new JLabel("!! Montant des charges :");
		LabelMontantCharges.setBounds(24, 179, 132, 14);
		contentPane.add(LabelMontantCharges);
		
		textFieldMontantCharges = new JTextField();
		textFieldMontantCharges.setColumns(10);
		textFieldMontantCharges.setBounds(166, 176, 132, 20);
		contentPane.add(textFieldMontantCharges);
		
		JLabel LabelParticipationEntretien = new JLabel("!! Participation entretien :");
		LabelParticipationEntretien.setBounds(24, 271, 132, 14);
		contentPane.add(LabelParticipationEntretien);
		
		textFieldParticipationEntretien = new JTextField();
		textFieldParticipationEntretien.setColumns(10);
		textFieldParticipationEntretien.setBounds(166, 271, 46, 20);
		contentPane.add(textFieldParticipationEntretien);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(304, 389, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(46, 389, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelNouvelleLocation = new JLabel("Nouvelle Location");
		LabelNouvelleLocation.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelNouvelleLocation.setBounds(24, 0, 307, 41);
		contentPane.add(LabelNouvelleLocation);
		
		JButton ButtonNouveauContrat = new JButton("Nouveau Contrat");
		ButtonNouveauContrat.setBounds(308, 73, 132, 23);
		ButtonNouveauContrat.addActionListener(this);
		contentPane.add(ButtonNouveauContrat);
		
		JComboBox comboBoxContrats = new JComboBox();
		comboBoxContrats.setBounds(166, 73, 132, 22);
		comboBoxContrats.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 10));
		contentPane.add(comboBoxContrats);
		try {
			ResultSet rsContrat = RequeteInsertion.RequeteAfficheComboContrat();
			int i = 0;
			rsContrat.next();
			while ( i < rsContrat.getRow()) {
				String idContrat = rsContrat.getString("IDCONTRAT");
				String dateContrat = rsContrat.getString("DATEMISEENEFFET").substring(0,10);
				String typeLoc = rsContrat.getString("TYPELOC");
				comboBoxContrats.addItem(dateContrat + " " + typeLoc + " (" + idContrat + ") ");
				rsContrat.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		comboBoxContrats.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        selectedComboContrat = (String) jcmbType.getSelectedItem();
		        selectedComboIDContrat = selectedComboContrat.substring(selectedComboContrat.lastIndexOf("(")+1,selectedComboContrat.lastIndexOf(")"));
		      }
		    });
		
		JComboBox comboBoxLogements = new JComboBox();
		comboBoxLogements.setBounds(167, 107, 132, 22);
		comboBoxLogements.setFont(new Font("Tahoma", Font.ROMAN_BASELINE, 8));
		contentPane.add(comboBoxLogements);
		try {
			ResultSet rsLogement = RequeteInsertion.RequeteAfficheLogement();
			int i = 0;
			rsLogement.next();
			while ( i < rsLogement.getRow()) {
				String idLogement = rsLogement.getString("IDLOGEMENT");
				String adresse = rsLogement.getString("ADRESSE");
				String codePostal = rsLogement.getString("CODEPOSTAL");
				comboBoxLogements.addItem( codePostal + " - " + adresse + " (" +idLogement+") ");
				rsLogement.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 		
		comboBoxLogements.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        selectedComboLogement = (String) jcmbType.getSelectedItem();
		        selectedComboIDLogement = selectedComboLogement.substring(selectedComboLogement.lastIndexOf("(")+1,selectedComboLogement.lastIndexOf(")"));		        
		      }
		    });
		
		
		JButton ButtonNouveauLogement = new JButton("Nouveau Logement");
		ButtonNouveauLogement.setBounds(309, 107, 132, 23);
		ButtonNouveauLogement.addActionListener(this);
		contentPane.add(ButtonNouveauLogement);
		
		JLabel LabelParticipationElectricite = new JLabel("!! Participation éléctricité :");
		LabelParticipationElectricite.setBounds(24, 299, 132, 14);
		contentPane.add(LabelParticipationElectricite);
		
		textFieldParticipationElectricite = new JTextField();
		textFieldParticipationElectricite.setColumns(10);
		textFieldParticipationElectricite.setBounds(166, 299, 46, 20);
		contentPane.add(textFieldParticipationElectricite);
		
		JLabel LabelDateCourante = new JLabel("* Date courante :");
		LabelDateCourante.setBounds(24, 46, 132, 14);
		contentPane.add(LabelDateCourante);
		
		textFieldDateCourante = new JTextField();
		textFieldDateCourante.setColumns(10);
		textFieldDateCourante.setBounds(166, 43, 66, 20);
		contentPane.add(textFieldDateCourante);
		
		JLabel LabelTypePaiement = new JLabel("Moyen de paiement :");
		LabelTypePaiement.setBounds(24, 214, 132, 14);
		contentPane.add(LabelTypePaiement);
		
		JComboBox comboBoxMoyenDePaiement = new JComboBox();
		comboBoxMoyenDePaiement.setBounds(166, 207, 132, 22);
		contentPane.add(comboBoxMoyenDePaiement);
		ArrayList<String> moyenPaiement = new ArrayList<String>();
		moyenPaiement.add("Virement");
		moyenPaiement.add("Espèces");
		moyenPaiement.add("Chèque");
		moyenPaiement.add("Autres...");
		int indexMoyen = 0;
		while ( indexMoyen < moyenPaiement.size()) {
			comboBoxMoyenDePaiement.addItem(moyenPaiement.get(indexMoyen));
			indexMoyen++;
		}
		comboBoxMoyenDePaiement.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        selectedMoyenDePaiement = (String) jcmbType.getSelectedItem();
		      }
		    });
		
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(232, 42, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
		
		JLabel LabelMontantRegle = new JLabel("Montant réglé :");
		LabelMontantRegle.setBounds(24, 239, 132, 14);
		contentPane.add(LabelMontantRegle);
		
		textFieldMontantRegle = new JTextField();
		textFieldMontantRegle.setColumns(10);
		textFieldMontantRegle.setBounds(166, 239, 66, 20);
		contentPane.add(textFieldMontantRegle);
		
		JLabel lblMontantCompteur = new JLabel("Montant compteur :");
		lblMontantCompteur.setBounds(24, 327, 132, 14);
		contentPane.add(lblMontantCompteur);
		
		textFieldMontantCompteur = new JTextField();
		textFieldMontantCompteur.setColumns(10);
		textFieldMontantCompteur.setBounds(166, 324, 66, 20);
		contentPane.add(textFieldMontantCompteur);
		
		JTextPane txtpnInformation = new JTextPane();
		txtpnInformation.setText("            !!   \nAuto complètion de ces champs si ce n'est pas le premier loyer.");
		txtpnInformation.setBounds(321, 207, 119, 78);
		txtpnInformation.setFont(new Font("Tahoma", Font.ITALIC, 12));
		txtpnInformation.setBackground(Color.LIGHT_GRAY);
		contentPane.add(txtpnInformation);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Nouveau Contrat":
				new NouveauContrat().setVisible(true);
				break;
			case "Nouveau Logement":
				new NouveauLogement().setVisible(true);
				break;
			case "Ajouter":		
				int idLog = Integer.parseInt(selectedComboIDLogement);
				int idcontr = Integer.parseInt(selectedComboIDContrat);			
				String dateloc  = textFieldDateCourante.getText();
				float loyer = Float.parseFloat(textFieldMontant.getText());
				float loyerregler = Float.parseFloat(textFieldMontantRegle.getText());			
				float pourcentr = Float.parseFloat(textFieldParticipationEntretien.getText());			
				float compteur = Float.parseFloat(textFieldMontantCompteur.getText()); 			
				float charge= Float.parseFloat(textFieldMontantCharges.getText()); 			
				String modepaie = selectedMoyenDePaiement; 
				float pourelec = Float.parseFloat(textFieldParticipationElectricite.getText());			
				try {
					RequeteInsertion.RequeteInsertContrat(idLog, idcontr, dateloc, loyer, loyerregler, pourcentr, compteur,  charge, modepaie, pourelec);
					JOptionPane.showMessageDialog(frame, "Nouvelle location insérée.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Date":
				DateTimeFormatter dtfJ = DateTimeFormatter.ofPattern("DD");
				LocalDate nowDate = LocalDate.now();
				String jours = nowDate.toString().substring(8);
				String mois = nowDate.toString().substring(5,7);
				String annee = nowDate.toString().substring(0,4);
				String dateCourrante = ( jours + "/" + mois + "/" + annee );
				this.textFieldDateCourante.setText(""+dateCourrante);
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