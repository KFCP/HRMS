package com.example.hrms.mapper;

import com.example.hrms.model.Position;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PositionMapper {
    int insertPosition(Position position); // Returns int for rows affected, useGeneratedKeys will populate ID in Position object

    Position findPositionById(@Param("id") Long id);

    List<Position> findAllPositions();

    List<Position> findPositionsByName(@Param("nameKeyword") String nameKeyword);

    int updatePosition(Position position);

    int deletePosition(@Param("id") Long id);
}
