package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        new SqlRuParse().parse(5);
    }

    public void parse(int pages) throws IOException {
        for (int i = 1; i <= pages; i++) {
            Document doc = Jsoup.connect(String.format("https://www.sql.ru/forum/job-offers/%d", i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                Element date = td.parent().child(5);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(date.text());
            }
        }
    }
}
