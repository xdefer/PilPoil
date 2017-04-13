--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:49:27 CEST

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
-- TOC entry 188 (class 1259 OID 1302548)
-- Name: breed; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE breed (
    id bigint NOT NULL,
    label character varying(255),
    animal_type_id bigint
);


ALTER TABLE breed OWNER TO ysmjcnckagthbo;

--
-- TOC entry 187 (class 1259 OID 1302546)
-- Name: breed_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE breed_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE breed_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2943 (class 0 OID 0)
-- Dependencies: 187
-- Name: breed_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE breed_id_seq OWNED BY breed.id;


--
-- TOC entry 2824 (class 2604 OID 1302551)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY breed ALTER COLUMN id SET DEFAULT nextval('breed_id_seq'::regclass);


--
-- TOC entry 2938 (class 0 OID 1302548)
-- Dependencies: 188
-- Data for Name: breed; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO breed VALUES (1014, 'Berger Allemand', 2);
INSERT INTO breed VALUES (1015, 'Carlin', 2);
INSERT INTO breed VALUES (1016, 'Golden Retriever', 2);
INSERT INTO breed VALUES (1017, 'Cavalier King Charles', 2);
INSERT INTO breed VALUES (1018, 'Akita Inu', 2);
INSERT INTO breed VALUES (1020, 'Siamois', 1);
INSERT INTO breed VALUES (1021, 'Bengal', 1);
INSERT INTO breed VALUES (1019, 'Sacré de Birmanie', 1);
INSERT INTO breed VALUES (1022, 'Maine coon', 1);
INSERT INTO breed VALUES (1023, 'Persan', 1);
INSERT INTO breed VALUES (1024, 'Norvégien', 1);
INSERT INTO breed VALUES (1025, 'Européen', 1);


--
-- TOC entry 2944 (class 0 OID 0)
-- Dependencies: 187
-- Name: breed_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('breed_id_seq', 1, false);


--
-- TOC entry 2826 (class 2606 OID 1302553)
-- Name: pk_breed; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY breed
    ADD CONSTRAINT pk_breed PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 1302554)
-- Name: fk_breed_animaltype_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY breed
    ADD CONSTRAINT fk_breed_animaltype_id FOREIGN KEY (animal_type_id) REFERENCES animal_type(id);


-- Completed on 2016-04-16 13:49:31 CEST

--
-- PostgreSQL database dump complete
--

