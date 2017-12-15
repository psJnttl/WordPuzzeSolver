package base;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import base.domain.Word;
import base.repository.WordRepository;

/**
 *
 * @author Pasi
 */
@Profile("test")
@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private WordRepository wordRepository;

    @Override
    public void run(String... strings) throws Exception {
        String [] words = {"abandon","babies","cabbage","daily","eager","fabric","gadgets","habanero","iceberg",
                "jacket","karat","label","machete","nab","oaring","pacemaker","quadrant","rabbi","sacred","table",
                "ubiquitous","vacation","wages","xray","yahoo","zealot"};
        insertWords(Arrays.asList(words));
        
    }

    private void insertWords(List<String> items) {
        for (String item: items) {
            Word word = new Word(item);
            wordRepository.saveAndFlush(word);
        }
    }
}
