--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.5.2

-- Started on 2016-04-16 13:51:51 CEST

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
-- TOC entry 192 (class 1259 OID 1302582)
-- Name: ad; Type: TABLE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE TABLE ad (
    id bigint NOT NULL,
    description character varying(255),
    recover boolean,
    date character varying(255),
    phone character varying(255),
    email character varying(255),
    adress character varying(255),
    complement character varying(255),
    postal_code character varying(255),
    city character varying(255),
    country character varying(255),
    longitude character varying(255),
    lattitude character varying(255),
    ad_type_id bigint,
    animal_id bigint,
    user_id bigint
);


ALTER TABLE ad OWNER TO ysmjcnckagthbo;

--
-- TOC entry 191 (class 1259 OID 1302580)
-- Name: ad_id_seq; Type: SEQUENCE; Schema: public; Owner: ysmjcnckagthbo
--

CREATE SEQUENCE ad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ad_id_seq OWNER TO ysmjcnckagthbo;

--
-- TOC entry 2945 (class 0 OID 0)
-- Dependencies: 191
-- Name: ad_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: ysmjcnckagthbo
--

ALTER SEQUENCE ad_id_seq OWNED BY ad.id;


--
-- TOC entry 2824 (class 2604 OID 1302585)
-- Name: id; Type: DEFAULT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad ALTER COLUMN id SET DEFAULT nextval('ad_id_seq'::regclass);


--
-- TOC entry 2940 (class 0 OID 1302582)
-- Dependencies: 192
-- Data for Name: ad; Type: TABLE DATA; Schema: public; Owner: ysmjcnckagthbo
--

INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1048, NULL, NULL, '28/03/2016', '0669553011', NULL, '18 Rue des Bouleaux', NULL, '33600', 'Pessac', 'France', '-0.688220699999988', '44.7973314', 2, 10, NULL);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1047, NULL, NULL, '02/04/2016', NULL, NULL, '391 Rue de Vaugirard', NULL, '75015', 'Paris', 'France', '2.292319799999973', '48.8347839', 2, 9, NULL);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1049, NULL, true, '29/03/2014', '0669553011', 'thibault@free.fr', '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 11, NULL);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1043, 'Chat perdu près du veto à Mérignac le 08/04 à 18H', NULL, '05/04/2016', NULL, NULL, '9 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6455985999999712', '44.8429936', 1, 3, 1027);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1044, 'Mon chat a sauté du balcon le 20/09/2015', NULL, '21/09/2015', NULL, NULL, '18 Avenue du Chut', 'Domaine du Valois', '33700', 'Mérignac', 'France', '-0.6597447000000329', '44.84913179999999', 3, 2, 1027);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1045, 'Mon chien a fait un trou dans le grillage pendant la nuit et s''est enfuit', NULL, '01/04/2016', NULL, NULL, '420 Rue du Grand Barrail', NULL, '33127', 'Saint-Jean-d''Illac', 'France', '-0.8493601000000126', '44.8068572', 1, 7, 1029);
INSERT INTO ad (id, description, recover, date, phone, email, adress, complement, postal_code, city, country, longitude, lattitude, ad_type_id, animal_id, user_id) VALUES (1046, 'Chat retrouvé avenue de la libération, je l''ai trouvé en pleine nuit et l''ai amené chez moi', true, '04/04/2016', NULL, NULL, '30 avenue de la libération', NULL, '33700', 'Mérignac', 'France', '-0.6447370999999293', '44.84533239999999', 2, 8, 1030);


--
-- TOC entry 2946 (class 0 OID 0)
-- Dependencies: 191
-- Name: ad_id_seq; Type: SEQUENCE SET; Schema: public; Owner: ysmjcnckagthbo
--

SELECT pg_catalog.setval('ad_id_seq', 1, false);


--
-- TOC entry 2826 (class 2606 OID 1302590)
-- Name: pk_ad; Type: CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad
    ADD CONSTRAINT pk_ad PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 1302591)
-- Name: fk_ad_adtype_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad
    ADD CONSTRAINT fk_ad_adtype_id FOREIGN KEY (ad_type_id) REFERENCES ad_type(id);


--
-- TOC entry 2828 (class 2606 OID 1302596)
-- Name: fk_ad_animal_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad
    ADD CONSTRAINT fk_ad_animal_id FOREIGN KEY (animal_id) REFERENCES animal(id);


--
-- TOC entry 2829 (class 2606 OID 1307697)
-- Name: fk_user_ad_id; Type: FK CONSTRAINT; Schema: public; Owner: ysmjcnckagthbo
--

ALTER TABLE ONLY ad
    ADD CONSTRAINT fk_user_ad_id FOREIGN KEY (user_id) REFERENCES jhi_user(id);


-- Completed on 2016-04-16 13:51:55 CEST

--
-- PostgreSQL database dump complete
--

