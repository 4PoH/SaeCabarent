package vue;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import JDBC.CictOracleDataSource;
import Requetes.Requete;
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
import vue.insertion.NouveauEntretien;
import vue.insertion.NouveauTravaux;
import vue.insertion.NouvelleChargeSupp;
import vue.insertion.NouvelleFactureEau;
import vue.insertion.NouvelleFactureElectricite;
import vue.insertion.NouvelleLocation;
import vue.insertion.NouvelleProtectionJuridique;
import vue.insertion.NouvelleTaxeFonciere;

public class InformationsBailleur extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldNom;
	private JTextField textFieldPrenom;
	private JTextField textFieldAdresse;
	private JTextField textFieldVille;
	private JTextField textFieldCodeP;
	private JTextField textFieldMail;
	private JTextField textFieldTel;
	private JTextField textFieldDestination;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InformationsBailleur frame = new InformationsBailleur();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private ResultSet resInfosBailleur() throws SQLException {
		ResultSet retourRequete = null;
		Requete requeteSelect = new Requetes.Requete();
		String texteSQL = "select * from bailleur";
		try {
			retourRequete = requeteSelect.requeteSelection(texteSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retourRequete; 
	}
	
	
	
	private int updateBailleur(String nomBailleur, String prenomBailleur, String adresseBailleur, String codePost, String villeBailleur, String mailBailleur, String telBailleur, String destinationFichier) {
		int resultatMiseJour = 0;
		try {
		CictOracleDataSource cict = new CictOracleDataSource();		
			Connection connex = cict.getConnection();
			CallableStatement CallState = connex.prepareCall("{ call updateBailleur( ?, ?, ?, ?, ?, ?, ?, ?)}");
			CallState.setString(1, nomBailleur);
			CallState.setString(2, prenomBailleur);
			CallState.setString(3, adresseBailleur);
			CallState.setString(4, codePost);
			CallState.setString(5, villeBailleur);
			CallState.setString(6, mailBailleur);
			CallState.setString(7, telBailleur);
			CallState.setString(8, destinationFichier);
			resultatMiseJour = CallState.executeUpdate();
			
		} catch(SQLException e) {
				e.printStackTrace();
			}
			return resultatMiseJour;
	}		
	
	public InformationsBailleur() {
		setTitle("Bailleur");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 520);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 680, 300);
		contentPane.add(scrollPane);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			ResultSet resInfosBailleur = resInfosBailleur();
			int i = 0;
			resInfosBailleur.next();
			while ( i < resInfosBailleur.getRow()) {
				
				textFieldNom = new JTextField(resInfosBailleur.getString("NOM"));
				textFieldNom.setBounds(270, 50, 140, 20);
				contentPane.add(textFieldNom);
				textFieldNom.setColumns(10);
				
				textFieldPrenom = new JTextField(resInfosBailleur.getString("PRENOM"));
				textFieldPrenom.setColumns(10);
				textFieldPrenom.setBounds(270, 90, 140, 20);
				contentPane.add(textFieldPrenom);
				
				textFieldAdresse = new JTextField(resInfosBailleur.getString("ADRESSE"));
				textFieldAdresse.setColumns(10);
				textFieldAdresse.setBounds(270, 130, 140, 20);
				contentPane.add(textFieldAdresse);
				
				textFieldVille = new JTextField(resInfosBailleur.getString("VILLE"));
				textFieldVille.setColumns(10);
				textFieldVille.setBounds(270, 170, 140, 20);
				contentPane.add(textFieldVille);
				
				textFieldCodeP = new JTextField(resInfosBailleur.getString("CODEPOSTAL"));
				textFieldCodeP.setColumns(10);
				textFieldCodeP.setBounds(270, 210, 140, 20);
				contentPane.add(textFieldCodeP);
				
				textFieldMail = new JTextField(resInfosBailleur.getString("MAIL"));
				textFieldMail.setColumns(10);
				textFieldMail.setBounds(270, 250, 140, 20);
				contentPane.add(textFieldMail);
				
				textFieldTel = new JTextField(resInfosBailleur.getString("NUMTEL"));
				textFieldTel.setColumns(10);
				textFieldTel.setBounds(270, 290, 140, 20);
				contentPane.add(textFieldTel);
				
				textFieldDestination = new JTextField(resInfosBailleur.getString("DESTINATION"));
				textFieldDestination.setColumns(10);
				textFieldDestination.setBounds(270, 330, 140, 20);
				contentPane.add(textFieldDestination);
				
				i++;
				resInfosBailleur.next();
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		JLabel LabelNom = new JLabel("* Nom");
		LabelNom.setBounds(95, 50, 140, 14);
		contentPane.add(LabelNom);
		
		JLabel LabelPrenom = new JLabel("*Prénom");
		LabelPrenom.setBounds(95, 90, 140, 14);
		contentPane.add(LabelPrenom);
		
		JLabel LabelAdresse = new JLabel("*Adresse");
		LabelAdresse.setBounds(95, 130, 140, 14);
		contentPane.add(LabelAdresse);
		
		JLabel LabelVille = new JLabel("*Ville");
		LabelVille.setBounds(95, 170, 140, 14);
		contentPane.add(LabelVille);
		
		JLabel LabelCp = new JLabel("*Code Postal");
		LabelCp.setBounds(95, 210, 140, 14);
		contentPane.add(LabelCp);
		
		JLabel LabelMail = new JLabel("*Mail");
		LabelMail.setBounds(95, 250, 140, 14);
		contentPane.add(LabelMail);
		
		JLabel LabelTel = new JLabel("*Telephone");
		LabelTel.setBounds(95, 290, 140, 14);
		contentPane.add(LabelTel);
		
		JButton ButtonDestination = new JButton("Choix fichier...");
		ButtonDestination.setBounds(70, 327, 140, 25);
		ButtonDestination.addActionListener(this);
		contentPane.add(ButtonDestination);
		
		JButton ButtonAjouter = new JButton("Confirmer");
		ButtonAjouter.setBounds(310, 385, 140, 25);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(50, 385, 140, 25);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelFactureDEau = new JLabel("Informations du bailleur");
		LabelFactureDEau.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelFactureDEau.setBounds(37, 0, 260, 41);
		contentPane.add(LabelFactureDEau);
	}
	
public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
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
				
			case "Annuler":
				this.dispose();
				break;
				
			case "Confirmer":
				this.dispose();
				updateBailleur(this.textFieldNom.getText(),
								this.textFieldPrenom.getText(),
								this.textFieldAdresse.getText(),
								this.textFieldCodeP.getText(),
								this.textFieldVille.getText(),
								this.textFieldMail.getText(),
								this.textFieldTel.getText(),
								this.textFieldDestination.getText());
				new InformationsBailleur().setVisible(true);
				break;
			
			case "Choix fichier...":
				JFileChooser chooser = new JFileChooser();
			    chooser.setDialogTitle("Choisir un répertoire de stockage");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    chooser.setAcceptAllFileFilterUsed(false);

			    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
			      this.textFieldDestination.setText("" + chooser.getSelectedFile());
			      } else {
			      System.out.println("No Selection ");
			    }
				break;
				
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}