package com.example.dbdemo.mapper;

import com.example.dbdemo.domain.Data;
import org.apache.ibatis.annotations.Param;

public interface DataMapper {

    Data getDataById(@Param("id") long id);
}
