package com.bzz.cloud;

import com.bzz.cloud.gen.entity.GenScheme;
import com.bzz.cloud.gen.entity.GenTable;
import com.bzz.cloud.gen.entity.GenTableColumn;
import com.bzz.cloud.gen.service.GenTableColumnService;
import com.bzz.cloud.gen.service.GenTableService;
import com.bzz.cloud.util.GenUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={GenApp.class})
public class GenTableTest {


    @Autowired
    private GenTableService genTableService;
    @Autowired
    private GenTableColumnService genTableColumnService;
    @Test
    public void testColumn(){
        String result = "[GenTableColumn(genTable=null, columnName=id, comments=编号, jdbcType=bigint(64), javaType=null, javaField=null, isPk=null, isNull=0, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=10), " +
                "GenTableColumn(genTable=null, columnName=login_name, comments=登录名, jdbcType=varchar(100), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=20), " +
                "GenTableColumn(genTable=null, columnName=old_login_name, comments=原登录名, jdbcType=varchar(100), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=30), " +
                "GenTableColumn(genTable=null, columnName=password, comments=密码, jdbcType=varchar(100), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=40), " +
                "GenTableColumn(genTable=null, columnName=new_password, comments=新密码, jdbcType=varchar(255), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=50), " +
                "GenTableColumn(genTable=null, columnName=work_num, comments=工号, jdbcType=varchar(100), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=60), " +
                "GenTableColumn(genTable=null, columnName=name, comments=姓名, jdbcType=varchar(100), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=70), " +
                "GenTableColumn(genTable=null, columnName=email, comments=邮箱, jdbcType=varchar(200), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=80), " +
                "GenTableColumn(genTable=null, columnName=phone, comments=电话, jdbcType=varchar(200), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=90), " +
                "GenTableColumn(genTable=null, columnName=mobile, comments=手机, jdbcType=varchar(11), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=100), " +
                "GenTableColumn(genTable=null, columnName=user_type, comments=用户类型, jdbcType=int(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=110), " +
                "GenTableColumn(genTable=null, columnName=photo, comments=用户头像, jdbcType=varchar(1000), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=120), " +
                "GenTableColumn(genTable=null, columnName=account_non_expired, comments=账户是否过期,过期无法验证, jdbcType=tinyint(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=130), " +
                "GenTableColumn(genTable=null, columnName=account_non_locked, comments=用户是否被锁定或者解锁,锁定的用户无法进行身份验证, jdbcType=tinyint(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=140)," +
                "GenTableColumn(genTable=null, columnName=credentials_non_expired, comments=指示是否已过期的用户的凭据(密码),过期的凭据防止认证, jdbcType=tinyint(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=150), " +
                "GenTableColumn(genTable=null, columnName=enabled, comments=是否被禁用,禁用的用户不能身份验证, jdbcType=tinyint(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=160), " +
                "GenTableColumn(genTable=null, columnName=create_user_id, comments=创建者, jdbcType=bigint(64), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=170), " +
                "GenTableColumn(genTable=null, columnName=create_time, comments=创建时间, jdbcType=datetime, javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=180), " +
                "GenTableColumn(genTable=null, columnName=update_user_id, comments=更新者, jdbcType=bigint(64), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=190)," +
                "GenTableColumn(genTable=null, columnName=update_time, comments=更新时间, jdbcType=datetime, javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=200), " +
                "GenTableColumn(genTable=null, columnName=todo, comments=备注信息, jdbcType=varchar(255), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=210)," +
                "GenTableColumn(genTable=null, columnName=remarks, comments=备注信息, jdbcType=varchar(255), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=220), " +
                "GenTableColumn(genTable=null, columnName=del_flag, comments=删除标记, jdbcType=int(1), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=230), " +
                "GenTableColumn(genTable=null, columnName=version, comments=版本, jdbcType=int(11), javaType=null, javaField=null, isPk=null, isNull=1, isInsert=null, isEdit=null, isList=null, isQuery=null, queryType=null, showType=null, dictType=, sort=240)]";
        List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_user");
        System.out.println(lists.toString());

    }

