package base.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import base.command.TileColorAdd;
import base.command.TileColorMod;
import base.domain.TileColor;
import base.repository.TileColorRepository;

@Service
public class TileColorService {

    @Autowired
    private TileColorRepository tileColorRepository;
    
    @Transactional
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

    @Transactional
    public void deleteColor(long id) {
        tileColorRepository.delete(id);
    }

    @Transactional
    public TileColorDto modifyTileColor(long id, TileColorMod color) {
        TileColor tc = tileColorRepository.findOne(id);
        tc.setAlpha(color.getAlpha());
        tc.setBlue(color.getBlue());
        tc.setGreen(color.getGreen());
        tc.setRed(color.getRed());
        tc = tileColorRepository.saveAndFlush(tc);
        return createDto(tc);
    }

    public List<TileColorDto> listAll() {
        List<TileColor> tileColors = tileColorRepository.findAll();
        return tileColors.stream().map(t -> createDto(t)).collect(Collectors.toList());
    }
}
