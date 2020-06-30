package com.chaolifang.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface BookMapper extends BaseMapper<BookManagerDTO> {
        @Select({"<script>",
                "select * from BookManager where 1=1 " ,
                " <if test='searchDTO.borrowTimeStartStr != null and searchDTO.borrowTimeStartStr.length > 0'>" ,
                " and borrowTime &gt;= #{searchDTO.borrowTimeStartStr} "  ,
                "</if> " ,
                " <if test='searchDTO.borrowTimeEndStr != null and searchDTO.borrowTimeEndStr.length > 0'>" ,
                " and borrowTime &lt;= #{searchDTO.borrowTimeEndStr} " ,
                "</if> " ,
                " <if test='searchDTO.borrowPerson != null and searchDTO.borrowPerson.length > 0'>" ,
                " and borrowPerson like CONCAT('%', #{searchDTO.borrowPerson} , '%') " ,
                "</if> " ,
                " <if test='searchDTO.borrowStatus != null and searchDTO.borrowStatus > 0'>" ,
                " and borrowStatus = #{searchDTO.borrowStatus} " ,
                "</if> " ,
                " limit #{searchDTO.pageIndex},#{searchDTO.pageSize}",
                "</script>"}
        )
        List<BookManagerDTO> getBookManagerList(@Param("searchDTO") BookManagerSearchDTO searchDTO);

        /**
         * @Param("searchDTO") 这个注解必须加上
         * @param searchDTO
         * @return
         */
        @Select({"<script>",
                "select count(*) from BookManager where 1=1 " ,
                " <if test='searchDTO.borrowTimeStartStr != null and searchDTO.borrowTimeStartStr.length > 0'>" ,
                " and borrowTime &gt;= #{searchDTO.borrowTimeStartStr} "  ,
                "</if> " ,
                " <if test='searchDTO.borrowTimeEndStr != null and searchDTO.borrowTimeEndStr.length > 0'>" ,
                " and borrowTime &lt;= #{searchDTO.borrowTimeEndStr} " ,
                "</if> " ,
                " <if test='searchDTO.borrowPerson != null and searchDTO.borrowPerson.length > 0'>" ,
                " and borrowPerson like CONCAT('%', #{searchDTO.borrowPerson} , '%') " ,
                "</if> " ,
                " <if test='searchDTO.borrowStatus != null and searchDTO.borrowStatus > 0'>" ,
                " and borrowStatus = #{searchDTO.borrowStatus} " ,
                "</if> " ,
                "</script>"}
        )
        Integer getBookManagerCount(@Param("searchDTO") BookManagerSearchDTO searchDTO);
}
