

select * from jhi_user;

--update jhi_user
--set password_hash = '$2a$10$1cPu6y0y35mdMNTeyyzdNOxcL/EYErAwh0.2SemGVqB/q/QJ3AP8G'
--where id = 6;

select * from jhi_authority;

select * from jhi_user_authority;

select * from user_extra;

update user_extra
set id = 0
where vendor_id = 1

select * from shipment_info order by id desc;

select * from shiper_receiver_info order by id desc;

select * from shipment_tracking order by id desc;

select * from shipment_type;

select * from vendor;