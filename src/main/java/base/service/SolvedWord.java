package base.service;

import java.util.Arrays;

public class SolvedWord {

    private String value;
    private int points;
    private int [] path;
    
    public SolvedWord() {
    }

    public SolvedWord(String value, int points, int [] path) {
        this.value = value;
        this.points = points;
        this.path = path;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(path);
        result = prime * result + points;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SolvedWord other = (SolvedWord) obj;
        if (!Arrays.equals(path, other.path)) return false;
        if (points != other.points) return false;
        if (value == null) {
            if (other.value != null) return false;
        }
        else if (!value.equals(other.value)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SolvedWord [value=" + value + ", points=" + points + ", path=" + Arrays.toString(path) + "]";
    }
    
}
