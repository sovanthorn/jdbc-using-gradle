package repository;

import com.github.javafaker.Faker;
import model.Person;
import utils.PropertyUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class PersonRepository {
    //==========================<<This relates to the JavaFaker>>===========================
    private static final long SEEDVALUE = 123456L;
    private static final Faker faker = new Faker(new Random(SEEDVALUE));
    private static List<Person> cachedPersons = null;

    public  List<Person> getAllPerson2() {
        if( cachedPersons!=null){
            return cachedPersons;
        }
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            persons.add(new Person()
                    .setId(1000 + i)
                    .setEmail(faker.internet().emailAddress())
                    .setFullName(faker.name().fullName())
                    .setGender(faker.options().option("male", "female"))
                    .setAddress(faker.address().country()));
        }
        cachedPersons = persons;
        return persons;

    }

    //=======================================================
    private Properties properties;
    public PersonRepository(){
        properties = PropertyUtils.loadProperty();
    }


    public List<Person> getAllPerson(){
        try(
                Connection connection = DriverManager.getConnection(
                        properties.getProperty("DB_URL"),
                        properties.getProperty("USERNAME"),
                        properties.getProperty("PASSWORD")
                );
                Statement statement = connection.createStatement();
                ){
                var personList = new ArrayList<Person>();
                var rs = statement.executeQuery(SQLUtils.PersonSQL.getAllPersonSql);
                while(rs.next()){
                    personList.add(
                            new Person()
                                    .setId(rs.getInt("id"))
                                    .setFullName(rs.getString("fullname"))
                                    .setEmail(rs.getString("email"))
                                    .setAddress(rs.getString("address"))
                                    .setGender(rs.getString("gender"))

                            );
                }
                return personList;

        }catch (SQLException ex ){
            System.out.println("Failed to retreive all the person data ! ");
            ex.printStackTrace();
        }

        return null;
    }

    public int addNewPerson(Person person){
        try(
                Connection connection = DriverManager.getConnection(
                        properties.getProperty("DB_URL"),
                        properties.getProperty("USERNAME"),
                        properties.getProperty("PASSWORD")
                );
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.insertNewPerson);
                ){
            ps.setString(1,person.getFullName());
            ps.setString(2,person.getGender());
            ps.setString(3,person.getEmail());
            ps.setString(4,person.getAddress());
            return ps.executeUpdate();

        }catch (SQLException ex){
            System.out.println("Error when adding a new person");
            ex.printStackTrace();
        }
        return 0;
    }
}
