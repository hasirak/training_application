package no.hials.trainingapp.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * Data source for the sqlite database
 */
public class DataSourceSqlite extends BaseDataSource {

    /**
     * Initialize the connection pool
     *
     * @param connectionString the connection string
     *
     * @throws ClassNotFoundException
     */
    public static void initPool(String connectionString) throws ClassNotFoundException {

        setConnectionPool(createConnectionPool(connectionString));

    }

    /**
     * Creates a new sqlite data source. This can safely be shared between
     * threads.
     */
    public DataSourceSqlite() {
        super(null);
    }

    /**
     * Returns all the customers
     *
     * @param limit limit the number of users returned
     * @return a list of customers
     * @throws SQLException
     */
    @Override
    public List<DataItem> getAllCustomers(int limit) throws SQLException {

        return queryList(
                "select * from customer ORDER BY customer_first_name,"
                + " customer_last_name LIMIT ?", limit);
    }

    /**
     * Stores a new customer to the database
     *
     * @param data the customer data
     * @throws SQLException
     */
    @Override
    public void storeNewCustomer(DataItem data) throws SQLException {

        String query = "INSERT INTO customer "
                + "(customer_first_name, customer_last_name,"
                + "customer_email, customer_pw, customer_sex,"
                + "customer_program_id, customer_weight,"
                + "customer_height, customer_date_of_birth) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        executeUpdate(query,
                data.get("customer_first_name"),
                data.get("customer_last_name"),
                data.get("customer_email"),
                data.get("customer_pw"),
                data.get("customer_sex"),
                data.get("customer_program_id"),
                data.get("customer_weight"),
                data.get("customer_height"),
                data.get("customer_date_of_birth"));

    }

    /**
     * Returns the next workouts scheduled for the given customer
     *
     * @param customerId the customer id
     * @param limit the maximum number of workouts to return
     * @return
     * @throws SQLException
     */
    @Override
    public List<DataItem> getNextWorkoutsForCustomer(int customerId, int limit) throws SQLException {

        return queryList(
                "SELECT w.* FROM workout AS w "
                + "INNER JOIN customer AS u ON u.customer_program_id=w.workout_program_id "
                + "WHERE u.customer_id=? AND w.workout_done=? "
                + "LIMIT ?",
                customerId, false, limit);
    }

    /**
     * Returns the workout log for a customer. (completed workouts)
     *
     * @param customerId the customer id
     * @param limit the maximum number of workouts to return
     * @return
     * @throws SQLException
     */
    @Override
    public List<DataItem> getWorkoutLogForCustomer(int customerId, int limit) throws SQLException {
        return queryList(
                "SELECT w.* FROM workout AS w "
                + "INNER JOIN customer AS u ON u.customer_program_id=w.workout_program_id "
                + "WHERE u.customer_id=? AND w.workout_done=? "
                + "ORDER BY w.workout_id DESC "
                + "LIMIT ?",
                customerId, true, limit);
    }

    /**
     * Returns the workout log for a customer. (completed workouts)
     *
     * @param customerId the customer id
     * @param pag a pagination object which determines which workouts to return
     * @return
     * @throws SQLException
     */
    @Override
    public List<DataItem> getWorkoutLogForCustomer(int customerId, Pagination pag) throws SQLException {

        DataItem count = querySingle("SELECT COUNT(1) AS count FROM workout AS w "
                + "INNER JOIN customer AS u ON u.customer_program_id=w.workout_program_id "
                + "WHERE u.customer_id=? AND w.workout_done=?", customerId, true);

        pag.setItemCount(count.getInteger("count"));
        
                return queryList(
                "SELECT w.* FROM workout AS w "
                + "INNER JOIN customer AS u ON u.customer_program_id=w.workout_program_id "
                + "WHERE u.customer_id=? AND w.workout_done=? "
                + "ORDER BY w.workout_id DESC "
                + "LIMIT ? OFFSET ?",
                customerId, true, pag.getLimit(), pag.getOffset());
    }
    
    
    /**
     * Method used by the SQLiteSetup script to create the database.
     * (Shouldn't be used for anything else.)
     * 
     * @param ddl a SQL statement
     * @return 
     * @throws SQLException 
     */
    public int execureDDLStatement(String ddl) throws SQLException{
        return executeUpdate(ddl);
    }

    /**
     * XXX
     */
    @Override
    public List<DataItem> getSets(int set_workout_id) throws SQLException {
        return queryList(
                "SELECT * FROM exercise, exercise_set "
                + "WHERE set_workout_id=? AND set_exercise_id=exercise_id"
                + " ORDER BY set_id ASC", set_workout_id);
    }

    /**
     * Creates a new data source for use in a transaction
     *
     * This is used internally to create new instance of the data source to be
     * used inside a transaction. Objects created using this constructor must
     * not be shared between threads.
     */
    protected DataSourceSqlite(BaseTransaction trans) {
        super(trans);
    }

    /**
     * Creates a new data source for use in a transaction
     *
     * @param tr
     * @return
     */
    @Override
    protected DataSource createTransactionDataSource(BaseTransaction tr) {
        return new DataSourceSqlite(tr);
    }

    /**
     * Create the connection pool
     */
    private static HikariDataSource createConnectionPool(String connectionString) throws ClassNotFoundException {

        Class.forName("org.sqlite.JDBC");

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(connectionString);
        config.addDataSourceProperty("foreign_keys", "true");

        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    @Override
    public void storeExerciseDone(int workoutID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
