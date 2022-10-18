package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {
    }

    private final Util util = new Util();

    public void createUsersTable() {
        String query = "create table if not exists users(" +
                "id serial primary key," +
                "name varchar(50) not null," +
                "lastName varchar(50) not null," +
                "age smallint not null);";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
            System.out.println("Table is created on database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void dropUsersTable() {
        String query = "drop table users;";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table deleted on database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "insert into users(name, lastName, age) values(?, ?, ?);";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User "+name+" saved successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String query = "delete from users where id = ?;";
        try (Connection connection = util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            System.out.println("Successfully deleted user by id!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String query = "select * from users;";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<User> usersList = new ArrayList<>();
            while (resultSet.next()) {
                usersList.add(new User(
                        resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
            return usersList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new SQLException();
        }
    }

    public void cleanUsersTable() {
        String query = "truncate table users;";
        try (Connection connection = util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("Table is truncate on database!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}