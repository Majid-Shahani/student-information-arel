package data;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class DataManager<T> {

    private final List<T> items = new LinkedList<>();

    private final Function<String, T> parser;
    private final Function<T, String> serializer;

    public DataManager(
            Function<String, T> parser,
            Function<T, String> serializer) {

        this.parser = parser;
        this.serializer = serializer;
    }

    public void add(T t) {
        items.add(t);
    }
    public List<T> get() { return items; }

    public void save(Path path) {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (T item : items) {
                writer.write(serializer.apply(item));
                writer.newLine();
            }
        } catch (IOException _) {
        }
    }

    public void load(Path path) {
        try (var lines = Files.lines(path)) {
            items.clear();
            lines.forEach(line -> items.add(parser.apply(line)));
        } catch (IOException _) {
        }
    }
}