package base.domain;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class TileColor extends AbstractPersistable<Long> {

    @Size(min=0, max=255)
    private int red;
    
    @Size(min=0, max=255)
    private int green;
    
    @Size(min=0, max=255)
    private int blue;
    
    @Size(min=0, max=1)
    private double alpha;
    
    public TileColor() {
    }
    
    public TileColor(int r, int g, int b, double a) {
        red = r;
        green = g;
        blue = b;
        alpha = a;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(alpha);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + red;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        TileColor other = (TileColor) obj;
        if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha)) return false;
        if (blue != other.blue) return false;
        if (green != other.green) return false;
        if (red != other.red) return false;
        return true;
    }

    @Override
    public String toString() {
        return "TileColor [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
    }

}
