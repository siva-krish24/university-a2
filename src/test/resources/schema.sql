CREATE TABLE IF NOT EXISTS professor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    department VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    credits INT,
    professorId INT,
    FOREIGN KEY (professorId) REFERENCES professor (id)
);

CREATE TABLE IF NOT EXISTS course_student (
    courseId INT,
    studentId INT,
    PRIMARY KEY (courseId, studentId),
    FOREIGN KEY (courseId) REFERENCES course (id),
    FOREIGN KEY (studentId) REFERENCES student (id)
);