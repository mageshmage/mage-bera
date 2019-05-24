CREATE SEQUENCE public.user_extra_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 999999999
  START 1
  CACHE 1;
ALTER TABLE public.user_extra_id_seq
  OWNER TO kcwhzjjuuakldi;

-- DROP TABLE public.jhi_user;

CREATE TABLE public.user_extra
(
  id bigint NOT NULL DEFAULT nextval('user_extra_id_seq'::regclass),
  vendor_id bigint DEFAULT NULL,
  user_id bigint DEFAULT NULL,
  CONSTRAINT pk_user_extra PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.user_extra
  OWNER TO kcwhzjjuuakldi;

select * from jhi_user;

select nextval('city_id_seq'::regclass)

select nextval('jhi_persistent_audit_event_event_id_seq'::regclass);

select nextval('jhi_user_id_seq'::regclass);

select * from vendor;

select * from shipment_info;

select * from shipment_info_pod;

select * from shipment_tracking;

select * from shipment_info as si
inner join shipment_tracking as st on st.shipment_info_id = si.id
where si.id = 16

update shipment_info
set shipment_type_id = 1, carrier_details_id = null
where id = 4

select * from shiper_receiver_info;

select * from user_extra;

update shipment_info
set vendor_id = 1
where id = 2

update shiper_receiver_info
set shipment_info_id = 2
where id = 2

--update jhi_user
--set password_hash = '$2a$10$1cPu6y0y35mdMNTeyyzdNOxcL/EYErAwh0.2SemGVqB/q/QJ3AP8G'
--where id = 5

-- Table: public.series_table

CREATE TABLE public.series_table
(
  name character varying(50) NOT NULL,
  prefix character varying(60) NOT NULL,
  next_series bigint,
  created_by character varying(50) NOT NULL,
  created_date timestamp without time zone,
  last_modified_by character varying(50),
  last_modified_date timestamp without time zone
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.series_table
  OWNER TO kcwhzjjuuakldi;

INSERT INTO series_table VALUES ('GKCARGO','GKCARGO',10000,'ADMIN',NULL,NULL,NULL);

ALTER TABLE public.user_extra ADD COLUMN auto_consignment boolean;
ALTER TABLE public.user_extra ADD COLUMN prefix character varying(50);
ALTER TABLE public.user_extra ADD COLUMN expire_date timestamp without time zone;
ALTER TABLE public.user_extra ADD COLUMN created_by character varying(50);
ALTER TABLE public.user_extra ADD COLUMN created_date time without time zone;
ALTER TABLE public.user_extra ADD COLUMN last_modified_by character varying(50);
ALTER TABLE public.user_extra ADD COLUMN last_modified_date time without time zone;

ALTER TABLE public.shipment_info
  ADD COLUMN is_in_transit boolean;
ALTER TABLE public.shipment_info
  ADD COLUMN is_reached_nearest_hub boolean;
ALTER TABLE public.shipment_info
  ADD COLUMN is_out_for_delivery boolean;
ALTER TABLE public.shipment_info
  ADD COLUMN is_delivered boolean;

