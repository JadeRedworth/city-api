package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Hello {

	@RequestMapping("/")
	public String home() {
		return "Hello World!";
	}

	@RequestMapping("/api/v1/city")
	public String getCities() throws Exception {
		String[] cities = this.getCitiesFromDB();
		return Arrays.deepToString(cities);
	}

	public static void main(String[] args) {
		SpringApplication.run(Hello.class, args);
	}

	private String[] getCitiesFromDB() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		SQLException sqlEx = null;

		String dbHost = System.getenv("MYSQL_HOST");
		if (dbHost == null) {
			dbHost = "localhost";
		}
		String dbPort = System.getenv("MYSQL_PORT");
		if (dbPort == null) {
			dbPort = "3306";
		}

		String dbUser = System.getenv("MYSQL_USERNAME");
		if (dbUser == null) {
			dbUser = "root";
		}

		String dbPwd = System.getenv("MYSQL_PASSWORD");
		if (dbPwd == null) {
			dbPwd = "welcome1";
		}

		String dbURL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/citydb?user=" + dbUser + "&password=" + dbPwd + "&useSSL=false";
		System.out.println("DB URL=" + dbURL);

		ArrayList<String> allCities = new ArrayList<String>();
		try {
			conn = DriverManager.getConnection(dbURL);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name from city");

			while (rs.next()) {
				String cityName = rs.getString("name");
				System.out.println("Found city: " + cityName);
				allCities.add(cityName);
			}			
		}
		catch (SQLException ex) {
			System.out.println("Error getting JDBC connection:" + ex);
			sqlEx = ex;
		}
		finally {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}

		if (sqlEx != null) {
			throw sqlEx;
		}

		return allCities.toArray(new String[0]);
	}
}