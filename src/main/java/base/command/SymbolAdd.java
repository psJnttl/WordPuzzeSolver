package base.command;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SymbolAdd {

    @NotNull
    @Pattern(regexp="[a-zA-Z]{1,3}|[a-zA-Z]{2,3}-|-[a-zA-Z]{2,3}|[a-zA-Z]/[a-zA-Z]", message="Symbol not valid.")
    private String value;
    
    @Min(value = 1, message = "Points minimum is 1.")
    private int score;
    
    public SymbolAdd() {

    }

    public SymbolAdd(String value, int score) {
        this.value = value;
        this.score = score;
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
        result = prime * result + score;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SymbolAdd other = (SymbolAdd) obj;
        if (score != other.score) return false;
        if (value == null) {
            if (other.value != null) return false;
        }
        else if (!value.equals(other.value)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SymbolAdd [value=" + value + ", score=" + score + "]";
    }
    
}
