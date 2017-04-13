--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:50:18 CEST

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
-- TOC entry 186 (class 1259 OID 1302540)
-- Name: animal_type; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE animal_type (
    id bigint NOT NULL,
    label character varying(255)
);


ALTER TABLE animal_type OWNER TO ysmjcnckagthbo;

--
-- TOC entry 185 (class 1259 OID 1302538)
-- Name: animal_type_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE animal_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animal_type_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2942 (class 0 OID 0)
-- Dependencies: 185
-- Name: animal_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE animal_type_id_seq OWNED BY animal_type.id;


--
-- TOC entry 2824 (class 2604 OID 1302543)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal_type ALTER COLUMN id SET DEFAULT nextval('animal_type_id_seq'::regclass);


--
-- TOC entry 2937 (class 0 OID 1302540)
-- Dependencies: 186
-- Data for Name: animal_type; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO animal_type VALUES (1, 'Chat');
INSERT INTO animal_type VALUES (2, 'Chien');
INSERT INTO animal_type VALUES (3, 'Autre');
INSERT INTO animal_type VALUES (4, 'Poisson');
INSERT INTO animal_type VALUES (5, 'Lapin');


--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 185
-- Name: animal_type_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('animal_type_id_seq', 1, false);


--
-- TOC entry 2826 (class 2606 OID 1302545)
-- Name: pk_animal_type; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal_type
    ADD CONSTRAINT pk_animal_type PRIMARY KEY (id);


-- Completed on 2016-04-16 13:50:22 CEST

--
-- PostgreSQL database dump complete
--

