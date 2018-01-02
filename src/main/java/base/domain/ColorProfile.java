package base.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class ColorProfile extends AbstractPersistable<Long> {

    @Column(unique=true)
    private String name;
    
    @OneToMany
    @Size(min=0, max=15)
    private List<TileColor> colors;
    
    ColorProfile() {}
    
    ColorProfile(String name, List<TileColor> colors) {
        this.name = name;
        this.colors = colors;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<TileColor> getColors() {
        return colors;
    }
    public void setColors(List<TileColor> colors) {
        this.colors = colors;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((colors == null) ? 0 : colors.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ColorProfile other = (ColorProfile) obj;
        if (colors == null) {
            if (other.colors != null) return false;
        }
        else if (!colors.equals(other.colors)) return false;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ColorProfile [name=" + name + ", colors=" + colors + "]";
    }
    
}
