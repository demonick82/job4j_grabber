package ru.job4j.grabber;

import ru.job4j.html.SqlRuParse;
import ru.job4j.utils.SqlProperties;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement("insert into posts (name, text, link, created)" +
                " values (?,?,?,?) on conflict do nothing ", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getPostName());
            statement.setString(2, post.getPostText());
            statement.setString(3, post.getPostLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getPostCreated()));
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    post.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement("select * from posts")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Post post = new Post(resultSet.getString("name"), resultSet.getString("text"),
                            resultSet.getString("link"), getLocalDateTime(resultSet.getTimestamp("created")));
                    post.setId(resultSet.getInt("id"));
                    posts.add(post);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement = cnn.prepareStatement("select * from posts where id=?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = new Post(resultSet.getString("name"), resultSet.getString("text"),
                            resultSet.getString("link"), getLocalDateTime(resultSet.getTimestamp("created")));
                    post.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    private LocalDateTime getLocalDateTime(Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    public static void main(String[] args) {
        SqlRuParse parse = new SqlRuParse();
        PsqlStore store = new PsqlStore(SqlProperties.getProperties("grabber.properties"));
        try {
            for (Post post : parse.list("https://www.sql.ru/forum/job-offers")) {
                store.save(post);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        store.getAll().forEach(System.out::println);
        System.out.println(store.findById(1));
    }
}
