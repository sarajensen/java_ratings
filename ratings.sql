--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: course_ratings; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE course_ratings (
    id integer NOT NULL,
    course_id integer,
    rating integer
);


ALTER TABLE course_ratings OWNER TO "Guest";

--
-- Name: course_ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE course_ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_ratings_id_seq OWNER TO "Guest";

--
-- Name: course_ratings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE course_ratings_id_seq OWNED BY course_ratings.id;


--
-- Name: courses; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE courses (
    id integer NOT NULL,
    name character varying,
    subject character varying,
    skill_level integer,
    schoolid integer
);


ALTER TABLE courses OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_id_seq OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE courses_id_seq OWNED BY courses.id;


--
-- Name: school_ratings; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE school_ratings (
    id integer NOT NULL,
    school_id integer,
    culture_rating integer,
    value_rating integer,
    ux_rating integer
);


ALTER TABLE school_ratings OWNER TO "Guest";

--
-- Name: school_ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE school_ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE school_ratings_id_seq OWNER TO "Guest";

--
-- Name: school_ratings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE school_ratings_id_seq OWNED BY school_ratings.id;


--
-- Name: schools; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE schools (
    id integer NOT NULL,
    name character varying,
    offline boolean,
    coding_only boolean,
    paid boolean,
    url character varying
);


ALTER TABLE schools OWNER TO "Guest";

--
-- Name: schools_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE schools_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE schools_id_seq OWNER TO "Guest";

--
-- Name: schools_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE schools_id_seq OWNED BY schools.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY course_ratings ALTER COLUMN id SET DEFAULT nextval('course_ratings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY courses ALTER COLUMN id SET DEFAULT nextval('courses_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY school_ratings ALTER COLUMN id SET DEFAULT nextval('school_ratings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY schools ALTER COLUMN id SET DEFAULT nextval('schools_id_seq'::regclass);


--
-- Data for Name: course_ratings; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY course_ratings (id, course_id, rating) FROM stdin;
6	10	3
\.


--
-- Name: course_ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('course_ratings_id_seq', 6, true);


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY courses (id, name, subject, skill_level, schoolid) FROM stdin;
10	Java Basics	Back End	1	5
\.


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_id_seq', 10, true);


--
-- Data for Name: school_ratings; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY school_ratings (id, school_id, culture_rating, value_rating, ux_rating) FROM stdin;
4	5	3	4	5
\.


--
-- Name: school_ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('school_ratings_id_seq', 4, true);


--
-- Data for Name: schools; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY schools (id, name, offline, coding_only, paid, url) FROM stdin;
5	Team Treehouse	f	f	t	https://teamtreehouse.com/
\.


--
-- Name: schools_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('schools_id_seq', 5, true);


--
-- Name: course_ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY course_ratings
    ADD CONSTRAINT course_ratings_pkey PRIMARY KEY (id);


--
-- Name: courses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: school_ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY school_ratings
    ADD CONSTRAINT school_ratings_pkey PRIMARY KEY (id);


--
-- Name: schools_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY schools
    ADD CONSTRAINT schools_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

