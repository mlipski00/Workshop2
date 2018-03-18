package workshop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application {

	public static void main(String[] args) {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop2?useSSL=false", "root",
					"coderslab");
			User user = new User("Imię1", "email@email.com", "hasło");
			user.saveToDB(conn);
			user.saveToDB(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}



	}

}
