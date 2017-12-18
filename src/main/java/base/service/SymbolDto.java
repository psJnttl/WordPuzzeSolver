package base.service;

public class SymbolDto {

    private long id;
    private String value;
    private int score;
    
    public SymbolDto() {

    }
    
    public SymbolDto(long id, String value, int score) {
        this.id = id;
        this.value = value;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + score;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SymbolDto other = (SymbolDto) obj;
        if (id != other.id) return false;
        if (score != other.score) return false;
        if (value == null) {
            if (other.value != null) return false;
        }
        else if (!value.equals(other.value)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SymbolDto [id=" + id + ", value=" + value + ", score=" + score + "]";
    }
    
}
