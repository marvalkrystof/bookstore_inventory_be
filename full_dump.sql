--
-- PostgreSQL database cluster dump
--

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'book_store_app') THEN
      CREATE ROLE book_store_app;
   END IF;
END $$;
ALTER ROLE book_store_app WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION NOBYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:6eaang6WiYAcAbf7KKOk1Q==$KN4t+bMuh67tobLmyb4AlZEnhbJ64/x6SBXWZ7Fe6Eo=:aoEh1We3UX8uBvAdvIQ26z6UeSRFn5nBfBYxlGhVyiU=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- Database "book_store_information" dump
--

--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: book_store_information; Type: DATABASE; Schema: -; Owner: postgres
--


ALTER DATABASE book_store_information OWNER TO postgres;

\connect book_store_information

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: book_store_information; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA book_store_information;


ALTER SCHEMA book_store_information OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: authors; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.authors (
    id integer NOT NULL,
    name character varying(100) NOT NULL
);


ALTER TABLE book_store_information.authors OWNER TO postgres;

--
-- Name: authors_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.authors_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.authors_id_seq OWNER TO postgres;

--
-- Name: authors_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.authors_id_seq OWNED BY book_store_information.authors.id;


--
-- Name: books; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.books (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    author_id integer,
    genre_id integer,
    price numeric(10,2) NOT NULL
);


ALTER TABLE book_store_information.books OWNER TO postgres;

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.books_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.books_id_seq OWNER TO postgres;

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.books_id_seq OWNED BY book_store_information.books.id;


