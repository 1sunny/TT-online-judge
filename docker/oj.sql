/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : oj

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 27/04/2022 16:53:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_contest
-- ----------------------------
DROP TABLE IF EXISTS `t_contest`;
CREATE TABLE `t_contest`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片路径',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `rule_type` int(2) NOT NULL COMMENT '1->ACM,2->IOI,3->OI',
  `visible` tinyint(1) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password_protect` tinyint(1) NULL DEFAULT NULL,
  `registered_num` int(11) NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `penalty` int(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `fk_c_creator_id`(`creator_id`) USING BTREE,
  INDEX `fk_c_creator_name`(`creator_name`) USING BTREE,
  CONSTRAINT `fk_c_creator_id` FOREIGN KEY (`creator_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_c_creator_name` FOREIGN KEY (`creator_name`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest
-- ----------------------------
INSERT INTO `t_contest` VALUES (1, 'TTOJ测试赛', '歌德曾经提到过，没有人事先了解自己到底有多大的力量，直到他试过以后才知道。这不禁令我深思。 要想清楚，算法比赛，到底是一种怎么样的存在。 莫扎特在不经意间这样说过，谁和我一样用功，谁就会和我一样成功。这句话语虽然很短，但令我浮想联翩。 我们一般认为，抓住了问题的关键，其他一切则会迎刃而解。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 经过上述讨论， 对我个人而言，算法比赛不仅仅是一个重大的事件，还可能会改变我的人生。 了解清楚算法比赛到底是一种怎么样的存在，是解决一切问题的关键。 算法比赛，发生了会如何，不发生又会如何。 既然如此， 既然如此， 算法比赛，到底应该如何实现。 所谓算法比赛，关键是算法比赛需要如何写。 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 拉罗什夫科曾经说过，取得成就时坚持不懈，要比遭到失败时顽强不屈更重要。带着这句话，我们还要更加慎重的审视这个问题： 所谓算法比赛，关键是算法比赛需要如何写。 每个人都不得不面对这些问题。 在面对这种问题时， 了解清楚算法比赛到底是一种怎么样的存在，是解决一切问题的关键。 总结的来说， 佚名曾经提到过，感激每一个新的挑战，因为它会锻造你的意志和品格。这不禁令我深思。 我们不得不面对一个非常尴尬的事实，那就是， 就我个人来说，算法比赛对我的意义，不能不说非常重大。 所谓算法比赛，关键是算法比赛需要如何写。 可是，即使是这样，算法比赛的出现仍然代表了一定的意义。 就我个人来说，算法比赛对我的意义，不能不说非常重大。 带着这些问题，我们来审视一下算法比赛。 这样看来， 西班牙在不经意间这样说过，自己的鞋子，自己知道紧在哪里。这似乎解答了我的疑惑。 算法比赛，到底应该如何实现。 一般来说， 西班牙曾经提到过，自知之明是最难得的知识。带着这句话，我们还要更加慎重的审视这个问题： 我们不得不面对一个非常尴尬的事实，那就是， 对我个人而言，算法比赛不仅仅是一个重大的事件，还可能会改变我的人生。 每个人都不得不面对这些问题。 在面对这种问题时， 现在，解决算法比赛的问题，是非常非常重要的。 所以， 我认为， 现在，解决算法比赛的问题，是非常非常重要的。 所以， 算法比赛因何而发生？ 米歇潘曾经说过，生命是一条艰险的峡谷，只有勇敢的人才能通过。这不禁令我深思。 在这种困难的抉择下，本人思来想去，寝食难安。 莎士比亚曾经说过，本来无望的事，大胆尝试，往往能成功。这似乎解答了我的疑惑。 而这些并不是完全重要，更加重要的问题是， 我认为， 所谓算法比赛，关键是算法比赛需要如何写。 布尔沃在不经意间这样说过，要掌握书，莫被书掌握；要为生而读，莫为读而生。这句话语虽然很短，但令我浮想联翩。 总结的来说， 问题的关键究竟为何？ 现在，解决算法比赛的问题，是非常非常重要的。 所以， 培根曾经提到过，阅读使人充实，会谈使人敏捷，写作使人精确。这句话语虽然很短，但令我浮想联翩。 算法比赛，发生了会如何，不发生又会如何。 希腊在不经意间这样说过，最困难的事情就是认识自己。这句话语虽然很短，但令我浮想联翩。 既然如何， 可是，即使是这样，算法比赛的出现仍然代表了一定的意义。 从这个角度来看， 经过上述讨论， 既然如此， 这种事实对本人来说意义重大，相信对这个世界也是有一定意义的。 在这种困难的抉择下，本人思来想去，寝食难安。 在这种困难的抉择下，本人思来想去，寝食难安。 每个人都不得不面对这些问题。 在面对这种问题时， 总结的来说， 这样看来。', 'default-contestCover.png', '2022-04-20 15:18:50', '2022-08-01 12:18:53', 1, 1, NULL, 0, 2, 'root', 1487355513478254593, '2022-04-17 20:07:59', '2022-04-24 20:16:17', 20);
INSERT INTO `t_contest` VALUES (2, '2', '2', 'default-contestCover.png', '2022-06-24 17:05:33', '2022-09-01 00:00:00', 1, 1, NULL, NULL, 0, 'root', 1487355513478254593, '2022-03-27 17:05:51', '2022-03-27 17:05:51', 20);
INSERT INTO `t_contest` VALUES (3, '3', '3', 'default-contestCover.png', '2022-03-15 17:05:33', '2022-03-17 00:00:00', 1, 1, NULL, NULL, 0, 'root', 1487355513478254593, '2022-03-27 17:06:04', '2022-03-27 17:06:04', 20);

-- ----------------------------
-- Table structure for t_contest_author
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_author`;
CREATE TABLE `t_contest_author`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `contest_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_ca_user_id`(`user_id`) USING BTREE,
  INDEX `fk_ca_contest_id`(`contest_id`) USING BTREE,
  INDEX `fk_ca_username`(`username`) USING BTREE,
  CONSTRAINT `fk_ca_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `t_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ca_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ca_username` FOREIGN KEY (`username`) REFERENCES `t_user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest_author
