package vue.insertion;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

public class NouveauContrat extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textFieldDate;
	private JTextField textFieldCaution;
	private JLabel LabelLibelleLocataire;
	private JCheckBox chckbxColocataire ;
	private JButton ButtonColocationPlus;
	private JButton ButtonColocationMoins;
	private int nbLocataire = 1;
	private JComboBox<String> comboBoxTypeLocation;
	private JComboBox<String> comboBoxLocataire;
	private String locataire;
	private String idLocataire;
	private boolean locataireSelected;
	private String typeLocation;
	private NouveauContrat frame;
	private int nbTableRows;
	private ArrayList<String> tableAllSelectedData = new ArrayList<String>();
	private String lastIDContrat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NouveauContrat frame = new NouveauContrat();
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

	
	public NouveauContrat() {
		setTitle("Nouveau contrat");
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
		
		textFieldDate = new JTextField();
		textFieldDate.setBounds(136, 130, 75, 20);
		contentPane.add(textFieldDate);
		textFieldDate.setColumns(10);
		
		JLabel LabelDateMiseEnEffet = new JLabel("* Date de mise en effet :");
		LabelDateMiseEnEffet.setBounds(11, 130, 132, 14);
		contentPane.add(LabelDateMiseEnEffet);
		
		textFieldCaution = new JTextField();
		textFieldCaution.setColumns(10);
		textFieldCaution.setBounds(136, 166, 132, 20);
		contentPane.add(textFieldCaution);
		
		JLabel LabelCaution = new JLabel("Caution :");
		LabelCaution.setBounds(11, 165, 132, 14);
		contentPane.add(LabelCaution);
		
		JLabel LabelType = new JLabel("Type de location :");
		LabelType.setBounds(11, 198, 132, 14);
		contentPane.add(LabelType);
		
		JButton ButtonAjouter = new JButton("Ajouter");
		ButtonAjouter.setBounds(307, 384, 132, 23);
		ButtonAjouter.addActionListener(this);
		contentPane.add(ButtonAjouter);
		
		JButton ButtonAnnuler = new JButton("Annuler");
		ButtonAnnuler.setBounds(49, 384, 132, 23);
		ButtonAnnuler.addActionListener(this);
		contentPane.add(ButtonAnnuler);
		
		JLabel LabelContrat = new JLabel("Nouveau contrat");
		LabelContrat.setFont(new Font("Tahoma", Font.BOLD, 20));
		LabelContrat.setBounds(24, 0, 307, 41);
		contentPane.add(LabelContrat);
		
		JComboBox comboBoxTypeLocation = new JComboBox();
		comboBoxTypeLocation.setBounds(136, 197, 182, 22);
		contentPane.add(comboBoxTypeLocation);
		ArrayList<String> typeLoc = new ArrayList<String>();
		typeLoc.add("Appartement");
		typeLoc.add("Maison");
		typeLoc.add("Autres...");
		int indexTypeLoc = 0;
		while ( indexTypeLoc < typeLoc.size()) {
			comboBoxTypeLocation.addItem(typeLoc.get(indexTypeLoc));
			indexTypeLoc++;
		}
		comboBoxTypeLocation.addActionListener(new ActionListener() {
		      @Override
		      public void actionPerformed(ActionEvent e) {
		        JComboBox jcmbType = (JComboBox) e.getSource();
		        typeLocation = (String) jcmbType.getSelectedItem();
		        System.out.println(typeLocation);
		      }
		    });		
		
		this.LabelLibelleLocataire = new JLabel("* Locataires :");
		LabelLibelleLocataire.setBounds(10, 270, 132, 14);
		contentPane.add(LabelLibelleLocataire);
		
		JButton ButtonNouveauLocataire = new JButton("Nouveau Locataire");
		ButtonNouveauLocataire.setBounds(322, 270, 132, 23);
		ButtonNouveauLocataire.addActionListener(this);
		contentPane.add(ButtonNouveauLocataire);
				
		JButton ButtonNouveauDocument = new JButton("Nouveau Document");
		ButtonNouveauDocument.setBounds(322, 100, 132, 23);
		ButtonNouveauDocument.addActionListener(this);
		contentPane.add(ButtonNouveauDocument);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 313, 405, 70);
		contentPane.add(scrollPane);
		
		DefaultTableModel tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		tableModel.addColumn("Locataires");
		try {
			ResultSet rsTable = RequeteInsertion.RequeteGetLocataire();
			int i = 0;
			rsTable.next();
			while ( i < rsTable.getRow()) {
				String idLocataire = rsTable.getString("idloc");
				String locNom = rsTable.getString("locNom");
				String locPrenom = rsTable.getString("locPrenom");
				tableModel.insertRow(0, new Object[] { " (" +idLocataire +") "+ locNom + " | " +  locPrenom  });
				rsTable.next();
				}
			System.out.println(nbTableRows);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent mouseEvent) {
		        JTable table =(JTable) mouseEvent.getSource();
		        Point point = mouseEvent.getPoint();
		        int row = table.rowAtPoint(point);
		        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
		        	 String selectedData = null;
		    	        int[] selectedRow = table.getSelectedRows();
		    	        int[] selectedColumns = table.getSelectedColumns();
		    	        for (int i = 0; i < selectedRow.length; i++) {
		    	          for (int j = 0; j < selectedColumns.length; j++) {
		    	            selectedData = (String) table.getValueAt(selectedRow[i], selectedColumns[j]);
		    	          }
		    	        }	    	        
		    	        if (!tableAllSelectedData.contains(selectedData)) {
					        	tableAllSelectedData.add(selectedData);
					        System.out.println("Selected: " + selectedData.substring(selectedData.lastIndexOf("(")+1,selectedData.lastIndexOf(")")));
						} else {
								tableAllSelectedData.remove(selectedData);
								System.out.println("Unselected: " + selectedData.substring(selectedData.lastIndexOf("(")+1,selectedData.lastIndexOf(")")));
						}
		        }
		    }
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(310);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);
	

	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
			case "Ajouter":				
				String DATEMISEENEFFET = textFieldDate.getText();
				float CAUTION = Float.parseFloat(textFieldCaution.getText());
				String TYPELOC = this.typeLocation;
				try {				
					RequeteInsertion.RequeteInsertContrat(DATEMISEENEFFET, CAUTION, TYPELOC) ;
					JOptionPane.showMessageDialog(frame, "Nouveau contrat inséré.");
				} catch (SQLException e3) {
					e3.printStackTrace();
				}			
				if(tableAllSelectedData.isEmpty()) { 
					this.dispose();
					new Accueil().setVisible(true);
					break;
				} else {
				      for (int counter = 0; counter < tableAllSelectedData.size(); counter++) { 		     
				          String idLoc =(tableAllSelectedData.get(counter).substring(tableAllSelectedData.get(counter).lastIndexOf("(")+1,tableAllSelectedData.get(counter).lastIndexOf(")"))); 		
						try {
							 String idContrat = RequeteInsertion.RequeteLastIDContrat();
							 RequeteInsertion.RequeteInsertRelie(idLoc,idContrat);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}				         
				      }   	
						this.dispose();
						new Accueil().setVisible(true);
						break;
				}			
			case "Nouveau Document":
				new NouveauDocumentContrat().setVisible(true);
				break;
			case "Annuler":
				this.dispose();
				new Accueil().setVisible(true);
				break;
			case "Nouveau Locataire":
				new NouveauLocataire().setVisible(true);
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
       
			default:
				System.out.println("Choix incorrect");
				break;
		}
	}
}