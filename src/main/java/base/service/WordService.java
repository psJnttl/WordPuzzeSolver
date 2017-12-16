package base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.controller.WordAdd;
import base.domain.Word;
import base.repository.WordRepository;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;
    
    public List<WordDto> listAll() {
        List<Word> words = wordRepository.findAll();
        return words.stream().map(w -> createDto(w)).collect(Collectors.toList());
    }
    
    private WordDto createDto(Word word) {
        return new WordDto(word.getId(), word.getValue());
    }

    public boolean doesWordExistInDb(WordAdd word) {
        List<Word> wordList = wordRepository.findByValueIgnoreCase(word.getValue());
        if (wordList.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional
    public WordDto addWord(WordAdd word) {
        Word newWord = new Word(word.getValue());
        newWord = wordRepository.save(newWord);
        return createDto(newWord);
    }

    public Optional<WordDto> findWord(long id) {
        Word word = wordRepository.findOne(id);
        if (null == word) {
            return Optional.empty();
        }
        return Optional.of(createDto(word));
    }
}