    /**
     * 用户表
     */
    @Test
    public void testGetUserTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_user");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysUser");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysUser");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("系统用户表");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("用户表");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_user");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("用户管理");
                genScheme.setName("用户管理");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("sys");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }

    /**
     * 角色表
     */
    @Test
    public void testGetRoleTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_role");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysRole");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysRole");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("角色表");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("角色表");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_role");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("角色管理");
                genScheme.setName("角色管理");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }

    /**
     * 用户组表
     */
    @Test
    public void testGetGroupTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_group");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysGroup");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysGroup");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("用户组表");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("用户组表");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_group");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("用户组管理");
                genScheme.setName("用户组管理");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }


    /**
     * 用户组表
     */
    @Test
    public void testGetApiTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_api");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysApi");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysApi");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("Api表");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("Api表");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_api");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("Api表管理");
                genScheme.setName("Api表管理");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }

    /**
     * 权限表
     */
    @Test
    public void testGetAuthTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_authority");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysAuthority");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysAuthority");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("权限表");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("权限表");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_authority");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("权限表管理");
                genScheme.setName("权限表管理");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }


    /**
     * 权限表
     */
    @Test
    public void testAAGetAuthTable(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
       // String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "branch_info");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("BranchInfo");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("branchInfo");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("BranchInfo");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("BranchInfo");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "branch_info");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("branch_info");
                genScheme.setName("branch_info");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }


    /**
     * 地域表
     */
    @Test
    public void testArea(){
        /*GenTable genTable = genTableService.get(1L);
        System.out.println(genTable.getClassName());*/
        // String result= "{Comment=用户表, Data_free=0, Create_options=row_format=DYNAMIC, Check_time=null, Collation=utf8_general_ci, Create_time=2019-02-17 11:09:06.0, Name=sys_user, Avg_row_length=2340, Row_format=Dynamic, Version=10, Checksum=null, Update_time=2019-02-17 16:00:34.0, Max_data_length=0, Index_length=49152, Auto_increment=null, Engine=InnoDB, Data_length=16384, Rows=7}";
        List<Map<String,String>> tables = genTableService.getTable("bzz", "sys_area");
        if(tables!= null && tables.size()>0){
            if (tables.size()==1){
                Map<String, String> table = tables.get(0);
                GenTable genTable = new GenTable();
                genTable.setClassName("SysArea");
                genTable.setComments(table.get("Comment"));
                genTable.setTableName(table.get("Name"));
                genTable.setClassNameLower("sysArea");

                //System.out.println(table.get("Create_time"));

                //genTable.setCreateTime(DateUtils.getDateToString(table.get("Create_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                //genTable.setUpdateTime(DateUtils.getDateToString(table.get("Update_time"),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                genTable.setCreateUserId(1L);
                genTable.setUpdateUserId(1L);
                genTable.setTodo("行政区域");
                genTable.setDelFlag(1);
//                genTable.setVersion(Integer.valueOf(table.get("Version")));
                genTable.setRemarks("行政区域");
                List<GenTableColumn> lists = genTableColumnService.getTableColumn("bzz", "sys_area");
                genTable.setColumnList(lists);

                GenUtils.initColumnField(genTable);
                GenScheme genScheme = new GenScheme();
                genScheme.setGenTable(genTable);

                genScheme.setEmail("624003618@qq.com");

                genScheme.setModuleAuthor("yang qianli");
                genScheme.setModuleDesc("行政区域");
                genScheme.setName("行政区域");
                genScheme.setPackageName("com.bzz.cloud");
                genScheme.setModuleName("rbac");
                genScheme.setSubModuleName("");
                genScheme.setReplaceFile(true);

                genScheme.setCreateTime(new Date());
                genScheme.setUpdateTime(new Date());



                GenUtils.genEntity(genScheme);
                GenUtils.genDao(genScheme);
                GenUtils.genService(genScheme);
                GenUtils.genController(genScheme);
                GenUtils.genMapper(genScheme);
            }
        }

    }
}
