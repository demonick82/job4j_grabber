package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SqlRuParse {
    public static void main(String[] args) throws IOException {
        new SqlRuParse().parseList(5);
    }

    public void parseList(int pages) throws IOException {
        for (int i = 1; i <= pages; i++) {
            Document doc = Jsoup.connect(String.format("https://www.sql.ru/forum/job-offers/%d", i)).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element href = td.child(0);
                Element date = td.parent().child(5);
                String link = href.attr("href");
                String name = href.text();
                String dateText = date.text();
                System.out.println(link);
                System.out.println(name);
                System.out.println(dateText);
                parseLink(link);
                System.out.println();
            }
        }
    }

    public void parseLink(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".msgBody");
        Elements footer = doc.select(".msgFooter");
        String postDetail = row.get(1).text();
        String postDate = footer.first().ownText().replace(" [] |", "");
        System.out.println(postDetail);
        System.out.println(postDate);
    }
}
