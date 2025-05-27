package com.example.hrms.service.impl;

import com.example.hrms.model.Position;
import com.example.hrms.mapper.PositionMapper;
import com.example.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionMapper positionMapper;

    @Autowired
    public PositionServiceImpl(PositionMapper positionMapper) {
        this.positionMapper = positionMapper;
    }

    @Override
    @Transactional // Modifies data
    public boolean addPosition(Position position) {
        if (position == null || position.getPositionName() == null || position.getPositionName().trim().isEmpty()) {
            return false; // Basic validation
        }
        // Potentially check for duplicate position name/level if required by business logic
        return positionMapper.insertPosition(position) > 0;
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction
    public Position findPositionById(Long id) {
        return positionMapper.findPositionById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> getAllPositions() {
        return positionMapper.findAllPositions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Position> findPositionsByName(String nameKeyword) {
        if (nameKeyword == null || nameKeyword.trim().isEmpty()) {
            return getAllPositions(); // Or return an empty list, depending on desired behavior
        }
        return positionMapper.findPositionsByName(nameKeyword);
    }

    @Override
    @Transactional // Modifies data
    public boolean updatePosition(Position position) {
        if (position == null || position.getId() == null || position.getPositionName() == null || position.getPositionName().trim().isEmpty()) {
            return false; // Basic validation
        }
        return positionMapper.updatePosition(position) > 0;
    }

    @Override
    @Transactional // Modifies data
    public boolean deletePosition(Long id) {
        if (id == null) {
            return false;
        }
        // Add business logic here: e.g., check if any employees are assigned to this position before deleting.
        // For now, direct deletion.
        return positionMapper.deletePosition(id) > 0;
    }
}
