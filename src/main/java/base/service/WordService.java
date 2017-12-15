package base.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
