--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:51:22 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 184 (class 1259 OID 1302532)
-- Name: ad_type; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE ad_type (
    id bigint NOT NULL,
    label character varying(255)
);


ALTER TABLE ad_type OWNER TO ysmjcnckagthbo;

--
-- TOC entry 183 (class 1259 OID 1302530)
-- Name: ad_type_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE ad_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ad_type_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2942 (class 0 OID 0)
-- Dependencies: 183
-- Name: ad_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE ad_type_id_seq OWNED BY ad_type.id;


--
-- TOC entry 2824 (class 2604 OID 1302535)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad_type ALTER COLUMN id SET DEFAULT nextval('ad_type_id_seq'::regclass);


--
-- TOC entry 2937 (class 0 OID 1302532)
-- Dependencies: 184
-- Data for Name: ad_type; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO ad_type VALUES (1, 'Perdu');
INSERT INTO ad_type VALUES (2, 'Trouvé');
INSERT INTO ad_type VALUES (3, 'Archivé');


--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 183
-- Name: ad_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('ad_type_id_seq', 1, false);


--
-- TOC entry 2826 (class 2606 OID 1302537)
-- Name: pk_ad_type; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad_type
    ADD CONSTRAINT pk_ad_type PRIMARY KEY (id);


-- Completed on 2016-04-16 13:51:26 CEST

--
-- PostgreSQL database dump complete
--

