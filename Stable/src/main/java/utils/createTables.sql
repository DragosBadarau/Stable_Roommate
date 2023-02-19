DECLARE
    v_count INT;
BEGIN
    SELECT COUNT (*) INTO v_count FROM user_tables WHERE table_name in (UPPER('students'), UPPER('rooms'));
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE 'CREATE TABLE students (
                                                     id   NUMBER(10) PRIMARY KEY,
                                                     name VARCHAR2(50) NOT NULL,
                                                     punctaj NUMBER(10) NOT NULL,
                                                     gen VARCHAR(1) NOT NULL,
                                                     P1 NUMBER(10) NOT NULL,
                                                     P2 NUMBER(10) NOT NULL,
                                                     P3 NUMBER(10) NOT NULL,
                                                     P4 NUMBER(10) NOT NULL,
                                                     P5 NUMBER(10) NOT NULL)';
        EXECUTE IMMEDIATE 'CREATE TABLE rooms (
                                                  id_room   NUMBER(10) PRIMARY KEY,
                                                  name_room VARCHAR2(50) NOT NULL,
                                                  s1 NUMBER(10) NOT NULL,
                                                  s2 NUMBER(10) NOT NULL,
                                                  gen VARCHAR(1) NOT NULL)';
    END IF;
END;