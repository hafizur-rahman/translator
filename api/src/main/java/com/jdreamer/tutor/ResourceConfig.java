package com.jdreamer.tutor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

@Configuration
public class ResourceConfig {

    @Value("classpath:edict")
    private Resource edict;

    @Bean
    @Qualifier("dictionary")
    public Map<String, String> dictionary() {
        Map<String, String> dict = new HashMap<>();

        Pattern regex = Pattern.compile("([^\\s]+)\\s?\\[.*\\]\\s?/\\s?(.+)");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(edict.getInputStream(), "EUC-JP"))) {
            String line = reader.readLine();

            while (line != null) {
                Matcher m = regex.matcher(line);

                if (m.matches()) {
                    dict.put(m.group(1), m.group(2));
                }

                // read next line
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dict;
    }
}
