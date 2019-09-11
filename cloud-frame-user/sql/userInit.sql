CREATE TABLE `sys_function`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `MODULE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '模块标识',
  `CODE` varchar(128) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `VALIDATE` varchar(32) NOT NULL COMMENT '是否验证',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `HTTP_METHOD` varchar(128) NOT NULL COMMENT 'HTTP请求方法类型',
  `URL` varchar(1024) NULL DEFAULT NULL COMMENT 'URL',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `CODE`(`CODE`)
) COMMENT = '功能表';

CREATE TABLE `sys_menu`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `PARENT_CODE` varchar(128) NULL DEFAULT NULL COMMENT '所属模块标识',
  `PARENT_CODES` varchar(2048) NULL DEFAULT NULL COMMENT '所属模块标识关系',
  `CODE` varchar(128) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `URL` varchar(1024) NULL DEFAULT NULL COMMENT 'URL',
  `ICON` varchar(1024) NULL DEFAULT NULL COMMENT '图标',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '菜单表';

CREATE TABLE `sys_menu_module`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `MENU_CODE` varchar(128) NULL DEFAULT NULL COMMENT '菜单标识',
  `MODULE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '模块标识',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '菜单模块关联表';

CREATE TABLE `sys_module`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `PARENT_CODE` varchar(128) NULL DEFAULT NULL COMMENT '所属模块标识',
  `PARENT_CODES` varchar(2048) NULL DEFAULT NULL COMMENT '所属模块标识关系',
  `CODE` varchar(128) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `VALIDATE` varchar(32) NOT NULL COMMENT '是否验证',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `URL` varchar(1024) NULL DEFAULT NULL COMMENT 'URL',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '模块表';

CREATE TABLE `sys_org`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `PARENT_CODE` varchar(128) NULL DEFAULT NULL COMMENT '父标识',
  `PARENT_CODES` varchar(2048) NULL DEFAULT NULL COMMENT '所属模块标识关系',
  `CODE` varchar(128) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '构机表';

CREATE TABLE `sys_post`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `CODE` varchar(32) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `CODE`(`CODE`)
) COMMENT = '岗位表';

CREATE TABLE `sys_role`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `CODE` varchar(32) NOT NULL COMMENT '标识',
  `NAME` varchar(512) NOT NULL COMMENT '名称',
  `USEABLE` varchar(32) NOT NULL COMMENT '是否可用',
  `ORDERS` int(11) NULL DEFAULT NULL COMMENT '排序值',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) ,
  UNIQUE INDEX `sys_role_code`(`CODE`)
) COMMENT = '角色表';

CREATE TABLE `sys_role_module`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `ROLE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '角色标识',
  `MODULE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '模块标识',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '角色模块关联表';

CREATE TABLE `sys_user`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `USER_SOURCE` varchar(512) NOT NULL COMMENT '用户来源',
  `SOURCE_ID` varchar(512) NOT NULL COMMENT '用户来源ID',
  `USER_ID` varchar(512) NOT NULL COMMENT '用户ID',
  `USER_NO` varchar(512) NULL DEFAULT NULL COMMENT '用户编号',
  `USERNAME` varchar(512) NOT NULL COMMENT '用户名',
  `EMAIL` varchar(512) NULL DEFAULT NULL COMMENT '电子邮件',
  `PHONE_NO` varchar(512) NULL DEFAULT NULL COMMENT '手机号',
  `REALNAME` varchar(512) NULL DEFAULT NULL COMMENT '真实名称',
  `PASSWORD` varchar(512) NOT NULL COMMENT '密码',
  `TYPE_CODE` varchar(32) NULL DEFAULT NULL COMMENT '类别标识',
  `USER_STATUS` varchar(32) NOT NULL COMMENT '用户状态',
  `STATUS` varchar(32) NULL DEFAULT 'NORMAL' COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`) ,
  INDEX `USER_ID`(`USER_ID`) ,
  INDEX `USER_NO`(`USER_NO`) ,
  INDEX `CELLPHONE`(`PHONE_NO`) ,
  INDEX `EMAIL`(`EMAIL`)
) COMMENT = '用户';

CREATE TABLE `sys_user_org`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `ROLE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '角色标识',
  `USERNAME` varchar(128) NULL DEFAULT NULL COMMENT '用户名',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '用户机构关联';


