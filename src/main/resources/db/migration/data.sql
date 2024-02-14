USE mandacaru_broker;
INSERT INTO stock(id,symbol,company_name,price) VALUES
                                                      ('ID1', 'KK1', 'Company KK', 78.50),
                                                      ('ID2', 'LL2', 'Company LL', 95.25),
                                                      ('ID3', 'MM3', 'Company MM', 110.75),
                                                      ('ID4', 'NN4', 'Company NN', 88.00),
                                                      ('ID5', 'OO5', 'Company OO', 120.50),
                                                      ('ID6', 'PP6', 'Company PP', 65.75),
                                                      ('ID7', 'QQ7', 'Company QQ', 105.25),
                                                      ('ID8', 'RR8', 'Company RR', 80.50),
                                                      ('ID9', 'SS9', 'Company SS', 115.00),
                                                      ('ID10', 'TT0', 'Company TT', 55.75),
                                                      ('ID11', 'KK1', 'Company KK', 78.50),
                                                      ('ID12', 'LL2', 'Company LL', 95.25),
                                                      ('ID13', 'MM3', 'Company MM', 110.75),
                                                      ('ID14', 'NN4', 'Company NN', 88.00),
                                                      ('ID15', 'OO5', 'Company OO', 120.50),
                                                      ('ID16', 'PP6', 'Company PP', 65.75),
                                                      ('ID17', 'QQ7', 'Company QQ', 105.25),
                                                      ('ID18', 'RR8', 'Company RR', 80.50),
                                                      ('ID19', 'SS9', 'Company SS', 115.00),
                                                      ('ID20', 'TT0', 'Company TT', 55.75);


INSERT INTO stock(id,symbol,company_name,price)
SELECT
    CONCAT('ID', 100 + ROW_NUMBER() OVER ()) AS id,
    UPPER(CONCAT(CHAR(65 + (ROW_NUMBER() OVER () - 1) DIV 10), CHAR(65 + (ROW_NUMBER() OVER () - 1) % 26), (ROW_NUMBER() OVER () - 1) % 10)) AS symbol,
    CONCAT('Company ', CHAR(65 + (ROW_NUMBER() OVER () - 1) DIV 10), CHAR(65 + (ROW_NUMBER() OVER () - 1) % 26)) AS company_name,
    ROUND(RAND() * 150 + 50, 2) AS price
FROM information_schema.tables
LIMIT 100;

SELECT * FROM stock;

DROP TABLE stock;
DROP DATABASE mandacaru_broker;

INSERT INTO stock(id,symbol,company_name,price) VALUES ('TestID1','','Banco do Brasil',12.9);
INSERT INTO stock(id,symbol,company_name,price) VALUES ('TestID2','BB4','',12.9);
INSERT INTO stock(id,symbol,company_name) VALUES ('TestID3','BB3','Banco do Brasil');
