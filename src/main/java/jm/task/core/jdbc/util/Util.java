package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Util {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.Driver", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/base")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "root1!")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .addAnnotatedClass(User.class);
        return configuration.buildSessionFactory(new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build());
    }


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String login = "root";
        String pass = "root1!";
        String url = "jdbc:mysql://localhost:3306/base";
        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, login, pass);

    }
}

