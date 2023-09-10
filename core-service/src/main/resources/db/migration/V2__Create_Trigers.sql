-- Trigger for check_in_date
DELIMITER $$
CREATE TRIGGER validate_check_in_date
    BEFORE INSERT
    ON reservation
    FOR EACH ROW
BEGIN
IF NEW.check_in_date < CURDATE() THEN
        SIGNAL SQLSTATE '45000'
SET MESSAGE_TEXT = 'Check-in date must be today or a future date';
END IF;
END $$

DELIMITER ;

-- Trigger for check_out_date
DELIMITER $$
CREATE TRIGGER validate_check_out_date
    BEFORE INSERT
    ON reservation
    FOR EACH ROW
BEGIN
    IF NEW.check_out_date < NEW.check_in_date THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Check-out date cannot be earlier than check-in date';
    END IF;
END $$

DELIMITER ;