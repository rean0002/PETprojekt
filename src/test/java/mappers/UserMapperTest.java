package mappers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "NextThing";

    private static ConnectionPool connectionPool;
    private static UserMapper userMapper;

    @BeforeAll
    static void setUpClass() {
        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
        userMapper = new UserMapper(connectionPool);

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {


            stmt.execute("CREATE SCHEMA IF NOT EXISTS test");

// Drop tables
            stmt.execute("DROP TABLE IF EXISTS test.tasks");
            stmt.execute("DROP TABLE IF EXISTS test.routines");
            stmt.execute("DROP TABLE IF EXISTS test.users_households");
            stmt.execute("DROP TABLE IF EXISTS test.categories");
            stmt.execute("DROP TABLE IF EXISTS test.frequencies");
            stmt.execute("DROP TABLE IF EXISTS test.households");
            stmt.execute("DROP TABLE IF EXISTS test.users");
            stmt.execute("DROP TABLE IF EXISTS test.roles");

// Drop sequences
            stmt.execute("DROP SEQUENCE IF EXISTS test.tasks_task_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.routines_routines_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.users_households_user_household_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.categories_category_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.frequencies_frequency_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.households_household_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq");
            stmt.execute("DROP SEQUENCE IF EXISTS test.roles_roles_id_seq");

// Create tables
            stmt.execute("""
    CREATE TABLE test.categories AS
    SELECT * FROM public.categories
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.frequencies AS
    SELECT * FROM public.frequencies
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.households AS
    SELECT * FROM public.households
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.users AS
    SELECT * FROM public.users
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.roles AS
    SELECT * FROM public.roles
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.routines AS
    SELECT * FROM public.routines
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.tasks AS
    SELECT * FROM public.tasks
    WITH NO DATA
    """);

            stmt.execute("""
    CREATE TABLE test.users_households AS
    SELECT * FROM public.users_households
    WITH NO DATA
    """);

// Create sequences and set defaults

            stmt.execute("CREATE SEQUENCE test.categories_category_id_seq");
            stmt.execute("""
    ALTER TABLE test.categories
    ALTER COLUMN category_id
    SET DEFAULT nextval('test.categories_category_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.frequencies_frequency_id_seq");
            stmt.execute("""
    ALTER TABLE test.frequencies
    ALTER COLUMN frequency_id
    SET DEFAULT nextval('test.frequencies_frequency_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.households_household_id_seq");
            stmt.execute("""
    ALTER TABLE test.households
    ALTER COLUMN household_id
    SET DEFAULT nextval('test.households_household_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.users_user_id_seq");
            stmt.execute("""
    ALTER TABLE test.users
    ALTER COLUMN user_id
    SET DEFAULT nextval('test.users_user_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.roles_roles_id_seq");
            stmt.execute("""
    ALTER TABLE test.roles
    ALTER COLUMN roles_id
    SET DEFAULT nextval('test.roles_roles_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.routines_routines_id_seq");
            stmt.execute("""
    ALTER TABLE test.routines
    ALTER COLUMN routines_id
    SET DEFAULT nextval('test.routines_routines_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.tasks_task_id_seq");
            stmt.execute("""
    ALTER TABLE test.tasks
    ALTER COLUMN task_id
    SET DEFAULT nextval('test.tasks_task_id_seq')
    """);

            stmt.execute("CREATE SEQUENCE test.users_households_user_household_id_seq");
            stmt.execute("""
    ALTER TABLE test.users_households
    ALTER COLUMN user_household_id
    SET DEFAULT nextval('test.users_households_user_household_id_seq')
    """);



        } catch (SQLException e) {
            fail("Database setup failed: " + e.getMessage());
        }
    }


    @BeforeEach
    void setUp() {
        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute("""
    TRUNCATE TABLE
        test.tasks,
        test.routines,
        test.users_households,
        test.categories,
        test.frequencies,
        test.households,
        test.users,
        test.roles
    RESTART IDENTITY CASCADE
    """);

            // categories
            stmt.execute("""
            INSERT INTO test.categories (title) VALUES
              ('CLEANING'),
              ('FOOD'),
              ('SHOPPING'),
              ('LAUNDRY'),
              ('HOME'),
              ('EXERCISE'),
              ('TRASH'),
              ('OTHER')
            """);

            // frequencies
            stmt.execute("""
            INSERT INTO test.frequencies (title) VALUES
              ('NONE'),
                ('DAILY'),
                ('WEEKLY'),
                ('EVERY_OTHER_DAY'),
                ('BI_WEEKLY'),
                ('MONTHLY'),
                ('BI_MONTHLY'),
              ('OTHER')
            """);

            // ROLES
            stmt.execute("""
            INSERT INTO test.roles (title) VALUES
              ('Admin'),
              ('Member')
            """);




        } catch (SQLException e) {
            fail("Test data setup failed: " + e.getMessage());
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void getUserByEmail() {
    }
}