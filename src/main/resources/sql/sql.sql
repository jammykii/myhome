ALTER TABLE `board` AUTO_INCREMENT=1;
SET @COUNT = 0;
UPDATE `board` SET id = @COUNT:=@COUNT+1;

SELECT * FROM board b, `type` t WHERE b.board_type = t.type_id WHERE b.id = ?

SELECT * from role r, `user` u WHERE u.id = ur.user_id

INSERT INTO user_role VALUES(2, 3);

DELETE FROM user_role WHERE user_id = 5 AND role_id = 2

SELECT ur.user_id, u.username, ur.role_id, r.name FROM user_role ur, `user` u, `role` r WHERE ur.user_id = u.id AND ur.role_id = r.id

SELECT ROW_NUMBER() over (ORDER BY COUNT(b.id) desc) AS Rank, u.id AS id, u.username AS username, COUNT(b.id) AS count FROM board b, `user` u WHERE b.user_id = u.id GROUP BY u.id, u.username ORDER BY 1 asc


DELETE FROM user_role WHERE user_id = 20

