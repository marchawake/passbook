-- auto-generated definition
create table merchants
(
    id                   int unsigned auto_increment
        primary key,
    name                 varchar(64) collate utf8_bin  not null comment '商户名称',
    logo_url             varchar(256) collate utf8_bin not null comment '商户logo',
    business_license_url varchar(256) collate utf8_bin not null comment '商户营业执照',
    phone                varchar(64) collate utf8_bin  not null comment '商户电话',
    address              varchar(64) collate utf8_bin  not null comment '商户地址',
    is_audit             tinyint(1)                    not null comment '是否通过审核'
)
    charset = utf8;