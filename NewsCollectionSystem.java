import java.sql.*;
import java.util.Scanner;

public class NewsCollectionSystem {
    // Database URL, username, and password
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/news_db";
    static final String USER = "root";  // Change if needed
    static final String PASSWORD = "";  // Change if needed

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            while (true) {
                System.out.println("\n*** News Collection System ***");
                System.out.println("1. Insert News");
                System.out.println("2. Retrieve News");
                System.out.println("3. Update News");
                System.out.println("4. Delete News");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 1:
                        insertNews(conn, scanner);
                        break;
                    case 2:
                        retrieveNews(conn);
                        break;
                    case 3:
                        updateNews(conn, scanner);
                        break;
                    case 4:
                        deleteNews(conn, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Insert News
    private static void insertNews(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter News Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter News Content: ");
        String content = scanner.nextLine();
        System.out.print("Enter Source URL: ");
        String sourceUrl = scanner.nextLine();
        System.out.print("Enter Publication Date (YYYY-MM-DD): ");
        String pubDate = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();

        String sql = "INSERT INTO news_articles (title, content, source_url, publication_date, category) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, sourceUrl);
            pstmt.setDate(4, Date.valueOf(pubDate));
            pstmt.setString(5, category);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted > 0 ? "News inserted successfully!" : "Failed to insert news.");
        }
    }

    // Retrieve News
    private static void retrieveNews(Connection conn) throws SQLException {
        String sql = "SELECT * FROM news_articles";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n*** News Articles ***");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("news_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Content: " + rs.getString("content"));
                System.out.println("Source URL: " + rs.getString("source_url"));
                System.out.println("Publication Date: " + rs.getDate("publication_date"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("---------------------------------");
            }
        }
    }

    // Update News
    private static void updateNews(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter News ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter New Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter New Content: ");
        String content = scanner.nextLine();
        System.out.print("Enter New Source URL: ");
        String sourceUrl = scanner.nextLine();
        System.out.print("Enter New Publication Date (YYYY-MM-DD): ");
        String pubDate = scanner.nextLine();
        System.out.print("Enter New Category: ");
        String category = scanner.nextLine();

        String sql = "UPDATE news_articles SET title = ?, content = ?, source_url = ?, publication_date = ?, category = ? WHERE news_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setString(3, sourceUrl);
            pstmt.setDate(4, Date.valueOf(pubDate));
            pstmt.setString(5, category);
            pstmt.setInt(6, id);

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println(rowsUpdated > 0 ? "News updated successfully!" : "News ID not found.");
        }
    }

    // Delete News
    private static void deleteNews(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter News ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM news_articles WHERE news_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println(rowsDeleted > 0 ? "News deleted successfully!" : "News ID not found.");
        }
    }
}