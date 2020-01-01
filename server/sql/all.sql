CREATE TABLE `ray`.`t_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT current_timestamp(0),
  `up_time` datetime(0) NOT NULL DEFAULT current_timestamp(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



select tread.id,tread.name,tread.item_id,ii.name from (select s.id, s.name,(case  when r.iid is null then (select i.id from t_item i   where i.subject_id = s.id order by i.name asc limit 1)  else r.iid end ) as item_id from t_subject s  left join t_read r on s.id = r.iid and r.uid =3 order by s.name asc LIMIT 30 )  tread inner join t_item ii on tread.item_id = ii.id;


select tread.id,tread.title,tread.item_id,ii.title from (select s.id, s.title,(case  when r.iid is null then (select i.id from t_item i   where i.subject_id = s.id order by i.title asc limit 1)  else r.iid end ) as item_id from t_subject s  left join t_read r on s.id = r.iid and r.uid =3 order by s.title asc LIMIT 30 )  tread inner join t_item ii on tread.item_id = ii.id;