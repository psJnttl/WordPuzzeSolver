package base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.command.ColorAdd;
import base.domain.TileColor;
import base.repository.TileColorRepository;

@Service
public class ColorService {

    @Autowired
    private TileColorRepository tileColorRepository;
    
    public ColorDto addColor(ColorAdd nc) {
        TileColor color = new TileColor(nc.getRed(), nc.getGreen(), nc.getBlue(), nc.getAlpha());
        color = tileColorRepository.saveAndFlush(color);
        return createDto(color);
    }
    
    private ColorDto createDto(TileColor c) {
        ColorDto dto = new ColorDto(c.getId(), c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        return dto;
    }
}
