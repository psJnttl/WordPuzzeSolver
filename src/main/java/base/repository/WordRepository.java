package base.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import base.domain.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
    Word findByValue(String value);
    List<Word> findByValueIgnoreCase(String value);
    Page<Word> findByValueContainsIgnoreCase(String searchValue, Pageable pageRequest);
}
