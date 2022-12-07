CREATE TABLE course (id bigint NOT NULL, PRIMARY KEY (id));
CREATE TABLE student (id bigint NOT NULL, PRIMARY KEY (id));

CREATE TABLE course_like (student_id bigint NOT NULL, course_id bigint NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES student (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);

CREATE TABLE course_rating (course_id bigint NOT NULL, student_id bigint NOT NULL, rating int NOT NULL,
  PRIMARY KEY (course_id, student_id),
  FOREIGN KEY (student_id) REFERENCES student (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);


CREATE TABLE course_registration (id bigint NOT NULL, grade int, registered_at TIMESTAMP NOT NULL,
  course_id bigint NOT NULL, student_id bigint NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (student_id) REFERENCES student (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);
