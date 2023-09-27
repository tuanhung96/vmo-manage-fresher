package com.vmo.manage_fresher.dao;

import com.vmo.manage_fresher.model.response.NumberOfFresherEachScoreRange;
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
        String sql = "with abc as (\n" +
                "select cast(((score_1+score_2+score_3)/3) as decimal(3,1)) as average_core,\n" +
                "case \n" +
                "when ((score_1+score_2+score_3)/3)>=0 and ((score_1+score_2+score_3)/3)<1 then '0-1'\n" +
                "when ((score_1+score_2+score_3)/3)>=1 and ((score_1+score_2+score_3)/3)<2 then '1-2'\n" +
                "when ((score_1+score_2+score_3)/3)>=2 and ((score_1+score_2+score_3)/3)<3 then '2-3'\n" +
                "when ((score_1+score_2+score_3)/3)>=3 and ((score_1+score_2+score_3)/3)<4 then '3-4'\n" +
                "when ((score_1+score_2+score_3)/3)>=4 and ((score_1+score_2+score_3)/3)<5 then '4-5'\n" +
                "when ((score_1+score_2+score_3)/3)>=5 and ((score_1+score_2+score_3)/3)<6 then '5-6'\n" +
                "when ((score_1+score_2+score_3)/3)>=6 and ((score_1+score_2+score_3)/3)<7 then '6-7'\n" +
                "when ((score_1+score_2+score_3)/3)>=7 and ((score_1+score_2+score_3)/3)<8 then '7-8'\n" +
                "when ((score_1+score_2+score_3)/3)>=8 and ((score_1+score_2+score_3)/3)<9 then '8-9'\n" +
                "when ((score_1+score_2+score_3)/3)>=9 and ((score_1+score_2+score_3)/3)<10 then '9-10'\n" +
                "else '10'\n" +
                "end as score_range\n" +
                "from fresher\n" +
                ")\n" +
                "\n" +
                "select count(*) as score, score_range\n" +
                "from abc\n" +
                "group by score_range\n" +
                "order by score_range";

//        String sql = "SELECT * FROM your_table WHERE column1 = :param1 AND column2 = :param2";
//        Map<String, Object> params = Map.of("param1", param1, "param2", param2);

        return jdbcTemplate.query(sql, (rs, rowNum) -> { // if needed, params is second parameter of query method
            NumberOfFresherEachScoreRange obj = new NumberOfFresherEachScoreRange();
            obj.setScore(rs.getInt("score")); // Assuming "score" is a column in your_table
            obj.setScoreRange(rs.getString("score_range")); // Assuming "score_range" is a column in your_table
// Map other properties as needed
            return obj;
        });
    }
}
