package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Post;
import ru.job4j.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SqlRuParse implements Parse {
    private String postName;
    private Pattern javaSearch = Pattern.compile("java\\W", Pattern.CASE_INSENSITIVE);

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> postList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Document doc = Jsoup.connect(String.format("%s/%d", link, i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                String linkSubPost = href.attr("href");
                postName = href.text();
                if (javaSearch.matcher(postName).find()) {
                    postList.add(detail(linkSubPost));
                }
            }
        }
        return postList;
    }

    @Override
    public Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgBody");
        Elements footer = doc.select(".msgFooter");
        String postText = row.get(1).text();
        String postDate = footer.first().ownText().replace(" [] |", "");
        return new Post(postName, postText, link, new SqlRuDateTimeParser().parse(postDate));
    }

    public static void main(String[] args) {
        try {
            new SqlRuParse().list("https://www.sql.ru/forum/job-offers").forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
