package com.vmo.demo.dao;

import com.vmo.demo.model.response.NumberOfFresherEachScoreRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YourRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public YourRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NumberOfFresherEachScoreRange> getNumberOfFresherEachScoreRange() {

        String sql = """
        with abc as (
                select cast(((score_1+score_2+score_3)/3) as decimal(3,1)) as average_core,
                case
                when ((score_1+score_2+score_3)/3)>=0 and ((score_1+score_2+score_3)/3)<1 then '0-1'
                when ((score_1+score_2+score_3)/3)>=1 and ((score_1+score_2+score_3)/3)<2 then '1-2'
                when ((score_1+score_2+score_3)/3)>=2 and ((score_1+score_2+score_3)/3)<3 then '2-3'
                when ((score_1+score_2+score_3)/3)>=2 and ((score_1+score_2+score_3)/3)<3 then '2-3'
                when ((score_1+score_2+score_3)/3)>=2 and ((score_1+score_2+score_3)/3)<3 then '2-3'
                when ((score_1+score_2+score_3)/3)>=3 and ((score_1+score_2+score_3)/3)<4 then '3-4'
                when ((score_1+score_2+score_3)/3)>=4 and ((score_1+score_2+score_3)/3)<5 then '4-5'
                when ((score_1+score_2+score_3)/3)>=5 and ((score_1+score_2+score_3)/3)<6 then '5-6'
                when ((score_1+score_2+score_3)/3)>=6 and ((score_1+score_2+score_3)/3)<7 then '6-7'
                when ((score_1+score_2+score_3)/3)>=7 and ((score_1+score_2+score_3)/3)<8 then '7-8'
                when ((score_1+score_2+score_3)/3)>=8 and ((score_1+score_2+score_3)/3)<9 then '8-9'
                when ((score_1+score_2+score_3)/3)>=9 and ((score_1+score_2+score_3)/3)<10 then '9-10'
                else '10'
                end as score_range
                from fresher
                )
                select count(*) as score, score_range
                from abc
                group by score_range
                order by score_range """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> { // if needed, params is second parameter of query method
            NumberOfFresherEachScoreRange obj = new NumberOfFresherEachScoreRange();
            obj.setScore(rs.getInt("score")); // Assuming "score" is a column in your_table
            obj.setScoreRange(rs.getString("score_range")); // Assuming "score_range" is a column in your_table
// Map other properties as needed
            return obj;
        });
    }
}
