-- ========================
-- User
-- ========================
INSERT INTO user (id, prenom, name, email, gender)
VALUES (1, 'Finn', 'Schutt', 'fschutt0@dailymail.co.uk', 'Male'),
       (2, 'Martynne', 'Cauldfield', 'mcauldfield1@ebay.co.uk', 'Female'),
       (3, 'Torre', 'Caldecutt', 'tcaldecutt2@clickbank.net', 'Female'),
       (4, 'Filmore', 'Perryman', 'fperryman3@qq.com', 'Male'),
       (5, 'Fran', 'Guerrieri', 'fguerrieri4@plala.or.jp', 'Female'),
       (6, 'Lesya', 'Drejer', 'ldrejer5@va.gov', 'Female'),
       (7, 'Gideon', 'Richley', 'grichley6@mapy.cz', 'Male'),
       (8, 'Gloriane', 'Pharo', 'gpharo7@opensource.org', 'Female'),
       (9, 'Amitie', 'Lazenby', 'alazenby8@google.it', 'Female'),
       (10, 'Sean', 'Whitehorne', 'swhitehorne9@blogspot.com', 'Male'),
       (11, 'Shauna', 'Fritschmann', 'sfritschmanna@geocities.jp', 'Female'),
       (12, 'Cooper', 'Cato', 'ccatob@example.com', 'Male'),
       (13, 'Melli', 'Wickham', 'mwickhamc@npr.org', 'Male'),
       (14, 'Prinz', 'Bondley', 'pbondleyd@cbsnews.com', 'Male'),
       (15, 'Suzanne', 'Shilito', 'sshilitoe@state.tx.us', 'Female'),
       (16, 'Alvan', 'Kneafsey', 'akneafseyf@paypal.com', 'Male'),
       (17, 'Dayle', 'Cobden', 'dcobdeng@desdev.cn', 'Female'),
       (18, 'Haroun', 'Riddles', 'hriddlesh@samsung.com', 'Male'),
       (19, 'Abner', 'Mabbett', 'amabbetti@istockphoto.com', 'Male'),
       (20, 'Augustin', 'Santarelli', 'asantarellij@360.cn', 'Male'),
       (21, 'Michaela', 'Hordle', 'mhordlek@constantcontact.com', 'Female'),
       (22, 'Gwenore', 'Wannes', 'gwannesl@hostgator.com', 'Female'),
       (23, 'Tiffanie', 'Pitherick', 'tpitherickm@hatena.ne.jp', 'Male'),
       (24, 'Becca', 'Mortimer', 'bmortimern@slate.com', 'Female'),
       (25, 'Oralle', 'Smallpiece', 'osmallpieceo@zdnet.com', 'Female'),
       (26, 'Max', 'Houliston', 'mhoulistonp@amazonaws.com', 'Female'),
       (27, 'Brandy', 'Boast', 'bboastq@ovh.net', 'Male'),
       (28, 'Terrie', 'Layson', 'tlaysonr@netlog.com', 'Female'),
       (29, 'Hobie', 'Tomalin', 'htomalins@businesswire.com', 'Male'),
       (30, 'Culver', 'Muldownie', 'cmuldowniet@mozilla.com', 'Male');

-- ========================
-- Series
-- ========================
INSERT INTO series (id, titre, genre, nb_episodes, note)
VALUES (1, 'Breaking Bad', 'Drame', 62, 9.5),
       (2, 'Stranger Things', 'Science-fiction', 42, 8.7),
       (3, 'Game of Thrones', 'Fantasy', 73, 9.2),
       (4, 'The Office', 'Comédie', 201, 8.9),
       (5, 'Friends', 'Comédie', 236, 8.8),
       (6, 'Sherlock', 'Policier', 13, 9.1),
       (7, 'Peaky Blinders', 'Drame', 36, 8.8),
       (8, 'The Crown', 'Historique', 60, 8.6),
       (9, 'Black Mirror', 'Anthologie', 27, 8.8),
       (10, 'The Mandalorian', 'Science-fiction', 24, 8.7),
       (11, 'Better Call Saul', 'Drame', 63, 8.9),
       (12, 'The Witcher', 'Fantasy', 24, 8.1),
       (13, 'Dark', 'Science-fiction', 26, 8.7),
       (14, 'The Boys', 'Super-héros', 32, 8.8),
       (15, 'Money Heist', 'Thriller', 41, 8.2),
       (16, 'Narcos', 'Drame', 30, 8.8),
       (17, 'Vikings', 'Historique', 89, 8.5),
       (18, 'Lost', 'Mystère', 121, 8.3),
       (19, 'Prison Break', 'Thriller', 90, 8.3),
       (20, 'How I Met Your Mother', 'Comédie', 208, 8.3),
       (21, 'Brooklyn Nine-Nine', 'Comédie', 153, 8.4),
       (22, 'House of the Dragon', 'Fantasy', 20, 8.5),
       (23, 'The Last of Us', 'Post-apocalyptique', 9, 8.9),
       (24, 'Succession', 'Drame', 39, 8.9),
       (25, 'True Detective', 'Policier', 32, 8.9),
       (26, 'Severance', 'Thriller', 9, 8.7),
       (27, 'The Expanse', 'Science-fiction', 62, 8.5),
       (28, 'Arcane', 'Animation', 9, 9.0),
       (29, 'Mr. Robot', 'Thriller', 45, 8.6),
       (30, 'Rick and Morty', 'Animation', 61, 9.1);

-- ========================
-- History (table de jointure ManyToMany)
-- ========================
-- Chaque personne regarde plusieurs séries, et plusieurs personnes regardent la même série
INSERT INTO history (user_id, serie_id) VALUES
-- Personne 1
(1,1),(1,2),(1,3),
-- Personne 2
(2,2),(2,5),(2,10),
-- Personne 3
(3,3),(3,4),(3,6),
-- Personne 4
(4,1),(4,7),(4,8),
-- Personne 5
(5,5),(5,9),(5,20),
-- Personne 6
(6,6),(6,12),(6,15),
-- Personne 7
(7,7),(7,13),(7,16),
-- Personne 8
(8,8),(8,14),(8,18),
-- Personne 9
(9,9),(9,19),(9,21),
-- Personne 10
(10,10),(10,11),(10,22),
-- Personne 11
(11,11),(11,17),(11,23),
-- Personne 12
(12,12),(12,24),(12,25),
-- Personne 13
(13,13),(13,26),(13,27),
-- Personne 14
(14,14),(14,28),(14,29),
-- Personne 15
(15,15),(15,30),(15,1),
-- Personne 16
(16,16),(16,2),(16,5),
-- Personne 17
(17,17),(17,6),(17,8),
-- Personne 18
(18,18),(18,3),(18,7),
-- Personne 19
(19,19),(19,4),(19,9),
-- Personne 20
(20,20),(20,10),(20,11),
-- Personne 21
(21,21),(21,12),(21,13),
-- Personne 22
(22,22),(22,14),(22,15),
-- Personne 23
(23,23),(23,16),(23,17),
-- Personne 24
(24,24),(24,18),(24,19),
-- Personne 25
(25,25),(25,20),(25,21),
-- Personne 26
(26,26),(26,22),(26,23),
-- Personne 27
(27,27),(27,24),(27,25),
-- Personne 28
(28,28),(28,26),(28,29),
-- Personne 29
(29,29),(29,27),(29,30),
-- Personne 30
(30,30),(30,28),(30,1);
