package base.command;

public class ColorMod {

    private long id;
    private int red;
    private int green;
    private int blue;
    private double alpha;

    public ColorMod(long id, int red, int green, int blue, double alpha) {
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public ColorMod() {

    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(alpha);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + red;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ColorMod other = (ColorMod) obj;
        if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha)) return false;
        if (blue != other.blue) return false;
        if (green != other.green) return false;
        if (id != other.id) return false;
        if (red != other.red) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ColorMod [id=" + id + ", red=" + red + ", green=" + green + ", blue=" + blue + ", alpha=" + alpha + "]";
    }

    
}
