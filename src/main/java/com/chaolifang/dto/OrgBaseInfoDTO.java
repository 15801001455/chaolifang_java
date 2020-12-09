package com.chaolifang.dto;

import lombok.Data;

import java.util.*;
//存房项目实体 演示vue tree 和 获取树结构用
@Data
public class OrgBaseInfoDTO {

    public OrgBaseInfoDTO() {

    }

    public OrgBaseInfoDTO(String id, String name, String supid, String cityName) {
        this.id = id;
        this.name = name;
        this.supid = supid;
        this.cityName = cityName;
    }

    /**
    * 组织架构id
    * */
    private String id;
    /**
     * 组织架构名称
     * */
    private String name;
    /**
     * 上级id
     * */
    private String supid;
    /**
     * 排序
     * */
    private String showorder;
    /**
     * 组织架构级别id
     * */
    private String orgid;
    /**
     * 组织架构级别名称
     * */
    private String orgname;

    /**
     * 组织架构级别名称
     * */
    private String cityName;

    private Integer orgLevel;

    private List<OrgBaseInfoDTO> children = new ArrayList<OrgBaseInfoDTO>();
    /**
     * 组织架构类型
     * */
    private String type;

    public void sortChildren(){
        Collections.sort(children, new Comparator<OrgBaseInfoDTO>() {
            @Override
            public int compare(OrgBaseInfoDTO o1, OrgBaseInfoDTO o2) {
                int result = 0;
                String orderby1 = o1.getShoworder();
                String orderby2 = o2.getShoworder();
                String id1 = o1.getId();
                String id2 = o2.getId();
                if(null != orderby1 && null != orderby2 && !"".equals(orderby1) && !"".equals(orderby2)){
                    result = (Integer.parseInt(orderby1) < Integer.parseInt(orderby2) ? -1 : (Integer.parseInt(orderby1) == Integer.parseInt(orderby2) ? 0 : 1));
                }else{
                    result = (Integer.parseInt(id1) < Integer.parseInt(id2) ? -1 : (id1.equals(id2) ? 0 : 1));
                }
                return result;
            }
        });
        //对每个节点的下一层节点进行排序
        children.forEach(item -> item.sortChildren());
    }

    public static List<OrgBaseInfoDTO> createTreeMenus(List<OrgBaseInfoDTO> menus){
        List<OrgBaseInfoDTO> treeMenus = null;
        if(null != menus && !menus.isEmpty()){
            OrgBaseInfoDTO root = new OrgBaseInfoDTO();
            root.setName("根目录");
            // 组装map数据
            Map<String, OrgBaseInfoDTO> dataMap = new HashMap<>();
            for(OrgBaseInfoDTO menu : menus){
                dataMap.put(menu.getId(),menu);
            }
            //组装树形结构
            Set<Map.Entry<String, OrgBaseInfoDTO>> entrySet = dataMap.entrySet();
            for(Map.Entry<String, OrgBaseInfoDTO> entry : entrySet){
                OrgBaseInfoDTO menu = entry.getValue();
                if(null == menu.getSupid() || "0".equals(menu.getSupid()) || "".equals(menu.getSupid())) {
                    root.getChildren().add(menu);
                }else{
                    dataMap.get(menu.getSupid()).getChildren().add(menu);
                }
            }
            root.sortChildren();
            treeMenus = root.getChildren();
        }
        return treeMenus;
    }

    public static void main(String[] args) {
        List<OrgBaseInfoDTO> data = new ArrayList<>();
        data.add(new OrgBaseInfoDTO("1","公司1",null,"北京"));
        data.add(new OrgBaseInfoDTO("2","公司2","1","北京"));
        data.add(new OrgBaseInfoDTO("3","公司3","2","北京"));
        data.add(new OrgBaseInfoDTO("4","公司4","2","北京"));
        data.add(new OrgBaseInfoDTO("5","公司5","1","北京"));
        data.add(new OrgBaseInfoDTO("6","公司6","3","北京"));
        List<OrgBaseInfoDTO> treeMenus = OrgBaseInfoDTO.createTreeMenus(data);
    }
}
