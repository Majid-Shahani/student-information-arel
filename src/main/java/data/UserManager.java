package data;

import model.Role;
import model.User;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;

public class UserManager {
    private UserManager() {}

    private static final DataManager<User> s_Users =
            new DataManager<>(
               User::fromLine,
               User::toFileString
            );

    public static void load(Path path) {
        s_Users.load(path);
    }
    public static void save(Path path) {
        s_Users.save(path);
    }

    public static boolean add(String username, String password, Role role, String fullName, String id) {
        return s_Users.add(new User(username, password, role, fullName, id));
    }

    public static boolean authenticate(String username, String password) {
        for (var user : s_Users.get()) {
            if (user.username().equals(username))
                if (user.password().equals(password)) return true;
        }
        return false;
    }

    public static @Nullable User get(String username) {
        for (var user : s_Users.get()) if (user.username().equals(username)) return user;
        return null;
    }
    public static List<User> get() {
        return s_Users.get();
    }
}
