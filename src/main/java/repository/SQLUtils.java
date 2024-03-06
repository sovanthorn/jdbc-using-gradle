package repository;

public class SQLUtils {
    public  static class PersonSQL{
        public static final String getAllPersonSql= """
            select * from person_tb;
            """;

        public static final String insertNewPerson= """
                INSERT INTO person_tb ("fullname","gender","email","address") VALUES(?,?,?,?);
                """;
    }
}
