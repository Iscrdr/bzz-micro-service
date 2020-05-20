<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.${moduleName}.dao<#if subModuleName != "">.${subModuleName}</#if>.${genTable.className}Dao">

    <sql id="${genTable.classNameLower}Columns">
		<#assign columnField>
			<#list genTable.columnList as c>
				t.${c.columnName} AS ${c.javaField},
			</#list>
		</#assign>
		${columnField?substring(0, columnField?last_index_of(","))}
    </sql>

    <!--通过id查询-->
    <select id="get" resultType="${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.entity.${genTable.className}">
        select
        	<include refid="genTableColumns"/>
        from ${genTable.tableName} t
        <where>
            t.del_flag = 0 AND t.id = ${"#"}{id}
        </where>
    </select>

    <!--查询-->
    <select id="findList" resultType="${packageName}.${moduleName}<#if subModuleName != "">.${subModuleName}</#if>.entity.${genTable.className}">
        select
        	<include refid="genTableColumns"/>
        from ${genTable.tableName} t
        <where>
            t.del_flag = 0
		<#--<if test="dbType != null and dbType != '' ">
            AND t.table_name LIKE
            <if test="dbType == 'oracle'">'%,'||#{tableName}||',%')</if>
            <if test="dbType == 'mssql'">'%,'+#{tableName}+',%')</if>
            <if test="dbType == 'mysql'">CONCAT('%,', #{tableName}, ',%'))</if>
        </if>-->
        </where>
    </select>

    <!--插入-->
    <insert id="insert">
        INSERT INTO ${genTable.tableName}(
				<#assign columnField>
					<#list genTable.columnList as c>
							${c.columnName},
					</#list>
				</#assign>
	${columnField?substring(0, columnField?last_index_of(","))}
        ) VALUES (
					<#assign columnField>
						<#list genTable.columnList as c>
                            ${"#"}{${c.javaField}},
						</#list>
					</#assign>
	${columnField?substring(0, columnField?last_index_of(","))}

        )
    </insert>

    <!-- 更新用户 -->
    <update id="update">
        UPDATE
			${genTable.tableName}
        SET
		<#assign columnField>
			<#list genTable.columnList as c>
					${c.columnName} =	${"#"}{${c.javaField}},
			</#list>
		</#assign>
	${columnField?substring(0, columnField?last_index_of(","))}

    </update>

    <!-- 逻辑删除用户 -->
    <update id="deleteFalse">
        UPDATE
			${genTable.tableName}
        SET
            del_flag = 1
        WHERE
            id = ${"#"}{id}
	</update>


    <!-- 物理删除用户 -->
    <delete id="delete">
        DELETE FROM
            ${genTable.tableName}
        WHERE
            id = ${"#"}{id}
	</delete>


</mapper>