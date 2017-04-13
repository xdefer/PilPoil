--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:48:26 CEST

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
-- TOC entry 179 (class 1259 OID 1302489)
-- Name: jhi_user_authority; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE jhi_user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);


ALTER TABLE jhi_user_authority OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2937 (class 0 OID 1302489)
-- Dependencies: 179
-- Data for Name: jhi_user_authority; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO jhi_user_authority VALUES (1, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority VALUES (1, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (3, 'ROLE_ADMIN');
INSERT INTO jhi_user_authority VALUES (3, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (4, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1026, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1027, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1028, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1029, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1030, 'ROLE_USER');
INSERT INTO jhi_user_authority VALUES (1059, 'ROLE_USER');


--
-- TOC entry 2825 (class 2606 OID 1302493)
-- Name: jhi_user_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT jhi_user_authority_pkey PRIMARY KEY (user_id, authority_name);


--
-- TOC entry 2826 (class 2606 OID 1302494)
-- Name: fk_authority_name; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_authority_name FOREIGN KEY (authority_name) REFERENCES jhi_authority(name);


--
-- TOC entry 2827 (class 2606 OID 1302499)
-- Name: fk_user_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user_authority
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);


-- Completed on 2016-04-16 13:48:29 CEST

--
-- PostgreSQL database dump complete
--

