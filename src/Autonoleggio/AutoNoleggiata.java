package Autonoleggio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AutoNoleggiata {
	private String emailAffidatario;
	private String targa;
	private LocalDateTime inizioNoleggio;
	private LocalDateTime fineNoleggio;
	private double pagamento;



	public AutoNoleggiata(String emailAffidatario, String targa, LocalDateTime inizioNoleggio, LocalDateTime fineNoleggio,
			double pagamento) {
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

	@Override
	public String toString() {
		return getEmailAffidatario() + "," + getTarga() + ","+ getInizioNoleggio()+","+getFineNoleggio()+","+getPagamento();
	}
	public String toStringFormat() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return "Affidatario: " +getEmailAffidatario()+ " --- Targa: "+getTarga()+" --- Inizio Noleggio: " + getInizioNoleggio().format(formatter)+ " --- Fine Noleggio: "+ getFineNoleggio().format(formatter)+ " --- Pagamento Euro: "+ getPagamento(); 
	}

	}
