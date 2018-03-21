package workshop2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group {

	private int id;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Group(String name) {
		this.name = name;
	}

	public Group() {

	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}

	public void saveToDB(Connection conn) throws SQLException {
		if(this.id==0) {
			String sql = "INSERT INTO user_group (name) VALUES (?);";
			String columnNames[] = { "ID" };
			PreparedStatement prepStat = conn.prepareStatement(sql, columnNames);
			prepStat.setString(1, this.name);
			prepStat.executeUpdate();
			ResultSet rs = prepStat.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		}
		else {
			String sql = "UPDATE user_group SET name = ?;";
			PreparedStatement prepStat = conn.prepareStatement(sql);
			prepStat.setString(1, this.name);
			prepStat.executeUpdate();
			}
	}

	static public Group loadGroupById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM user_group WHERE ID = ?;";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, id);
		ResultSet rs = prepStat.executeQuery();
		if (rs.next()) {
			Group group = new Group();
			group.id = rs.getInt(1);
			group.name = rs.getString(2);
			return group;
		}
		return null;

	}
	
	static public List<Group> loadAllGroups (Connection conn) throws SQLException {
		ArrayList<Group> groups = new ArrayList<>();
		String sql = "SELECT * FROM user_group";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		ResultSet rs = prepStat.executeQuery();
		while (rs.next()) {
			Group group = new Group();
			group.id = rs.getInt(1);
			group.name = rs.getString(2);
			groups.add(group);
			
		}
		return groups;
		
	}
	public void delete(Connection conn) throws SQLException {
		String sql = "DELETE FROM user_group WHERE id = ?;";
		PreparedStatement prepStat = conn.prepareStatement(sql);
		prepStat.setInt(1, this.id);
		prepStat.executeUpdate();
		this.id = 0;
	}
	
}