-- ----------------------------
INSERT INTO `t_contest_author` VALUES (1, 1487355513478254593, 'root', 2);
INSERT INTO `t_contest_author` VALUES (2, 1487355513478254593, 'root', 3);
INSERT INTO `t_contest_author` VALUES (1515640313023668226, 1487355513478254593, 'root', 1);
INSERT INTO `t_contest_author` VALUES (1518474124121210882, 2, '222', 2);

-- ----------------------------
-- Table structure for t_contest_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_problem`;
CREATE TABLE `t_contest_problem`  (
  `id` bigint(20) NOT NULL,
  `contest_id` bigint(20) NULL DEFAULT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  `problem_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `problem_display_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `submit_num` int(11) NOT NULL,
  `ac_num` int(11) NOT NULL,
  `first_ac_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `first_ac_user_id` bigint(20) NULL DEFAULT NULL,
  `problem_first_ac_time` int(11) NULL DEFAULT NULL,
  `visible` tinyint(1) NOT NULL,
  `author_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  INDEX `fk_cp_contest_id`(`contest_id`) USING BTREE,
  INDEX `fk_cp_problem_id`(`problem_id`) USING BTREE,
  INDEX `fk_cp_first_ac_user_id`(`first_ac_user_id`) USING BTREE,
  INDEX `fk_cp_first_ac_username`(`first_ac_username`) USING BTREE,
  INDEX `fk_cp_author_name`(`author_name`) USING BTREE,
  INDEX `fk_cp_problem_name`(`problem_name`) USING BTREE,
  CONSTRAINT `fk_cp_author_name` FOREIGN KEY (`author_name`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_cp_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `t_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cp_first_ac_user_id` FOREIGN KEY (`first_ac_user_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_cp_first_ac_username` FOREIGN KEY (`first_ac_username`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_cp_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cp_problem_name` FOREIGN KEY (`problem_name`) REFERENCES `t_problem` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest_problem
-- ----------------------------
INSERT INTO `t_contest_problem` VALUES (3, 1, 1111, 'A+B', '1001', 0, 0, NULL, NULL, NULL, 1, 'root');
INSERT INTO `t_contest_problem` VALUES (1509899123768123393, 1, 2222, 'A-B', '1002', 0, 0, NULL, NULL, NULL, 1, 'root');
INSERT INTO `t_contest_problem` VALUES (1518574813379964930, 2, 1111, 'A+B', '1001', 0, 0, NULL, NULL, NULL, 0, 'root');

