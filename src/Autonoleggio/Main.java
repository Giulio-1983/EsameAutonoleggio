package Autonoleggio;

public class Main {
	public static void main(String[] args) {

		GestioneAutonoleggio ga = new GestioneAutonoleggio();
		ga.caricaBatmobili();
		ga.caricaStoricoAuto();
		ga.caricaAutoNoleggiate();
		ga.caricaAuto();
		ga.caricaUtenti();
		ga.logMenu();
	}
}
