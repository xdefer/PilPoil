--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:50:48 CEST

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
-- TOC entry 190 (class 1259 OID 1302561)
-- Name: animal; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE animal (
    id bigint NOT NULL,
    name character varying(255),
    age character varying(255),
    sexe character varying(255),
    tatoo character varying(255),
    chip character varying(255),
    photo character varying(255),
    colors character varying(255),
    animal_type_id bigint,
    breed_id bigint,
    user_id bigint
);


ALTER TABLE animal OWNER TO ysmjcnckagthbo;

--
-- TOC entry 189 (class 1259 OID 1302559)
-- Name: animal_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE animal_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE animal_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2945 (class 0 OID 0)
-- Dependencies: 189
-- Name: animal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE animal_id_seq OWNED BY animal.id;


--
-- TOC entry 2824 (class 2604 OID 1302564)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal ALTER COLUMN id SET DEFAULT nextval('animal_id_seq'::regclass);


--
-- TOC entry 2940 (class 0 OID 1302561)
-- Dependencies: 190
-- Data for Name: animal; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO animal VALUES (9, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL);
INSERT INTO animal VALUES (10, NULL, 'Jeune', 'Male', NULL, NULL, NULL, NULL, 1, 1021, NULL);
INSERT INTO animal VALUES (11, NULL, 'Adulte', 'Male', 'TATEE125ED', NULL, 'http://soocurious.com/fr/wp-content/uploads/2013/12/10-chats-au-pelage-etonnant13.jpg', NULL, 1, NULL, NULL);
INSERT INTO animal VALUES (8, NULL, NULL, 'Femelle', NULL, NULL, 'http://www.slate.fr/sites/default/files/imagecache/1090x500/catvsmouse.jpg', NULL, 1, 1020, NULL);
INSERT INTO animal VALUES (1, 'Lumia', 'Jeune', 'Male', 'TATOO123ED', NULL, 'http://static.wamiz.fr/images/news/medium/chat-tabby.jpg', 'Tigré gris noir', 1, 1025, 1026);
INSERT INTO animal VALUES (2, 'Gollum', 'Jeune', 'Male', 'TATOO145TG', NULL, NULL, 'Beige, blanc', 1, 1020, 1027);
INSERT INTO animal VALUES (3, 'Marie', 'Adulte', 'Femelle', 'TATOO156TY', NULL, NULL, 'Beige, blanc', 1, 1020, 1027);
INSERT INTO animal VALUES (4, 'Pascal', NULL, NULL, NULL, NULL, NULL, 'Bleu', 4, NULL, 1027);
INSERT INTO animal VALUES (5, 'LeChien', 'Adulte', 'Male', NULL, NULL, 'http://static.pratique.fr/images/unsized/ed/eduquer-chien.jpg', NULL, 2, 1018, 1028);
INSERT INTO animal VALUES (6, 'ChatChat', NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, 1029);
INSERT INTO animal VALUES (7, 'ChienChien', NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, 1029);


--
-- TOC entry 2946 (class 0 OID 0)
-- Dependencies: 189
-- Name: animal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('animal_id_seq', 1, false);


--
-- TOC entry 2826 (class 2606 OID 1302569)
-- Name: pk_animal; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT pk_animal PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 1302570)
-- Name: fk_animal_animaltype_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT fk_animal_animaltype_id FOREIGN KEY (animal_type_id) REFERENCES animal_type(id);


--
-- TOC entry 2828 (class 2606 OID 1302575)
-- Name: fk_animal_breed_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT fk_animal_breed_id FOREIGN KEY (breed_id) REFERENCES breed(id);


--
-- TOC entry 2829 (class 2606 OID 1307692)
-- Name: fk_user_animal_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY animal
    ADD CONSTRAINT fk_user_animal_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);


-- Completed on 2016-04-16 13:50:52 CEST

--
-- PostgreSQL database dump complete
--