-- ----------------------------
-- Table structure for t_contest_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_rank`;
CREATE TABLE `t_contest_rank`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `contest_id` bigint(20) NULL DEFAULT NULL,
  `total_time` int(11) NULL DEFAULT NULL,
  `ac_num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `fk_cr_contest_id`(`contest_id`) USING BTREE,
  INDEX `fk_cr_username`(`username`) USING BTREE,
  INDEX `fk_cr_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_cr_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `t_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cr_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_cr_username` FOREIGN KEY (`username`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest_rank
-- ----------------------------

-- ----------------------------
-- Table structure for t_contest_submission
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_submission`;
CREATE TABLE `t_contest_submission`  (
  `id` bigint(20) NOT NULL,
  `contest_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  `problem_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `problem_display_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `submit_time` datetime NOT NULL,
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_use` int(11) NOT NULL,
  `memory_use` int(11) NOT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `code_length` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_cs_contest_id`(`contest_id`) USING BTREE,
  INDEX `fk_cs_user_id`(`user_id`) USING BTREE,
  INDEX `fk_cs_username`(`username`) USING BTREE,
  INDEX `fk_cs_problem_id`(`problem_id`) USING BTREE,
  INDEX `fk_cs_problem_name`(`problem_name`) USING BTREE,
  CONSTRAINT `fk_cs_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `t_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cs_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_cs_problem_name` FOREIGN KEY (`problem_name`) REFERENCES `t_problem` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_cs_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_cs_username` FOREIGN KEY (`username`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest_submission
-- ----------------------------

-- ----------------------------
-- Table structure for t_contest_user_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_contest_user_problem`;
CREATE TABLE `t_contest_user_problem`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `contest_id` bigint(20) NULL DEFAULT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  `wa_times` int(11) NULL DEFAULT NULL,
  `first_ac_time` int(11) NULL DEFAULT NULL,
  `first_blood` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_cup_user_id`(`user_id`) USING BTREE,
  INDEX `fk_cup_contest_id`(`contest_id`) USING BTREE,
  INDEX `fk_cup_problem_id`(`problem_id`) USING BTREE,
  CONSTRAINT `fk_cup_contest_id` FOREIGN KEY (`contest_id`) REFERENCES `t_contest` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cup_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_cup_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_contest_user_problem
-- ----------------------------

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint(20) NOT NULL,
  `auth_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `auth_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_using` tinyint(1) NULL DEFAULT NULL COMMENT '1->使用, 0->停用',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, '管理所有题目', 'manage:all:problem', 1, NULL, NULL, 'ManageProblem', 0);
INSERT INTO `t_menu` VALUES (2, '管理私有题目', 'manage:own:problem', 1, NULL, NULL, 'ManageProblem', 0);
INSERT INTO `t_menu` VALUES (3, '管理所有比赛', 'manage:all:contest', 1, NULL, NULL, 'ManageContest', 0);
INSERT INTO `t_menu` VALUES (4, '管理私有比赛', 'manage:own:contest', 1, NULL, NULL, 'ManageContest', 0);
INSERT INTO `t_menu` VALUES (5, '管理题目标签', 'manage:all:problemTag', 1, NULL, NULL, 'ManageProblemTag', 0);
INSERT INTO `t_menu` VALUES (6, '管理所有用户', 'manage:all:user', 1, NULL, NULL, 'ManageUser', 0);

