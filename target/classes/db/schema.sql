-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(64)    NOT NULL UNIQUE,
    password    VARCHAR(128)   NOT NULL,
    nickname    VARCHAR(64)    NOT NULL DEFAULT '',
    avatar      VARCHAR(255)   DEFAULT '',
    balance     DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 菜品分类表（支持两级：一级分类 parent_id=0，二级分类 parent_id=一级id）
CREATE TABLE IF NOT EXISTS t_category (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(64)    NOT NULL,
    parent_id   BIGINT         NOT NULL DEFAULT 0,
    sort        INT            NOT NULL DEFAULT 0,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 菜品表
CREATE TABLE IF NOT EXISTS t_dish (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT         NOT NULL,
    name        VARCHAR(128)   NOT NULL,
    description VARCHAR(255)   DEFAULT '',
    price       DECIMAL(10,2)  NOT NULL,
    image       VARCHAR(512)   DEFAULT '',
    status      TINYINT        NOT NULL DEFAULT 1,
    sort        INT            NOT NULL DEFAULT 0,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 订单表
CREATE TABLE IF NOT EXISTS t_order (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no    VARCHAR(32)    NOT NULL UNIQUE,
    user_id     BIGINT         NOT NULL,
    table_no    VARCHAR(16)    DEFAULT '',
    remark      VARCHAR(255)   DEFAULT '',
    total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status      VARCHAR(16)    NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 订单明细表
CREATE TABLE IF NOT EXISTS t_order_item (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id    BIGINT         NOT NULL,
    dish_id     BIGINT         NOT NULL,
    dish_name   VARCHAR(128)   NOT NULL,
    price       DECIMAL(10,2)  NOT NULL,
    count       INT            NOT NULL DEFAULT 1,
    subtotal    DECIMAL(10,2)  NOT NULL
);

-- 充值记录表
CREATE TABLE IF NOT EXISTS t_recharge (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    amount      DECIMAL(10,2)  NOT NULL,
    gift        DECIMAL(10,2)  NOT NULL DEFAULT 0.00,
    remark      VARCHAR(128)   DEFAULT '',
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
);
