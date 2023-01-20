package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import JDBC.CictOracleDataSource;
import Requetes.Requete;
import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.consultation.ChargesSupplementaires;
import vue.consultation.EntretiensPartiesAnciens;
import vue.consultation.FacturesEauAPayees;
import vue.consultation.FacturesEauPayees;
import vue.consultation.FacturesElectriciteAPayees;
import vue.consultation.FacturesElectricitePayees;
import vue.consultation.Impositions;
import vue.consultation.LocatairesAnciens;
import vue.consultation.LocatairesEnCours;
import vue.consultation.LocationsAnciennes;
import vue.consultation.LocationsEnCours;
import vue.consultation.ProtectionJuridique;
import vue.consultation.TaxeFonciere;
import vue.consultation.TravauxAnciens;
import vue.consultation.TravauxEnCours;

public class NouvelleTaxeFonciere extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldAnneeFacture;
	private JTextField textFieldMontantPartieFixe;
	private JTextField textFieldMontantTotal;
	private JTextField textFieldReferenceAvis;
	private JTextField textFieldRepPDF;
	private JTextField textFieldNomPDF;
	private NouvelleTaxeFonciere frame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouvelleTaxeFonciere frame = new NouvelleTaxeFonciere();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	// SHOW COMBO * ENTERPRISE.NOM
			private ResultSet RequeteAfficheComboEntreprise() throws SQLException {
				ResultSet retourRequete = null;
				Requete requete = new Requetes.Requete();
				String texteSQL = "select SIREN, NOM from ENTREPRISE";
				retourRequete = requete.requeteSelection(texteSQL);
				return retourRequete;
			}
			
			// GET INT SIREN FROM ENTREPRISE.NOM
			public int RequeteGetSirenEntrepriseCombo(String nomEnt) throws SQLException  {
				ResultSet retourRequete = null;
				Requete requete = new Requetes.Requete();
				try {
					String texteSQL = ("select SIREN from ENTREPRISE where NOM = '" + nomEnt + "'");
					retourRequete = requete.requeteSelection(texteSQL);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				retourRequete.next();
				return retourRequete.getInt("SIREN");
			}
			
			// DB INSERT x6 : STRING STRING FLOAT FLOAT STRING STRING			
			private void RequeteInsertTaxFonc(String refAvisTaxeF, String anneeTaxeF, float totalOrdureTaxeF, float totalTaxeF, String direct, String nom) throws SQLException {
				CictOracleDataSource cict = new CictOracleDataSource();
				String requete = "{ call insertTaxeFonciere(?,?,?,?,?,?) } ";				
						try {
							Connection connection = cict.getConnection();
							CallableStatement cs = connection.prepareCall(requete);
							cs.setString(1, refAvisTaxeF);
							cs.setString(2, anneeTaxeF);
							cs.setFloat(3, totalOrdureTaxeF);
					        cs.setFloat(4, totalTaxeF);
							cs.setString(5, direct);
							cs.setString(6, nom);
							cs.execute();
						} catch(SQLException e) {
							e.printStackTrace();
						}
			}
	/**
	 * Create the frame.
	 */
	public NouvelleTaxeFonciere() {
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
		MenuItemAnciensLocataires.addActionListener(this);
		MenuLocations.add(MenuItemAnciensLocataires);
		
		JMenuItem MenuItemLocatairesEnCours = new JMenuItem("Locataires en cours");
		MenuItemLocatairesEnCours.addActionListener(this);
		MenuLocations.add(MenuItemLocatairesEnCours);
		
		JMenu MenuCharges = new JMenu("Charges");
		MenuCharges.addActionListener(this);
		menuBarTop.add(MenuCharges);
		
		JMenu MenuEntretiens = new JMenu("Entretiens");
		MenuEntretiens.addActionListener(this);
		MenuCharges.add(MenuEntretiens);
		
		JMenuItem MenuItemNouveauxEntretiens = new JMenuItem("Nouveaux entretiens des parties communes");
		MenuItemNouveauxEntretiens.addActionListener(this);
		
		JMenuItem MenuItemAnciensEntretiensPartiesCommunes = new JMenuItem("Entretiens des parties communes");
		MenuItemAnciensEntretiensPartiesCommunes.addActionListener(this);
		MenuItemAnciensEntretiensPartiesCommunes.setSelected(true);
		MenuEntretiens.add(MenuItemAnciensEntretiensPartiesCommunes);
		MenuItemNouveauxEntretiens.setSelected(true);
		MenuEntretiens.add(MenuItemNouveauxEntretiens);
		
		JMenu MenuFacturesEau = new JMenu("Factures d'eau");
		MenuFacturesEau.addActionListener(this);
		MenuCharges.add(MenuFacturesEau);
		
		JMenuItem MenuItemAnciennesFacturesEau = new JMenuItem("Factures d'eau payées");
		MenuItemAnciennesFacturesEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemAnciennesFacturesEau);
		
		JMenuItem MenuItemFacturesEauEnCours = new JMenuItem("Factures d'eau à payées");
		MenuItemFacturesEauEnCours.addActionListener(this);
		MenuFacturesEau.add(MenuItemFacturesEauEnCours);
		
		JMenuItem MenuItemNouvellesFactureEau = new JMenuItem("Nouvelles factures d'eau");
		MenuItemNouvellesFactureEau.addActionListener(this);
		MenuFacturesEau.add(MenuItemNouvellesFactureEau);
		
		JMenu MenuElectricite = new JMenu("Electricité");
		MenuElectricite.addActionListener(this);
		MenuCharges.add(MenuElectricite);
		
		JMenuItem MenuItemAnciennesFacturesElectricite = new JMenuItem("Factures d'électricité payées");
		MenuItemAnciennesFacturesElectricite.addActionListener(this);
		MenuElectricite.add(MenuItemAnciennesFacturesElectricite);
		
		JMenuItem mntmFacturesDlectricitEn = new JMenuItem("Factures d'électricité à payées");
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
		
		textFieldAnneeFacture = new JTextField();
		textFieldAnneeFacture.setColumns(10);
		textFieldAnneeFacture.setBounds(179, 127, 68, 20);
		contentPane.add(textFieldAnneeFacture);
		
		JLabel LabelAnneeFacture = new JLabel("Année :");
		LabelAnneeFacture.setBounds(36, 129, 132, 14);
		contentPane.add(LabelAnneeFacture);
		
		JLabel LabelMontantPartieFixe = new JLabel("Montant ordure ménagère :");
		LabelMontantPartieFixe.setBounds(37, 162, 144, 14);
		contentPane.add(LabelMontantPartieFixe);
		
		textFieldMontantPartieFixe = new JTextField();
		textFieldMontantPartieFixe.setColumns(10);
		textFieldMontantPartieFixe.setBounds(177, 159, 132, 20);
		contentPane.add(textFieldMontantPartieFixe);
		
		JLabel LabelMontantTotal = new JLabel("Montant  total :");
		LabelMontantTotal.setBounds(37, 193, 132, 14);
		contentPane.add(LabelMontantTotal);
		
		textFieldMontantTotal = new JTextField();
		textFieldMontantTotal.setColumns(10);
		textFieldMontantTotal.setBounds(177, 190, 132, 20);
		contentPane.add(textFieldMontantTotal);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelTaxeFonciere = new JLabel("Nouvelle taxe foncière");
		LabelTaxeFonciere.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelTaxeFonciere.setBounds(37, 0, 260, 41);
		contentPane.add(LabelTaxeFonciere);
		
		JLabel LabelReferenceAvis = new JLabel("* Référence avis :");
		LabelReferenceAvis.setBounds(37, 99, 132, 14);
		contentPane.add(LabelReferenceAvis);
		
		textFieldReferenceAvis = new JTextField();
		textFieldReferenceAvis.setColumns(10);
		textFieldReferenceAvis.setBounds(179, 96, 132, 20);
		contentPane.add(textFieldReferenceAvis);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(37, 223, 132, 14);
		contentPane.add(LabelPDF);
		
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(176, 219, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setEditable(false);
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(176, 249, 274, 20);
		contentPane.add(textFieldRepPDF);
		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accès  :");
		LabelCheminPDF.setBounds(60, 253, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(60, 285, 119, 14);
		contentPane.add(LabelNomPDF);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setEditable(false);
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(176, 282, 132, 20);
		contentPane.add(textFieldNomPDF);
		
		JButton btnDateDeMaintenant = new JButton("Date");
		btnDateDeMaintenant.setBounds(247, 125, 66, 23);
		btnDateDeMaintenant.addActionListener(this);
		contentPane.add(btnDateDeMaintenant);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Date":
				DateTimeFormatter dtfJ = DateTimeFormatter.ofPattern("DD");
				LocalDate nowDate = LocalDate.now();
				String annee = nowDate.toString().substring(0,4);
				String dateCourrante = ( annee );
				this.textFieldAnneeFacture.setText(dateCourrante);
				break;
			case "Ajouter":					
				String refAvisTaxeF = textFieldReferenceAvis.getText(); 
				String anneeTaxeF = textFieldAnneeFacture.getText();
				float totalOrdureTaxeF = Float.parseFloat(textFieldMontantPartieFixe.getText());
				float totalTaxeF = Float.parseFloat(textFieldMontantTotal.getText());
				String direct = textFieldRepPDF.getText();
				String nom = textFieldNomPDF.getText();	
				try {					
					RequeteInsertTaxFonc(refAvisTaxeF, anneeTaxeF, totalOrdureTaxeF, totalTaxeF, direct, nom);
					JOptionPane.showMessageDialog(frame, "Taxe foncière " + refAvisTaxeF + " insérée.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}				
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Choix fichier...":
				JFileChooser pdfChooser = new JFileChooser();
				int reponse = pdfChooser.showOpenDialog(null);
				if (reponse == JFileChooser.APPROVE_OPTION) {
					File file = new File(pdfChooser.getSelectedFile().getAbsolutePath());
					textFieldNomPDF.setText(file.getName());
					textFieldRepPDF.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - textFieldNomPDF.getText().length() - 1));
				}
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
				
			case "Entretiens des parties communes":
				this.dispose();
				new EntretiensPartiesAnciens().setVisible(true);
				break;
				
			case "Nouveaux entretiens des parties communes":
				this.dispose();
				new NouveauEntretien().setVisible(true);
				break;
				
			case "Factures d'eau payées":
				this.dispose();
				new FacturesEauPayees().setVisible(true);
				break;
				
			case "Factures d'eau à payées":
				this.dispose();
				new FacturesEauAPayees().setVisible(true);
				break;
				
			case "Nouvelles factures d'eau":
				this.dispose();
				new NouvelleFactureEau().setVisible(true);
				break;
				
			case "Factures d'électricité payées":
				this.dispose();
				new FacturesElectricitePayees().setVisible(true);
				break;
				
			case "Factures d'électricité à payées":
				this.dispose();
				new FacturesElectriciteAPayees().setVisible(true);
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
				new ChargesSupplementaires().setVisible(true);
				break;
			
			case "Nouvelle charges supplémentaires":
				this.dispose();
				new NouvelleChargeSupp().setVisible(true);
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
				
			case "Insérer":
				this.dispose();
				new NouveauDiagnostic().setVisible(true);
				break;
			
			case "Mise à jour":
				//this.dispose();
				//new ().setVisible(true);
				System.out.println("A implémenter");
				break;
				
			case "Supprimer":
				//this.dispose();
				//new Impositions().setVisible(true);
				System.out.println("A implémenter");
				break;
				
				
			case "Annuler":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}