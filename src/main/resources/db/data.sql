-- 初始用户（密码由 DataInitializer 在启动时用 BCrypt 编码为 123456）
MERGE INTO t_user (id, username, password, nickname, balance) KEY(id)
VALUES (1, 'admin', 'INIT', '管理员', 100.00),
       (2, 'user1', 'INIT', '张三',  200.00);

-- 一级分类
MERGE INTO t_category (id, name, parent_id, sort) KEY(id)
VALUES (1, '热菜', 0, 1),
       (2, '凉菜', 0, 2),
       (3, '主食', 0, 3),
       (4, '饮品', 0, 4);

-- 二级分类
MERGE INTO t_category (id, name, parent_id, sort) KEY(id)
VALUES (11, '家常菜', 1, 1),
       (12, '海鲜',   1, 2),
       (21, '凉拌类', 2, 1),
       (31, '米饭面食', 3, 1),
       (41, '冷饮',   4, 1),
       (42, '热饮',   4, 2);

-- 菜品
MERGE INTO t_dish (id, category_id, name, description, price, image, sort) KEY(id)
VALUES
(101, 11, '红烧肉',   '肥而不腻，入口即化', 48.00, 'https://images.unsplash.com/photo-1560717845-968823efbee1?w=300&q=80', 1),
(102, 11, '鱼香肉丝', '经典川菜，酸甜微辣', 28.00, 'https://images.unsplash.com/photo-1604908176997-125f25cc6f3d?w=300&q=80', 2),
(103, 11, '宫保鸡丁', '花生香脆，鲜嫩可口', 32.00, 'https://images.unsplash.com/photo-1547592180-85f173990554?w=300&q=80', 3),
(104, 11, '麻婆豆腐', '麻辣鲜香，嫩滑豆腐', 22.00, 'https://images.unsplash.com/photo-1585032226651-759b368d7246?w=300&q=80', 4),
(105, 12, '清蒸鲈鱼', '新鲜鲈鱼，鲜嫩清甜', 68.00, 'https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=300&q=80', 1),
(106, 12, '蒜蓉虾',   '蒜香浓郁，虾肉弹牙', 58.00, 'https://images.unsplash.com/photo-1565680018434-b513d5e5fd47?w=300&q=80', 2),
(107, 12, '葱姜炒蟹', '鲜活螃蟹，葱姜提鲜', 88.00, 'https://images.unsplash.com/photo-1550172560-f6a9cc5ad4f8?w=300&q=80', 3),
(201, 21, '凉拌黄瓜', '清脆爽口，蒜香十足', 12.00, 'https://images.unsplash.com/photo-1449300079323-02e209d9d3a6?w=300&q=80', 1),
(202, 21, '夫妻肺片', '麻辣鲜香，经典凉菜', 26.00, 'https://images.unsplash.com/photo-1563245372-f21724e3856d?w=300&q=80', 2),
(203, 21, '口水鸡',   '鸡肉嫩滑，红油飘香', 32.00, 'https://images.unsplash.com/photo-1598103442097-8b74394b95c2?w=300&q=80', 3),
(301, 31, '白米饭',   '香糯软饭',           3.00,  'https://images.unsplash.com/photo-1516684732162-798a0062be99?w=300&q=80', 1),
(302, 31, '蛋炒饭',   '粒粒分明，蛋香浓郁', 16.00, 'https://images.unsplash.com/photo-1512058564366-18510be2db19?w=300&q=80', 2),
(303, 31, '阳春面',   '清汤细面，葱香扑鼻', 12.00, 'https://images.unsplash.com/photo-1569718212165-3a8278d5f624?w=300&q=80', 3),
(304, 31, '牛肉拉面', '手工拉面，牛肉浓汤', 22.00, 'https://images.unsplash.com/photo-1555126634-323283e090fa?w=300&q=80', 4),
(401, 41, '可乐',     '冰镇可口可乐',       8.00,  'https://images.unsplash.com/photo-1581636625402-29b2a704ef13?w=300&q=80', 1),
(402, 41, '橙汁',     '鲜榨橙汁，维C满满',  15.00, 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=300&q=80', 2),
(403, 41, '柠檬水',   '清新解腻，微酸爽口', 12.00, 'https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=300&q=80', 3),
(404, 42, '普洱茶',   '醇厚回甘，解腻助消化',18.00,'https://images.unsplash.com/photo-1556742049-0cfed4f6a45d?w=300&q=80', 1),
(405, 42, '菊花茶',   '清热去火，淡雅清香', 15.00, 'https://images.unsplash.com/photo-1564890369478-c89ca6d9cde9?w=300&q=80', 2);
