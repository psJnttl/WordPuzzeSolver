package base.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import base.command.TileColorAdd;
import base.domain.TileColor;
import base.repository.TileColorRepository;

@Service
public class TileColorService {

    @Autowired
    private TileColorRepository tileColorRepository;
    
    public TileColorDto addColor(TileColorAdd nc) {
        TileColor color = new TileColor(nc.getRed(), nc.getGreen(), nc.getBlue(), nc.getAlpha());
        color = tileColorRepository.saveAndFlush(color);
        return createDto(color);
    }
    
    private TileColorDto createDto(TileColor c) {
        TileColorDto dto = new TileColorDto(c.getId(), c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        return dto;
    }
    
    public Optional<TileColorDto> findTileColor(long id) {
        TileColor tc = tileColorRepository.findOne(id);
        if (null == tc) {
            return Optional.empty();
        }
        TileColorDto dto = createDto(tc);
        return Optional.of(dto);
    }

    public void deleteColor(long id) {
        tileColorRepository.delete(id);
    }
}
