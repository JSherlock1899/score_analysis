package com.slxy.analysis.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author: sherlock
 * @description:
 * @date: 2019/9/7 13:21
 */

public interface SuperAdminMapper {

    @Select("CREATE TABLE ${tableName} (\n" +
            "  `id` int(11) NOT NULL,\n" +
            "  `classNumber` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,\n" +
            "  `chinese_grades` char(8) DEFAULT NULL,\n" +
            "  `math_grades` char(8) DEFAULT NULL,\n" +
            "  `english_grades` char(8) DEFAULT NULL,\n" +
            "  `physics_grades` char(8) DEFAULT NULL,\n" +
            "  `chemistry_grades` char(8) DEFAULT NULL,\n" +
            "  `biology_grades` char(8) DEFAULT NULL,\n" +
            "  `history_grades` char(8) DEFAULT NULL,\n" +
            "  `politics_grades` char(8) DEFAULT NULL,\n" +
            "  `geography_grades` char(8) DEFAULT NULL,\n" +
            "  `technology_grades` char(8) DEFAULT NULL,\n" +
            "  `total_point_grades` char(8) DEFAULT NULL,\n" +
            "  `total_point_class_ranking` char(8) DEFAULT NULL,\n" +
            "  `total_point_ranking` char(8) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;")
    void createStudentTable(String tableName);
}
