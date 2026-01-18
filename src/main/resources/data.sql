-- STUDENTS
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('12345', 'Simon');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('111111', 'Pesho');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('222222', 'Ceco');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('333333', 'Gosho');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('444444', 'Samantha');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('555555', 'Kapka');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('666666', 'Kristian');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('777777', 'Chavdar');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('888888', 'Vasil');
INSERT INTO `diplomation-demo`.`student` (`f_number`, `name`) VALUES ('999999', 'Roza');

-- TUTORS
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Pashovski', '0');
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Lilova', '1');
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Karadjov', '2');
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Viktorov', '3');
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Gramadov', '0');
INSERT INTO `diplomation-demo`.`uni_tutor` (`name`, `position_type`) VALUES ('Velichkoz', '1');

-- APPLICATIONS
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Solve world hunger', 'World hunger', '1', 'SQL, Java', 'Taking ozempic instead of eating', '1', '1');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Achieving world piece', 'Greedy wars', '1', 'Automation', 'Automation as a peace maker', '2', '2');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Creating wealth for pensioners', 'Low pensions', '0', 'Robots', 'Robots generating wealth for your grandma', '3', '3');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Finding the perfect technology for making sandwiches', 'No problem, just sandwich', '2', 'Cheap labour', 'What a sandwich shop helps to achieve', '3', '3');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Achieving higher happiness rate worldwide', 'Lack of motivation in the younger generations', '2', 'Smartphone ban', 'Last hope for the youngsters', '4', '4');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Inventing plastic-eating bacteria', 'Pollution', '1', 'Biology stuff', 'Solving the problem with microplastics', '5', '5');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Living as the amish', 'Sins', '1', 'Piety', 'What the amish actually have right about', '4', '4');
INSERT INTO `diplomation-demo`.`application` (`aims`, `problems`, `status`, `technologies`, `topic`, `student_id`, `uni_tutor_id`)
VALUES ('Bettering reading comprehension among students', 'Lack of a good school program for the subject of logic', '1', 'Logic', 'A Logic book', '6', '4');

-- THESES
INSERT INTO `diplomation-demo`.`thesis` (`date_of_submission`, `text`, `title`, `application_id`)
VALUES ('2025-02-01', 'Though ozempic created a bunch of problems, it suppressed the hunger of the high class. Let us make it available to yhe public and see what happens! I bet it will solve world hunger.', 'Solving World hunger with pills', '1');
INSERT INTO `diplomation-demo`.`thesis` (`date_of_submission`, `text`, `title`, `application_id`)
VALUES ('2024-03-25', 'Since AI is becoming more intelligent every day, soon enough it will become sentient, start ruling instead of humans. If that proves true, there would no longer be greedy people starting wars. Instead, AI, if programmed ethically, would strive towards creating long-term solutions for us and therefore eliminating the need and hunger for war.', 'Automating peace', '2');
INSERT INTO `diplomation-demo`.`thesis` (`date_of_submission`, `text`, `title`, `application_id`)
VALUES ('2023-03-04', 'We have created a species of bacteria that eats plastics and turns it into biodegradable material, that by the way, could be later transformed into energy.', 'Microplastics eating bacteria', '6');
INSERT INTO `diplomation-demo`.`thesis` (`date_of_submission`, `text`, `title`, `application_id`)
VALUES ('2025-02-22', 'We decided to make an experiment living among the amish for three months. Living in such a manner actually reduces the amount of stress on a daily basis. Highly recommend.', 'The amish lifestyle', '7');
INSERT INTO `diplomation-demo`.`thesis` (`date_of_submission`, `text`, `title`, `application_id`)
VALUES ('2025-01-02', '*This is a book about practical logic*', 'Practical logic', '8');

-- REVIEWS
INSERT INTO `diplomation-demo`.`review` (`conclusion`, `date_of_submission`, `is_passed`, `text`, `reviewer_id`, `thesis_id`)
VALUES ('Love it!', '2025-05-02', TRUE, 'just yes.', '2', '1');
INSERT INTO `diplomation-demo`.`review` (`conclusion`, `date_of_submission`, `is_passed`, `text`, `reviewer_id`, `thesis_id`)
VALUES ('That aint it.', '2024-05-01', FALSE, 'just no.', '1', '2');
INSERT INTO `diplomation-demo`.`review` (`conclusion`, `date_of_submission`, `is_passed`, `text`, `reviewer_id`, `thesis_id`)
VALUES ('Revolutionary!', '2023-05-15', TRUE, 'This is significant work.', '4', '3');
INSERT INTO `diplomation-demo`.`review` (`conclusion`, `date_of_submission`, `is_passed`, `text`, `reviewer_id`, `thesis_id`)
VALUES ('Exceptional!', '2025-05-11', TRUE, 'no', '4', '4');
INSERT INTO `diplomation-demo`.`review` (`conclusion`, `date_of_submission`, `is_passed`, `text`, `reviewer_id`, `thesis_id`)
VALUES ('Really?', '2025-05-10', FALSE, 'It is too lazy to use Gen Z humour in a graduation thesis.', '4', '5');

-- DEFENSES
INSERT INTO `diplomation-demo`.`defense` (`date`, `grade`, `review_id`) VALUES ('2025-05-22', '5', '1');
INSERT INTO `diplomation-demo`.`defense` (`date`, `grade`, `review_id`) VALUES ('2023-05-25', '6', '3');
INSERT INTO `diplomation-demo`.`defense` (`date`, `grade`, `review_id`) VALUES ('2025-05-01', '5', '4');