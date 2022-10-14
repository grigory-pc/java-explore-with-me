DROP TABLE IF EXISTS STATISTICS;

CREATE TABLE statistics (
                              id SERIAL PRIMARY KEY,
                              app varchar(50),
                              uri varchar(50),
                              ip varchar(15),
                              timestamp timestamp
);
