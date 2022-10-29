CREATE TABLE "hello_world"
(
  "id" UUID NOT NULL,
  CONSTRAINT "hello_world_pk" PRIMARY KEY ("id")
)
WITH (
  OIDS=FALSE
);

COMMENT ON TABLE "hello_world" IS 'This is just a DEMO table!';
COMMENT ON COLUMN "hello_world"."id" IS 'PLEASE REWRITE OR DELETE THIS MIGRATION!';
