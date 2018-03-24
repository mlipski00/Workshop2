package workshop2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Solution {

	private int id;
	private Timestamp created;
	private Timestamp updated;
	private String description;
	private int excercise_id;
	private int user_id;

	public Solution(String description, int excercise_id, int user_id) {
		java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		this.created = new Timestamp(javaTime);
		this.updated = new Timestamp(javaTime);
		this.description = description;
		this.excercise_id = excercise_id;
		this.user_id = user_id;
	}

	public Solution() {
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int geteEcercise_id() {
		return excercise_id;
	}

	public void setExcercise_id(int excercise_id) {
		this.excercise_id = excercise_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO solution (created, updated, description, excercise_id, user_id) VALUES (?, ?, ?, ?, ?);";
			String columnNames[] = { "ID" };
			PreparedStatement prepStat = conn.prepareStatement(sql, columnNames);
			prepStat.setTimestamp(1, this.created);
			prepStat.setTimestamp(2, this.updated);
			prepStat.setString(3, this.description);
			prepStat.setInt(4, this.excercise_id);
			prepStat.setInt(5, this.user_id);
			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE solution SET updated=?, excercise_id=? WHERE id=?";
			java.util.Date javaDate = new java.util.Date();
			long javaTime = javaDate.getTime();
			this.updated = new Timestamp(javaTime);
			PreparedStatement prepStat = conn.prepareStatement(sql);
			prepStat.setTimestamp(1, this.updated);
			prepStat.setInt(2, this.excercise_id);
			prepStat.setInt(3, this.id);
			prepStat.executeUpdate();

		}
	}

	@Override
	public String toString() {
		return "Solution [id=" + id + ", created=" + created + ", updated=" + updated + ", description=" + description
				+ ", excercise_id=" + excercise_id + ", user_id=" + user_id + "]\n";
	}

	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution WHERE ID = ?;";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		if (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt(1);
			solution.created = rs.getTimestamp(2);
			solution.updated = rs.getTimestamp(3);
			solution.description = rs.getString(4);
			solution.excercise_id = rs.getInt(5);
			solution.user_id = rs.getInt(6);
			return solution;
		}
		return null;
	}

	static public List<Solution> loadAllSolutions(Connection conn) throws SQLException {
		String sql = "SELECT * FROM solution;";
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		PreparedStatement prepStat = conn.prepareStatement(sql);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt(1);
			solution.created = rs.getTimestamp(2);
			solution.updated = rs.getTimestamp(3);
			solution.description = rs.getString(4);
			solution.excercise_id = rs.getInt(5);
			solution.user_id = rs.getInt(6);
			solutions.add(solution);
		}
		return solutions;
	}
	
	static public List<Solution> loadAllSolutionsByUserID (Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM solution WHERE user_id = ?";
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt(1);
			solution.created = rs.getTimestamp(2);
			solution.updated = rs.getTimestamp(3);
			solution.description = rs.getString(4);
			solution.excercise_id = rs.getInt(5);
			solution.user_id = rs.getInt(6);
			solutions.add(solution);
		}
		return solutions;
	}
	
	static public List<Solution> loadAllByExerciseId (Connection conn, int id) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM solution WHERE excercise_id = ?";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {
			Solution solution = new Solution();
			solution.id = rs.getInt(1);
			solution.created = rs.getTimestamp(2);
			solution.updated = rs.getTimestamp(3);
			solution.description = rs.getString(4);
			solution.excercise_id = rs.getInt(5);
			solution.user_id = rs.getInt(6);
			solutions.add(solution);
		}
		return solutions;
	}
	
	
	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM solution WHERE id = ?;";
			PreparedStatement prepStat = conn.prepareStatement(sql);
			prepStat.setInt(1, this.id);
			prepStat.executeUpdate();
			this.id=0;
		}
	}
}
