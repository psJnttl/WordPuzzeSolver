package base.service;

public class WordCountDto {
    private long count;
    
    public WordCountDto() {
    }
    
    public WordCountDto(long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (count ^ (count >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        WordCountDto other = (WordCountDto) obj;
        if (count != other.count) return false;
        return true;
    }

    @Override
    public String toString() {
        return "WordCountDto [count=" + count + "]";
    }
    
}
