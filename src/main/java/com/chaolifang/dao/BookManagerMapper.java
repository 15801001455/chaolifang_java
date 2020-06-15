package com.chaolifang.dao;

import com.chaolifang.domain.BookManagerDTO;
import com.chaolifang.dto.BookManagerSearchDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookManagerMapper {

    @Select("select * from BookManager limit #{searchDTO.pageIndex},#{searchDTO.pageSize}")
    List<BookManagerDTO> getBookManagerList(@Param("searchDTO") BookManagerSearchDTO searchDTO);

    @Insert("insert into BookManager()")
    int addBookManager(BookManagerDTO dto);

    /**
     * @Param("searchDTO") 这个注解必须加上
     * @param searchDTO
     * @return
     */
    @Select("select count(*) from BookManager")
    Integer getBookManagerCount(@Param("searchDTO") BookManagerSearchDTO searchDTO);
}
