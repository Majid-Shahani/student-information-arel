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
        for (var user : s_Users.get())
            if (user.username().equals(username) || user.id().equals(id))
                return false;
        s_Users.add(new User(username, password, role, fullName, id));
        return true;
    }
    public static void removeUser(String username) {
        s_Users.get().removeIf(u -> u.username().equals(username));
    }
    public static void removeUser(User user) {
        s_Users.get().remove(user);
    }

    public static @Nullable User authenticate(String username, String password) {
        for (var user : s_Users.get()) {
            if (user.username().equals(username))
                if (user.password().equals(password)) return user;
        }
        return null;
    }

    public static @Nullable User get(String username) {
        for (var user : s_Users.get()) if (user.username().equals(username)) return user;
        return null;
    }
    public static List<User> get() {
        return s_Users.get();
    }
}
