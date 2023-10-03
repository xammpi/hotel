ALTER TABLE reservation
    ADD COLUMN expired             BOOLEAN DEFAULT false,
    ADD COLUMN date_record_created DATETIME,
    ADD COLUMN date_record_updated DATETIME,
    ADD COLUMN created_by          VARCHAR(20),
    ADD COLUMN updated_by          VARCHAR(20);

ALTER TABLE guest
    ADD COLUMN expired             BOOLEAN DEFAULT false,
    ADD COLUMN date_record_created DATETIME,
    ADD COLUMN date_record_updated DATETIME,
    ADD COLUMN created_by          VARCHAR(20),
    ADD COLUMN updated_by          VARCHAR(20);

ALTER TABLE room
    ADD COLUMN expired             BOOLEAN DEFAULT false,
    ADD COLUMN date_record_created DATETIME,
    ADD COLUMN date_record_updated DATETIME,
    ADD COLUMN created_by          VARCHAR(20),
    ADD COLUMN updated_by          VARCHAR(20);