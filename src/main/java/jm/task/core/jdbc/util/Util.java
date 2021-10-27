package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/myuser";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public Util(){
        }
    public static SessionFactory getFactory(){
        try {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER,DRIVER );
            properties.put(Environment.URL,URL);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect" );
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.FORMAT_SQL,"true");
            properties.put(Environment.DEFAULT_SCHEMA,"myuser");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.addAnnotatedClass(User.class);
            configuration.setProperties(properties);
            return configuration.buildSessionFactory();
        } catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }

     public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Не загружен драйвер");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

}


