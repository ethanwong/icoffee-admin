/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50641
 Source Host           : localhost:3306
 Source Schema         : icoffee_admin

 Target Server Type    : MySQL
 Target Server Version : 50641
 File Encoding         : 65001

 Date: 10/03/2021 16:02:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for system_authority
-- ----------------------------
DROP TABLE IF EXISTS `system_authority`;
CREATE TABLE `system_authority`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `module` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_authority
-- ----------------------------
INSERT INTO `system_authority` VALUES ('19c49c7283bc4891b8205949cb4ea543', '用户分页', '/api/system/user/page', 'GET', 'user:page', 'user', 'com.icoffee.system.web.UserController#page(HttpServletRequest, int, int)', 1612767675626, 1615362166681);
INSERT INTO `system_authority` VALUES ('1e433f1890304d8abb604894c2acb417', '获取角色列表', '/api/system/role/getList', 'GET', 'role:getList', 'role', 'com.icoffee.system.web.RoleController#getList(HttpServletRequest)', 1612767675626, 1615362166803);
INSERT INTO `system_authority` VALUES ('1fcdc5fd231241888de266fd631818ea', '根据ID获取菜单', '/api/system/menu/getById', 'GET', 'menu:getById', 'menu', 'com.icoffee.system.web.MenuController#getById(String)', 1612767675625, 1615362166388);
INSERT INTO `system_authority` VALUES ('24ad74c48c2242909158b1ba2e18cb95', '根据ID获取授权', '/api/system/authority/getById', 'GET', 'authority:getById', 'authority', 'com.icoffee.system.web.AuthorityController#getById(String)', 1612767675625, 1615362166366);
INSERT INTO `system_authority` VALUES ('2a83ce7aecc24fc591db832952b3d781', '获取菜单树', '/api/system/menu/getTree', 'GET', 'menu:getTree', 'menu', 'com.icoffee.system.web.MenuController#getTree(String)', 1612767675626, 1615362166547);
INSERT INTO `system_authority` VALUES ('2e469de24142427e97960d94217c9d7b', '删除菜单', '/api/system/menu', 'DELETE', 'menu:delete', 'menu', 'com.icoffee.system.web.MenuController#delete(HttpServletRequest, String)', 1612767675625, 1615362166743);
INSERT INTO `system_authority` VALUES ('43e5177c78ad43db80c00197a107814c', '更新菜单', '/api/system/menu', 'PUT', 'menu:update', 'menu', 'com.icoffee.system.web.MenuController#update(Menu)', 1612767675625, 1615362166299);
INSERT INTO `system_authority` VALUES ('4a6e281ed57d4ab4a263da96fefc84e4', '获取角色分页列表', '/api/system/role/page', 'GET', 'role:page', 'role', 'com.icoffee.system.web.RoleController#page(HttpServletRequest, int, int)', 1612767675626, 1615362166783);
INSERT INTO `system_authority` VALUES ('4e6c35d41d7440fa951851db0f79620f', '获取授权分页列表', '/api/system/authority/page', 'GET', 'authority:page', 'authority', 'com.icoffee.system.web.AuthorityController#page(HttpServletRequest, int, int)', 1612767675625, 1615362166458);
INSERT INTO `system_authority` VALUES ('57b31e8fc1904ec99e6bf6ae59c36bb4', '修改用户', '/api/system/user', 'PUT', 'user:update', 'user', 'com.icoffee.system.web.UserController#update(HttpServletRequest, User)', 1612767675626, 1615362166763);
INSERT INTO `system_authority` VALUES ('5d94909caf3d4c7899fc34f1490d0575', '根据用户名获取用户', '/api/system/user/getByUserName', 'GET', 'user:getByUserName', 'user', 'com.icoffee.system.web.UserController#getByUserName(HttpServletRequest, String)', 1612767675625, 1615362166504);
INSERT INTO `system_authority` VALUES ('6ef6ca3fcf7040959c4ab79441827245', '新增角色', '/api/system/role', 'POST', 'role:create', 'role', 'com.icoffee.system.web.RoleController#create(HttpServletRequest, Role)', 1612767675625, 1615362166276);
INSERT INTO `system_authority` VALUES ('a1dd1958a5864f4c89de4c65ab428d0c', '菜单分页', '/api/system/menu/page', 'GET', 'menu:page', 'menu', 'com.icoffee.system.web.MenuController#page(HttpServletRequest, int, int)', 1612767675625, 1615362166525);
INSERT INTO `system_authority` VALUES ('b3d26ceff152432e9cd37d05caaa6a99', '菜单获取列表', '/api/system/menu/list', 'GET', 'menu:getMenuList', 'menu', 'com.icoffee.system.web.MenuController#getMenuList(HttpServletRequest)', 1612767675626, 1615362166605);
INSERT INTO `system_authority` VALUES ('b923258748e54750b2fb31a17859cb23', '获取角色授权信息', '/api/system/role/auth', 'GET', 'role:auth', 'role', 'com.icoffee.system.web.RoleController#auth(Model, String)', 1612767675626, 1615362166251);
INSERT INTO `system_authority` VALUES ('c7a2499f9c9c4a9dac114cb889824803', '修改密码', '/api/system/user/resetPassword', 'PUT', 'user:resetPassword', 'user', 'com.icoffee.system.web.UserController#resetPassword(String, String)', 1614307846641, 1615362166481);
INSERT INTO `system_authority` VALUES ('cca4fbc2304a4a09b8593b888d379041', '根据模块获取授权', '/api/system/authority/getByModule', 'GET', 'authority:getByModule', 'authority', 'com.icoffee.system.web.AuthorityController#getByModule(String)', 1614146343440, 1615362166435);
INSERT INTO `system_authority` VALUES ('ce0321910acf4141bbde4946d65ac6fc', '删除用户', '/api/system/user', 'DELETE', 'user:delete', 'user', 'com.icoffee.system.web.UserController#delete(HttpServletRequest, String)', 1612767675624, 1615362166320);
INSERT INTO `system_authority` VALUES ('d4a348061d27491da359a55fbd002c3d', '添加菜单', '/api/system/menu', 'POST', 'menu:create', 'menu', 'com.icoffee.system.web.MenuController#create(Menu)', 1612767675626, 1615362166701);
INSERT INTO `system_authority` VALUES ('e094036e052c4e709fc71f8b8dda3c2e', '新增用户', '/api/system/user', 'POST', 'user:create', 'user', 'com.icoffee.system.web.UserController#create(HttpServletRequest, User)', 1612767675625, 1615362166722);
INSERT INTO `system_authority` VALUES ('e4f993099e164a0c85f16b69683f5f0d', '修改角色', '/api/system/role', 'PUT', 'role:update', 'role', 'com.icoffee.system.web.RoleController#update(HttpServletRequest, Role)', 1612767675626, 1615362166640);
INSERT INTO `system_authority` VALUES ('e53f1e2bf17147f3b94bdc03c50895c5', '设置角色授权信息', '/api/system/role/setRoleAuth', 'PUT', 'role:setRoleAuth', 'role', 'com.icoffee.system.web.RoleController#setRoleAuth(RoleMenuAuthDto)', 1612767675626, 1615362166661);
INSERT INTO `system_authority` VALUES ('ed6a4c1df66b49349546876105c5260c', '删除角色', '/api/system/role', 'DELETE', 'role:delete', 'role', 'com.icoffee.system.web.RoleController#delete(HttpServletRequest, String)', 1612767675626, 1615362166343);
INSERT INTO `system_authority` VALUES ('f8e78aae1f6f4538912290e4c76bfb16', '根据ID查询角色', '/api/system/role/getById', 'GET', 'role:getById', 'role', 'com.icoffee.system.web.RoleController#getById(HttpServletRequest, String)', 1612767675625, 1615362166412);
INSERT INTO `system_authority` VALUES ('fae75122f9784a2292ec26f09bfa8db7', '根据ID获取用户', '/api/system/user/getById', 'GET', 'user:getById', 'user', 'com.icoffee.system.web.UserController#getById(HttpServletRequest, String)', 1612767675626, 1615362166568);

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `module_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hidden` bit(1) NULL DEFAULT NULL,
  `order_no` int(11) NULL DEFAULT NULL,
  `parent_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `route_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  `redirect` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('0644600df7744b6ea0ad091c4a35afee', 'GIS地图', 'clipboard', 'map', b'0', 1, '776c25528390480d9f0b7bedd0f3c960', 'gis/map', 'map', 1612332382323, 1612432482249, b'0');
