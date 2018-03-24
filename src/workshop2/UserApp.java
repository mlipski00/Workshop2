package workshop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Scanner;

public class UserApp {

	public static void main(String[] args) {
		Connection conn;
		if (args.length == 0) {
			System.out.println("Brak wprowadzonego argumentu. Program zakończony");
			System.exit(0);
		}
		int user_id = Integer.parseInt(args[0]);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop2?useSSL=false", "root",
					"coderslab");
		int choice = choice1Of3();
		boolean finished = false;
		while (!finished) {
			switch (choice) {
			case 1:
				System.out.println(Excercise.loadAllExcercises(conn));
				System.out.println("Podaj id zadania do którego chcesz dodać rozwiązanie");
				Solution solution = new Solution();
				solution.setExcercise_id(AdminApp.getId());
				solution.setUser_id(user_id);
				System.out.println("Podaj opis rozwiązania");
				solution.setDescription(AdminApp.getString());
				java.util.Date javaDate = new java.util.Date();
				long javaTime = javaDate.getTime();
				Timestamp timeStamp = new Timestamp(javaTime);
				solution.setCreated(timeStamp);
				solution.setUpdated(timeStamp);
				solution.saveToDB(conn);
				System.exit(0);
			case 2:
				System.out.println(Solution.loadAllSolutionsByUserID(conn, user_id));
				System.exit(0);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static int choice1Of3() {
		boolean finished = false;
		Scanner scan;
		System.out.println(
				"add - dodanie rozwiązania\nview - przeglądanie swoich rozwiązań\nquit - zakończenie programu");
		while (!finished) {
			scan = new Scanner(System.in);
			String choice = scan.nextLine();
			if (choice.equals("add")) {
				return 1;
			} else if (choice.equals("view")) {
				return 2;
			} else if (choice.equals("quit")) {
				System.exit(0);
			} else {
				System.out.println("Podaj jedną z wskazanych opcji");
			}
		}
		return 0;
	}
}
