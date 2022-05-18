package ru.pevnenko.springmvc.dao;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import ru.pevnenko.springmvc.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Alex Pevnenko <Alex.pevnenko@gmail.com>
 * @version: v 1.0
 * @date: 14.05.2022
 */
@Component
public class PersonDAO{
    private static int PEOPLE_COUNT ;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "141199";

    private static Connection connection;

    static{
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        try{
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() throws SQLException{
        List<Person> people = new ArrayList<>();

        Statement statement = connection.createStatement();
        String SQL = "SELECT * FROM person";
        ResultSet resultSet = statement.executeQuery(SQL);

        while(resultSet.next()){
            Person person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

            people.add(person);
        }

        return people;
    }

    public Person show(int id){
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
        return null;
    }

    public void save(Person person) throws SQLException{
//        person.setId(++PEOPLE_COUNT );
//        people.add(person);

        Statement statement = connection.createStatement();
        String SQL ="INSERT INTO person VALUES("+1+",'"+person.getName()+"',"+person.getAge()+",'"+person.getEmail()+"')";
        statement.executeUpdate(SQL);
    }

    public void update(int id,Person person){
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(person.getName());
        }

    public void delete(int id){
//        people.removeIf(p->p.getId() == id);
    }

}
