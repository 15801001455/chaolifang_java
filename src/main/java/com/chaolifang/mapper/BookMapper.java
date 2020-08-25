package com.chaolifang.mapper;

import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.pojo.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper{

        int insert(Book book);

        Book selectById(String id);

        @Select({"<script>",
                "select * from t_book where 1=1 " ,
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
        List<Book> getBookManagerList(@Param("searchDTO") BookSearchDTO searchDTO);

        /**
         * @Param("searchDTO") 这个注解必须加上
         * @param searchDTO
         * @return
         */
        @Select({"<script>",
                "select count(*) from t_book where 1=1 " ,
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
        Integer getBookManagerCount(@Param("searchDTO") BookSearchDTO searchDTO);

        Integer updateById(Book dto);

        Integer deleteById(String id);
}
