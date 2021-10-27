package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try(Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE `myuser`.`users` (`id` BIGINT(5) NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NOT NULL," +
                    "`lastname` VARCHAR(45) NOT NULL, `age` TINYINT(3) NULL, PRIMARY KEY (`id`))").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e){
            System.out.println("Таблица уже существует");
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS myuser.users").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {
            System.out.println("Таблица не существует");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("User с именем " + user.getName() + " добавлен в базу данных");
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (IllegalArgumentException e){
            System.out.println("User с id " + id + " не существует");
        }
    }

     @Override
    public List<User> getAllUsers() {
         List<User> list = new ArrayList<>();
         try(Session session = Util.getFactory().getCurrentSession()) {
             session.beginTransaction();
             list = session.createQuery("SELECT a FROM User a", User.class).getResultList();
             session.getTransaction().commit();
         } catch (Throwable e){
             e.printStackTrace();
         }
         return list;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE myuser.users").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e){
            System.out.println("Таблица не существует");
        }
    }
}
