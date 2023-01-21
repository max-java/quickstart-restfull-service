INSERT INTO issue(issue_id, city, description, issue_status, photo, timestamp)
VALUES ('f8cf1713-e411-4558-af76-0b0d6129fef1', 'city1','description1', 'NEW', 'photo1', '2022-02-02T00:00'),
       ('f8cf1713-e411-4558-af76-0b0d6129fef2', 'city2','description2', 'NEW', 'photo2', '2022-02-02T00:00'),
       ('f8cf1713-e411-4558-af76-0b0d6129fef3', 'city3','description3', 'NEW', 'photo3', '2022-02-02T00:00'),
       ('f8cf1713-e411-4558-af76-0b0d6129fef4', 'city4','description4', 'NEW', 'photo4', '2022-02-02T00:00');

INSERT INTO customer_issues(customer_id, issue_id)
VALUES ('14ded5c9-2b65-41c8-82ec-ad6ac17e2f51', 'f8cf1713-e411-4558-af76-0b0d6129fef1'),
       ('14ded5c9-2b65-41c8-82ec-ad6ac17e2f51', 'f8cf1713-e411-4558-af76-0b0d6129fef2'),
       ('24ded5c9-2b65-41c8-82ec-ad6ac17e2f52', 'f8cf1713-e411-4558-af76-0b0d6129fef3'),
       ('24ded5c9-2b65-41c8-82ec-ad6ac17e2f52', 'f8cf1713-e411-4558-af76-0b0d6129fef4');