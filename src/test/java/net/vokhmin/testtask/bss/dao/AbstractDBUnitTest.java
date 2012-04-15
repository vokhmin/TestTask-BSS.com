package net.vokhmin.testtask.bss.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
public abstract class AbstractDBUnitTest {	
	static final Logger log = LoggerFactory.getLogger("net.vokhmin.testtask.bss");

	static final String LOG_TEMPLATE = 
			"\n--------------------------------\n"
			+ "%s %s()" 
			+ "\n--------------------------------\n";

	final String presetFile = "src/main/sql/set-constrains-db.sql";
	final String postsetFile = "src/main/sql/drop-constrains-db.sql";

	@Autowired
	DataSource dataSource;
	
	@PersistenceUnit
	EntityManagerFactory entityManagerFactory;

    protected JdbcTemplate jdbcTemplate;
	protected IDatabaseConnection dbConnection;
	protected DatabaseConfig dbConfig;
	protected IDataSet dataSet;

	protected List<String> getDataSetFiles() {
		return new ArrayList<String>();
	}
	
	protected IDataSet loadDataSetFiles() throws DataSetException {
		List<String> files = getDataSetFiles();
		if (files.size() == 0) return null;
		int i = 0;
		IDataSet[] dataSets= new IDataSet[files.size()];
		for (String file : getDataSetFiles()) {
	        InputStream input = Thread.currentThread().getContextClassLoader()
	                .getResourceAsStream(file);
	        dataSets[i++] = new XmlDataSet(input);
	        //dataSet = new ReplacementDataSet(new FlatXmlDataSet(input));
	        //dataSet.addReplacementObject("[NULL]", null);
		}
		CompositeDataSet composite = new CompositeDataSet(dataSets);
		return composite;
	}
	
	@Before
	public void setUp() throws Exception {
		jdbcTemplate = new JdbcTemplate(dataSource);
		
        log.debug("Before test it uses ddl-file is {} - {}",  new File(".").getCanonicalPath(), presetFile);        
        dbConnection = new DatabaseConnection(dataSource.getConnection());
        dbConfig = dbConnection.getConfig();
        dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new H2DataTypeFactory());
        // Load the dataset files
        dataSet = loadDataSetFiles();        
        try {
            executeSqlScript(dbConnection, presetFile);
            DatabaseOperation.CLEAN_INSERT.execute(dbConnection, dataSet);
        }
        finally {
            dbConnection.close();
        }
	}
	
	@After
	public void tearDown() throws Exception {
        log.debug("After test it uses ddl-file is {} - {}",  new File(".").getCanonicalPath(), postsetFile);
        dbConnection = new DatabaseConnection(dataSource.getConnection());
		try {
			//DatabaseOperation.DELETE.execute(conn, dataSet);
			executeSqlScript(dbConnection, postsetFile);
		} finally {
			dbConnection.close();
		}
	}
	
	void executeSqlScript(IDatabaseConnection conn, String script) throws Exception {
		String sql = readSqlFromFile(script);
		executeSql(conn, sql);
	}
	
	String readSqlFromFile(String file) throws Exception {
		BufferedReader sqlReader = new BufferedReader(new FileReader(file));
		String line;
		StringBuffer sqlBuffer = new StringBuffer();
		while ((line = sqlReader.readLine()) != null) {
			line = line.trim();
			if (line.length() > 0 && !line.startsWith("--")) {
				sqlBuffer.append(line).append("\n");
			}
		}
		return sqlBuffer.toString();
	}
	
	void executeSql(IDatabaseConnection conn, final String sql) throws SQLException {
		Statement stmt = conn.getConnection().createStatement();
		final String[] statements = sql.split(";");
		for (int i = 0; i < statements.length; i++) {
			if (statements[i].trim().length() > 0) {
				log.info("Adding batch stmt: {}", statements[i]);
				stmt.addBatch(statements[i]);
			}
		}
		stmt.executeBatch();
	}
	
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

	public static Calendar createGregorianCalendarGMT(int year, int month,
			int date, int hourOfDay, int minute, int second) {
		GregorianCalendar gc = new GregorianCalendar(TimeZone
				.getTimeZone("GMT"));
		gc.set(year, month, date, hourOfDay, minute, second);
		return gc;
	}

	static protected String getMethodName() {
		return getMethodName(3);
	}

	static protected String getMethodName(int level) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[level];
		return String.format("%s.%s", ste.getClassName(), ste.getMethodName());
	}

	public static void logStart() {
		log.info(String.format(LOG_TEMPLATE, "STARTED", getMethodName(3)));
	}

	public static void logFinish() {
		log.info(String.format(LOG_TEMPLATE, "FINISHED", getMethodName(3)));
	}
	
}
