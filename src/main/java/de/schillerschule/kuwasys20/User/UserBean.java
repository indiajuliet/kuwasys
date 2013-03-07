package de.schillerschule.kuwasys20.User;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.schillerschule.kuwasys20.Database.DatabaseHandler;
import de.schillerschule.kuwasys20.Teacher.TeacherBean;

/**
 * Klasse für User-Handling im System
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean implements Serializable {

	private static Logger logger = Logger.getLogger(TeacherBean.class
			.getCanonicalName());

	private static final long serialVersionUID = 2L;

	// Property-Strings
	private String name;
	private String lastname;
	private String konfession;
	private String geb;
	private String gebDay;
	private String gebMonth;
	private String gebYear;
	private String klasse;

	// Default Strings: "schueler"
	public static String role = "schueler";

	public UserBean() {
	}

	// Get-Methoden
	/**
	 * Vorname
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Nachname
	 * 
	 * @return lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Konfession
	 * 
	 * @return konfession
	 */
	public String getKonfession() {
		return konfession;
	}

	/**
	 * Klasse
	 * 
	 * @return klasse
	 */
	public String getKlasse() {
		return klasse;
	}

	/**
	 * Geburtstdatum Tag
	 * 
	 * @return gebDay
	 */
	public String getGebDay() {
		return gebDay;
	}

	/**
	 * Geburtstdatum Monat
	 * 
	 * @return gebMonth
	 */
	public String getGebMonth() {
		return gebMonth;
	}

	/**
	 * Geburtstdatum Tag Jahr
	 * 
	 * @return gebYear
	 */
	public String getGebYear() {
		return gebYear;
	}

	/**
	 * Geburtstdatum Tag Jahr
	 * 
	 * @return geb
	 */
	public String getGeb() {
		return geb;
	}

	// Set-Methoden
	/**
	 * Vorname
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Nachname
	 * 
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Konfession
	 * 
	 * @param konfession
	 */
	public void setKonfession(String konfession) {
		this.konfession = konfession;
	}

	/**
	 * Klasse
	 * 
	 * @param klasse
	 */
	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	/**
	 * Geburtsdatum
	 * 
	 * @param gebDay
	 *            , gebMonth, gebYear
	 * 
	 */
	public void setGebDay(String gebDay) {
		this.gebDay = gebDay;
	}

	public void setGebMonth(String gebMonth) {
		this.gebMonth = gebMonth;
	}

	public void setGebYear(String gebYear) {
		this.gebYear = gebYear;
	}

	public void setGeb(String geb) {
		this.geb = geb;
	}

	/**
	 * Neuen User anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String sendUser() {

		// DEBUG
		System.out.println("Klasse: " + klasse);
		System.out.println("Nachname: " + name);
		System.out.println("Vorname: " + lastname);
		System.out.println("Geburtstag: " + geb);
		System.out.println("Konfession: " + konfession);
		
		geb = gebYear + gebMonth + gebDay; // Geburtstag formatieren

		DatabaseHandler.SQLConnection();
		DatabaseHandler.addUser(klasse, lastname, name, geb, konfession, role);
		DatabaseHandler.SQLConnectionClose();
		
		logger.info("Schüler: " + name + " " + lastname + " angelegt");
		return "useraddsuccess";
	}

	/**
	 * Username des aktuellen Users zurückgeben
	 * 
	 * @return username
	 */
	public String showUsername() {
		String username = DatabaseHandler.showUserFullName();
		return username;
	}

}
