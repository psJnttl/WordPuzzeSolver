package base.service;

import java.util.List;

public class SolvedWord {

    private String value;
    private int points;
    private List<Integer> path;
    
    public SolvedWord() {
    }

    public SolvedWord(String value, int points, List<Integer> path) {
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

    public List<Integer> getPath() {
        return path;
    }

    public void setPath(List<Integer> path) {
        this.path = path;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        if (path == null) {
            if (other.path != null) return false;
        }
        else if (!path.equals(other.path)) return false;
        if (points != other.points) return false;
        if (value == null) {
            if (other.value != null) return false;
        }
        else if (!value.equals(other.value)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "SolvedWord [value=" + value + ", points=" + points + ", path=" + path + "]";
    }
    
}
