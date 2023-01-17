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

import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class NouvelleFactureEau extends JFrame implements ActionListener {

	private JPanel contentPane;
	protected JTextField textFieldNumeroFacture;
	protected JTextField textFieldPrixM3;
	protected JTextField textFieldPartieFixe;
	protected JTextField textFieldRepPDF;
	protected JTextField textFieldNomPDF;
	protected JTextField textFieldDateFac;
	protected JTextField textFieldMontantTotal;
	protected JTextField textFieldFrequence;
	protected JComboBox<String> comboBoxEntreprise;
	protected String comboEntNom;
	protected NouvelleFactureEau frame;

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
	

	// DB INSERT x8 : INT INT FLOAT STRING FLOAT FLOAT STRING+STRING INT
	private void RequeteInsertFacEau(int siren, int numfact, float prixm3, String datefact, float partiefixe, float total, String chemin, String pdf, int frequencefacture ) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertFactureEau(?,?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setInt(1, siren);
					cs.setInt(2, numfact);
					cs.setFloat(3, prixm3);
			        cs.setString(4, datefact);
			        cs.setFloat(5, partiefixe);
			        cs.setFloat(6, total);
					cs.setString(7, chemin);
					cs.setString(8, pdf);
					cs.setInt(9, frequencefacture);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * Create the frame.
	 */
	public NouvelleFactureEau() {
		setTitle("Nouvelle facture d'eau");
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
		
		textFieldNumeroFacture = new JTextField();
		textFieldNumeroFacture.setBounds(166, 69, 132, 20);
		contentPane.add(textFieldNumeroFacture);
		textFieldNumeroFacture.setColumns(10);
		
		textFieldPrixM3 = new JTextField();
		textFieldPrixM3.setColumns(10);
		textFieldPrixM3.setBounds(166, 202, 132, 20);
		contentPane.add(textFieldPrixM3);
		
		JLabel LabelNumeroFacture = new JLabel("* Numéro de facture :");
		LabelNumeroFacture.setBounds(38, 69, 132, 14);
		contentPane.add(LabelNumeroFacture);
		
		JLabel LabelPrixM3 = new JLabel("Prix au m3 :");
		LabelPrixM3.setBounds(38, 202, 132, 14);
		contentPane.add(LabelPrixM3);
		
		JLabel LabelPartieFixe = new JLabel("Partie fixe :");
		LabelPartieFixe.setBounds(38, 167, 132, 14);
		contentPane.add(LabelPartieFixe);
		
		textFieldPartieFixe = new JTextField();
		textFieldPartieFixe.setColumns(10);
		textFieldPartieFixe.setBounds(166, 167, 132, 20);
		contentPane.add(textFieldPartieFixe);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelFactureDEau = new JLabel("Nouvelle facture d'eau");
		LabelFactureDEau.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelFactureDEau.setBounds(37, 0, 260, 41);
		contentPane.add(LabelFactureDEau);
		
		JLabel LabelEntreprise = new JLabel("* Entreprise :");
		LabelEntreprise.setBounds(37, 40, 132, 14);
		contentPane.add(LabelEntreprise);
		
		JComboBox comboBoxEntreprise = new JComboBox();
		comboBoxEntreprise.setBounds(165, 36, 132, 22);
		contentPane.add(comboBoxEntreprise);
		try {
			ResultSet rsEntNom = RequeteAfficheComboEntreprise();
			int i = 0;
			rsEntNom.next();
			while ( i < rsEntNom.getRow()) {
				comboBoxEntreprise.addItem(rsEntNom.getString("NOM"));
				rsEntNom.next();
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		JButton ButtonNouvelleEntreprise = new JButton("Nouvelle entreprise");
		ButtonNouvelleEntreprise.setBounds(307, 36, 132, 23);
		ButtonNouvelleEntreprise.addActionListener(this);
		contentPane.add(ButtonNouvelleEntreprise);
		
		JLabel LabelPDF = new JLabel("* Fichier :");
		LabelPDF.setBounds(38, 274, 132, 14);
		contentPane.add(LabelPDF);
		
		JButton ButtonPDF = new JButton("Choix fichier...");
		ButtonPDF.setBounds(166, 270, 132, 23);
		ButtonPDF.addActionListener(this);
		contentPane.add(ButtonPDF);
		
		JLabel LabelCheminPDF = new JLabel("Chemin d'accès  :");
		LabelCheminPDF.setBounds(50, 304, 119, 14);
		contentPane.add(LabelCheminPDF);
		
		textFieldRepPDF = new JTextField();
		textFieldRepPDF.setEditable(false);
		textFieldRepPDF.setColumns(10);
		textFieldRepPDF.setBounds(166, 300, 274, 20);
		contentPane.add(textFieldRepPDF);
		
		textFieldNomPDF = new JTextField();
		textFieldNomPDF.setEditable(false);
		textFieldNomPDF.setColumns(10);
		textFieldNomPDF.setBounds(166, 333, 132, 20);
		contentPane.add(textFieldNomPDF);
		
		JLabel LabelNomPDF = new JLabel("Nom fichier :");
		LabelNomPDF.setBounds(50, 336, 119, 14);
		contentPane.add(LabelNomPDF);
		
		JLabel LabelDateFacture = new JLabel("* Date de la facture  :");
		LabelDateFacture.setBounds(37, 100, 132, 14);
		contentPane.add(LabelDateFacture);
		
		textFieldDateFac = new JTextField();
		textFieldDateFac.setColumns(10);
		textFieldDateFac.setBounds(165, 100, 68, 20);
		contentPane.add(textFieldDateFac);
		
		JLabel LabelMontant = new JLabel("* Montant  total :");
		LabelMontant.setBounds(38, 131, 132, 14);
		contentPane.add(LabelMontant);
		
		textFieldMontantTotal = new JTextField();
		textFieldMontantTotal.setColumns(10);
		textFieldMontantTotal.setBounds(166, 131, 132, 20);
		contentPane.add(textFieldMontantTotal);
		
		JLabel LabelFrequence = new JLabel("Fréquence :");
		LabelFrequence.setBounds(38, 233, 132, 14);
		contentPane.add(LabelFrequence);
		
		textFieldFrequence = new JTextField();
		textFieldFrequence.setColumns(10);
		textFieldFrequence.setBounds(166, 233, 132, 20);
		contentPane.add(textFieldFrequence);
		
		// GET COMBO SELECTED VALUE
		comboBoxEntreprise.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        comboEntNom = (String) jcmbType.getSelectedItem();
		      }
		    });
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Nouvelle entreprise":
				new NouvelleEntreprise().setVisible(true);
				break;
			case "Ajouter":
				int numfact = Integer.parseInt(textFieldNumeroFacture.getText());
				float prixm3 = Float.parseFloat(textFieldPrixM3.getText());
				String datefact = textFieldDateFac.getText();
				float partiefixe = Float.parseFloat(textFieldPartieFixe.getText());
				float total = Float.parseFloat(textFieldMontantTotal.getText());					
				String chemin = textFieldRepPDF.getText() ;
				String pdf = textFieldNomPDF.getText() ;		
				int frequencefacture = Integer.parseInt(textFieldFrequence.getText());   
				try {
					int siren = RequeteGetSirenEntrepriseCombo(comboEntNom);
					RequeteInsertFacEau(siren, numfact, prixm3, datefact, partiefixe, total, chemin, pdf, frequencefacture);
					JOptionPane.showMessageDialog(frame, "Facture " + numfact + " insérée.");
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
				
			case "Choix fichier...":
				JFileChooser pdfChooser = new JFileChooser();
				int reponse = pdfChooser.showOpenDialog(null);
				if (reponse == JFileChooser.APPROVE_OPTION) {
					File file = new File(pdfChooser.getSelectedFile().getAbsolutePath());
					textFieldNomPDF.setText(file.getName());
					textFieldRepPDF.setText(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - textFieldNomPDF.getText().length() - 1));
				}
				break;       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}