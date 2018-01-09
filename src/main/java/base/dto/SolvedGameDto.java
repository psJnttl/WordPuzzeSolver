package base.dto;

import java.util.List;

import base.service.SolvedWord;

public class SolvedGameDto {

    private long time;
    private List<SolvedWord> words;
    
    public SolvedGameDto() {
    }
    
    public SolvedGameDto(List<SolvedWord> words) {
        this.words = words;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<SolvedWord> getWords() {
        return words;
    }

    public void setWords(List<SolvedWord> words) {
        this.words = words;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (time ^ (time >>> 32));
        result = prime * result + ((words == null) ? 0 : words.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SolvedGameDto other = (SolvedGameDto) obj;
        if (time != other.time) return false;
        if (words == null) {
            if (other.words != null) return false;
        }
        else if (!words.equals(other.words)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SolvedGameDto [time=" + time + ", words=" + words + "]";
    }
    
}
