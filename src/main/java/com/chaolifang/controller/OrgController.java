package com.chaolifang.controller;

import com.chaolifang.dto.OrgBaseInfoDTO;
import com.chaolifang.pojo.Book;
import com.chaolifang.result.BaseResult;
import com.chaolifang.result.DataTablesResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/org")
@Api(tags = "组织架构")
public class OrgController {
    @RequestMapping(value = "/getOrgTree", method = RequestMethod.GET)
    public BaseResult getOrgTree() {
        List<OrgBaseInfoDTO> data = new ArrayList<>();
        data.add(new OrgBaseInfoDTO("1","公司1",null,"北京"));
        data.add(new OrgBaseInfoDTO("2","公司2","1","北京"));
        data.add(new OrgBaseInfoDTO("3","公司3","2","北京"));
        data.add(new OrgBaseInfoDTO("4","公司4","2","北京"));
        data.add(new OrgBaseInfoDTO("5","公司5","1","北京"));
        data.add(new OrgBaseInfoDTO("6","公司6","3","北京"));
        List<OrgBaseInfoDTO> treeMenus = OrgBaseInfoDTO.createTreeMenus(data);
        return BaseResult.ok(treeMenus);
    }
}
