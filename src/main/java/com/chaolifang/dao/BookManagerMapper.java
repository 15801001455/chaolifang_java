package com.chaolifang.dao;

import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BookManagerMapper {

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

    @Insert("insert into BookManager(id,name,mark,borrowStatus,insertTime) values (#{dto.id},#{dto.name},#{dto.mark},#{dto.borrowStatus},now())")
    Integer addBookManager(@Param("dto") BookManagerDTO dto);

    @Update("update BookManager set name = #{dto.name},mark = #{dto.mark},borrowStatus = #{dto.borrowStatus},borrowPerson = #{dto.borrowPerson},borrowTime = #{dto.borrowTime},returnTime = #{dto.returnTime},updateTime = now() where id = #{dto.id}")
    Integer updateBookManager(@Param("dto") BookManagerDTO dto);

    @Select("select * from BookManager where 1=1 and id = #{id}")
    BookManagerDTO selectById(@Param("id") String id);

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

    @Delete("delete from BookManager where id = #{id}")
    Integer deleteById(@Param("id") String id);
}
