package com.studyhub.repository;

import com.studyhub.domain.Revenue;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    // 일별
    @Query(value = """
    SELECT TRUNC(r.created_at), SUM(r.amount)
    FROM revenues r
    WHERE r.study_cafe_id = :cafeId
      AND TRUNC(r.created_at) BETWEEN :from AND :to
    GROUP BY TRUNC(r.created_at)
    ORDER BY TRUNC(r.created_at)
""", nativeQuery = true)
    List<Object[]> findDailyRevenue(@Param("cafeId") Long cafeId,
                                    @Param("from") Timestamp from,
                                    @Param("to") Timestamp to);


    // 주별
    @Query(value = """
        SELECT TO_CHAR(TRUNC(r.created_at, 'IW'), 'YYYY-MM-DD'), SUM(r.amount)
        FROM revenues r
        WHERE r.study_cafe_id = :cafeId
          AND r.created_at BETWEEN :from AND :to
        GROUP BY TRUNC(r.created_at, 'IW')
        ORDER BY TRUNC(r.created_at, 'IW')
    """, nativeQuery = true)
    List<Object[]> findWeeklyRevenue(@Param("cafeId") Long cafeId,
                                     @Param("from") Timestamp from,
                                     @Param("to") Timestamp to);

    // 월별
    @Query(value = """
        SELECT TO_CHAR(r.created_at, 'YYYY-MM'), SUM(r.amount)
        FROM revenues r
        WHERE r.study_cafe_id = :cafeId
          AND r.created_at BETWEEN :from AND :to
        GROUP BY TO_CHAR(r.created_at, 'YYYY-MM')
        ORDER BY TO_CHAR(r.created_at, 'YYYY-MM')
    """, nativeQuery = true)
    List<Object[]> findMonthlyRevenue(@Param("cafeId") Long cafeId,
                                      @Param("from") Timestamp from,
                                      @Param("to") Timestamp to);

    // 연도별
    @Query(value = """
        SELECT TO_CHAR(r.created_at, 'YYYY'), SUM(r.amount)
        FROM revenues r
        WHERE r.study_cafe_id = :cafeId
          AND r.created_at BETWEEN :from AND :to
        GROUP BY TO_CHAR(r.created_at, 'YYYY')
        ORDER BY TO_CHAR(r.created_at, 'YYYY')
    """, nativeQuery = true)
    List<Object[]> findYearlyRevenue(@Param("cafeId") Long cafeId,
                                     @Param("from") Timestamp from,
                                     @Param("to") Timestamp to);

}