INSERT INTO `system_menu` VALUES ('0e49797901c244eca38abe1531ae67de', '开发指南', 'documentation', 'doc', b'0', 1, '0', 'Layout', '/doc', 1614735661526, 1614742959238, b'1');
INSERT INTO `system_menu` VALUES ('2b323fba108a45799413876e68481bf4', '数据分析', 'bug', 'data', b'0', 1, '82a572cc30334dfa976019ec72f9f80d', 'report/data', 'data', 1612430713657, 1612433008906, b'0');
INSERT INTO `system_menu` VALUES ('3290e8fe75494f53a791f15121cfa67b', '小度地图', 'international', 'xiaodu', b'0', 1, 'a53a207b352d4e6c81ffa9f72ee4cb26', 'gis/baidu/xiaodu', 'xiaodu', 1612434599765, NULL, b'0');
INSERT INTO `system_menu` VALUES ('339bc44881854368a1b2ec14c943c43f', '菜单管理', 'tree-table', 'menu', b'0', 3, '5500273007004ae7ba24dc0b101c4ee5', 'system/menu', 'menu', 1612346970723, 1612348485880, b'0');
INSERT INTO `system_menu` VALUES ('4b45fd3283944a34b506f789079999a6', '授权管理', 'tree', 'authority', b'0', 10, '5500273007004ae7ba24dc0b101c4ee5', 'system/authority', 'authority', 1612493606328, 1612513547657, b'0');
INSERT INTO `system_menu` VALUES ('5500273007004ae7ba24dc0b101c4ee5', '系统管理', 'dashboard', 'system', b'0', 1, '0', 'Layout', '/system', 1612334840015, 1614743170038, b'0');
INSERT INTO `system_menu` VALUES ('5fe5f1eb67ae4370a4e0252e79f66fb5', '系统配置', 'set', 'setting', b'0', 20, '5500273007004ae7ba24dc0b101c4ee5', 'system/setting', 'setting', 1614665820880, 1614666621584, b'0');
INSERT INTO `system_menu` VALUES ('6e724dd981bf4c749b0d093bb896ed6b', 'os', '404', 'os', b'0', 1, '9f2da778019f46f7b19c91ab6256777a', 'monitor/os', 'os', 1612435702726, 1612435764708, b'0');
INSERT INTO `system_menu` VALUES ('6f02dc1d26be47e38122dabd3337ec7a', '用户管理', 'user', 'user', b'0', 1, '5500273007004ae7ba24dc0b101c4ee5', 'system/user', '/user', 1612340427278, 1612493975747, b'0');
INSERT INTO `system_menu` VALUES ('776c25528390480d9f0b7bedd0f3c960', '地理信息', 'drag', 'gis', b'0', 2, '0', 'Layout', '/gis', 1612334132296, 1612430814943, b'0');
INSERT INTO `system_menu` VALUES ('7c542f13ed11486981a31b99e43c3bc4', '小德地图', 'bug', 'xiaode', b'0', 1, 'fd4eaeb2a35c4a24bfd4d7c181e42759', 'gis/gaode/xiaode', 'xiaode', 1612433469154, 1612434475301, b'0');
INSERT INTO `system_menu` VALUES ('82a572cc30334dfa976019ec72f9f80d', '报表统计', 'drag', 'report', b'1', 5, '0', 'Layout', '67', 1612334849214, 1612494387393, b'0');
INSERT INTO `system_menu` VALUES ('9f2da778019f46f7b19c91ab6256777a', '系统监控', 'component', 'monitor', b'0', 1, '0', 'Layout', 'monitor', 1612334538047, 1612435723455, b'0');
INSERT INTO `system_menu` VALUES ('a53a207b352d4e6c81ffa9f72ee4cb26', '百度地图', 'component', 'baidu', b'0', 1, '776c25528390480d9f0b7bedd0f3c960', 'gis/baidu', 'baidu', 1612433410163, NULL, b'0');
INSERT INTO `system_menu` VALUES ('b589e0d292e34688bba62870a8ae659c', '角色管理', 'peoples', 'role', b'0', 2, '5500273007004ae7ba24dc0b101c4ee5', 'system/role', 'role', 1612346919835, 1612348491969, b'0');
INSERT INTO `system_menu` VALUES ('c266f30587a04861bd7d32ef2c81cd65', '分类管理', 'chart', 'category', b'0', 1, 'e35f3fb48f4c4da985465ce42c94be9f', 'cms/category', 'category', 1612434846955, NULL, b'0');
INSERT INTO `system_menu` VALUES ('c914b01c485443c6a3249c0b8c4dafda', '1111111', 'drag', '11', b'0', 1, 'c914b01c485443c6a3249c0b8c4dafda', '11', '11', 1612334698060, 1612340668713, b'0');
INSERT INTO `system_menu` VALUES ('d33c1d6e97c84c719f63c4eba0784187', '操作日志', 'drag', 'log', b'1', 1, '0', 'Layout', 'log', 1612334083237, 1612494374039, b'0');
INSERT INTO `system_menu` VALUES ('e11f66f48b6b47c5b9dbcd78602db4d7', '小小地图', 'bug', 'xiaoxiao', b'0', 1, '0644600df7744b6ea0ad091c4a35afee', 'gis/map/xiaoxiao', 'xiaoxiao', 1612434755974, NULL, b'0');
INSERT INTO `system_menu` VALUES ('e35f3fb48f4c4da985465ce42c94be9f', '内容管理', 'drag', 'cms', b'0', 1, '0', 'Layout', 'cms', 1612334122136, 1612434808960, b'0');
INSERT INTO `system_menu` VALUES ('fd4eaeb2a35c4a24bfd4d7c181e42759', '高德地图', 'clipboard', 'gaode', b'0', 1, '776c25528390480d9f0b7bedd0f3c960', 'gis/gaode', 'gaode', 1612433279476, NULL, b'0');
INSERT INTO `system_menu` VALUES ('fd6f5ba38cdd4a19a11bad24d332ae89', 'mem', 'clipboard', 'men', b'0', 1, '9f2da778019f46f7b19c91ab6256777a', 'monitor/men', 'men', 1614666522585, NULL, b'0');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `auth_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `menu_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('38e2aadb712e4a7499feb58c649a666b', 1612771417635, 1614247690328, '系统管理员', '系统管理员', '', '5500273007004ae7ba24dc0b101c4ee5');
INSERT INTO `system_role` VALUES ('7934c883db4b43698e10d38bae2421d3', 1612771628048, 1614247772457, '普通用户', '普通用户', '', '4b45fd3283944a34b506f789079999a6');
INSERT INTO `system_role` VALUES ('e8cdecb5edb04dc98a9624959c8c3ec8', 1614911321772, 1615356886919, '1', '1', NULL, NULL);
INSERT INTO `system_role` VALUES ('f39d671ecd81478bbfc4a9f884115cad', 1614236554555, 1614735683718, '超级管理员', '超级管理员', '', '0e49797901c244eca38abe1531ae67de,5500273007004ae7ba24dc0b101c4ee5,9f2da778019f46f7b19c91ab6256777a,d33c1d6e97c84c719f63c4eba0784187,e35f3fb48f4c4da985465ce42c94be9f,776c25528390480d9f0b7bedd0f3c960,82a572cc30334dfa976019ec72f9f80d');

