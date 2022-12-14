DROP TABLE IF EXISTS USERS, CATEGORIES, EVENTS, COMPILATIONS, REQUESTS, COMPILATIONS_EVENTS CASCADE;

CREATE TABLE IF NOT EXISTS users (
                         id BIGSERIAL PRIMARY KEY NOT NULL,
                         name varchar(255) NOT NULL,
                         email varchar(512) unique NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
                          id BIGSERIAL PRIMARY KEY NOT NULL,
                          name varchar(50) unique  NOT NULL,
                          pinned varchar(10)
);

CREATE TABLE IF NOT EXISTS events (
                          id BIGSERIAL PRIMARY KEY,
                          annotation varchar(2000) NOT NULL,
                          title varchar(120) NOT NULL,
                          created_on timestamp,
                          description varchar(7000) NOT NULL,
                          event_date timestamp NOT NULL,
                          paid boolean default false,
                          participant_limit int,
                          published_on timestamp,
                          request_moderation boolean default false,
                          state varchar(20),
                          category_id bigint NOT NULL,
                          initiator_id bigint,
                          confirmed_requests int,
                          location_lat float NOT NULL,
                          location_lon float NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations (
                          id BIGSERIAL PRIMARY KEY NOT NULL,
                          title varchar(100) NOT NULL,
                          pinned boolean default false
);

CREATE TABLE IF NOT EXISTS requests (
                          id BIGSERIAL PRIMARY KEY,
                          event_id bigint,
                          requester_id bigint,
                          status varchar(20),
                          created timestamp
);

CREATE TABLE IF NOT EXISTS compilations_events (
                           event_id bigint,
                           compilation_id bigint,
                           PRIMARY KEY (event_id, compilation_id)
);

CREATE TABLE IF NOT EXISTS comments (
                           id BIGSERIAL PRIMARY KEY,
                           text varchar(200),
                           event_id bigint,
                           author_id bigint,
                           state_comment varchar(20),
                           created timestamp
);

ALTER TABLE events ADD FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE CASCADE;

ALTER TABLE events ADD FOREIGN KEY (initiator_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE requests ADD FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE;

ALTER TABLE requests ADD FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE;

ALTER TABLE compilations_events ADD FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE;

ALTER TABLE compilations_events ADD FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE;

ALTER TABLE comments ADD FOREIGN KEY (event_id) REFERENCES events (id) ON DELETE CASCADE;

ALTER TABLE comments ADD FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE;