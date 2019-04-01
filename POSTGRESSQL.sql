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

select nextval('jhi_persistent_audit_event_event_id_seq'::regclass)