-- ----------------------------
-- Table structure for system_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `system_role_authority`;
CREATE TABLE `system_role_authority`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  `authority_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_role_authority
-- ----------------------------
INSERT INTO `system_role_authority` VALUES ('02a156f440a649bf9a18754a9a5d9b8a', 1614735683907, NULL, '57b31e8fc1904ec99e6bf6ae59c36bb4', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('072e86f87de8497391b42090ffa7f248', 1614222179825, NULL, 'cca4fbc2304a4a09b8593b888d379041', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('0af26c4bbfab4ef1b3c77b6516480f73', 1614247690381, NULL, 'cca4fbc2304a4a09b8593b888d379041', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('0d697b50af3d457f9b650642d6239306', 1614222115214, NULL, 'e53f1e2bf17147f3b94bdc03c50895c5', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('11c07065d58f486a84c5e35310225a7f', 1614222115214, NULL, '2e469de24142427e97960d94217c9d7b', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('13693344301d4888998e04670a79b314', 1614222179825, NULL, '24ad74c48c2242909158b1ba2e18cb95', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('1592ae9312d447a0a698e949f53255c8', 1614247690381, NULL, 'ce0321910acf4141bbde4946d65ac6fc', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('18eb01fb52a84661a114431118a6a99b', 1614735683907, NULL, 'ed6a4c1df66b49349546876105c5260c', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('1bfc15d2f3924847ad6e90c8d5347f7b', 1614735683907, NULL, 'f8e78aae1f6f4538912290e4c76bfb16', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('2649372b4c5e4a51b10a17febc70eb1f', 1614222179825, NULL, 'e53f1e2bf17147f3b94bdc03c50895c5', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('264e48c6a6e04153bce0925e2aafaff0', 1614222179825, NULL, '1e433f1890304d8abb604894c2acb417', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('3045d531db32472cafb43fd1ef5eff26', 1614222115214, NULL, 'b3d26ceff152432e9cd37d05caaa6a99', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('366db0ad738a4b20b2d5e909f1bd95e1', 1614735683907, NULL, '2e469de24142427e97960d94217c9d7b', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('3b1c6a2bd149491eb043e62d0cbca0d4', 1614222179825, NULL, 'e4f993099e164a0c85f16b69683f5f0d', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('3c1a3b38df264a52ae8d63f666172324', 1614247690381, NULL, '4e6c35d41d7440fa951851db0f79620f', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('3dd270b5bd3346e984e7f3e328b8b0c7', 1614222115214, NULL, '4e6c35d41d7440fa951851db0f79620f', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('3e854ab6306145ab90b0d684c8b3ce78', 1614222115214, NULL, 'e4f993099e164a0c85f16b69683f5f0d', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('3f1e2480529c448a99e6c4185a4955e5', 1614222115214, NULL, 'a1dd1958a5864f4c89de4c65ab428d0c', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('3fe826abe6bc462f9157bd20a8c681fa', 1614222115214, NULL, 'd4a348061d27491da359a55fbd002c3d', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('408448e89d404a3f88781582c79dae59', 1614222115213, NULL, '19c49c7283bc4891b8205949cb4ea543', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('42a15bf5e6524c418e59d6340dd9b998', 1614735683907, NULL, '6ef6ca3fcf7040959c4ab79441827245', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('43bcf9d56957482393043603f4e02896', 1614247690381, NULL, '1fcdc5fd231241888de266fd631818ea', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('4472054cb1f34a549dcc5b365eca61da', 1614222115214, NULL, '1e433f1890304d8abb604894c2acb417', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('4ced71059b1046629d5068a872b3ad3c', 1614222115214, NULL, 'ce0321910acf4141bbde4946d65ac6fc', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('5046900a1e214a5394b3b97abaa8abab', 1614222179825, NULL, 'b923258748e54750b2fb31a17859cb23', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('519883d506a143cda45327e6fd2a5a81', 1614222179825, NULL, '6ef6ca3fcf7040959c4ab79441827245', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('547935dd00db4f2fa6608e1f4e5a17ca', 1614222115214, NULL, 'f8e78aae1f6f4538912290e4c76bfb16', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('578638ab176e4e98b5d2b5724b31db21', 1614222115214, NULL, 'fae75122f9784a2292ec26f09bfa8db7', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('588cb8813fca479a81c5d0de7b9b6078', 1614247699794, NULL, '4e6c35d41d7440fa951851db0f79620f', '7934c883db4b43698e10d38bae2421d3');
INSERT INTO `system_role_authority` VALUES ('58edd312c5a54f699b86dee467001762', 1614222115214, NULL, '6ef6ca3fcf7040959c4ab79441827245', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('59fd4262ef2a4e2c91d18ea5172217a1', 1614735683907, NULL, 'fae75122f9784a2292ec26f09bfa8db7', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('5fb07e7b5c8e426e84961b28cdd7a22e', 1614735683907, NULL, '19c49c7283bc4891b8205949cb4ea543', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('5fb5101eee2b4ec49bd2994a12201a85', 1614247690381, NULL, '5d94909caf3d4c7899fc34f1490d0575', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('6212adbe84ef4ef3a3baec4def8941aa', 1614735683907, NULL, 'e094036e052c4e709fc71f8b8dda3c2e', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('6404758608be4cf6b319000636feab07', 1614247690381, NULL, '2e469de24142427e97960d94217c9d7b', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('6597309df10f4622b1019ce2a0a10910', 1614735683907, NULL, 'e4f993099e164a0c85f16b69683f5f0d', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('6f0e0d88e75f4b39ae9a88965c5da6d1', 1614735683907, NULL, '2a83ce7aecc24fc591db832952b3d781', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('6f72ed5b24b04be187ae9e269225e471', 1614222115214, NULL, '4a6e281ed57d4ab4a263da96fefc84e4', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('7333a33769ee4040a275102842dc058a', 1614247690381, NULL, '4a6e281ed57d4ab4a263da96fefc84e4', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('7771643654414c3d938e0e9cb531415b', 1614247690381, NULL, 'ed6a4c1df66b49349546876105c5260c', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('7a7e178dbbcf403c82a88f8f15649d4a', 1614735683907, NULL, 'ce0321910acf4141bbde4946d65ac6fc', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('7dd9b3ea53f647d7ba8e4740e014fec0', 1614222115214, NULL, '2a83ce7aecc24fc591db832952b3d781', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('7ee3869b16e449029adf6a9d962802ea', 1614247690381, NULL, '24ad74c48c2242909158b1ba2e18cb95', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('85b3a02fa9754bba8631f065e6a8ca2c', 1614735683907, NULL, 'b923258748e54750b2fb31a17859cb23', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('8abb127a464f477e9eaea7b693cae459', 1614222115214, NULL, 'ed6a4c1df66b49349546876105c5260c', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('97046c4d374346a29a2faa28e5dd32ab', 1614247690381, NULL, 'e53f1e2bf17147f3b94bdc03c50895c5', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('9c160e61e8a34a299e98d6085ee71c96', 1614735683907, NULL, '43e5177c78ad43db80c00197a107814c', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('9cd66601c96842bcb1a3f4810b9c94be', 1614247690381, NULL, 'd4a348061d27491da359a55fbd002c3d', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('a606743b997c4991be5f73d480ad3f68', 1614222179825, NULL, 'f8e78aae1f6f4538912290e4c76bfb16', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('aa63751a5f3e403a960748b7571bcadf', 1614735683907, NULL, 'd4a348061d27491da359a55fbd002c3d', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('ac5ef373234e43a59bb6b1e8a741785e', 1614222179825, NULL, 'ed6a4c1df66b49349546876105c5260c', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('ae6c79cd996c4930b434bffa192cf311', 1614735683907, NULL, 'a1dd1958a5864f4c89de4c65ab428d0c', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('b07ac200dc5747fa9d0fb17f25b16ea3', 1614735683907, NULL, '1fcdc5fd231241888de266fd631818ea', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('b0f17406d13e465488e909a480953d1f', 1614222115214, NULL, 'cca4fbc2304a4a09b8593b888d379041', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('b3199118b97849b0be243aa49c9b9a3b', 1614222115214, NULL, '57b31e8fc1904ec99e6bf6ae59c36bb4', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('b3510b24145a48d2ad8b234b6fd7e121', 1614247690381, NULL, '57b31e8fc1904ec99e6bf6ae59c36bb4', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('c1393c23e682491286c53c61dbee34e7', 1614247690381, NULL, 'b3d26ceff152432e9cd37d05caaa6a99', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('c148b3f4a61c4ec8abce707eeea70346', 1614735683907, NULL, '24ad74c48c2242909158b1ba2e18cb95', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('c168d2460d904c63b3a9afbc0e82f781', 1614735683907, NULL, '5d94909caf3d4c7899fc34f1490d0575', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('c6b91b4c613d445ea68a619a801a7df0', 1614247690381, NULL, '19c49c7283bc4891b8205949cb4ea543', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('c6c4422276684b839e3e95102476826f', 1614247699794, NULL, '24ad74c48c2242909158b1ba2e18cb95', '7934c883db4b43698e10d38bae2421d3');
INSERT INTO `system_role_authority` VALUES ('c7a4765d471744dfbd4f32a392ff3187', 1614222115214, NULL, '5d94909caf3d4c7899fc34f1490d0575', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('c87483e192154624b0e33563c8013853', 1614247699794, NULL, 'cca4fbc2304a4a09b8593b888d379041', '7934c883db4b43698e10d38bae2421d3');
INSERT INTO `system_role_authority` VALUES ('ca1c3a0e177b40a7bf529923d82d0de1', 1614735683907, NULL, 'b3d26ceff152432e9cd37d05caaa6a99', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('cb0d79b2dea942d890dec2cfcca4de07', 1614222115214, NULL, 'b923258748e54750b2fb31a17859cb23', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('cb35a00f3c4d499b9c3cde637570d1c2', 1614247690381, NULL, '1e433f1890304d8abb604894c2acb417', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('cd9241ce2a4c4803bc88104f22d5eb35', 1614735683907, NULL, '1e433f1890304d8abb604894c2acb417', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('ce02ccfbea8e4b2e95032fec9f35c387', 1614735683907, NULL, 'e53f1e2bf17147f3b94bdc03c50895c5', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('cee3b28117314949a2dbe08dcece552d', 1614247690381, NULL, '6ef6ca3fcf7040959c4ab79441827245', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('cfde0aa8ccee44289c4b8ba3948c6982', 1614222115214, NULL, 'e094036e052c4e709fc71f8b8dda3c2e', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('d00870ea54224103838b8e25e8380eb7', 1614222115214, NULL, '43e5177c78ad43db80c00197a107814c', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('d029e2d047a34db79c5b59c2dfe294b7', 1614735683907, NULL, 'c7a2499f9c9c4a9dac114cb889824803', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('d1a760f03419405c8841cf73ae7b2610', 1614247690381, NULL, 'f8e78aae1f6f4538912290e4c76bfb16', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('d71ccc5de73d4ded804eb9575c456c46', 1614247690381, NULL, 'a1dd1958a5864f4c89de4c65ab428d0c', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('d741f8b127f6481e887fbd56f49a6dcb', 1614247690381, NULL, 'e4f993099e164a0c85f16b69683f5f0d', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('d93ef9170e8040b4a3b5fd8da6501d43', 1614222179825, NULL, '4e6c35d41d7440fa951851db0f79620f', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('dee7eb859f7046908c04c1560111b2ef', 1614222179825, NULL, '4a6e281ed57d4ab4a263da96fefc84e4', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_authority` VALUES ('e4171eec235e4b9eb8e6b3a9c84875a7', 1614735683908, NULL, '4e6c35d41d7440fa951851db0f79620f', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('e78315d0064d4db3a42c3fced7275c9b', 1614247690381, NULL, 'fae75122f9784a2292ec26f09bfa8db7', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('e7dbb565e03d46309d714d75df345d3d', 1614247690381, NULL, '43e5177c78ad43db80c00197a107814c', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('eb4319f572e64e67859bebe88235a3f4', 1614222115214, NULL, '24ad74c48c2242909158b1ba2e18cb95', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('eeb20214d52f4a2fbedd3dea15382069', 1614247690381, NULL, 'b923258748e54750b2fb31a17859cb23', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('f1093adfec7044b0a0e24eece4e35bfb', 1614247690381, NULL, 'e094036e052c4e709fc71f8b8dda3c2e', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('f1c705ae10864cc398ec45fd4bad4210', 1614735683908, NULL, 'cca4fbc2304a4a09b8593b888d379041', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_authority` VALUES ('f311222979774ae9b2decdc43312524a', 1614247690381, NULL, '2a83ce7aecc24fc591db832952b3d781', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_authority` VALUES ('fcf7860291c04de68a37ed7e04eb7e4e', 1614222115214, NULL, '1fcdc5fd231241888de266fd631818ea', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_authority` VALUES ('fe4e22c16dcb434bb78bbcf53acecd14', 1614735683907, NULL, '4a6e281ed57d4ab4a263da96fefc84e4', 'f39d671ecd81478bbfc4a9f884115cad');

-- ----------------------------
-- Table structure for system_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  `menu_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_role_menu
-- ----------------------------
INSERT INTO `system_role_menu` VALUES ('0816d3f520324614a076c0aa263e4824', 1614735683772, NULL, 'fd4eaeb2a35c4a24bfd4d7c181e42759', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('0ebe6f11f22847b8b93a25f71314f57a', 1614735683772, NULL, 'fd6f5ba38cdd4a19a11bad24d332ae89', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('15ddeef3ec384eb98eb2551d49bf9dd2', 1614735683772, NULL, '3290e8fe75494f53a791f15121cfa67b', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('1dd59bed93ec4a729255eefb1da54484', 1614735683772, NULL, 'e35f3fb48f4c4da985465ce42c94be9f', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('213356c95ff543b2988e163183929c0b', 1614735683772, NULL, 'c266f30587a04861bd7d32ef2c81cd65', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('26a17b21d7bb47c8ac86d4b7c892136e', 1614735683772, NULL, 'e11f66f48b6b47c5b9dbcd78602db4d7', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('288e0dff3f674cce8a62327aa8cbf719', 1614735683772, NULL, '4b45fd3283944a34b506f789079999a6', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('373015dedada488e802fb26b942e870b', 1614247690354, NULL, 'b589e0d292e34688bba62870a8ae659c', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_menu` VALUES ('409d3e790a994bd386de6f34abee831f', 1614735683772, NULL, '5fe5f1eb67ae4370a4e0252e79f66fb5', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('4ef8f1c075844a7da0cb1faabe6db13a', 1614222179784, NULL, '4b45fd3283944a34b506f789079999a6', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_menu` VALUES ('4fe5daa121384f6daeb102edb540cbb2', 1614247690354, NULL, '6f02dc1d26be47e38122dabd3337ec7a', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_menu` VALUES ('52e67199ddd744fd88f5ee6e39cba4ac', 1614222179784, NULL, 'b589e0d292e34688bba62870a8ae659c', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_menu` VALUES ('607c867f9fbd4cf5abac177d48cc727b', 1614735683772, NULL, '0644600df7744b6ea0ad091c4a35afee', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('6256e5f0c1f04f41b794ebe042c6fd76', 1614735683772, NULL, '0e49797901c244eca38abe1531ae67de', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('650a786e1e3f4114a0b6bedf3b45ed1a', 1614735683772, NULL, 'a53a207b352d4e6c81ffa9f72ee4cb26', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('6e9f51378cc341d795c03813c4ecc1a6', 1614735683772, NULL, 'b589e0d292e34688bba62870a8ae659c', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('707343c1ea064bd8a02810c8b12f4dad', 1614735683772, NULL, '9f2da778019f46f7b19c91ab6256777a', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('72dc5920072f43ea93df20b19740e443', 1614735683772, NULL, '6e724dd981bf4c749b0d093bb896ed6b', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('8318e7f44c374a818763890fbe5078c7', 1614735683772, NULL, '2b323fba108a45799413876e68481bf4', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('8ea8fbde705e4f378932c35863f7d036', 1614222179784, NULL, '5500273007004ae7ba24dc0b101c4ee5', '2958eca23d7443c3bf6429714edfbc41');
INSERT INTO `system_role_menu` VALUES ('90ccc4286df14ed988f55cad2164e4e8', 1614735683772, NULL, '7c542f13ed11486981a31b99e43c3bc4', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('984063757a13497bbac768d4eba77f73', 1614222115186, NULL, '5500273007004ae7ba24dc0b101c4ee5', 'bb8cc8fe13554d40bf38ababf39dbe20');
INSERT INTO `system_role_menu` VALUES ('9dde199b621443be98df0cabc2a171cf', 1614247699786, NULL, '4b45fd3283944a34b506f789079999a6', '7934c883db4b43698e10d38bae2421d3');
INSERT INTO `system_role_menu` VALUES ('a78a8c0e2d8745ab86b73b0276c91f04', 1614247690354, NULL, '4b45fd3283944a34b506f789079999a6', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_menu` VALUES ('aaa1908c3c684fc89c704b07f38f0020', 1614247699786, NULL, '5500273007004ae7ba24dc0b101c4ee5', '7934c883db4b43698e10d38bae2421d3');
INSERT INTO `system_role_menu` VALUES ('b5733ea40e6948348d4aacd86634a330', 1614247690354, NULL, '5500273007004ae7ba24dc0b101c4ee5', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_menu` VALUES ('b796d398e10c4f54bcab1633af335aca', 1614735683772, NULL, '776c25528390480d9f0b7bedd0f3c960', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('ce087eba73074c5e9d060c7083d0f981', 1614735683772, NULL, '339bc44881854368a1b2ec14c943c43f', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('d8c976f361b443bcadbe29ae72756761', 1614735683772, NULL, '6f02dc1d26be47e38122dabd3337ec7a', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('df45a27388ce4b419c5e3f8403367a59', 1614735683772, NULL, 'd33c1d6e97c84c719f63c4eba0784187', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('e6e485f40cbb41698aaa2a5ccc830f34', 1614735683772, NULL, '5500273007004ae7ba24dc0b101c4ee5', 'f39d671ecd81478bbfc4a9f884115cad');
INSERT INTO `system_role_menu` VALUES ('e73c26d7c90a4343b0a2ea7674ebcfba', 1614247690354, NULL, '339bc44881854368a1b2ec14c943c43f', '38e2aadb712e4a7499feb58c649a666b');
INSERT INTO `system_role_menu` VALUES ('ee024b4c190b40f6b3c38fab893298fb', 1614735683772, NULL, '82a572cc30334dfa976019ec72f9f80d', 'f39d671ecd81478bbfc4a9f884115cad');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `active` int(11) NULL DEFAULT NULL,
  `create_at` bigint(20) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `realname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_at` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `locked` int(11) NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('088c49f786894126870318483b4fff7d', 1, 1582859563196, 'ethanwong@qq.com', '$2a$10$7JEdZjbsAMTdVZRvDzWvW.NqquWdoWFsMwJ2EMS3hXHMrjtHXkbAO', '15960203283', 'coffee', '7934c883db4b43698e10d38bae2421d3', 1615361124975, 'coffee', 1, '1');
INSERT INTO `system_user` VALUES ('108123cf9210452f88a2549880c918b0', 1, 1582859592885, 'ethanwong@qq.com', '$2a$10$pUs7cjqp3HNBRP5Ry6Yad.s1hz0EJ20.NH2XLOtjDKkUBDwfUvNoa', '15960203283', '超级管理员', 'f39d671ecd81478bbfc4a9f884115cad', 1614840458280, 'root', 0, '1');
INSERT INTO `system_user` VALUES ('ef0c402ca66647f6b123d19901f8656c', 1, 1582859576383, 'ethanwong@qq.com', '$2a$10$MgidNs3EIvbcgoVLbpF/XedxCgeg6dZ.1Cz/Dgz9gGV2AWzyd7oGu', '15960203283', 'admin', '38e2aadb712e4a7499feb58c649a666b', 1614247737937, 'admin', 0, '2');
INSERT INTO `system_user` VALUES ('fa187de310d24e89876af5b0b78d4638', 1, 1582859587207, 'ethanwong@qq.com', '$2a$10$7N.NEYNrFx10pDe0YKGqAuzUOdsFn6DrOoc6tvgwhd6dqyGTHA/H.', '15960203283', 'hello', '7934c883db4b43698e10d38bae2421d3', 1615360587873, 'hello', 1, '1');

SET FOREIGN_KEY_CHECKS = 1;
