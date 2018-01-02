package base.command;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ColorAdd {

    @Min(value = 0, message = "Red min is 0.")
    @Max(value = 255, message = "Red max is 255.")
    private int red;
    
    @Min(value = 0, message = "Green min is 0.")
    @Max(value = 255, message = "Green max is 255.")
    private int green;
    
    @Min(value = 0, message = "Blue min is 0.")
    @Max(value = 255, message = "Blue max is 255.")
    private int blue;
    
    @Min(value = 0, message = "Alpha min is 0.")
    @Max(value = 1, message = "Alpha max is 1")
    private double alpha;

    public int getRed() {
        return red;
    }
    
    public ColorAdd(int red, int green, int blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public ColorAdd() {

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
        int result = 1;
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
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ColorAdd other = (ColorAdd) obj;
        if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha)) return false;
        if (blue != other.blue) return false;
        if (green != other.green) return false;
        if (red != other.red) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ColorAdd [red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
    }

    
}
