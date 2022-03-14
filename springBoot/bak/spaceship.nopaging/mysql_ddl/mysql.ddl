Drop database zb_db;
CREATE database zb_db;
use zb_db;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(1, 'admin', '123456', '13918891675','mmc@163.com', '男', '管理员');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(2, 'lisi2', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(3, 'lisi3', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(4, 'lisi4', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(5, 'lisi5', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(6, 'lisi6', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(7, 'lisi7', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(8, 'lisi8', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(9, 'lisi9', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(10, 'lisi10', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(11, 'lisi11', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(12, 'lisi12', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(13, 'lisi13', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
insert into `user`(id, username, password, mobile, email,sex, nickname) VALUES(14, 'lisi14', '123456', '13918891675','mmc@163.com', 'm', 'lisi1');
