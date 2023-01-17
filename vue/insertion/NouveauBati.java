package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import JDBC.CictOracleDataSource;
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

public class NouveauBati extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldCodePostal;
	private JTextField textFieldVille;
	private JTextField textFieldAnnee;
	private JTextField textFieldAdresse;
	private JTextField textFieldPartiesCommunes;
	private JComboBox<String> comboBoxTypeHabitation;
	private String comboTypeHabitation;
	private JCheckBox checkboxCopropriete;
	private JTextField textFieldAnneObtention;
	private String copropriete;
	private NouveauBati frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauBati frame = new NouveauBati();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// DB INSERT x8 : STRING INT STRING STRING STRING STRING BOOLEAN STRING
	private void RequeteInsertBati(String adresse, int code, String ville, String typeHabitation, String anneeConstruction, String listePartieCommune, String copropriete, String dateObtention) throws SQLException {
		CictOracleDataSource cict = new CictOracleDataSource();
		String requete = "{ call insertBati(?,?,?,?,?,?,?,?) } ";		
				try {
					Connection connection = cict.getConnection();
					CallableStatement cs = connection.prepareCall(requete);
					cs.setString(1, adresse);
					cs.setInt(2, code);
					cs.setString(3, ville);
					cs.setString(4, typeHabitation);
			        cs.setString(5, anneeConstruction);
					cs.setString(6, listePartieCommune);					
					cs.setString(7, copropriete);
					cs.setString(8, dateObtention);
					cs.execute();
				} catch(SQLException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * Create the frame.
	 */
	public NouveauBati() {
		setTitle("Nouveau bâtiment");
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
		
		textFieldCodePostal = new JTextField();
		textFieldCodePostal.setColumns(10);
		textFieldCodePostal.setBounds(181, 113, 68, 20);
		contentPane.add(textFieldCodePostal);
		
		JLabel LabelCodePostal = new JLabel("* Code postal :");
		LabelCodePostal.setBounds(39, 112, 132, 14);
		contentPane.add(LabelCodePostal);
		
		JLabel LabelVille = new JLabel("Ville :");
		LabelVille.setBounds(37, 144, 144, 14);
		contentPane.add(LabelVille);
		
		textFieldVille = new JTextField();
		textFieldVille.setColumns(10);
		textFieldVille.setBounds(179, 145, 132, 20);
		contentPane.add(textFieldVille);
		
		JLabel LabelTypeHabitat = new JLabel("Type d'habitat :");
		LabelTypeHabitat.setBounds(37, 175, 132, 14);
		contentPane.add(LabelTypeHabitat);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelTaxeFonciere = new JLabel("Nouveau bâtiment");
		LabelTaxeFonciere.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelTaxeFonciere.setBounds(37, 0, 260, 41);
		contentPane.add(LabelTaxeFonciere);
		
		textFieldAnnee = new JTextField();
		textFieldAnnee.setColumns(10);
		textFieldAnnee.setBounds(179, 208, 68, 20);
		contentPane.add(textFieldAnnee);
		
		JLabel LabelAnnee = new JLabel("Année de construction :");
		LabelAnnee.setBounds(37, 207, 132, 14);
		contentPane.add(LabelAnnee);
		
		JLabel LabelAdresse = new JLabel("* Adresse :");
		LabelAdresse.setBounds(39, 82, 132, 14);
		contentPane.add(LabelAdresse);
		
		textFieldAdresse = new JTextField();
		textFieldAdresse.setColumns(10);
		textFieldAdresse.setBounds(181, 82, 233, 20);
		contentPane.add(textFieldAdresse);
		
		JLabel LabelPartiesCommunes = new JLabel("Liste des parties communes :");
		LabelPartiesCommunes.setBounds(39, 244, 132, 14);
		contentPane.add(LabelPartiesCommunes);
		
		textFieldPartiesCommunes = new JTextField();
		textFieldPartiesCommunes.setColumns(10);
		textFieldPartiesCommunes.setBounds(179, 239, 235, 73);
		contentPane.add(textFieldPartiesCommunes);
		
		JLabel LabelCopropriete = new JLabel("Copropriété :");
		LabelCopropriete.setBounds(39, 323, 132, 14);
		contentPane.add(LabelCopropriete);
		
		JCheckBox checkboxCopropriete = new JCheckBox("Copropriété");
		checkboxCopropriete.setBounds(176, 319, 97, 23);
		contentPane.add(checkboxCopropriete);
		checkboxCopropriete.addActionListener(this);
		
		JComboBox comboBoxTypeHabitation = new JComboBox();
		comboBoxTypeHabitation.setBounds(181, 176, 130, 22);
		contentPane.add(comboBoxTypeHabitation);
		ArrayList<String> typeHab = new ArrayList<String>();
		typeHab.add("Appartement");
		typeHab.add("Maison");
		typeHab.add("Autres...");
		int indexTypeHab = 0;
		while ( indexTypeHab < typeHab.size()) {
			comboBoxTypeHabitation.addItem(typeHab.get(indexTypeHab));
			indexTypeHab++;
		}
		comboBoxTypeHabitation.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        comboTypeHabitation = (String) jcmbType.getSelectedItem();
		      }
		    });
		
		JLabel LabelAnneeObtention = new JLabel("Date d'obtention :");
		LabelAnneeObtention.setBounds(39, 348, 132, 14);
		contentPane.add(LabelAnneeObtention);
		
		textFieldAnneObtention = new JTextField();
		textFieldAnneObtention.setColumns(10);
		textFieldAnneObtention.setBounds(181, 349, 68, 20);
		contentPane.add(textFieldAnneObtention);
		
		this.copropriete = "N";
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Ajouter":	
				System.out.println("test");
				String adresse = textFieldAdresse.getText();
				int code = Integer.parseInt(textFieldCodePostal.getText()) ;
				String ville = textFieldVille.getText();
				String typeHabitation = comboTypeHabitation ;
				String anneeConstruction = textFieldAnnee.getText();
				String listePartieCommune = textFieldPartiesCommunes.getText() ; 
				String dateObtention = textFieldAnneObtention.getText();
				String copropriete = this.copropriete;
		        System.out.println(adresse + code + ville + typeHabitation + anneeConstruction + listePartieCommune + dateObtention + copropriete);
				try {					
					RequeteInsertBati(adresse,code, ville, typeHabitation, anneeConstruction, listePartieCommune, copropriete, dateObtention);
					JOptionPane.showMessageDialog(frame, "Batîment " + typeHabitation + " de " + anneeConstruction + " inséré.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Copropriété":
				this.checkboxCopropriete = (JCheckBox) e.getSource();
				if (checkboxCopropriete.isSelected()) {
					this.copropriete = "Y";
				} else {
					this.copropriete = "N";
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
				
			case "Anciennes factures d'Ã©lectricitÃ©":
				this.dispose();
				new FacturesElectriciteAnciennes().setVisible(true);
				break;
				
			case "Factures d'Ã©lectricitÃ© en cours":
				this.dispose();
				new FacturesElectriciteEnCours().setVisible(true);
				break;
				
			case "Nouvelles factures d'Ã©lectricitÃ©":
				this.dispose();
				new NouvelleFactureElectricite().setVisible(true);
				break;
				
			case "Consultation taxes fonciÃ¨res":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
			
			case "Nouvelles taxes fonciÃ¨res":
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
				
			case "Consultation charges supplÃ©mentaires":
				this.dispose();
				new TaxeFonciere().setVisible(true);
				break;
			
			case "Nouvelle charges supplÃ©mentaires":
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
       
			default:
				System.out.println("Choix incorrect");
				break;
		}	}
}