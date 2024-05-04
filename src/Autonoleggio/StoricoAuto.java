package Autonoleggio;

import java.time.LocalDateTime;

public class StoricoAuto{
private int ID;
private String emailAffidatario;
private String targa;
private LocalDateTime inizioNoleggio;
private LocalDateTime fineNoleggio;
private double pagamento;



public StoricoAuto(int iD, String emailAffidatario, String targa, LocalDateTime inizioNoleggio, LocalDateTime fineNoleggio,
		double pagamento) {
	super();
	ID = iD;
	this.emailAffidatario = emailAffidatario;
	this.inizioNoleggio = inizioNoleggio;
	this.fineNoleggio = fineNoleggio;
	this.pagamento = pagamento;
	this.targa = targa;
}

public String getEmailAffidatario() {
	return emailAffidatario;
}
public void setEmailAffidatario(String emailAffidatario) {
	this.emailAffidatario = emailAffidatario;
}
public LocalDateTime getInizioNoleggio() {
	return inizioNoleggio;
}
public void setInizioNoleggio(LocalDateTime inizioNoleggio) {
	this.inizioNoleggio = inizioNoleggio;
}
public LocalDateTime getFineNoleggio() {
	return fineNoleggio;
}
public void setFineNoleggio(LocalDateTime fineNoleggio) {
	this.fineNoleggio = fineNoleggio;
}
public double getPagamento() {
	return pagamento;
}
public void setPagamento(double pagamento) {
	this.pagamento = pagamento;
}
public String getTarga() {
	return targa;
}
public void setTarga(String targa) {
	this.targa = targa;
}
public int getID() {
	return ID;
}
public void setID(int iD) {
	ID = iD;
}
@Override
public String toString() {
	return getID() + "," + getEmailAffidatario() + "," + getTarga() + ","+ getInizioNoleggio()+","+getFineNoleggio()+","+getPagamento();
}
public String toStringFormat() {
	return "ID: "+getID()+" --- Affidatario: " +getEmailAffidatario()+ " --- Targa: "+getTarga()+" --- Inizio Noleggio: " + getInizioNoleggio() + " --- Fine Noleggio: "+ getFineNoleggio()+ " --- Pagamento: "+ getPagamento(); 
}

}
