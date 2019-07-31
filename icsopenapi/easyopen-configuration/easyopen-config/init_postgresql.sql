DROP TABLE IF EXISTS admin_user;
CREATE TABLE admin_user (
  id serial NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT admin_user_pk PRIMARY KEY (id)
)WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.admin_user IS '全局配置表';
COMMENT ON COLUMN public.admin_user.username IS '用户名';
COMMENT ON COLUMN public.admin_user.password IS '密码';

insert  into admin_user(username,password,gmt_create,gmt_update)
values ('admin','14e1b600b1fd579f47433b88e8d85291','2018-07-13 15:20:05','2018-07-13 15:20:07');

DROP TABLE IF EXISTS public.global_config;
CREATE TABLE public.global_config (
	id serial NOT NULL,
	key_name varchar(64) NOT NULL,
	field_name varchar(64) NOT NULL,
	field_value varchar(100) NOT NULL,
	remark varchar(100) NULL,
	gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
	gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
	CONSTRAINT global_config_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.global_config IS '全局配置表';
COMMENT ON COLUMN public.global_config.remark IS '描述';

insert  into global_config(key_name,field_name,field_value,remark,gmt_create,gmt_update)
values ('limit','default_limit_type','LIMIT','默认漏桶策略（不可删除）','2018-07-12 19:50:58','2018-07-13 15:33:48'),
('limit','default_limit_count','50','默认每秒可处理请求数（不可删除）','2018-07-12 19:53:34','2018-07-13 15:33:51'),
('limit','default_token_bucket_count','50','默认令牌桶个数（不可删除）','2018-07-12 19:54:11','2018-07-13 15:33:53');

DROP TABLE IF EXISTS public.limit_app_config;
CREATE TABLE limit_app_config (
  id serial NOT NULL,
  api_id bigint NOT NULL,
  app varchar(100) NOT NULL,
  name varchar(64) NOT NULL,
  version varchar(64) NOT NULL,
  limit_type varchar(20) NOT NULL,
  limit_count int DEFAULT 0,
  limit_code varchar(20) DEFAULT NULL,
  limit_msg varchar(100) DEFAULT NULL,
  token_bucket_count int NOT NULL DEFAULT 0,
  status int NOT NULL DEFAULT 0,
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT limit_app_config_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.limit_app_config IS '限流配置';
comment on column limit_app_config.app is 'app信息';
comment on column limit_app_config.api_id is 'perm_api_info.id';
comment on column limit_app_config.limit_type is '限流策略';
comment on column limit_app_config.limit_count is '每秒可处理请求数';
comment on column limit_app_config.token_bucket_count is '令牌桶容量';
comment on column limit_app_config.status is '1:开启，0关闭';

CREATE UNIQUE INDEX limit_app_config_app_id_idx ON public.limit_app_config (api_id);

insert  into limit_app_config(api_id,app,name,version,limit_type,limit_count,limit_code,limit_msg,token_bucket_count,status,gmt_create,gmt_update)
values (548,'app1','manager.session.get','','TOKEN_BUCKET',50,NULL,NULL,50,1,'2018-07-13 09:51:36','2018-07-13 11:29:53'),
(549,'app1','goods.add','','LIMIT',50,'sdfgsdfg','asfasdfsdf',50,1,'2018-07-13 09:52:09','2018-07-16 09:48:04'),
(550,'app1','doc.param.6','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-16 09:48:09'),
(551,'app1','param.type.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-13 11:31:33'),
(552,'app1','doc.param.5','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:17:04','2018-07-13 11:34:16'),
(553,'app1','param.type.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:03','2018-07-16 09:48:36'),
(554,'app1','doc.param.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:34:33'),
(555,'app1','doc.param.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(556,'app1','doc.param.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(557,'app1','doc.param.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(558,'app1','wrapResult.false','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(559,'app1','session.set','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(560,'app1','doc.result.7','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(561,'app1','doc.result.4','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(562,'app1','doc.result.3','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(563,'app1','goods.pageinfo','2.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(564,'app1','doc.result.6','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(565,'app1','doc.result.5','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(566,'app1','doc.result.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(567,'app1','doc.result.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,0,'2018-07-13 11:29:53','2018-07-13 11:34:16'),
(568,'app1','goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(569,'app1','hello','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(570,'app1','user.goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(571,'app1','userjwt.goods.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(572,'app1','file.upload2','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(573,'app1','goods.list','2.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(574,'app1','goods.pageinfo','1.0','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(575,'app1','file.upload3','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(576,'app1','file.upload','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(577,'app1','session.get','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(578,'app1','param.type.1','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(579,'app1','param.type.2','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53'),
(580,'app1','userlock.test','','TOKEN_BUCKET',NULL,NULL,NULL,50,1,'2018-07-13 11:29:53','2018-07-13 11:29:53');


DROP TABLE IF EXISTS public.perm_api_info;
CREATE TABLE perm_api_info (
  id serial NOT NULL,
  name varchar(100) NOT NULL default '0',
  version varchar(50) NOT NULL,
  app varchar(50) NOT NULL,
  status int NOT NULL DEFAULT 0,
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT perm_api_info_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.perm_api_info IS '接口信息表';
comment on column perm_api_info.name is '接口名';
comment on column perm_api_info.version is '版本号';
comment on column perm_api_info.app is '所属app';
comment on column perm_api_info.status is '0：使用中，1：未使用';

insert  into perm_api_info(name,version,app,status,gmt_create,gmt_update)
values
('manager.session.get','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('goods.add','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.6','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('param.type.3','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.5','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('param.type.4','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.4','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.3','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.2','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.param.1','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('wrapResult.false','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('session.set','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.7','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.4','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.3','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('goods.pageinfo','2.0','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.6','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.5','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.2','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('doc.result.1','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('goods.get','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('hello','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('user.goods.get','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('userjwt.goods.get','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('file.upload2','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('goods.list','2.0','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('goods.pageinfo','1.0','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('file.upload3','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('file.upload','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('session.get','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('param.type.1','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('param.type.2','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59'),
('userlock.test','','app1',0,'2018-07-09 09:43:59','2018-07-09 09:43:59');

DROP TABLE IF EXISTS public.perm_client;
CREATE TABLE perm_client (
  id serial NOT NULL,
  app_key varchar(100) NOT NULL,
  secret varchar(200) NOT NULL,
  pub_key text,
  pri_key text,
  app varchar(50) NOT NULL,
  status int NOT NULL DEFAULT '0',
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT perm_client_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.perm_client IS 'app信息表';
comment on column perm_client.app_key is 'appKey';
comment on column perm_client.secret is 'secret';
comment on column perm_client.pub_key is '公钥';
comment on column perm_client.pri_key is '私钥';
comment on column perm_client.app is 'app名称';
comment on column perm_client.status is '0启用，1禁用';

CREATE UNIQUE INDEX perm_client_app_key_idx ON public.perm_client (app_key,app);

insert  into perm_client(app_key,secret,pub_key,pri_key,app,status,gmt_create,gmt_update)
values ('admin','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 16:25:20'),
('test1','1234561','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 16:25:11'),
('aaaa','123456',NULL,NULL,'app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('bbbb','123456',NULL,NULL,'app1',0,'2018-01-22 21:03:17','2018-07-09 14:24:23'),
('cccc','123456',NULL,NULL,'app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('ddd','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('eeee','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('ffff','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('ggggg','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('hhhhhh','123456','MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDhlWrRQXJeI09CyCB/L2Yxcbh2IMMSxwB+V99Y\r\nt+ZWeVhcZUPRRcM79ThLSEpkd9QLX/A+ZleI1K9RssbJhxZ7t9XuJNXgZlBzIF5yVmgZl7bRR767\r\n+XZxnf8jm7KZ2rSdVwyCGvl+1CBlEYLRnv9sB1ZpkzBCj12gBinYMj5xZQIDAQAB','MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOGVatFBcl4jT0LIIH8vZjFxuHYg\r\nwxLHAH5X31i35lZ5WFxlQ9FFwzv1OEtISmR31Atf8D5mV4jUr1GyxsmHFnu31e4k1eBmUHMgXnJW\r\naBmXttFHvrv5dnGd/yObspnatJ1XDIIa+X7UIGURgtGe/2wHVmmTMEKPXaAGKdgyPnFlAgMBAAEC\r\ngYEAsbfUSn0kC/QHapZdu7Vs7kEoULAo3u82fVLfG3buGWxJ56jDz+gFEoRzUCPor9QTks6HZ7Ga\r\n/qqIYHXW1Ef/tgdUUeldjrKtmpc9H/U8+KqiryXasu9FpCTlMM4T/mZoowhJPpkG/7jNsoNizHN7\r\nXN1d3RQBdLr72Ip+U8Git2ECQQD4vuaLccLWjkTlFzLaV3wfrUCsyAkaRKCmGFNMq7a5ubN4QtTi\r\nfPSQdmoDGVD1F9Q+nBtXeSWbt047qvaqfII5AkEA6CmXm8YPh0xYntn94UxO31saw729P+hXuzNv\r\nabusSJOdrA2x1Jqz01AKjOpohUENl/6NyfXPxjRRCSEI46B4jQJAbOxbVBCauw1Nievgrs/EYLKj\r\nMYXexovqtRDN2TMQLr/soOrTAeKpzWCtB3JcixbGMCx3pJQ+LbPVJDe3D+y5sQJAAe1WdNSQDG91\r\nzNvCX7xiazg2YKmSiJVFJSioJBiKtY+EH4l9kGY4V+iyLblEZNbFZh2Wz7ZaoyqMAadki38pgQJA\r\nDVXVMV5X+vlfi338IGefp2DdROSPJr8vusp6b/VKEJIxxzESIQHqg8NkJmf8gj6sy3ijlR8p9Wrk\r\nfh5WjsD43w==','app1',0,'2018-01-22 21:03:17','2018-07-09 09:33:15'),
('iiiii','123456','','','app1',0,'2018-01-22 21:03:17','2018-07-09 16:32:57'),
('adm','123','','','app1',0,'2018-07-09 14:00:40','2018-07-09 16:32:40'),
('123','123','','','app1',0,'2018-07-09 15:53:54','2018-07-09 16:32:30');

DROP TABLE IF EXISTS public.perm_client_role;
CREATE TABLE perm_client_role (
  id serial NOT NULL,
  client_id bigint NOT NULL,
  role_code varchar(50) NOT NULL,
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT perm_client_role_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.perm_client_role IS '客户端角色';
comment on column perm_client_role.client_id is '客户端id';
comment on column perm_client_role.role_code is '角色code';

CREATE UNIQUE INDEX perm_client_role_idx ON public.perm_client_role (client_id,role_code);

insert  into perm_client_role(client_id,role_code,gmt_create,gmt_update)
values (2,'normal','2018-07-09 16:25:12','2018-07-09 16:25:12'),
(1,'pay','2018-07-09 16:25:20','2018-07-09 16:25:20'),
(14,'normal','2018-07-09 16:32:30','2018-07-09 16:32:30'),
(13,'normal','2018-07-09 16:32:40','2018-07-09 16:32:40'),
(12,'normal','2018-07-09 16:32:57','2018-07-09 16:32:57');

DROP TABLE IF EXISTS public.perm_role;
CREATE TABLE perm_role (
  id serial NOT NULL,
  role_code varchar(50) NOT NULL,
  description varchar(50) NOT NULL,
  gmt_create timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT perm_role_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.perm_role IS '角色表';
comment on column perm_role.role_code is '角色代码';
comment on column perm_role.description is '角色描述';

CREATE UNIQUE INDEX perm_role_idx ON public.perm_role (role_code);

insert  into perm_role(role_code,description,gmt_create,gmt_update)
values ('normal','普通ISV','2018-01-23 09:21:59','2018-01-23 09:22:02'),
('pay','付费ISV','2018-07-09 15:04:26','2018-07-09 15:04:27');

DROP TABLE IF EXISTS public.perm_role_permission;
CREATE TABLE perm_role_permission (
  id serial NOT NULL,
  role_code varchar(50) NOT NULL,
  api_id bigint NOT NULL,
  app varchar(50) NOT NULL,
  gmt_create timestamp  NULL DEFAULT LOCALTIMESTAMP(0),
  gmt_update timestamp NOT NULL DEFAULT LOCALTIMESTAMP(0),
  CONSTRAINT perm_role_permission_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
);
COMMENT ON TABLE public.perm_role_permission IS '角色权限表';
comment on column perm_role_permission.role_code is '角色代码';
comment on column perm_role_permission.api_id is 'api_id';
comment on column perm_role_permission.app is 'app名称';

CREATE UNIQUE INDEX perm_role_permission_idx ON public.perm_role_permission (app,role_code,api_id);

insert  into perm_role_permission(role_code,api_id,app,gmt_create,gmt_update) 
values ('normal',581,'app2','2018-07-24 15:30:50','2018-07-24 15:30:50'),
('normal',554,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',552,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',550,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',567,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',566,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',562,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',561,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',565,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',564,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',560,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',576,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',572,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',575,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',549,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',568,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',573,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',574,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',563,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',569,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',548,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',578,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',579,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',577,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',559,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',570,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',571,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',580,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50'),
('normal',558,'app1','2018-07-24 15:50:50','2018-07-24 15:50:50');
