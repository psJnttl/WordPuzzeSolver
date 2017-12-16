package base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import base.domain.Word;
import base.service.WordDto;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findByValueIgnoreCase(String value);

}
