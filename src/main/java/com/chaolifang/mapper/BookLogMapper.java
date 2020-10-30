package com.chaolifang.mapper;

import com.chaolifang.dto.BookSearchDTO;
import com.chaolifang.pojo.Book;
import com.chaolifang.pojo.BookLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookLogMapper {
        /**
         * 添加书籍日志
         * @param book
         * @return
         */
        @Insert("insert into t_book_log(id,name,borrowTime,returnTime,borrowPerson,borrowStatus,mark,insertTime) values (#{book.id},#{book.name},#{book.borrowTime},#{book.returnTime},#{book.borrowPerson},#{book.borrowStatus},#{book.mark},#{book.insertTime})")
        int insert(@Param("book") BookLog book);

        @Select("select * from t_book_log where id = #{id}")
        List<BookLog> selectById(@Param("id") String id);

}
