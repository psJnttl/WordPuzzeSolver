package base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.controller.WordAdd;
import base.controller.WordMod;
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

    @Transactional
    public void deleteWord(long id) {
        wordRepository.delete(id);        
    }

    @Transactional
    public WordDto modifyWord(long id, WordMod word) {
        Word word2mod = wordRepository.findOne(id);
        word2mod.setValue(word.getValue());
        word2mod = wordRepository.saveAndFlush(word2mod);
        return createDto(word2mod);
    }

    public boolean doesWordValueExist(long id, WordMod word) {
        Word oldWord = wordRepository.findByValue(word.getValue());
        if (null == oldWord || id == oldWord.getId()) {
            return false;
        }
        return true;
    }
}
