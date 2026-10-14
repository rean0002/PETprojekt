package mappers;

import Exceptions.DatabaseException;
import entities.Household;
import entities.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HouseholdMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "NextThing";

    private static ConnectionPool connectionPool;
    private static HouseholdMapper householdMapper;

    @BeforeAll
    static void setUpClass() {
        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
        householdMapper = new HouseholdMapper(connectionPool);

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


            // USERS
            stmt.execute("""
        INSERT INTO test.users (firstname, lastname, email, password) VALUES
          ('Olga', 'Hansen', 'olga@test.dk', 'password1'),
          ('Rebecca', 'Jensen', 'rebecca@test.dk', 'password2'),
          ('Maja', 'Sørensen', 'maja@test.dk', 'password3'),
          ('Alexander', 'Nielsen', 'alexander@test.dk', 'password4'),
          ('Rasmus', 'Pedersen', 'rasmus@test.dk', 'password5'),
          ('Sofie', 'Andersen', 'sofie@test.dk', 'password6'),
          ('Mikkel', 'Larsen', 'mikkel@test.dk', 'password7'),
          ('Ida', 'Christensen', 'ida@test.dk', 'password8'),
          ('Jonas', 'Møller', 'jonas@test.dk', 'password9'),
          ('Emma', 'Rasmussen', 'emma@test.dk', 'password10')
        """);

            // HOUSEHOLDS
            stmt.execute("""
        INSERT INTO test.households (name, code) VALUES
          ('Studiegruppen', 'K7M2QX'),
          ('Kollektivet', 'B4ZT9R'),
          ('Familien Hansen', 'W3N8HD'),
          ('Bofællesskabet', 'P6C1YJ'),
          ('Lejligheden', 'T9X5LA')
        """);

            // USERS_HOUSEHOLDS
            stmt.execute("""
        INSERT INTO test.users_households (users_user_id, households_household_id, role_id) VALUES
          (1, 1, 1),
          (2, 1, 2),
          (3, 1, 2),
          (4, 2, 1),
          (5, 2, 2),
          (1, 2, 2),
          (6, 3, 1),
          (7, 3, 2),
          (3, 3, 2),
          (8, 4, 1),
          (2, 4, 2),
          (9, 5, 1),
          (10, 5, 2)
        """);

            // ROUTINES
            stmt.execute("""
        INSERT INTO test.routines (title, description, start_date, "time", frequency_id, category_id, household_id, user_id) VALUES
          ('Støvsug stuen', 'Støvsug stue og gang', '2026-10-05', '10:00', 3, 1, 1, 1),
          ('Lav aftensmad', 'Lav aftensmad til hele husstanden', '2026-10-05', '17:30', 2, 2, 1, 2),
          ('Tøm skraldespanden', 'Tøm skraldespanden i køkkenet', '2026-10-06', NULL, 4, 7, 1, 3),
          ('Vask tøj', 'Vask og hæng en maskine tøj op', '2026-10-07', '18:00', 5, 4, 2, 4),
          ('Køb ind', 'Køb ind til ugen', '2026-10-08', '16:00', 3, 3, 2, 5),
          ('Gør badeværelset rent', 'Skrub bad, håndvask og toilet', '2026-10-10', NULL, 6, 1, 2, 1),
          ('Gå tur med hunden', 'Gå en lang tur med hunden', '2026-10-05', '07:30', 2, 6, 3, 6),
          ('Vand planterne', 'Vand alle planter i huset', '2026-10-06', '09:00', 5, 5, 3, 7),
          ('Ryd op i kælderen', 'Sorter og ryd op i kælderen', '2026-10-12', NULL, 7, 8, 3, 3),
          ('Tør støv af', 'Tør støv af på hylder og borde', '2026-10-09', '11:00', 3, 1, 4, 8),
          ('Lav frokost', 'Lav frokost til dagen', '2026-10-07', '12:00', 4, 2, 4, 2),
          ('Skift sengetøj', 'Skift sengetøj i alle senge', '2026-10-15', NULL, 7, 4, 4, 8),
          ('Løb en tur', 'Løb en tur på 5 km', '2026-10-05', '06:30', 5, 6, 5, 9),
          ('Tag skrald ud', 'Tag skrald ud til containeren', '2026-10-08', '20:00', 2, 7, 5, 10),
          ('Betal husleje', 'Overfør husleje til udlejer', '2026-10-01', NULL, 6, 8, 5, 9)
        """);

            // TASKS
            stmt.execute("""
        INSERT INTO test.tasks (routines_id, date_due) VALUES
          (1, '2026-10-05'),
          (1, '2026-10-12'),
          (2, '2026-10-05'),
          (2, '2026-10-06'),
          (3, '2026-10-06'),
          (3, '2026-10-08'),
          (4, '2026-10-07'),
          (4, '2026-10-10'),
          (5, '2026-10-08'),
          (5, '2026-10-15'),
          (6, '2026-10-10'),
          (6, '2026-11-10'),
          (7, '2026-10-05'),
          (7, '2026-10-06'),
          (8, '2026-10-06'),
          (8, '2026-10-09'),
          (9, '2026-10-12'),
          (9, '2026-10-26'),
          (10, '2026-10-09'),
          (10, '2026-10-16'),
          (11, '2026-10-07'),
          (11, '2026-10-09'),
          (12, '2026-10-15'),
          (12, '2026-10-29'),
          (13, '2026-10-05'),
          (13, '2026-10-08'),
          (14, '2026-10-08'),
          (14, '2026-10-09'),
          (15, '2026-10-01'),
          (15, '2026-11-01')
        """);





        } catch (SQLException e) {
            fail("Test data setup failed: " + e.getMessage());
        }
    }



    @Test
    void tasks() throws DatabaseException {

        Household household= householdMapper.getHouseholdById(1);

        ArrayList<Task> tasks = householdMapper.tasks(household);

        assertEquals(6, tasks.size());


    }
}