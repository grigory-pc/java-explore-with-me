DROP TABLE IF EXISTS USERS, CATEGORIES, EVENTS, COMPILATIONS, REQUESTS, COMPILATIONS_EVENTS;

CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE IF NOT EXISTS "users" (
                         "id" BIGSERIAL PRIMARY KEY,
                         "name" varchar(50),
                         "email" varchar(50) unique
);

CREATE TABLE IF NOT EXISTS "categories" (
                              "id" BIGSERIAL PRIMARY KEY,
                              "name" varchar(50) unique ,
                              "pinned" varchar(10)
);

CREATE TABLE IF NOT EXISTS "events" (
                          "id" BIGSERIAL PRIMARY KEY,
                          "annotation" varchar(500),
                          "title" varchar(100),
                          "created_on" timestamp,
                          "description" varchar(1000),
                          "event_date" timestamp,
                          "paid" varchar(10),
                          "participant_limit" int,
                          "published_on" timestamp,
                          "request_moderation" varchar(10),
                          "state" varchar(20),
                          "category_id" bigint,
                          "initiator_id" bigint,
                          "views" int,
                          "confirmed_requests" int
--                           "location" varchar
);

CREATE TABLE IF NOT EXISTS "compilations" (
                                "id" BIGSERIAL PRIMARY KEY,
                                "title" varchar(100),
                                "pinned" varchar(10)
);

CREATE TABLE IF NOT EXISTS "requests" (
                            "id" BIGSERIAL PRIMARY KEY,
                            "events_id" bigint,
                            "requester_id" bigint,
                            "status" varchar(20),
                            "created" timestamp
);

CREATE TABLE IF NOT EXISTS "compilations_events" (
                                       "events_id" bigint,
                                       "compilation_id" bigint,
                                       PRIMARY KEY ("events_id", "compilation_id")
);

ALTER TABLE "events" ADD FOREIGN KEY ("category_id") REFERENCES "categories" ("id") ON DELETE CASCADE;

ALTER TABLE "events" ADD FOREIGN KEY ("initiator_id") REFERENCES "users" ("id") ON DELETE CASCADE;

ALTER TABLE "requests" ADD FOREIGN KEY ("events_id") REFERENCES "events" ("id") ON DELETE CASCADE;

ALTER TABLE "requests" ADD FOREIGN KEY ("requester_id") REFERENCES "users" ("id") ON DELETE CASCADE;

ALTER TABLE "compilations_events" ADD FOREIGN KEY ("events_id") REFERENCES "events" ("id") ON DELETE CASCADE;

ALTER TABLE "compilations_events" ADD FOREIGN KEY ("compilation_id") REFERENCES "compilations" ("id") ON DELETE CASCADE;