--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:42:57 CEST

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
-- TOC entry 177 (class 1259 OID 1302471)
-- Name: jhi_user; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE jhi_user (
    id bigint NOT NULL,
    login character varying(50) NOT NULL,
    password_hash character varying(60),
    first_name character varying(50),
    last_name character varying(50),
    email character varying(100),
    activated boolean NOT NULL,
    lang_key character varying(5),
    activation_key character varying(20),
    reset_key character varying(20),
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone NOT NULL,
    reset_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone,
    phone character varying(10) DEFAULT ''::character varying,
    distance integer DEFAULT 20
);


ALTER TABLE jhi_user OWNER TO ysmjcnckagthbo;

--
-- TOC entry 176 (class 1259 OID 1302469)
-- Name: jhi_user_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE jhi_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE jhi_user_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2950 (class 0 OID 0)
-- Dependencies: 176
-- Name: jhi_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE jhi_user_id_seq OWNED BY jhi_user.id;


--
-- TOC entry 2824 (class 2604 OID 1302474)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user ALTER COLUMN id SET DEFAULT nextval('jhi_user_id_seq'::regclass);


--
-- TOC entry 2945 (class 0 OID 1302471)
-- Dependencies: 177
-- Data for Name: jhi_user; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO jhi_user VALUES (1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', true, 'en', NULL, NULL, 'system', '2016-04-14 17:41:29.610269', NULL, NULL, NULL, '', 20);
INSERT INTO jhi_user VALUES (2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', true, 'en', NULL, NULL, 'system', '2016-04-14 17:41:29.610269', NULL, NULL, NULL, '', 20);
INSERT INTO jhi_user VALUES (3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', true, 'en', NULL, NULL, 'system', '2016-04-14 17:41:29.610269', NULL, NULL, NULL, '', 20);
INSERT INTO jhi_user VALUES (4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', true, 'en', NULL, NULL, 'system', '2016-04-14 17:41:29.610269', NULL, NULL, NULL, '', 20);
INSERT INTO jhi_user VALUES (1026, 'yanick', '$2a$10$rV8htLQiqw3AlGfc.NNG0..G8hE49ERQa48g76pyiSWK4M.1q8.R2', NULL, NULL, 'yanick.servant@free.fr', true, 'en', '75042060379125176905', NULL, 'anonymousUser', '2016-04-15 14:00:41.024', NULL, 'anonymousUser', '2016-04-15 14:00:41.024', NULL, 0);
INSERT INTO jhi_user VALUES (1027, 'xavier', '$2a$10$iC3QBtJrWCqCfiLOx7oIweYiJK/cA.oAH9PFTQRWn37pLT/8nyENe', NULL, NULL, 'xavier@gmail.com', true, 'en', '52135339116224135831', NULL, 'anonymousUser', '2016-04-15 14:01:01.274', NULL, 'anonymousUser', '2016-04-15 14:01:01.274', NULL, 0);
INSERT INTO jhi_user VALUES (1028, 'thomas', '$2a$10$MyZCKR2UBAgW8hlKlMufUuCmNJCzQKDH.ViUNIq7IJgZ3AIpXHNAi', NULL, NULL, 'thomas@gmail.com', true, 'en', '47142519592871039444', NULL, 'anonymousUser', '2016-04-15 14:02:11.361', NULL, 'anonymousUser', '2016-04-15 14:02:11.361', NULL, 0);
INSERT INTO jhi_user VALUES (1029, 'lucas', '$2a$10$wesU/GJEwhZIXPaMhiPxZuW3vfatw6EgR9whqrC27nO4UPPW4l80K', NULL, NULL, 'lucas@gmail.com', true, 'en', '50616520180678462584', NULL, 'anonymousUser', '2016-04-15 14:03:19.823', NULL, 'anonymousUser', '2016-04-15 14:03:19.823', NULL, 0);
INSERT INTO jhi_user VALUES (1030, 'marion', '$2a$10$ZvefVZYw8IKsZwJqDse10.TzusJkTV5a3caUa0Ea6tnuxXe1OkzTi', NULL, NULL, 'marion@gmail.com', true, 'en', '15618376028285145934', NULL, 'anonymousUser', '2016-04-15 14:03:43.396', NULL, 'anonymousUser', '2016-04-15 14:03:43.396', NULL, 0);
INSERT INTO jhi_user VALUES (1059, 'alexa.peltant@gmail.com', '$2a$10$U/2vtN5AHRnH0myLD1J3M.12m/T/XLE2WOQXFOEdAvPZWkJaYTrGW', 'Alexa', NULL, 'alexa.peltant@gmail.com', true, 'fr', '68397549280236974389', NULL, 'anonymousUser', '2016-04-16 08:23:07.888', NULL, 'anonymousUser', '2016-04-16 08:23:07.888', NULL, 10);


--
-- TOC entry 2951 (class 0 OID 0)
-- Dependencies: 176
-- Name: jhi_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('jhi_user_id_seq', 1, false);


--
-- TOC entry 2830 (class 2606 OID 1302481)
-- Name: jhi_user_email_key; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT jhi_user_email_key UNIQUE (email);


--
-- TOC entry 2832 (class 2606 OID 1302479)
-- Name: jhi_user_login_key; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT jhi_user_login_key UNIQUE (login);


--
-- TOC entry 2834 (class 2606 OID 1302477)
-- Name: pk_jhi_user; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY jhi_user
    ADD CONSTRAINT pk_jhi_user PRIMARY KEY (id);


--
-- TOC entry 2827 (class 1259 OID 1302483)
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: ysmjcnckagthbo
--

CREATE UNIQUE INDEX idx_user_email ON jhi_user USING btree (email);


--
-- TOC entry 2828 (class 1259 OID 1302482)
-- Name: idx_user_login; Type: INDEX; Schema: public; Owner: ysmjcnckagthbo
--

CREATE UNIQUE INDEX idx_user_login ON jhi_user USING btree (login);


-- Completed on 2016-04-16 13:43:02 CEST

--
-- PostgreSQL database dump complete
--

