package workshop2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
	public static Scanner scan;

	public static void main(String[] args) {
		Connection conn;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/workshop2?useSSL=false", "root",
					"coderslab");
			boolean finished = false;
			while (!finished) {
				switch (Integer.parseInt(args[0])) {
				case 1:
					String choiceString = choice1Of4();
					if (choiceString.equals("add")) {
						System.out.println("Podaj imię użytkownika");
						String name = getString();
						System.out.println("Podaj email użytkownika");
						String email = getString();
						System.out.println("Podaj hasło użytkownika");
						String password = getString();
						User user = new User(name, email, password);
						user.saveToDB(conn);
					} else if (choiceString.equals("edit")) {
						System.out.println("Podaj ID użytkownika");
						User user = User.loadUserById(conn, getId());
						System.out.println("Podaj imię użytkownika");
						user.setUsername(getString());
						System.out.println("Podaj email użytkownika");
						user.setEmail(getString());
						System.out.println("Podaj hasło użytkownika");
						user.setPassword(getString());
						user.saveToDB(conn);
					} else if (choiceString.equals("delete")) {
						System.out.println("Podaj ID użytkownika do usunięcia");
						User.loadUserById(conn, getId()).delete(conn);
					}
					break;
				case 2:
					choiceString = choice1Of4();
					if (choiceString.equals("add")) {
						System.out.println("Podaj tytuł zadania");
						String title = getString();
						System.out.println("Podaj opis zadania");
						String description = getString();
						Excercise excercise = new Excercise(title, description);
						excercise.saveToDB(conn);
					} else if (choiceString.equals("edit")) {
						System.out.println("Podaj ID zadania");
						Excercise excercise = Excercise.loadExcerciseById(conn, getId());
						System.out.println("Podaj tytuł zadania");
						excercise.setTitle(getString());
						System.out.println("Podaj opis zadania");
						excercise.setDescription(getString());
						excercise.saveToDB(conn);
					} else if (choiceString.equals("delete")) {
						System.out.println("Podaj ID zadania do usunięcia");
						Excercise.loadExcerciseById(conn, getId()).delete(conn);
					}
					break;
				case 3:
					choiceString = choice1Of4();
					if (choiceString.equals("add")) {
						System.out.println("Podaj nazwę grupy");
						String name = getString();
						Group group = new Group(name);
						group.saveToDB(conn);
					} else if (choiceString.equals("edit")) {
						System.out.println("Podaj ID grupy");
						Group group = Group.loadGroupById(conn, getId());
						System.out.println("Podaj nazwę grupy");
						group.setName(getString());
						group.saveToDB(conn);
					} else if (choiceString.equals("delete")) {
						System.out.println("Podaj ID grupy");
						Group.loadGroupById(conn, getId()).delete(conn);
					}
					break;
				case 4:
					choiceString = choice1Of3();
					if (choiceString.equals("add")) {
						System.out.println(User.loadAllUsers(conn));
						System.out.println("Podaj ID użytkownika");
						int user_id = getId();
						System.out.println(Excercise.loadAllExcercises(conn));
						System.out.println("Podaj ID zadania");
						int excercise_id = getId();
						Solution solution = new Solution(null, excercise_id, user_id);
						solution.setUpdated(null);
						solution.saveToDB(conn);
					} else if (choiceString.equals("view")) {
						System.out.println("Podaj ID użytkownika");
						System.out.println(Solution.loadAllSolutionsByUserID(conn, getId()));
					}
				}
			}

		} catch (NullPointerException e) {
			System.out.println("Nie ma takiego ID");
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static String choice1Of4() {
		boolean finished = false;
		Scanner scan;
		System.out.println(
				"add - dodanie użytkownika\nedit - edycja użytkownika\ndelete - usunięcie użytkownika\nquit - zakończenie programu");
		while (!finished) {
			scan = new Scanner(System.in);
			String choice = scan.nextLine();
			if (choice.equals("add") || choice.equals("edit") || choice.equals("delete")) {
				return choice;
			} else if (choice.equals("quit")) {
				System.exit(0);
			} else {
				System.out.println("Podaj jedną z wskazanych opcji");
			}
		}
		return null;
	}

	public static String choice1Of3() {
		boolean finished = false;
		Scanner scan;
		System.out.println(
				"add - dodanie użytkownika\nview - przeglądanie rozwiązań danego użytkownika\nquit - zakończenie programu");
		while (!finished) {
			scan = new Scanner(System.in);
			String choice = scan.nextLine();
			if (choice.equals("add") || choice.equals("view")) {
				return choice;
			} else if (choice.equals("quit")) {
				System.exit(0);
			} else {
				System.out.println("Podaj jedną z wskazanych opcji");
			}
		}
		return null;
	}

	public static int getId() {
		scan = new Scanner(System.in);
		while (!scan.hasNextInt()) {
			scan.next();
			System.out.println("Podaj ID!");
		}
		return scan.nextInt();
	}

	public static String getString() {
		scan = new Scanner(System.in);
		String s = null;
		boolean finished = false;
		while (!finished) {
			s = scan.nextLine();
			if (s.equals("")) {
				System.out.println("Wartość nie może być pusta!");
			} else {
				finished = true;
			}
		}
		return s;
	}
}
