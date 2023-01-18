package vue.consultation;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Requetes.Requete;
import vue.Accueil;
import vue.IRL;
import vue.InformationsBailleur;
import vue.Quittances;
import vue.insertion.NouveauDiagnostic;
import vue.insertion.NouveauEntretien;
import vue.insertion.NouveauTravaux;
import vue.insertion.NouvelleChargeSupp;
import vue.insertion.NouvelleFactureEau;
import vue.insertion.NouvelleFactureElectricite;
import vue.insertion.NouvelleLocation;
import vue.insertion.NouvelleProtectionJuridique;
import vue.insertion.NouvelleTaxeFonciere;

public class LocationsAnciennes extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable tableAncienneLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LocationsAnciennes frame = new LocationsAnciennes();
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
	private ResultSet RequeteTableauAncieneLocation() throws SQLException {
		ResultSet retourRequete = null;
		Requete requete = new Requetes.Requete();
		String texteSQL = "select lieuxdelocations.libelle, lieuxdelocations.adresse, locataire.nom, locataire.prenom,contrat.datedepart, loue.montantreglerduloyer, loue.montantloyer, loue.modepaiement, factureeau.total, documentcontrat.pdf\r\n"
				+ "from factureeau, locataire, relie, lieuxdelocations, loue, contrat, documentcontrat, rattacher, bati\r\n"
				+ "where locataire.idlocataire = relie.idlocataire\r\n"
				+ "                        and contrat.idcontrat = documentcontrat.idcontrat\r\n"
				+ "                        and relie.idcontrat = contrat.idcontrat\r\n"
				+ "                        and contrat.idcontrat = loue.idcontrat\r\n"
				+ "                        and lieuxdelocations.idlogement = loue.idlogement\r\n"
				+ "                        and bati.codepostal = lieuxdelocations.codepostal\r\n"
				+ "                        and bati.adresse = lieuxdelocations.adresse\r\n"
				+ "                        and bati.codepostal = rattacher.codepostal\r\n"
				+ "                        and bati.adresse = rattacher.adresse\r\n"
				+ "                        and rattacher.numfact = factureeau.numfact\r\n"
				+ "                        and rattacher.siren = factureeau.siren\r\n"
				+ "                        and contrat.datedepart is not null\r\n"
				+ "                        and TO_CHAR(contrat.datedepart, 'MM/YYYY') >= TO_CHAR(loue.datelocation, 'MM/YYYY')\r\n"
				+ "                        and TO_CHAR(ADD_MONTHS(contrat.datedepart,-1), 'MM/YYYY') <= TO_CHAR(loue.datelocation, 'MM/YYYY');";
		retourRequete = requete.requeteSelection(texteSQL);
		return retourRequete;
	}
	
	public LocationsAnciennes() {
		setTitle("Anciennes locations");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 480);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 50, 916, 270);
		contentPane.add(scrollPane);
		
		final String[] columns = {"location", "locataire", "Date fin", "montant r\u00E9gler loyer", "Montant loyer", "moyen de paiement", "Facture d'eau", "pdf contrat"};
		scrollPane.setViewportView(tableAncienneLocation);
		
		final DefaultTableModel model = new DefaultTableModel(columns, 0);
		tableAncienneLocation = new JTable(model);
		tableAncienneLocation.setRowSelectionAllowed(false);
		tableAncienneLocation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAncienneLocation.setSurrendersFocusOnKeystroke(true);
		
		try {
			ResultSet rsEnsLocation = RequeteTableauAncieneLocation();
			rsEnsLocation.next();
			while (rsEnsLocation.next()) {
				String location = rsEnsLocation.getString(1);
				location += " ";
				location += rsEnsLocation.getString(2);
				String locataire = rsEnsLocation.getString(3);
				locataire += " ";
				locataire += rsEnsLocation.getString(4);
				Date resdatedepart = rsEnsLocation.getDate(5);
				String datedepart = String.valueOf(resdatedepart);
				int resmontantregler = rsEnsLocation.getInt(6);
				String montantregler = String.valueOf(resmontantregler);
				int resmontantotal = rsEnsLocation.getInt(7);
				String montanttotal = String.valueOf(resmontantotal);
				String moyenpaiement = rsEnsLocation.getString(8);
				int resfacteau = rsEnsLocation.getInt(9);
				String facteau = String.valueOf(resfacteau);
				//a modifier pour faire en sorte que ce soit un bouton qui renvoie vers le pdf du fichier
				String pdf = rsEnsLocation.getString(10);
				model.addRow(new String[]{location, locataire,datedepart, montantregler, montanttotal, moyenpaiement, facteau, pdf});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.setViewportView(tableAncienneLocation);
		
		JLabel TitreAnciennesLocations = new JLabel("Anciennes locations");
		TitreAnciennesLocations.setFont(new Font("Tahoma", Font.BOLD, 20));
		TitreAnciennesLocations.setBounds(22, 10, 431, 30);
		contentPane.add(TitreAnciennesLocations);
		
<<<<<<< Updated upstream
		JButton ButtonInserer = new JButton("Insérer");
		ButtonInserer.addActionListener(this);
		ButtonInserer.setBounds(75, 370, 100, 25);
		contentPane.add(ButtonInserer);
		
		JButton ButtonMiseJour = new JButton("Mise à  jour");
		ButtonMiseJour.addActionListener(this);
		ButtonMiseJour.setBounds(305, 370, 100, 25);
		contentPane.add(ButtonMiseJour);
		
		JButton ButtonSupprimer = new JButton("Supprimer");
		ButtonSupprimer.addActionListener(this);
		ButtonSupprimer.setBounds(550, 370, 100, 25);
		contentPane.add(ButtonSupprimer);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.addActionListener(this);
		ButtonAnnuler.setBounds(790, 370, 100, 25);
=======
		JButton ButtonInserer = new JButton("Inserer");
		ButtonInserer.addActionListener(this);
		ButtonInserer.setBounds(123, 376, 85, 21);
		contentPane.add(ButtonInserer);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.addActionListener(this);
		ButtonAnnuler.setBounds(771, 376, 85, 21);
>>>>>>> Stashed changes
		contentPane.add(ButtonAnnuler);
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
<<<<<<< Updated upstream
=======
			
			case "Anciens entretiens":
				this.dispose();
				//new EntretiensAnciens().setVisible(true);
				break;
>>>>>>> Stashed changes
				
			case "Entretiens des parties communes":
				this.dispose();
<<<<<<< Updated upstream
				new EntretiensPartiesAnciens().setVisible(true);
=======
				//new EntretiensEnCours().setVisible(true);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
				
			case "Insérer":
=======

			case "Inserer":
>>>>>>> Stashed changes
				this.dispose();
				new NouvelleLocation().setVisible(true);
				break;
			
<<<<<<< Updated upstream
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
				
				
=======
>>>>>>> Stashed changes
			case "Annuler":
				this.dispose();
				new Accueil().setVisible(true);
				break;
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}