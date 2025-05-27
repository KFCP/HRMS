package com.example.hrms.service;

import com.example.hrms.model.Position;
import java.util.List;

public interface PositionService {
    boolean addPosition(Position position);
    Position findPositionById(Long id);
    List<Position> getAllPositions();
    List<Position> findPositionsByName(String nameKeyword);
    boolean updatePosition(Position position);
    boolean deletePosition(Long id);
}
