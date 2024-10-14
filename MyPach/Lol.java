package MyPach;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Lol {
    public Lol(){
        Book book = new Book();
        book.title = "Обитаемый остров";
        book.author = "Стругацкий А., Стругацкий Б.";
        book.pages = 413;

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonBook = null;
        try {
            jsonBook = mapper.writeValueAsString(book);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(jsonBook);
    }
    class Book {
        public String title;
        public String author;
        public int pages;
    }
}