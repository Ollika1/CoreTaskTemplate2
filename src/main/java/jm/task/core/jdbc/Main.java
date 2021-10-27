package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Rick", "Sunchez", (byte) 65);
        userService.saveUser("Marty", "McFly", (byte) 23);
        userService.saveUser("Lev", "Myshkin", (byte) 35);
        userService.saveUser("Igor", "Koshkin", (byte) 49);
        List<User> list = userService.getAllUsers();
        list.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.getFactory().close();

    }
}
