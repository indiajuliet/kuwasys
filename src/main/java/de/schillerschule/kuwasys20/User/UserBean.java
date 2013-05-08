package de.schillerschule.kuwasys20.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.postgresql.translation.messages_bg;

import de.schillerschule.kuwasys20.Controller.kuwasysControllerBean;
import de.schillerschule.kuwasys20.Database.DatabaseHandler;

/**
 * Klasse für User-Handling im System
 * 
 * @author cy
 * 
 */
@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean implements Serializable {

	FacesContext context = FacesContext.getCurrentInstance();

	private static Logger logger = Logger.getLogger(UserBean.class
			.getCanonicalName());

	private static final long serialVersionUID = 2L;

	DatabaseHandler dbh = new DatabaseHandler();

	private List<User> users = new ArrayList<User>();

	private int id;
	private String nachname;
	private String vorname;
	private String geburtstag;
	private String konfession;
	private String klasse;
	private String username;
	private String passwort;
	private String passwortE;

	private String gebDay;
	private String gebMonth;
	private String gebYear;

	private String rolle;

	// Leere default Konstruktor
	public UserBean() {
	}

	public List<User> getUsers() {
		if (context.getExternalContext().isUserInRole("admin"))
			return dbh.listUsers();
		else
			return null;
	}

	public List<User> getSchedule() {
		if (context.getExternalContext().isUserInRole("admin"))
			return dbh.listClassesSchedule();
		if (context.getExternalContext().isUserInRole("lehrer"))
			return dbh.listClassesTeacherSchedule(dbh.getUserId());
		else
			return null;
	}

	public List<User> getTeacherClass() {
		if (context.getExternalContext().isUserInRole("admin"))
			return dbh.listClasses();
		if (context.getExternalContext().isUserInRole("lehrer"))
			return dbh.listClassesTeacher(dbh.getUserId());
		else
			return null;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getKlasse() {
		return klasse;
	}

	public void setKlasse(String klasse) {
		this.klasse = klasse;
	}

	public String getKonfession() {
		return konfession;
	}

	public void setKonfession(String konfession) {
		this.konfession = konfession;
	}

	public String getGeburtstag() {
		return geburtstag;
	}

	public void setGeburtstag(String geburtstag) {
		this.geburtstag = geburtstag;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public String getPasswortE() {
		return passwort;
	}

	public void setPasswortE(String passwortE) {
		this.passwortE = passwortE;
	}

	public void setRolle(String rolle) {
		rolle = "schueler";
		this.rolle = rolle;
	}

	public String getRolle() {
		return rolle;
	}

	// Helfer für Geburtstag Splitting
	public String getGebDay() {
		return gebDay;
	}

	public String getGebMonth() {
		return gebMonth;
	}

	public String getGebYear() {
		return gebYear;
	}

	public void setGebDay(String gebDay) {
		this.gebDay = gebDay;
	}

	public void setGebMonth(String gebMonth) {
		this.gebMonth = gebMonth;
	}

	public void setGebYear(String gebYear) {
		this.gebYear = gebYear;
	}

	/**
	 * Neuen User in DB anlegen
	 * 
	 * @return Facelet "useraddsuccess"
	 */
	public String sendUser() {

		String rolle = "schueler";

		// DEBUG
		System.out.println("Klasse: " + klasse);
		System.out.println("Nachname: " + vorname);
		System.out.println("Vorname: " + nachname);
		System.out.println("Geburtstag: " + geburtstag);
		System.out.println("Konfession: " + konfession);

		geburtstag = gebYear + gebMonth + gebDay; // Geburtstag formatieren

		dbh.SQLConnection();
		dbh.addUser(klasse, nachname, vorname, geburtstag, konfession, rolle);
		dbh.SQLConnectionClose();

		logger.info("Schüler: " + vorname + " " + nachname + " angelegt");
		return "studentaddsuccess";
	}

	/**
	 * Vorhandenen User in DB updaten
	 * 
	 * @return
	 */
	public String sendUserUpdate() {

		// TODO MESSAGE/LOG DEBUG
		System.out.println("Klasse: " + klasse);
		System.out.println("Nachname: " + vorname);
		System.out.println("Vorname: " + nachname);
		System.out.println("Konfession: " + konfession);

		// TODO Fix It!!!!
		// geburtstag = gebYear + gebMonth + gebDay; // Geburtstag formatieren

		dbh.updateUser(id, klasse, nachname, vorname, konfession);

		logger.info("Schüler: " + vorname + " " + nachname + " geändert!");
		return "kuwasys";
	}

	/**
	 * Username des aktuellen Users zurückgeben
	 * 
	 * @return username
	 */
	public String showUserFullName() {
		String username = dbh.showUserFullName();
		return username;
	}

	public String showUserFirstname() {
		String username = dbh.showUserFullName();
		return username;
	}

	public String showUserLastame() {
		String username = dbh.showUserFullName();
		return username;
	}

	public String showUserUsername() {
		String username = dbh.getUserUsername();
		return username;
	}

	public String showUserPassword() {
		String password = dbh.getUserPassword(dbh.getUserId());
		return password;
	}

	public String showUserClass() {
		String klasse = dbh.showUserClass(dbh.getUserId());
		return klasse;
	}

	public String addToUsers() {
		dbh.addToTeachers(klasse, nachname, vorname, geburtstag, konfession,
				rolle);
		return kuwasysControllerBean.goUsers();
	}

	public void addToUsers(User user) {
		users.add(user);
	}

	public void emptyUsers() {
		users.clear();

	}

	public String changePassword() {
		if (passwort.equals(passwortE)) {
			dbh.changePassword(dbh.getUserId(), passwort);
		} else {
			return "password_failed";
		}

		return "password_success";
	}

	/**
	 * Userdaten selektieren und bearbeiten
	 * 
	 * @return
	 */
	/*public String editUser() {
		vorname = dbh.getUserFirstname(_id);
		nachname = dbh.getUserLastname(_id);

		dbh.listEditorUser(_id);
		return kuwasysControllerBean.goUsereditor();
	}*/

	/**
	 * User-Klasse (Schüler)
	 * 
	 * @author cy
	 * 
	 */
	public static class User implements Serializable {

		DatabaseHandler dbh = new DatabaseHandler();
		private static final long serialVersionUID = 1L;

		private int _id;
		private String _nachname;
		private String _vorname;
		private String _geburtstag;
		private String _konfession;
		private String _klasse;
		private String _username;
		private String _passwort;
		private String _rolle; // default

		private String _termin1;
		private String _termin2;
		private String _termin3;
		private String _termin4;
		private String _termin5;
		private String _termin6;
		private String _termin7;
		private String _termin8;
		private String _termin9;
		private String _termin10;
				
		public User(int id, String vorname, String nachname, String geburtstag,
				String konfession, String klasse, String username,
				String passwort, String rolle) {

			_id = id;
			_nachname = nachname;
			_vorname = vorname;
			_geburtstag = geburtstag;
			_konfession = konfession;
			_klasse = klasse;
			_username = username;
			_passwort = passwort;
			_rolle = rolle;

		}

		public User(int id, String vorname, String nachname, String geburtstag,
				String konfession, String klasse, String username,
				String passwort, String rolle, String t1, String t2, String t3,
				String t4, String t5, String t6, String t7, String t8,
				String t9, String t10) {

			_id = id;
			_nachname = nachname;
			_vorname = vorname;
			_geburtstag = geburtstag;
			_konfession = konfession;
			_klasse = klasse;
			_username = username;
			_passwort = passwort;
			_rolle = rolle;
			set_termin1(t1);
			set_termin2(t2);
			set_termin3(t3);
			set_termin4(t4);
			set_termin5(t5);
			set_termin6(t6);
			set_termin7(t7);
			set_termin8(t8);
			set_termin9(t9);
			set_termin10(t10);

		}

		public int get_id() {
			return _id;
		}

		public void set_id(int _id) {
			this._id = _id;
		}

		public String get_nachname() {
			return _nachname;
		}

		public void set_nachname(String _nachname) {
			this._nachname = _nachname;
		}

		public String get_vorname() {
			return _vorname;
		}

		public void set_vorname(String _vorname) {
			this._vorname = _vorname;
		}

		public String get_geburtstag() {
			return _geburtstag;
		}

		public void set_geburtstag(String _geburtstag) {
			this._geburtstag = _geburtstag;
		}

		public String get_konfession() {
			return _konfession;
		}

		public void set_konfession(String _konfession) {
			this._konfession = _konfession;
		}

		public String get_klasse() {
			return _klasse;
		}

		public void set_klasse(String _klasse) {
			this._klasse = _klasse;
		}

		public String get_username() {
			return _username;
		}

		public void set_username(String _username) {
			this._username = _username;
		}

		public String get_passwort() {
			return _passwort;
		}

		public void set_passwort(String _passwort) {
			this._passwort = _passwort;
		}

		public void set_rolle(String _rolle) {
			_rolle = "schueler";
			this._rolle = _rolle;
		}

		public String get_rolle() {
			return _rolle;
		}

		public String get_termin1() {
			return _termin1;
		}

		public void set_termin1(String _termin1) {
			this._termin1 = _termin1;
		}

		public String get_termin2() {
			return _termin2;
		}

		public void set_termin2(String _termin2) {
			this._termin2 = _termin2;
		}

		public String get_termin3() {
			return _termin3;
		}

		public void set_termin3(String _termin3) {
			this._termin3 = _termin3;
		}

		public String get_termin4() {
			return _termin4;
		}

		public void set_termin4(String _termin4) {
			this._termin4 = _termin4;
		}

		public String get_termin5() {
			return _termin5;
		}

		public void set_termin5(String _termin5) {
			this._termin5 = _termin5;
		}

		public String get_termin6() {
			return _termin6;
		}

		public void set_termin6(String _termin6) {
			this._termin6 = _termin6;
		}

		public String get_termin7() {
			return _termin7;
		}

		public void set_termin7(String _termin7) {
			this._termin7 = _termin7;
		}

		public String get_termin8() {
			return _termin8;
		}

		public void set_termin8(String _termin8) {
			this._termin8 = _termin8;
		}

		public String get_termin9() {
			return _termin9;
		}

		public void set_termin9(String _termin9) {
			this._termin9 = _termin9;
		}

		public String get_termin10() {
			return _termin10;
		}

		public void set_termin10(String _termin10) {
			this._termin10 = _termin10;
		}

		/**
		 * Userdaten selektieren und bearbeiten
		 * 
		 * @return
		 */
		public String editUser() {
			String vornameEdit = dbh.getUserFirstname(_id);
			String nachnameEdit = dbh.getUserLastname(_id);
			String klasseEdit = dbh.showUserClass(_id);
			String konfessionEdit = dbh.getUserKonfession(_id);

			//dbh.listEditorUser(_id);
			return kuwasysControllerBean.goUsereditor();
		}
	}
}