CREATE TABLE `sys_user_post`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `ROLE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '角色标识',
  `USERNAME` varchar(128) NULL DEFAULT NULL COMMENT '用户名',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '用户岗位关联';

CREATE TABLE `sys_user_role`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `OPTIMISTIC` bigint(20) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `ROLE_CODE` varchar(128) NULL DEFAULT NULL COMMENT '角色标识',
  `USERNAME` varchar(128) NULL DEFAULT NULL COMMENT '用户名',
  `STATUS` varchar(32) NULL DEFAULT NULL COMMENT '数据状态',
  `DESCRIPTION` varchar(2048) NULL DEFAULT NULL COMMENT '描述',
  `CREATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_USER` varchar(32) NULL DEFAULT NULL COMMENT '修改人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) COMMENT = '用户角色关联';


-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES (1, 0, NULL, 'TEST_M_1', 'TEST_F_111', '测试1', 'Y', 'Y', 'GET', '/test/f111', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (2, 0, NULL, 'TEST_M_1112', 'TEST_F_112', '测试1', 'Y', 'Y', 'GET', '/test/f112', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (3, 0, NULL, 'TEST_M_1111', 'TEST_F_1111', '测试1', 'Y', 'Y', 'GET', '/test/f1111', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (4, 0, NULL, 'TEST_M_1111', 'TEST_F_1112', '测试1', 'Y', 'Y', 'GET', '/test/f1112', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (5, 0, NULL, 'TEST_M_1111', 'TEST_F_1113', '测试1', 'Y', 'Y', 'GET', '/test/f1113', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (6, 0, NULL, 'PUBLIC_INDEX', 'INDEX', '首页信息', 'N', 'Y', 'GET', '/user/sys/index', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (7, 0, NULL, 'PUBLIC_INDEX', 'MEUN', '菜单查询', 'N', 'Y', 'GET', '/user/sys/menu', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_function` VALUES (11, 0, NULL, 'PUBLIC_INDEX', 'USER', '用户个人信息', 'N', 'Y', 'GET', '/user/sys/user', NULL, 'NORMAL', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (2, 0, 'SYS_MENU', 'ROOT', NULL, 'AUTH', '权限管理', 'Y', NULL, '&#xe6b8;', 1, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, 0, 'SYS_MENU', 'AUTH', NULL, 'AUTH_USER', '用户管理', 'Y', 'page/adminUser/list.html', NULL, 11, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (6, 0, 'SYS_MENU', 'AUTH', NULL, 'AUTH_ROLE', '角色管理', 'Y', 'page/adminRole/list.html', NULL, 12, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (7, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_13', '测试111', 'Y', '/test/m112', NULL, 111, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (8, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_14', '测试112', 'Y', '/test/m112', NULL, 112, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (9, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_15', '测试1111', 'Y', '/test/m112', NULL, 1111, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_16', '测试1112', 'Y', '/test/m112', NULL, 1112, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 0, 'SYS_MENU', 'ROOT', NULL, 'TEST_M_2', '测试2', 'Y', NULL, NULL, 2, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 0, 'SYS_MENU', 'TEST_M_2', NULL, 'TEST_M_21', '测试21', 'Y', NULL, '&#xe66b;', 21, 'NORMAL', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_menu_module
-- ----------------------------
INSERT INTO `sys_menu_module` VALUES (1, 0, 'AUTH', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (2, 0, 'AUTH_USER', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (3, 0, 'AUTH_ROLE', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (4, 0, 'TEST_M_13', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (5, 0, 'TEST_M_14', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (6, 0, 'TEST_M_15', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (7, 0, 'TEST_M_16', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (8, 0, 'TEST_M_2', 'TEST_M_2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_menu_module` VALUES (9, 0, 'TEST_M_21', 'TEST_M_2', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES (2, 0, 'SYS_MENU', 'ROOT', NULL, 'TEST_M_1', '测试1', 'Y', 'Y', '/test/m1', 1, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (5, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_11', '测试11', 'Y', 'Y', '/test/m11', 11, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (6, 0, 'SYS_MENU', 'TEST_M_1', NULL, 'TEST_M_12', '测试12', 'Y', 'Y', '/test/m12', 12, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (7, 0, 'SYS_MODULE', 'TEST_M_11', NULL, 'TEST_M_111', '测试111', 'Y', 'Y', NULL, 111, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (8, 0, 'SYS_MODULE', 'TEST_M_11', NULL, 'TEST_M_112', '测试112', 'Y', 'Y', NULL, 112, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (9, 0, 'SYS_MODULE', 'TEST_M_111', NULL, 'TEST_M_1111', '测试1111', 'Y', 'Y', NULL, 1111, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (10, 0, 'SYS_MODULE', 'TEST_M_111', NULL, 'TEST_M_1112', '测试1112', 'Y', 'Y', NULL, 1112, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (11, 0, 'SYS_MENU', 'ROOT', NULL, 'TEST_M_2', '测试2', 'Y', 'Y', '/test/m2', 2, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (12, 0, 'SYS_MENU', 'TEST_M_2', NULL, 'TEST_M_21', '测试21', 'Y', 'Y', '/test/m21', 21, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (13, 0, 'SYS_MODULE', 'ROOT', NULL, 'PUBLIC', '公共模块', 'N', 'Y', NULL, 1, 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_module` VALUES (14, 0, 'SYS_MODULE', 'PUBLIC', NULL, 'PUBLIC_INDEX', '主页', 'N', 'Y', NULL, 11, 'NORMAL', NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_post
-- ----------------------------
INSERT INTO `sys_post` VALUES (1, 15, NULL, 'SUPER_ADMIN', '超级管理员', '', 102, 'DISABLE', 'yututy', 'admin', '2018-06-28 09:59:59', 'admin', '2018-07-03 10:13:30');
INSERT INTO `sys_post` VALUES (2, 2, NULL, 'TEST1', '测试1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (3, 0, NULL, 'TEST2', '测试2', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (6, 0, NULL, 'TEST3', '测试3', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (7, 0, NULL, '2', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (8, 0, NULL, '3', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (9, 0, NULL, '4', '11', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (10, 0, NULL, '5', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (11, 0, NULL, '6', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (12, 0, NULL, '7', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (13, 0, NULL, '8', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (14, 0, NULL, '9', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (15, 0, NULL, '10', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (16, 0, NULL, '11', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_post` VALUES (17, 0, NULL, '14', '1', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 15, NULL, 'SUPER_ADMIN', '超级管理员', 'Y', 102, 'NORMAL', 'yututy', 'admin', '2018-06-28 09:59:59', 'admin', '2018-07-03 10:13:30');
INSERT INTO `sys_role` VALUES (2, 2, NULL, 'TEST1', '测试1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (3, 1, NULL, 'TEST2', '测试2', 'Y', NULL, 'NORMAL', '11111', NULL, NULL, 'test3', '2019-02-20 13:34:55');
INSERT INTO `sys_role` VALUES (6, 1, NULL, 'TEST3', '测试3', 'Y', NULL, 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-26 16:12:18');
INSERT INTO `sys_role` VALUES (7, 0, NULL, '2', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (8, 0, NULL, '3', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (9, 2, NULL, '4', '11', 'N', NULL, 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-26 16:01:44');
INSERT INTO `sys_role` VALUES (10, 0, NULL, '5', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (11, 0, NULL, '6', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (12, 0, NULL, '7', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (13, 0, NULL, '8', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (14, 0, NULL, '9', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (15, 0, NULL, '10', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (16, 0, NULL, '11', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (17, 0, NULL, '14', '1', 'Y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role` VALUES (18, 0, NULL, '2222222', '2', 'Y', NULL, 'NORMAL', NULL, 'test3', '2019-02-22 12:05:21', NULL, NULL);
INSERT INTO `sys_role` VALUES (19, 0, NULL, '22222', '22', 'Y', NULL, 'NORMAL', NULL, 'test3', '2019-02-22 12:05:30', NULL, NULL);

-- ----------------------------
-- Records of sys_role_module
-- ----------------------------
INSERT INTO `sys_role_module` VALUES (1, 0, 'TEST2', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (2, 0, 'TEST2', 'TEST_M_12', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (3, 0, 'TEST2', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (4, 0, 'TEST2', 'TEST_M_11', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (5, 0, 'TEST2', 'TEST_M_111', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (6, 0, 'TEST2', 'TEST_M_1111', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (7, 0, 'TEST2', 'TEST_M_1112', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (8, 0, 'TEST3', 'TEST_M_1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (9, 0, 'TEST3', 'TEST_M_11', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (10, 0, 'TEST3', 'TEST_M_112', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (12, 0, 'TEST3', 'TEST_M_12', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (13, 0, 'TEST3', 'TEST_M_2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_role_module` VALUES (14, 0, 'TEST3', 'TEST_M_21', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 5, '', '', '000', '000', 'admin', 'duanchangqing90@163.com', '15000000000', '管理员', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-20 13:46:23');
INSERT INTO `sys_user` VALUES (2, 6, '', '', '001', '001', 'test1', NULL, NULL, '测试1', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'DELETED', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:50:12');
INSERT INTO `sys_user` VALUES (3, 3, '', '', '002', '002', 'test2', NULL, NULL, '测试2', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:50:17');
INSERT INTO `sys_user` VALUES (4, 9, '', '', '003', '003', 'test3', 'test3@test3.com', '13051060000', '测试3', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-26 16:28:05');
INSERT INTO `sys_user` VALUES (5, 4, '', '', '004', '004', 'test4', 'test4@test.com', '13500000000', '测试4', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'DELETED', 'DISABLED', NULL, NULL, NULL, 'test3', '2019-02-26 17:05:15');
INSERT INTO `sys_user` VALUES (6, 1, '', '', '005', '005', 'test5', NULL, NULL, '测试5', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:50:28');
INSERT INTO `sys_user` VALUES (7, 4, '', '', '006', '006', 'test6', NULL, NULL, '测试6', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'DELETED', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:49:59');
INSERT INTO `sys_user` VALUES (8, 2, '', '', '007', '007', 'test7', NULL, NULL, '测试7', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:36:07');
INSERT INTO `sys_user` VALUES (9, 1, '', '', '008', '008', 'test8', NULL, NULL, '测试8', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'DELETED', 'NORMAL', NULL, NULL, NULL, 'test3', '2019-02-19 16:50:03');
INSERT INTO `sys_user` VALUES (10, 0, '', '', '009', '009', 'test9', NULL, NULL, '测试9', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (11, 0, '', '', '010', '010', 'test10', NULL, NULL, '测试10', '$2a$10$EkzuXleOmdkKtopg0vIMGuEq14rF5BaN3PnjEfmVlXPHNaD8HuqlO', NULL, 'NORMAL', 'NORMAL', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user` VALUES (12, 0, '', '', 'dca15eb9-18c8-4131-99e4-421af1bfbb4b', '100', 'test100', NULL, NULL, '测试100', '123456', NULL, 'NORMAL', 'NORMAL', NULL, NULL, '2019-02-12 18:11:30', NULL, NULL);
INSERT INTO `sys_user` VALUES (13, 0, '', '', '6851cb29-6576-46b7-90e2-5a49353fd7c6', '116', '13581965977', '1@2.com', '11000000000', '1', '123456', NULL, 'NORMAL', 'NORMAL', NULL, 'test3', '2019-02-22 16:04:25', NULL, NULL);
INSERT INTO `sys_user` VALUES (14, 0, '', '', '5864fbb7-2ef9-4f3e-a6cc-509c333b6d16', '1', '1', NULL, '13050000000', '1', '123456', NULL, 'NORMAL', 'NORMAL', NULL, 'test3', '2019-02-26 15:33:45', NULL, NULL);
INSERT INTO `sys_user` VALUES (15, 0, '', '', 'd60f13ea-8c27-4989-aca4-ee9363a250f3', '1', '1', '1@1.com', '13051111111', '1', '123456', NULL, 'NORMAL', 'NORMAL', NULL, 'test3', '2019-02-26 15:52:08', NULL, NULL);
INSERT INTO `sys_user` VALUES (16, 0, '', '', 'ab04e077-5180-4ef7-b9cf-87b3d68eefaa', '1', '11', '1@1.com1', '13051000000', '1', '123456', NULL, 'NORMAL', 'NORMAL', NULL, 'test3', '2019-02-26 15:53:51', NULL, NULL);
INSERT INTO `sys_user` VALUES (17, 0, 'github', 'smile90', '43ab0ff4-29ad-4c15-b732-a52e3f7c0cfb', NULL, 'smile90', NULL, NULL, NULL, '123456', NULL, 'NORMAL', 'NORMAL', NULL, NULL, '2019-07-15 18:25:01', NULL, NULL);

-- ----------------------------
-- Records of sys_user_org
-- ----------------------------
INSERT INTO `sys_user_org` VALUES (1, 0, 'SUPER_ADMIN', 'admin', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (2, 0, 'TEST1', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (3, 0, 'TEST2', 'test2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (4, 0, 'TEST3', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (5, 0, 'TEST2', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (6, 0, 'TEST3', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (7, 0, 'PUBLIC', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (8, 0, 'PUBLIC', 'test2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (9, 0, 'PUBLIC', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_org` VALUES (10, 0, 'PUBLIC', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
INSERT INTO `sys_user_post` VALUES (1, 0, 'SUPER_ADMIN', 'admin', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (2, 0, 'TEST1', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (3, 0, 'TEST2', 'test2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (4, 0, 'TEST3', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (5, 0, 'TEST2', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (6, 0, 'TEST3', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (7, 0, 'PUBLIC', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (8, 0, 'PUBLIC', 'test2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (9, 0, 'PUBLIC', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_post` VALUES (10, 0, 'PUBLIC', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 0, 'SUPER_ADMIN', 'admin', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (2, 0, 'TEST1', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (3, 0, 'TEST2', 'test2', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (4, 0, 'TEST3', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (5, 0, 'TEST2', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (6, 0, 'TEST3', 'test4', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (7, 0, 'PUBLIC', 'test1', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `sys_user_role` VALUES (9, 0, 'SUPER_ADMIN', 'test3', NULL, NULL, NULL, NULL, NULL, NULL);