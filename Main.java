import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main
{

    public static void main(String []args) throws SQLException
    {
        String JDBC = "com.mysql.jdbc.Driver";
        String URL = "jdbc:mysql://10.0.10.3:3306/persons";
        String USER = "pkoryga";
        String PASSWORD = "haslo";
        Connection conn = null;
        Scanner scanner = new Scanner(System.in);
        
        
        int option = 0;
        try
        {
            while(conn == null)
            {
                    try {
                            System.out.println("Proba laczenia..");
                            conn = DriverManager.getConnection(URL, USER, PASSWORD);
                        } catch (SQLException e) {
                            System.out.println("blad");
                            System.out.println(e);
                            }
                        try {
                        Thread.sleep(10000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
            }
            
                System.out.println("Połączono!");
                String imie = null;
                String nazwisko = null;
                int id = 0;
                while (option != 5)
                {
                    showMenu();
                    option = scanner.nextInt();
                    switch(option)
                    {
                        case 1:
                            {
                                getPerson(conn);
                                break;
                            }
                        case 2:
                            {
                                scanner.nextLine();
                                System.out.println("Podaj imie: ");
                                imie = scanner.nextLine();
                                System.out.println("Podaj nazwisko: ");
                                nazwisko = scanner.nextLine();
                                insertPerson(conn, imie, nazwisko);
                                
                                break;
                            }
                        case 3:
                            {
                                System.out.println("Podaj id:");
                                id = scanner.nextInt();
                                scanner.nextLine();
                                System.out.println("Podaj imie: ");
                                imie = scanner.nextLine();
                                System.out.println("Podaj nazwisko: ");
                                nazwisko = scanner.nextLine();
                                
                                updatePerson(conn, id, imie, nazwisko); 
                                
                                break;
                            }
                            
                        case 4:
                            {
                                System.out.println("Podaj id:");
                                id = scanner.nextInt();
                                deletePerson(conn, id);
                                break;
                            }
                        
                        case 5:
                            {
                                conn.close();
                                System.exit(0);
                                break;
                            }
                        default:
                            {
                                break;
                            }
                    }
                }
        }
            catch (Exception e) {
            System.out.println(e);
            }
            conn.close();
            
    }
        public static void deletePerson(Connection conn, int tmp_id) throws SQLException 
		{
			String sql = "DELETE FROM persons WHERE ID = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, tmp_id);
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Osobe o id" + tmp_id + " usunieto");
			}
			statement.close();
		}
    
        public static void getPerson(Connection conn) throws SQLException
        {
            String sql = "SELECT * FROM persons";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next())
            {
                int person_id = result.getInt("id");
                String person_name = result.getString("name");
                String person_surname = result.getString("surname");
                
                System.out.println("id: " + person_id + " Imie: " + person_name + "Nazwisko: " + person_surname);
            }
            result.close();
        }
        
        public static void updatePerson(Connection conn, int tmp_id, String tmp_name, String tmp_username) throws SQLException 
        {
        String sql = "UPDATE persons SET name = ?, surname = ? WHERE ID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, tmp_name);
        statement.setString(2, tmp_username);
        statement.setInt(3, tmp_id);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Osobe o id=" + tmp_id + " zmodyfikowano!");
        }
        statement.close();
    }
  
        public static void insertPerson(Connection conn, String tmp_name, String tmp_surname) throws SQLException
        {
        String sql = "INSERT INTO persons(name, surname) VALUES (?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, tmp_name);
        statement.setString(2, tmp_surname);
        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Dodano pomylsnie!");
        }
        statement.close();
    }    
    
        public static void showMenu() 
        {
            System.out.println();
            System.out.println("Menu wyboru:");
            System.out.println("-------------------------------");
            System.out.println("1. Wyswietlanie z bazy");
            System.out.println("2. Dodaj nowa osobe");
            System.out.println("3. Edycja");
            System.out.println("4. Usun osobe");
            System.out.println("5. exit");
            System.out.println();
    }
}