--
-- Name: genres; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.genres (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE book_store_information.genres OWNER TO postgres;

--
-- Name: genres_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.genres_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.genres_id_seq OWNER TO postgres;

--
-- Name: genres_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.genres_id_seq OWNED BY book_store_information.genres.id;


--
-- Name: roles; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.roles (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE book_store_information.roles OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.roles_id_seq OWNER TO postgres;

--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.roles_id_seq OWNED BY book_store_information.roles.id;


--
-- Name: userroles; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.userroles (
    id integer NOT NULL,
    user_id integer,
    role_id integer
);


ALTER TABLE book_store_information.userroles OWNER TO postgres;

--
-- Name: userroles_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.userroles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.userroles_id_seq OWNER TO postgres;

--
-- Name: userroles_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.userroles_id_seq OWNED BY book_store_information.userroles.id;


--
-- Name: users; Type: TABLE; Schema: book_store_information; Owner: postgres
--

CREATE TABLE book_store_information.users (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password_hash text NOT NULL
);


ALTER TABLE book_store_information.users OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: book_store_information; Owner: postgres
--

CREATE SEQUENCE book_store_information.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE book_store_information.users_id_seq OWNER TO postgres;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: book_store_information; Owner: postgres
--

ALTER SEQUENCE book_store_information.users_id_seq OWNED BY book_store_information.users.id;


--
-- Name: authors id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.authors ALTER COLUMN id SET DEFAULT nextval('book_store_information.authors_id_seq'::regclass);


--
-- Name: books id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.books ALTER COLUMN id SET DEFAULT nextval('book_store_information.books_id_seq'::regclass);


--
-- Name: genres id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.genres ALTER COLUMN id SET DEFAULT nextval('book_store_information.genres_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.roles ALTER COLUMN id SET DEFAULT nextval('book_store_information.roles_id_seq'::regclass);


--
-- Name: userroles id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.userroles ALTER COLUMN id SET DEFAULT nextval('book_store_information.userroles_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.users ALTER COLUMN id SET DEFAULT nextval('book_store_information.users_id_seq'::regclass);


--
-- Data for Name: authors; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.authors (id, name) FROM stdin;
1	J.K. Rowling
2	George R.R. Martin
3	Agatha Christie
4	Stephen King
5	Isaac Asimov
\.


--
-- Data for Name: books; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.books (id, title, author_id, genre_id, price) FROM stdin;
1	Harry Potter and the Sorcerer's Stone	1	1	19.99
2	Harry Potter and the Chamber of Secrets	1	1	21.99
3	A Game of Thrones	2	1	24.99
4	A Clash of Kings	2	1	22.99
5	Murder on the Orient Express	3	3	18.50
6	The Murder of Roger Ackroyd	3	3	17.99
7	The Shining	4	4	25.00
8	It	4	4	27.50
9	Foundation	5	2	20.00
10	I, Robot	5	2	19.00
11	Harry Potter and the Prisoner of Azkaban	1	1	23.99
12	Harry Potter and the Goblet of Fire	1	1	24.99
13	Harry Potter and the Order of the Phoenix	1	1	26.99
14	Harry Potter and the Half-Blood Prince	1	1	28.99
15	Harry Potter and the Deathly Hallows	1	1	29.99
16	A Storm of Swords	2	1	23.50
17	A Feast for Crows	2	1	22.00
18	A Dance with Dragons	2	1	24.00
19	The ABC Murders	3	3	16.99
20	And Then There Were None	3	3	18.99
21	Doctor Sleep	4	4	26.00
22	Carrie	4	4	21.50
23	The Stand	4	4	29.00
24	Dune	5	2	22.50
25	Neuromancer	5	2	21.00
26	The Caves of Steel	5	2	19.50
27	The Gods Themselves	5	2	20.50
28	Salem’s Lot	4	4	24.50
29	Ender’s Game	5	2	21.99
30	Hyperion	5	2	22.99
\.


--
-- Data for Name: genres; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.genres (id, name) FROM stdin;
1	Fantasy
2	Science Fiction
3	Mystery
4	Horror
5	Thriller
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.roles (id, name) FROM stdin;
1	ADMIN
2	USER
\.


--
-- Data for Name: userroles; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.userroles (id, user_id, role_id) FROM stdin;
1	1	1
2	2	2
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: book_store_information; Owner: postgres
--

COPY book_store_information.users (id, username, password_hash) FROM stdin;
1	admin_user	$2a$12$Fc3gv7ORNmHfLNMcJZrO0u3SWqSs1/rB7G4mhi8qgB6WvP0Othv1u
2	default_user	$2a$12$h1RzqbAZlh.qUDOTGZmzCO7hbf6sFn1C6AnXdR3vDUC.z0a/ryfWO
\.


--
-- Name: authors_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.authors_id_seq', 5, true);


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.books_id_seq', 30, true);


--
-- Name: genres_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.genres_id_seq', 5, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.roles_id_seq', 2, true);


--
-- Name: userroles_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.userroles_id_seq', 2, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: book_store_information; Owner: postgres
--

SELECT pg_catalog.setval('book_store_information.users_id_seq', 2, true);


--
-- Name: authors authors_name_key; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.authors
    ADD CONSTRAINT authors_name_key UNIQUE (name);


--
-- Name: authors authors_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- Name: books books_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: genres genres_name_key; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.genres
    ADD CONSTRAINT genres_name_key UNIQUE (name);


--
-- Name: genres genres_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.genres
    ADD CONSTRAINT genres_pkey PRIMARY KEY (id);


--
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: userroles userroles_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.userroles
    ADD CONSTRAINT userroles_pkey PRIMARY KEY (id);


--
-- Name: userroles userroles_user_id_role_id_key; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.userroles
    ADD CONSTRAINT userroles_user_id_role_id_key UNIQUE (user_id, role_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_username_key; Type: CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.users
    ADD CONSTRAINT users_username_key UNIQUE (username);


--
-- Name: books books_author_id_fkey; Type: FK CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.books
    ADD CONSTRAINT books_author_id_fkey FOREIGN KEY (author_id) REFERENCES book_store_information.authors(id) ON DELETE SET NULL;


--
-- Name: books books_genre_id_fkey; Type: FK CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.books
    ADD CONSTRAINT books_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES book_store_information.genres(id) ON DELETE SET NULL;


--
-- Name: userroles userroles_role_id_fkey; Type: FK CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.userroles
    ADD CONSTRAINT userroles_role_id_fkey FOREIGN KEY (role_id) REFERENCES book_store_information.roles(id) ON DELETE CASCADE;


--
-- Name: userroles userroles_user_id_fkey; Type: FK CONSTRAINT; Schema: book_store_information; Owner: postgres
--

ALTER TABLE ONLY book_store_information.userroles
    ADD CONSTRAINT userroles_user_id_fkey FOREIGN KEY (user_id) REFERENCES book_store_information.users(id) ON DELETE CASCADE;


--
-- Name: DATABASE book_store_information; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE book_store_information TO book_store_app;


--
-- Name: SCHEMA book_store_information; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON SCHEMA book_store_information TO book_store_app;


--
-- Name: TABLE authors; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.authors TO book_store_app;


--
-- Name: SEQUENCE authors_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.authors_id_seq TO book_store_app;


--
-- Name: TABLE books; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.books TO book_store_app;


--
-- Name: SEQUENCE books_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.books_id_seq TO book_store_app;


--
-- Name: TABLE genres; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.genres TO book_store_app;


--
-- Name: SEQUENCE genres_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.genres_id_seq TO book_store_app;


--
-- Name: TABLE roles; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.roles TO book_store_app;


--
-- Name: SEQUENCE roles_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.roles_id_seq TO book_store_app;


--
-- Name: TABLE userroles; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.userroles TO book_store_app;


--
-- Name: SEQUENCE userroles_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.userroles_id_seq TO book_store_app;


--
-- Name: TABLE users; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT ALL ON TABLE book_store_information.users TO book_store_app;


--
-- Name: SEQUENCE users_id_seq; Type: ACL; Schema: book_store_information; Owner: postgres
--

GRANT SELECT,USAGE ON SEQUENCE book_store_information.users_id_seq TO book_store_app;


--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4 (Debian 17.4-1.pgdg120+2)
-- Dumped by pg_dump version 17.4 (Debian 17.4-1.pgdg120+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- PostgreSQL database dump complete
--

--
-- PostgreSQL database cluster dump complete
--

