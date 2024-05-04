package Autonoleggio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GestioneAutonoleggio {
	private HashMap<String, AutoNoleggiabile> parcoAuto = new HashMap<>();
	private HashMap<String, Utente> listaUtenti = new HashMap<>();
	private HashMap<String, Batmobile> listaBatmobili = new HashMap<>();
	private HashMap<String, AutoNoleggiata> listaAutoNoleggiate = new HashMap<>();
	private HashMap<Integer, StoricoAuto> listaFatture = new HashMap<>();

	public GestioneAutonoleggio(HashMap<String, AutoNoleggiabile> parcoAuto, HashMap<String, Utente> listaUtenti,
			HashMap<String, Batmobile> listaBatmobili) {
		this.parcoAuto = parcoAuto;
		this.listaUtenti = listaUtenti;
		this.listaBatmobili = listaBatmobili;
	}

	public GestioneAutonoleggio() {

	}

	private final static String fileAuto = "src" + File.separator + "file" + File.separator + "auto.txt";
	private final static String fileStoricoAuto = "src" + File.separator + "file" + File.separator + "storicoAuto.txt";
	private final static String fileAutoNoleggiate = "src" + File.separator + "file" + File.separator
			+ "autoNoleggiate.txt";
	private final static String fileBatmobili = "src" + File.separator + "file" + File.separator + "batmobili.txt";
	private final static String fileUtenti = "src" + File.separator + "file" + File.separator + "utenti.txt";
	String[] cString = new String[2];
	double[] cDouble = new double[2];
	ConsoleManage cm = new ConsoleManage();

	public void cercaAutoCostoDisp() {
		double costo = 0;
		cDouble = (cm.giveDouble("Inserisci il costo orario massimo dell'auto che vuoi cercare",
				"Non è stato riconosciuto come un numero", "Non è stato inserito un numero", 3));
		if (cString[0].equals("1")) {
			costo = cDouble[1];
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().isDisponibile() == true && entry.getValue().getCostoOrario() <= costo) {
				System.out.println(entry.getValue().toStringFormat());
			}
		}
	}

	public void stampaAutoDisp() {
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {

			if (entry.getValue().isDisponibile() == true) {
				System.out.println(entry.getValue().toStringFormat());
			}
		}
	}

	public void cercaAutoMarcaDisp() {
		String marca = null;
		cString = (cm.giveString("Inserisci la marca per la quale vuoi filtrare",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			marca = cString[1].toUpperCase();
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {

			if (entry.getValue().isDisponibile() == true && entry.getValue().getMarca().equals(marca)) {
				System.out.println(entry.getValue().toStringFormat());
			}
		}
	}

	public void stampaAuto() {
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {

			System.out.println(
					entry.getValue().toStringFormat() + " --- Disponibilità: " + entry.getValue().isDisponibile());
		}
	}

	public void noleggia(String mail) {
		boolean targaFound = false;
		String targa = null;
		LocalDateTime fineNoleggio = LocalDateTime.now();
		int hours = 0;
		String conferma = "N";
		cString = (cm.giveString("Inserisci la targa dell'auto che vuoi noleggiare",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			targa = cString[1].toUpperCase();
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().isDisponibile() == true && entry.getValue().getTarga().equals(targa)) {
				targaFound = true;
				int[] cNumbers = new int[2];
				cNumbers = (cm.giveInt("Inserisci il numero di ore per le quali vuoi affittare questa automobile.",
						"Non è stato riconosciuto come numero", "Non è stao inserito un numero", 3));
				if (cNumbers[0] == 1) {
					hours = cNumbers[1];
				}
				fineNoleggio = LocalDateTime.now().plusHours(hours);
				System.out.println(
						"Il costo per il noleggio è di: " + entry.getValue().getCostoOrario() * hours + " Euro.");
				String[] cMenu = new String[2];
				cMenu = (cm.giveMenuOption("Vuoi continuare? (Y/N)", "Non è stata riconosciuta come risposta valida",
						"Non è stata inserita una risposta valida", 3, "YN"));
				if (cMenu[0].equals("1")) {
					conferma = cMenu[1].toUpperCase();
				}
				if (conferma.equals("Y")) {
					entry.getValue().setDisponibile(false);
					listaAutoNoleggiate.put(targa, new AutoNoleggiata(mail, targa, LocalDateTime.now(), fineNoleggio,
							entry.getValue().getCostoOrario() * hours));
					salvaAuto();
					salvaAutoNoleggiate();
				}
			}
		}
		if (!targaFound)
			System.out.println(cm.messaggioErrore(
					"La targa inserita non è stata trovata, l'automobile potrebbe essere non disponibile o inesistente."));
	}

	public void rimuoviAuto() {
		String targa = null;
		cString = (cm.giveString("Inserisci la targa dell'automobile che vuoi rimuovere",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			targa = cString[1].toUpperCase();
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().getTarga().toUpperCase().equals(targa)) {
				parcoAuto.remove(targa);
				break;
			}
		}
		salvaAuto();
	}

	public void restituisciAuto() {
		String targa = null;
		cString = (cm.giveString("Inserisci la targa dell'automobile che è stata restituita",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			targa = cString[1];
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().getTarga().toUpperCase().equals(targa)) {
				entry.getValue().setDisponibile(true);
				for (Map.Entry<String, AutoNoleggiata> entry2 : listaAutoNoleggiate.entrySet()) {
					if (entry2.getValue().getTarga().toUpperCase().equals(targa)) {
						listaFatture.put(listaFatture.size() + 1,
								new StoricoAuto(listaFatture.size() + 1, entry2.getValue().getEmailAffidatario(), targa,
										entry2.getValue().getInizioNoleggio(), entry2.getValue().getFineNoleggio(),
										entry2.getValue().getPagamento()));
						listaAutoNoleggiate.remove(targa);
					}
					salvaAuto();
					salvaAutoNoleggiate();
					salvaStoricoAuto();
				}
			}
		}
	}

	public void stampaAutoNoleggiateCliente(String myMail) {
		for (Map.Entry<String, AutoNoleggiata> entry : listaAutoNoleggiate.entrySet()) {
			if (entry.getValue().getEmailAffidatario().equals(myMail)) {
				System.out.println(entry.getValue().toStringFormat());
			}
		}

	}

	public void stampaAutoNoleggiate() {
		for (Map.Entry<String, AutoNoleggiata> entry : listaAutoNoleggiate.entrySet()) {

			System.out.println(entry.getValue().toStringFormat());

		}
	}

	public void aggiungiUtenti() {
		String nome = "";
		String cognome = "";
		String mail = "";
		String pass = "";
		String cpass = "";
		cString = (cm.giveString("Inserisci il tuo nome", "Non è stata riconosciuta come stringa",
				"Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			nome = cString[1].toUpperCase();
		}
		if (!nome.equals("")) {
			cString = (cm.giveString("Inserisci il tuo cognnome", "Non è stata riconosciuta come stringa",
					"Non è stata inserita una stringa", 3));
			if (cString[0].equals("1")) {
				cognome = cString[1].toUpperCase();
			}
			if (!cognome.equals("")) {
				String[] cMail = new String[2];
				cMail = (cm.giveMail("Inserisci un indirizzo Email", "Non è stato riconosciuto come un indirizzo Email",
						"Non è stao inserito un indirizzo Email", 3));
				if (cMail[0] == "1") {
					mail = cMail[1].toUpperCase();
				}
				if (!mail.equals("")) {
					while (true) {
						cString = (cm.giveString("Inserisci la tua password", "Non è stata riconosciuta come stringa",
								"Non è stata inserita una stringa", 3));
						if (cString[0].equals("1")) {
							pass = cString[1];
						}
						cString = (cm.giveString("Conferma la tua password", "Non è stata riconosciuta come stringa",
								"Non è stata inserita una stringa", 3));
						if (cString[0].equals("1")) {
							cpass = cString[1];
						}
						if (!pass.equals("")) {
							if (cpass.equals(pass)) {
								break;
							} else {
								System.out.println("Non hai confermato la password");
							}
						}
						listaUtenti.put(mail, new Utente(nome, cognome, mail, pass, "Cliente"));
						salvaUtenti();
					}
				}
			}
		}
	}

	public void aggiungiAuto() {
		String marca = "";
		String modello = "";
		String targa = "";
		double costo = 0;
		cString = (cm.giveString("Inserisci la marca dell'auto che vuoi aggiungere",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			marca = cString[1].toUpperCase();
		}
		if (!marca.equals("")) {
			cString = (cm.giveString("Inserisci il modello dell'auto che vuoi aggiungere",
					"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
			if (cString[0].equals("1")) {
				modello = cString[1].toUpperCase();
			}
			if (!modello.equals("")) {
				cString = (cm.giveString("Inserisci la targa dell'auto che vuoi aggiungere",
						"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
				if (cString[0].equals("1")) {
					targa = cString[1].toUpperCase();
				}
				if (!targa.equals("")) {
					cDouble = (cm.giveDouble("Inserisci il costo orario dell'auto che vuoi aggiungere",
							"Non è stato riconosciuto come costo", "Non è stato inserito un costo", 3));
					if (cString[0].equals("1")) {
						costo = cDouble[1];
					}
					if (costo != 0) {
						parcoAuto.put(targa, new AutoNoleggiabile(marca, modello, targa, true, costo));
						salvaAuto();
					}
				}
			}
		}
	}

	public void cambiaStatoAuto() {
		String targa = null;
		cString = (cm.giveString("Inserisci la targa dell'automobile alla quale vuoi cambiare stato",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			targa = cString[1];
		}
		for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
			if (entry.getValue().getTarga().toUpperCase().equals(targa)) {
				entry.getValue().setDisponibile(!entry.getValue().isDisponibile());
				salvaAuto();
			}

		}
	}

	public void stampaClienti() {
		for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {

			if (entry.getValue().getRuolo().equals("Cliente")) {
				System.out.println(entry.getValue().toStringFormat());
			}
		}
	}

	public void stampaBatmobili() {
		for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {

			System.out.println(entry.getValue().toStringFormat());
		}
	}

	public void aggiungiBatmobile() {
		String marca = "";
		String modello = "";
		String targa = "";
		String nome = "";
		String accessori = "";
		cString = (cm.giveString("Inserisci la marca della Batmobile che vuoi aggiungere",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			marca = cString[1].toUpperCase();
		}
		if (!marca.equals("")) {
			cString = (cm.giveString("Inserisci il modello della Batmobile che vuoi aggiungere",
					"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
			if (cString[0].equals("1")) {
				modello = cString[1].toUpperCase();
			}
			if (!modello.equals("")) {
				cString = (cm.giveString("Inserisci la targa della Batmobile che vuoi aggiungere",
						"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
				if (cString[0].equals("1")) {
					targa = cString[1].toUpperCase();
				}
				if (!targa.equals("")) {
					cString = (cm.giveString("Inserisci il nome della Batmobile che vuoi aggiungere",
							"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
					if (cString[0].equals("1")) {
						nome = cString[1].toUpperCase();
					}
					if (!nome.equals("")) {
						cString = (cm.giveString("Inserisci gli accessori della Batmobile che vuoi aggiungere",
								"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
						if (cString[0].equals("1")) {
							accessori = cString[1].toUpperCase();
						}
						if (!accessori.equals("")) {
							listaBatmobili.put(targa, new Batmobile(marca, modello, targa, nome, accessori));
							salvaBatmobili();
						}
					}
				}
			}
		}
	}

	public void rimuoviBatmobile() {
		String targa = null;
		cString = (cm.giveString("Inserisci la targa dell'automobile che vuoi rimuovere",
				"Non è stata riconosciuta come stringa", "Non è stata inserita una stringa", 3));
		if (cString[0].equals("1")) {
			targa = cString[1].toUpperCase();
		}
		for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
			if (entry.getValue().getTarga().toUpperCase().equals(targa)) {
				listaBatmobili.remove(targa);
				break;
			}
		}
		salvaBatmobili();
	}

	public void stampaStoricoAuto() {
		for (Map.Entry<Integer, StoricoAuto> entry : listaFatture.entrySet()) {
			System.out.println(entry.getValue().toStringFormat());
		}
	}

	public void salvaAuto() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileAuto));
			for (Map.Entry<String, AutoNoleggiabile> entry : parcoAuto.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvaBatmobili() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileBatmobili));
			for (Map.Entry<String, Batmobile> entry : listaBatmobili.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvaUtenti() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileUtenti));
			for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvaAutoNoleggiate() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileAutoNoleggiate));
			for (Map.Entry<String, AutoNoleggiata> entry : listaAutoNoleggiate.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void salvaStoricoAuto() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileStoricoAuto));
			for (Map.Entry<Integer, StoricoAuto> entry : listaFatture.entrySet()) {
				bw.write(entry.getValue().toString());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaAuto() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileAuto));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] entry = linea.split(",");
				parcoAuto.put(entry[2], new AutoNoleggiabile(entry[0], entry[1], entry[2],
						Boolean.parseBoolean(entry[3]), Double.parseDouble(entry[4])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaBatmobili() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileBatmobili));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] entry = linea.split(",");
				listaBatmobili.put(entry[2], new Batmobile(entry[0], entry[1], entry[2], entry[3], entry[4]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaUtenti() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileUtenti));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] entry = linea.split(",");
				listaUtenti.put(entry[2], new Utente(entry[0], entry[1], entry[2], entry[3], entry[4]));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaAutoNoleggiate() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileAutoNoleggiate));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] entry = linea.split(",");
				listaAutoNoleggiate.put(entry[1], new AutoNoleggiata(entry[0], entry[1], LocalDateTime.parse(entry[2]),
						LocalDateTime.parse(entry[3]), Double.parseDouble(entry[4])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void caricaStoricoAuto() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileStoricoAuto));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] entry = linea.split(",");
				listaFatture.put(Integer.parseInt(entry[0]),
						new StoricoAuto(Integer.parseInt(entry[0]), entry[1], entry[2], LocalDateTime.parse(entry[3]),
								LocalDateTime.parse(entry[4]), Double.parseDouble(entry[5])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void logMenu() {
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption("Cosa desideri fare? \nA)Log In \nB)Registrarsi\nC)Esci",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABC"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				String myMail = logIn();
				for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
					if (entry.getValue().getEmail().equals(myMail)) {
						String myRole = entry.getValue().getRuolo();
						mainMenu(myMail, myRole);
					}
				}

				break;
			}
			case "B": {
				aggiungiUtenti();
				break;
			}
			case "C": {
				return;
			}
			default:
				break;
			}

		}
	}

	public String logIn() {
		String myMail = "";
		String myPass = "";
		boolean mailFound = false;
		String[] cMail = new String[2];
		cMail = (cm.giveMail("Inserisci il tuo indirizzo Email", "Non è stato riconosciuto come un indirizzo Email",
				"Non è stao inserito un indirizzo Email", 3));
		if (cMail[0] == "1") {
			myMail = cMail[1].toUpperCase();
		}
		for (Map.Entry<String, Utente> entry : listaUtenti.entrySet()) {
			if (entry.getValue().getEmail().equals(myMail)) {
				mailFound = true;
				cString = (cm.giveString("Inserisci la tua password", "Non è stata riconosciuta come stringa",
						"Non è stata inserita una stringa", 3));
				if (cString[0].equals("1")) {
					myPass = cString[1];
				}
				if (entry.getValue().getPassword().equals(myPass)) {
					System.out.println(cm.greenMessage("Benvenuto " + entry.getValue().getNome()));
					return myMail;
				} else
					System.out.println(cm.messaggioErrore("La password inserita non è corretta."));
			}
		}
		if (!mailFound)
			System.out.println(cm.messaggioErrore("L'indirizzo Email inserito non è presente nel nostro database."));
		return "";

	}

	public void mainMenu(String myMail, String myRole) {
		switch (myRole) {
		case "Cliente": {
			mainMenuCliente(myMail);
			break;
		}
		case "Manager": {
			mainMenuManager();
			break;
		}
		case "Batman": {
			mainMenuBatman();
			break;
		}
		default:
			break;
		}
	}

	private void mainMenuBatman() {
		System.out.println("SONO BATMAN");
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption(
					"Cosa vuoi fare \nA)Consulta la lista delle batmobili \nB)Aggiungi una batmobile\nC)Rimuovi una batmobile\nD)Esci",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABCD"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				stampaBatmobili();
				break;
			}
			case "B": {
				aggiungiBatmobile();
				break;
			}
			case "C": {
				rimuoviBatmobile();
				break;
			}
			case "D": {
				return;
			}
			default:
				break;
			}
		}
	}

	private void mainMenuManager() {
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption(
					"Cosa vuoi fare \nA)Opzioni Automobili\nB)Consulta la lista dei clienti\nC)Consulta lo storico delle automobili.\nD)Esci.",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABCD"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				OpzioniAutomobili();
				break;
			}
			case "B": {
				stampaClienti();
				break;
			}
			case "C": {
				stampaStoricoAuto();
				break;
			}
			case "D": {
				return;
			}
			default:
				break;
			}
		}
	}

	private void OpzioniAutomobili() {
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption(
					"Cosa vuoi fare \nA)Consulta la lista delle automobili.\nB)Aggiungi un'automobile.\nC)Rimuovi un'automobile.\nD)Accetta una riconsegna.\nE)Consulta la lista delle automobili noleggiate.\nF)Cambia lo stato di un'auto.\nG)Esci.",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABCDEFG"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				stampaAuto();
				break;
			}
			case "B": {
				aggiungiAuto();
				break;
			}
			case "C": {
				rimuoviAuto();
				break;
			}
			case "D": {
				restituisciAuto();
				break;
			}
			case "E": {
				stampaAutoNoleggiate();
				break;
			}
			case "F": {
				cambiaStatoAuto();
				break;
			}
			case "G": {
				return;
			}
			default:
				break;
			}
		}
	}

	private void mainMenuCliente(String myMail) {
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption("Cosa desideri fare? \nA)Accedi alle liste di auto \nB)Noleggia un'auto\nC)Esci",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABC"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				listeAuto(myMail);
				break;
			}
			case "B": {
				noleggia(myMail);
				break;
			}
			case "C": {
				return;
			}
			default:
				break;
			}

		}
	}

	private void listeAuto(String myMail) {
		while (true) {
			String menu = null;
			ConsoleManage cm = new ConsoleManage();
			String[] cMenu = new String[2];
			cMenu = (cm.giveMenuOption(
					"Quale lista auto vuoi consultare? \nA)Tutte le auto disponibili \nB)Le auto che hai noleggiato\nC)Tutte le auto sotto un certo costo orario\nD)Tutte le auto di una certa marca\nE)Esci",
					"Non è stata riconosciuta come risposta valida", "Non è stata inserita una risposta valida", 3,
					"ABCDE"));
			if (cMenu[0].equals("1")) {
				menu = cMenu[1].toUpperCase();
			} else
				break;
			switch (menu) {
			case "A": {
				stampaAutoDisp();
				break;
			}
			case "B": {
				stampaAutoNoleggiateCliente(myMail);
				break;
			}
			case "C": {
				cercaAutoCostoDisp();
				break;
			}
			case "D": {
				cambiaStatoAuto();
				break;
			}
			case "E": {
				return;
			}
			default:
				break;
			}

		}
	}
}