#代码生成器，使用方式。
```
特别注意 ，字段名用下划线模式，java代码用驼峰模式。所有字段，表必须要写comment.
```
##表结构中的默认数据
###BaseEntity中的自动填充字段
```sql
  `id` bigint(32) UNSIGNED NOT NULL COMMENT '表id',
  `create_user`	bigint(32) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(32) NULL DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted`	int(1) NOT NULL COMMENT '是否已删除',
  `status`	int(1)	NULL	DEFAULT	NULL	COMMENT '状态',
  `tenant_id` varchar(12) NULL DEFAULT '000000' COMMENT '租户ID',
```
以上字段已经做了自动填充，只要在建表的时候加上字段即可，java程序中不需要做处理。要继承BaseEntity。

###树形结构表处理
```sql_92
  `parent_id` bigint(32) NULL DEFAULT 0 COMMENT '父级菜单',
  `sort` int(2) NULL DEFAULT NULL COMMENT '排序',
  `is_leaf` int(1) NOT NULL DEFAULT 0 COMMENT '是否是叶子节点，0是，1不是',
```
当一个表里有parent_id 时，认为是树形结构表。java代码和vue前端会自动增加相关代码
###常用字段命名
* 开发中注意约定大于配置，用不着什么东西都去配置，按相关约定来写代码会减少大量的配置工作
```sql_92
 `remarks` varchar(512) DEFAULT NULL COMMENT '备注信息',
 `sort` int(2) NULL DEFAULT NULL COMMENT '排序',
```
###特殊说明
* 一个表必须有id字段做为主键，mysql中为bigint,oracle中为number(28),java代码中用Long.
* 一个字段是从字典里取值 ，只需要在comment里加入：取字典@dictKey@的值。这样前端会生成相关的代码。
* 字段是date,datetime,timestamp.java中的类型，统一用localDateTime,前端会自动写入选择时间的组件代码。
###增加一个菜单
```
 增加menu表数据。
 增加权限表数据，以便菜单能显示出来。
 修改router.js文件
 修改多语言文件zh.js中的router，加入相关中文显示 。
```