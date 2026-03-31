package it.configuration;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ConnectionConfig implements CommandLineRunner{

	
	private final JdbcTemplate jdbcTemplate;
	public ConnectionConfig(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("SE ENTRO QUI VA TUTTO BENE");
		String query = "SELECT * FROM author";
		try {
			
			jdbcTemplate.query(query,(rs, rowNum) -> {
				System.out.println(rs.getString("author_name"));
				return null;
			}
		);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	} 

}
