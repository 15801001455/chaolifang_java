package com.chaolifang.mapper;

import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.pojo.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper{
        /**
         * 添加书籍
         * @param book
         * @return
         */
        @Insert("insert into t_book(id,name,borrowStatus,mark,insertTime) values (#{book.id},#{book.name},1,#{book.mark},#{book.insertTime})")
        int insert(@Param("book") Book book);

        @Select("select * from t_book where id = #{id}")
        Book selectById(@Param("id") String id);

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
                " <if test='searchDTO.id != null and searchDTO.id.length > 0'>" ,
                " and id like CONCAT('%', #{searchDTO.id} , '%') " ,
                "</if> " ,
                " <if test='searchDTO.name != null and searchDTO.name.length > 0'>" ,
                " and name like CONCAT('%', #{searchDTO.name} , '%') " ,
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
                " <if test='searchDTO.id != null and searchDTO.id.length > 0'>" ,
                " and id like CONCAT('%', #{searchDTO.id} , '%') " ,
                "</if> " ,
                " <if test='searchDTO.name != null and searchDTO.name.length > 0'>" ,
                " and name like CONCAT('%', #{searchDTO.name} , '%') " ,
                "</if> " ,
                " <if test='searchDTO.borrowStatus != null and searchDTO.borrowStatus > 0'>" ,
                " and borrowStatus = #{searchDTO.borrowStatus} " ,
                "</if> " ,
                "</script>"}
        )
        Integer getBookManagerCount(@Param("searchDTO") BookSearchDTO searchDTO);

        @Update("update t_book set name = #{book.name},borrowTime = #{book.borrowTime},returnTime = #{book.returnTime},borrowPerson = #{book.borrowPerson},borrowStatus = #{book.borrowStatus},mark = #{book.mark},updateTime = #{book.updateTime} where id = #{book.id}")
        Integer updateById(@Param("book") Book book);

        @Delete("delete from t_book where id = #{id}")
        Integer deleteById(@Param("id") String id);

        @Select("SELECT * from t_book where borrowStatus = 2 and DATE_FORMAT(returnTime,'%Y-%m-%d') <= DATE_FORMAT(NOW(),'%Y-%m-%d');")
        List<Book> getUnReturnedBook();
}