-- ----------------------------
-- Table structure for t_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_problem`;
CREATE TABLE `t_problem`  (
  `id` bigint(20) NOT NULL,
  `display_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `visible` tinyint(1) NOT NULL,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `input` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `output` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hint` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `level` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `time_limit` int(11) NOT NULL,
  `memory_limit` int(11) NOT NULL,
  `languages` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vote` int(11) NULL DEFAULT NULL,
  `submit_num` int(11) NULL DEFAULT NULL,
  `ac_num` int(11) NULL DEFAULT NULL,
  `author_name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `io_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `deleted` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `solution_num` int(11) NULL DEFAULT NULL,
  `comment_num` int(11) NULL DEFAULT NULL,
  `test_case_dir` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sample_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `author_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `display_id`(`display_id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  INDEX `fk_p_author_name`(`author_name`) USING BTREE,
  INDEX `fk_p_author_id`(`author_id`) USING BTREE,
  CONSTRAINT `fk_p_author_id` FOREIGN KEY (`author_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_p_author_name` FOREIGN KEY (`author_name`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_problem
-- ----------------------------
INSERT INTO `t_problem` VALUES (1111, '1001', 1, 'A+B', '请计算a+b的值并输出', '输入两个整数A,B，用空格隔开，0≤A,B≤100', '一个整数', '没有hint111111111111111111111111111111111111111', '简单', 1000, 256, '[\"Cpp\",\"C\"]', 0, 12, 0, 'root', 'TTOJ problem', 'standard', '2021-11-01 21:04:23', 0, 0, 0, '1111', '[{\"input\":\"1 1\",\"output\":\"2\"},{\"input\":\"1 3\",\"output\":\"4\"}]', '2022-04-24 20:15:15', 1487355513478254593);
INSERT INTO `t_problem` VALUES (2222, '1002', 1, 'A-B', 'a-b', '输入两个整数A,B，用空格隔开，0≤A,B≤100', '一个整数', '入门', '入门', 1000, 256, '[\"Cpp\",\"Java\",\"C\",\"Python2\",\"Python3\"]', 1, 1, 1, 'root', 'TTOJ problem', 'standard', '2022-04-27 16:51:37', 0, 0, 12, '2222', '[{\"input\":\"1 1\",\"output\":\"0\"}]', '2022-04-10 20:21:45', 1487355513478254593);

-- ----------------------------
-- Table structure for t_problem_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_problem_tag`;
CREATE TABLE `t_problem_problem_tag`  (
  `id` bigint(20) NOT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  `problem_tag_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_ppt_problem_id`(`problem_id`) USING BTREE,
  INDEX `fk_ppt_problem_tag_id`(`problem_tag_id`) USING BTREE,
  CONSTRAINT `fk_ppt_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ppt_problem_tag_id` FOREIGN KEY (`problem_tag_id`) REFERENCES `t_problem_tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_problem_problem_tag
-- ----------------------------
INSERT INTO `t_problem_problem_tag` VALUES (1, 1111, 4);
INSERT INTO `t_problem_problem_tag` VALUES (2, 1111, 5);

-- ----------------------------
-- Table structure for t_problem_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_problem_tag`;
CREATE TABLE `t_problem_tag`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  `tag_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_problem_tag
-- ----------------------------
INSERT INTO `t_problem_tag` VALUES (1, '基本算法', 0, 1);
INSERT INTO `t_problem_tag` VALUES (2, '构造', 1, 2);
INSERT INTO `t_problem_tag` VALUES (3, '枚举', 1, 3);
INSERT INTO `t_problem_tag` VALUES (4, '模拟', 1, 4);
INSERT INTO `t_problem_tag` VALUES (5, '贪心', 1, 5);
INSERT INTO `t_problem_tag` VALUES (6, '分治', 1, 6);
INSERT INTO `t_problem_tag` VALUES (7, '递归', 1, 7);
INSERT INTO `t_problem_tag` VALUES (8, '搜索', 0, 8);
INSERT INTO `t_problem_tag` VALUES (9, '深度优先搜索', 8, 9);
INSERT INTO `t_problem_tag` VALUES (10, '广度优先搜索', 8, 10);
INSERT INTO `t_problem_tag` VALUES (11, '双向搜索', 8, 11);
INSERT INTO `t_problem_tag` VALUES (12, '记忆法搜索', 8, 12);
INSERT INTO `t_problem_tag` VALUES (13, '启发式搜索', 8, 13);
INSERT INTO `t_problem_tag` VALUES (14, '动态规划', 0, 14);
INSERT INTO `t_problem_tag` VALUES (15, '背包问题', 14, 15);
INSERT INTO `t_problem_tag` VALUES (16, '01背包', 15, 16);
INSERT INTO `t_problem_tag` VALUES (17, '多重背包', 15, 17);
INSERT INTO `t_problem_tag` VALUES (18, '完全背包', 15, 18);
INSERT INTO `t_problem_tag` VALUES (19, '混合背包', 15, 19);
INSERT INTO `t_problem_tag` VALUES (20, '分组背包', 15, 20);
INSERT INTO `t_problem_tag` VALUES (21, '有依赖背包', 15, 21);
INSERT INTO `t_problem_tag` VALUES (22, '二维费用背包', 15, 22);
INSERT INTO `t_problem_tag` VALUES (23, '背包问题求方案数', 15, 23);
INSERT INTO `t_problem_tag` VALUES (24, '背包问题求具体方案', 15, 24);
INSERT INTO `t_problem_tag` VALUES (25, '数位dp', 14, 25);
INSERT INTO `t_problem_tag` VALUES (26, '状态压缩dp', 14, 26);
INSERT INTO `t_problem_tag` VALUES (27, '区间dp', 14, 27);
INSERT INTO `t_problem_tag` VALUES (28, '树形dp', 14, 28);
INSERT INTO `t_problem_tag` VALUES (29, '动态规划优化方法', 14, 29);
INSERT INTO `t_problem_tag` VALUES (30, '滚动数组', 29, 30);
INSERT INTO `t_problem_tag` VALUES (31, '矩阵优化', 29, 31);
INSERT INTO `t_problem_tag` VALUES (32, '斜率优化', 29, 32);
INSERT INTO `t_problem_tag` VALUES (33, '四边形不等式优化', 29, 33);
INSERT INTO `t_problem_tag` VALUES (34, '数据结构优化', 29, 34);
INSERT INTO `t_problem_tag` VALUES (35, '字符串', 0, 35);
INSERT INTO `t_problem_tag` VALUES (36, 'manacher算法', 35, 36);
INSERT INTO `t_problem_tag` VALUES (37, '字符串hash', 35, 37);
INSERT INTO `t_problem_tag` VALUES (38, ' KMP算法', 35, 38);
INSERT INTO `t_problem_tag` VALUES (39, '普通KMP算法', 38, 39);
INSERT INTO `t_problem_tag` VALUES (40, '扩展KMP算法', 38, 40);
INSERT INTO `t_problem_tag` VALUES (41, 'Trie字典树', 35, 41);
INSERT INTO `t_problem_tag` VALUES (42, '字典树', 41, 42);
INSERT INTO `t_problem_tag` VALUES (43, '01字典树', 41, 43);
INSERT INTO `t_problem_tag` VALUES (44, '自动机', 35, 44);
INSERT INTO `t_problem_tag` VALUES (45, 'AC自动机', 44, 45);
INSERT INTO `t_problem_tag` VALUES (46, '回文自动机', 44, 46);
INSERT INTO `t_problem_tag` VALUES (47, '后缀自动机', 44, 47);
INSERT INTO `t_problem_tag` VALUES (48, '后缀数组', 35, 48);
INSERT INTO `t_problem_tag` VALUES (49, '其它算法与技巧', 0, 49);
INSERT INTO `t_problem_tag` VALUES (50, '分块算法', 49, 50);
INSERT INTO `t_problem_tag` VALUES (51, '莫队算法', 49, 51);
INSERT INTO `t_problem_tag` VALUES (52, 'CDQ分治', 49, 52);
INSERT INTO `t_problem_tag` VALUES (53, '尺取法', 49, 53);
INSERT INTO `t_problem_tag` VALUES (54, '图论', 0, 54);
INSERT INTO `t_problem_tag` VALUES (55, '最短路', 54, 55);
INSERT INTO `t_problem_tag` VALUES (56, 'Dijkstra算法', 55, 56);
INSERT INTO `t_problem_tag` VALUES (57, '二分图', 55, 57);
INSERT INTO `t_problem_tag` VALUES (58, '网络流', 55, 58);
INSERT INTO `t_problem_tag` VALUES (59, '拓扑排序', 55, 59);
INSERT INTO `t_problem_tag` VALUES (60, '2-SAT', 55, 60);
INSERT INTO `t_problem_tag` VALUES (61, '欧拉图与哈密顿图', 55, 61);
INSERT INTO `t_problem_tag` VALUES (62, '最小生成树', 54, 62);
INSERT INTO `t_problem_tag` VALUES (63, 'Prime算法', 62, 63);
INSERT INTO `t_problem_tag` VALUES (64, 'Kruskal算法', 62, 64);
INSERT INTO `t_problem_tag` VALUES (65, '二分图', 54, 65);
INSERT INTO `t_problem_tag` VALUES (66, '二分图判断', 65, 66);
INSERT INTO `t_problem_tag` VALUES (67, '匈牙利算法', 65, 67);
INSERT INTO `t_problem_tag` VALUES (68, 'KM算法', 65, 68);
INSERT INTO `t_problem_tag` VALUES (69, '网络流', 54, 69);
INSERT INTO `t_problem_tag` VALUES (70, '最大流', 69, 70);
INSERT INTO `t_problem_tag` VALUES (71, '最小费用流', 69, 71);
INSERT INTO `t_problem_tag` VALUES (72, '有界网络流', 69, 72);
INSERT INTO `t_problem_tag` VALUES (73, '拓扑排序', 54, 73);
INSERT INTO `t_problem_tag` VALUES (74, '2-SAT', 54, 74);
INSERT INTO `t_problem_tag` VALUES (75, '欧拉图与哈密顿图', 54, 75);
INSERT INTO `t_problem_tag` VALUES (76, '数据结构', 0, 76);
INSERT INTO `t_problem_tag` VALUES (77, '基础数据结构', 76, 77);
INSERT INTO `t_problem_tag` VALUES (78, '高级数据结构', 76, 78);
INSERT INTO `t_problem_tag` VALUES (79, '栈', 77, 79);
INSERT INTO `t_problem_tag` VALUES (80, '队列', 77, 80);
INSERT INTO `t_problem_tag` VALUES (81, '链表', 77, 81);
INSERT INTO `t_problem_tag` VALUES (82, '堆', 77, 82);
INSERT INTO `t_problem_tag` VALUES (83, '树', 77, 83);
INSERT INTO `t_problem_tag` VALUES (84, '集合', 77, 84);
INSERT INTO `t_problem_tag` VALUES (85, '映射', 77, 85);
INSERT INTO `t_problem_tag` VALUES (86, '高级数据结构', 76, 86);
INSERT INTO `t_problem_tag` VALUES (87, '单调栈', 86, 87);
INSERT INTO `t_problem_tag` VALUES (88, '单调队列', 86, 88);
INSERT INTO `t_problem_tag` VALUES (89, 'ST表', 86, 89);
INSERT INTO `t_problem_tag` VALUES (90, '并查集', 86, 90);
INSERT INTO `t_problem_tag` VALUES (91, '带权并查集', 90, 91);
INSERT INTO `t_problem_tag` VALUES (92, '种类并查集', 90, 92);
INSERT INTO `t_problem_tag` VALUES (93, '可持久化并查集', 90, 93);
INSERT INTO `t_problem_tag` VALUES (94, '树状数组', 86, 94);
INSERT INTO `t_problem_tag` VALUES (95, '线段树', 86, 95);
INSERT INTO `t_problem_tag` VALUES (96, '权值线段树', 95, 96);
INSERT INTO `t_problem_tag` VALUES (97, ' 主席树', 95, 97);
INSERT INTO `t_problem_tag` VALUES (98, '普通线段树', 95, 98);
INSERT INTO `t_problem_tag` VALUES (99, '平衡树', 86, 99);
INSERT INTO `t_problem_tag` VALUES (100, '字典树', 86, 100);
INSERT INTO `t_problem_tag` VALUES (101, '树链剖分', 86, 101);
INSERT INTO `t_problem_tag` VALUES (102, 'LCT', 86, 102);
INSERT INTO `t_problem_tag` VALUES (103, '数论', 0, 103);
INSERT INTO `t_problem_tag` VALUES (104, '模运算', 103, 104);
INSERT INTO `t_problem_tag` VALUES (105, '欧几里得定理', 103, 105);
INSERT INTO `t_problem_tag` VALUES (106, '扩展欧几里得', 103, 106);
INSERT INTO `t_problem_tag` VALUES (107, '线性同余方程', 103, 107);
INSERT INTO `t_problem_tag` VALUES (108, '中国剩余定理', 103, 108);
INSERT INTO `t_problem_tag` VALUES (109, '乘法逆元', 103, 109);
INSERT INTO `t_problem_tag` VALUES (110, ' 二次同余方程', 103, 110);
INSERT INTO `t_problem_tag` VALUES (111, ' 唯一分解定理', 103, 111);
INSERT INTO `t_problem_tag` VALUES (112, ' 素数', 103, 112);
INSERT INTO `t_problem_tag` VALUES (113, '同余', 104, 113);
INSERT INTO `t_problem_tag` VALUES (114, '快速幂', 104, 114);
INSERT INTO `t_problem_tag` VALUES (115, '素数筛', 112, 115);
INSERT INTO `t_problem_tag` VALUES (116, '反素数', 112, 116);
INSERT INTO `t_problem_tag` VALUES (117, '欧拉函数', 103, 117);
INSERT INTO `t_problem_tag` VALUES (118, '欧拉降幂', 103, 118);
INSERT INTO `t_problem_tag` VALUES (119, '积性函数', 103, 119);
INSERT INTO `t_problem_tag` VALUES (120, '莫比乌斯函数', 103, 120);
INSERT INTO `t_problem_tag` VALUES (121, '原根', 103, 121);
INSERT INTO `t_problem_tag` VALUES (122, '偏序关系', 103, 122);

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_using` tinyint(1) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `role_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', 1, NULL, NULL, NULL);
INSERT INTO `t_role` VALUES (2, '普通用户', 0, NULL, NULL, NULL);
INSERT INTO `t_role` VALUES (3, '题目创作者', 1, NULL, NULL, NULL);
INSERT INTO `t_role` VALUES (4, '比赛举办方', 1, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `menu_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_rm_role_id`(`role_id`) USING BTREE,
  INDEX `fk_rm_menu_id`(`menu_id`) USING BTREE,
  CONSTRAINT `fk_rm_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `t_menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rm_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (1, 1, 1);
INSERT INTO `t_role_menu` VALUES (2, 1, 3);
INSERT INTO `t_role_menu` VALUES (3, 1, 5);
INSERT INTO `t_role_menu` VALUES (4, 3, 2);
INSERT INTO `t_role_menu` VALUES (5, 4, 2);
INSERT INTO `t_role_menu` VALUES (6, 4, 4);
INSERT INTO `t_role_menu` VALUES (7, 1, 6);

-- ----------------------------
-- Table structure for t_submission
-- ----------------------------
DROP TABLE IF EXISTS `t_submission`;
CREATE TABLE `t_submission`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  `problem_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `submit_time` datetime NULL DEFAULT NULL,
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `language` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time_use` int(11) NULL DEFAULT NULL,
  `memory_use` int(11) NULL DEFAULT NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_submission_username`(`username`) USING BTREE,
  INDEX `fk_submission_problem_id`(`problem_id`) USING BTREE,
  INDEX `fk_submission_problem_name`(`problem_name`) USING BTREE,
  INDEX `fk_submission_user_id`(`user_id`) USING BTREE,
  CONSTRAINT `fk_submission_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_submission_problem_name` FOREIGN KEY (`problem_name`) REFERENCES `t_problem` (`name`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_submission_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_submission_username` FOREIGN KEY (`username`) REFERENCES `t_user` (`username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_submission
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_login` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `deleted` tinyint(1) NULL DEFAULT NULL,
  `rating` int(11) NULL DEFAULT NULL,
  `contribution` int(11) NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `friends_num` int(11) NULL DEFAULT NULL,
  `language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `ac_num` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1487355513478254594 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, '222', '$2a$10$nFY2TCYG6nM0wprcshoW4eawTfQADOYoMrL5EaZ9lsUUbfNxjfT42', NULL, NULL, NULL, NULL, 1234, 12, 'default-userAvatar.png', NULL, NULL, NULL, 0);
INSERT INTO `t_user` VALUES (1487355513478254593, 'root', '$2a$10$nFY2TCYG6nM0wprcshoW4eawTfQADOYoMrL5EaZ9lsUUbfNxjfT42', NULL, '2022-02-08 20:29:31', '2022-02-05 19:45:23', 0, 1236, 9, 'default-userAvatar.png', 0, 'C++', '2022-02-19 11:04:53', 1);

-- ----------------------------
-- Table structure for t_user_ac_problem
-- ----------------------------
DROP TABLE IF EXISTS `t_user_ac_problem`;
CREATE TABLE `t_user_ac_problem`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `problem_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_uap_user_id`(`user_id`) USING BTREE,
  INDEX `fk_uap_problem_id`(`problem_id`) USING BTREE,
  CONSTRAINT `fk_uap_problem_id` FOREIGN KEY (`problem_id`) REFERENCES `t_problem` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_uap_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_ac_problem
-- ----------------------------
INSERT INTO `t_user_ac_problem` VALUES (1, 1487355513478254593, 1111);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`, `user_id`, `role_id`) USING BTREE,
  INDEX `fk_ur_user_id`(`user_id`) USING BTREE,
  INDEX `fk_ur_role_id`(`role_id`) USING BTREE,
  CONSTRAINT `fk_ur_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ur_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1501376887931019265, 2, 3);
INSERT INTO `t_user_role` VALUES (1501376887931019266, 2, 4);
INSERT INTO `t_user_role` VALUES (1, 1487355513478254593, 1);

SET FOREIGN_KEY_CHECKS = 1;
