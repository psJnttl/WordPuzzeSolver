package base.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import base.domain.Word;

public interface WordRepository extends JpaRepository<Word, Long> {

}
