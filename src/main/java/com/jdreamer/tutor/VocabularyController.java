package com.jdreamer.tutor;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
public class VocabularyController {

    private Tokenizer tokenizer = new Tokenizer();

    @Autowired
    private Map<String, String> dictionary;

    @GetMapping("/parse-url")
    public String byURL(@RequestParam(name = "url") String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        return doc.text();
    }

    @PostMapping("/word-list")
    public Collection<Word> getWordList(@RequestBody String text) {
        List<Word> words = new ArrayList<>();

        List<Token> tokens = tokenizer.tokenize(text);
        for (Token token : tokens) {
            if (!token.getBaseForm().equals("*") && dictionary.containsKey(token.getBaseForm())) {
                words.add(new Word(token.getBaseForm(), token.getReading(),
                        token.getPronunciation(), dictionary.get(token.getBaseForm())));
            }
        }

        return words;
    }
}
