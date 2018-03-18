package workshop2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private int id;
	private String username;
	private String email;
	private String password;
	private int person_group_id;

	public User(String username, String email, String password) {

		this.username = username;
		this.email = email;
		this.setPassword(password);
	}

	private void setPassword(String password2) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());

	}

	public User() {
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Users(username, email, password) VALUES (?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
	}
}
