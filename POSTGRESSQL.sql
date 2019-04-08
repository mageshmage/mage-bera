CREATE SEQUENCE public.user_extra_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 1
  START 1
  CACHE 1;
ALTER TABLE public.user_extra_id_seq
  OWNER TO postgres;

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
  OWNER TO postgres;

select * from jhi_user;

select nextval('city_id_seq'::regclass)

select nextval('jhi_persistent_audit_event_event_id_seq'::regclass);

select * from vendor;

select * from shipment_info;

select * from shiper_receiver_info;

select * from user_extra;

update shipment_info
set vendor_id = 1
where id = 2

update shiper_receiver_info
set shipment_info_id = 2
where id = 2

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
  OWNER TO postgres;

INSERT INTO series_table VALUES ('GKCARGO','GKCARGO',10000,'ADMIN',NULL,NULL,NULL);

ALTER TABLE public.user_extra ADD COLUMN auto_consignment boolean NOT NULL DEFAULT false;
ALTER TABLE public.user_extra ADD COLUMN prefix character varying(50);
ALTER TABLE public.user_extra ADD COLUMN expire_date timestamp without time zone;
ALTER TABLE public.user_extra ADD COLUMN created_by character varying(50) NOT NULL;
ALTER TABLE public.user_extra ADD COLUMN created_date time without time zone;
ALTER TABLE public.user_extra ADD COLUMN last_modified_by character varying(50);
ALTER TABLE public.user_extra ADD COLUMN last_modified_date time without time zone;

