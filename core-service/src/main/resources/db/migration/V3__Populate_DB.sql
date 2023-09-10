-- Populate Room
DELIMITER $$

CREATE PROCEDURE PopulateRooms()
BEGIN
    DECLARE counter INT DEFAULT 1;
    DECLARE maxRooms INT DEFAULT 70;

    WHILE counter <= maxRooms
        DO
            -- Insert a room record with the current counter value
            INSERT INTO room (room_number,capacity)
            VALUES (counter,FLOOR(1 + (RAND() * 4))); -- Replace 'Other Value' with your actual column values

            SET counter = counter + 1;
        END WHILE;
END $$

DELIMITER ;

-- Call the stored procedure to populate the room table
CALL PopulateRooms();

-- Populate Guest
DELIMITER $$
CREATE PROCEDURE PopulateGuestTable()
BEGIN
    DECLARE counter INT DEFAULT 1;

    WHILE counter <= 70
        DO
            INSERT INTO guest (first_name, last_name, email, phone_number)
            VALUES (CONCAT('First-Name-', counter),
                    CONCAT('Last-Name-', counter),
                    CONCAT('Guest-', counter, '@mail.com'),
                    CONCAT('+373-', counter));

            SET counter = counter + 1;
        END WHILE;
END $$
DELIMITER ;

-- Call the procedure to populate the table
CALL PopulateGuestTable();

-- Drop the procedure after use
DROP PROCEDURE PopulateGuestTable;