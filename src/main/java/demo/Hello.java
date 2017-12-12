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
	public String getCities() {
		String[] cities = this.getCitiesFromDB();
		return Arrays.deepToString(cities);
	}

	public static void main(String[] args) {
		SpringApplication.run(Hello.class, args);
	}

	private String[] getCitiesFromDB() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		ArrayList<String> allCities = new ArrayList<String>();
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/citydb?user=test&password=welcome1&useSSL=false");
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
		}

		return allCities.toArray(new String[0]);
	}
}