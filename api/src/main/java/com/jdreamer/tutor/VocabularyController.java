package com.jdreamer.tutor;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.jdreamer.tutor.model.ParseSiteReq;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;


@RestController
public class VocabularyController {

    private Tokenizer tokenizer = new Tokenizer();

    @Autowired
    private Map<String, String> dictionary;

    @RequestMapping(value = "/parse-url", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String byURL(@RequestBody ParseSiteReq parseSiteReq) throws IOException {
        Document doc = Jsoup.connect(parseSiteReq.getUrl()).get();

        return doc.text();
    }

    @PostMapping("/word-list")
    public Map<String, Object> getWordList(@RequestBody String text) {
        List<Word> words = new ArrayList<>();

        List<Token> tokens = tokenizer.tokenize(text);
        for (Token token : tokens) {
            if (!token.getBaseForm().equals("*") && dictionary.containsKey(token.getBaseForm())) {
                words.add(new Word(token.getBaseForm(), token.getReading(),
                        token.getPronunciation(), dictionary.get(token.getBaseForm())));
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("message", "success");
        result.put("results", words);
        result.put("status", 200);

        return result;
    }
}
