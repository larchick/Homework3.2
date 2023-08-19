-- liquibase formatted sql

-- changeset larchick:1
CREATE INDEX students_name ON student (name)

-- changeset larchick:2
CREATE INDEX faculties_name_and_color ON faculty (name, color)